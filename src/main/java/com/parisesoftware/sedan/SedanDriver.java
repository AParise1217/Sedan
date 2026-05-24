package com.parisesoftware.sedan;

import com.parisesoftware.sedan.data.SedanDataFactory;
import com.parisesoftware.sedan.data.SedanDataSource;
import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.util.ArrayList;
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

        for (var key : sourceData.getAllKeys()) {
            if (!targetData.hasValue(key)) {
                result.add(new DeletionSedanOperation(key));
            } else if (hasDifferentValueAtKey(sourceData, targetData, key)) {
                result.add(new UpdateSedanOperation(key, targetData.getStringValue(key)));
            }
        }

        for (var key : targetData.getAllKeys()) {
            if (!sourceData.hasValue(key)) {
                result.add(new AdditionSedanOperation(key, targetData.getStringValue(key)));
            }
        }

        return List.copyOf(result);
    }

    boolean hasDifferentValueAtKey(SedanDataSource source, SedanDataSource target, String key) {
        return !Objects.equals(source.getStringValue(key), target.getStringValue(key));
    }
}
