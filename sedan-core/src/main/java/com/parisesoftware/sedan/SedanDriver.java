package com.parisesoftware.sedan;

import com.parisesoftware.sedan.data.SedanDataFactory;
import com.parisesoftware.sedan.data.SedanDataSource;
import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.MoveSedanOperation;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Main entry point for the Sedan diff algorithm.
 * Returns the minimum set of {@link SedanOperation} instances needed to transform source into target.
 */
public class SedanDriver {

    public List<SedanOperation> difference(Map<?, ?> source, Map<?, ?> target) {
        var sourceData = SedanDataFactory.construct(source);
        var targetData = SedanDataFactory.construct(target);
        var result = new ArrayList<SedanOperation>();
        var pendingDeletes = new LinkedHashMap<String, Object>();
        var pendingAdds = new LinkedHashMap<String, Object>();

        for (var key : sourceData.getAllKeys()) {
            if (!targetData.hasValue(key)) {
                pendingDeletes.put(key, sourceData.getObjectValue(key));
            } else if (hasDifferentValueAtKey(sourceData, targetData, key)) {
                result.add(new UpdateSedanOperation(key, targetData.getObjectValue(key)));
            }
        }

        for (var key : targetData.getAllKeys()) {
            if (!sourceData.hasValue(key)) {
                pendingAdds.put(key, targetData.getObjectValue(key));
            }
        }

        var addsByValue = new LinkedHashMap<Object, String>();
        pendingAdds.forEach((k, v) -> addsByValue.putIfAbsent(v, k));

        for (var entry : pendingDeletes.entrySet()) {
            var matchingAddKey = addsByValue.remove(entry.getValue());
            if (matchingAddKey != null) {
                result.add(new MoveSedanOperation(entry.getKey(), matchingAddKey, entry.getValue()));
                pendingAdds.remove(matchingAddKey);
            } else {
                result.add(new DeletionSedanOperation(entry.getKey()));
            }
        }

        pendingAdds.forEach((k, v) -> result.add(new AdditionSedanOperation(k, v)));

        return List.copyOf(result);
    }

    boolean hasDifferentValueAtKey(SedanDataSource source, SedanDataSource target, String key) {
        return !Objects.equals(source.getObjectValue(key), target.getObjectValue(key));
    }

    public DiffResult diff(Map<?, ?> source, Map<?, ?> target) {
        return new DiffResult(difference(source, target));
    }

    public Map<String, Object> apply(Map<?, ?> source, List<SedanOperation> operations) {
        var result = new LinkedHashMap<String, Object>();
        source.forEach((k, v) -> result.put(String.valueOf(k), v));
        for (var op : operations) {
            switch (op) {
                case AdditionSedanOperation add -> result.put(add.name(), add.value());
                case UpdateSedanOperation update -> result.put(update.name(), update.value());
                case DeletionSedanOperation delete -> result.remove(delete.name());
                case MoveSedanOperation move -> {
                    result.remove(move.sourceName());
                    result.put(move.targetName(), move.value());
                }
            }
        }
        return Collections.unmodifiableMap(result);
    }
}
