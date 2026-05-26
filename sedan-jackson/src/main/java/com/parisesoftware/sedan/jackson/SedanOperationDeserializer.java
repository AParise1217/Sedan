package com.parisesoftware.sedan.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.MoveSedanOperation;
import com.parisesoftware.sedan.operation.OperationType;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.io.IOException;

public class SedanOperationDeserializer extends StdDeserializer<SedanOperation> {

    public SedanOperationDeserializer() {
        super(SedanOperation.class);
    }

    @Override
    public SedanOperation deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        OperationType type = OperationType.valueOf(node.get("type").asText());
        return switch (type) {
            case ADD -> new AdditionSedanOperation(
                    node.get("name").asText(),
                    rawValue(node.get("value")));
            case UPDATE -> new UpdateSedanOperation(
                    node.get("name").asText(),
                    rawValue(node.get("value")));
            case DELETE -> new DeletionSedanOperation(node.get("name").asText());
            case MOVE -> new MoveSedanOperation(
                    node.get("sourceName").asText(),
                    node.get("targetName").asText(),
                    rawValue(node.get("value")));
        };
    }

    private static Object rawValue(JsonNode node) {
        if (node == null || node.isNull()) return null;
        if (node.isInt())     return node.intValue();
        if (node.isLong())    return node.longValue();
        if (node.isDouble())  return node.doubleValue();
        if (node.isBoolean()) return node.booleanValue();
        return node.asText();
    }
}
