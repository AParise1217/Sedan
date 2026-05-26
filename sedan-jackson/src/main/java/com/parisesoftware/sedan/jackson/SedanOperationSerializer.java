package com.parisesoftware.sedan.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.parisesoftware.sedan.operation.AdditionSedanOperation;
import com.parisesoftware.sedan.operation.DeletionSedanOperation;
import com.parisesoftware.sedan.operation.MoveSedanOperation;
import com.parisesoftware.sedan.operation.SedanOperation;
import com.parisesoftware.sedan.operation.UpdateSedanOperation;

import java.io.IOException;

public class SedanOperationSerializer extends StdSerializer<SedanOperation> {

    public SedanOperationSerializer() {
        super(SedanOperation.class);
    }

    @Override
    public void serialize(SedanOperation op, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", op.type().name());
        switch (op) {
            case AdditionSedanOperation add -> {
                gen.writeStringField("name", add.name());
                gen.writeObjectField("value", add.value());
            }
            case UpdateSedanOperation update -> {
                gen.writeStringField("name", update.name());
                gen.writeObjectField("value", update.value());
            }
            case DeletionSedanOperation delete ->
                gen.writeStringField("name", delete.name());
            case MoveSedanOperation move -> {
                gen.writeStringField("sourceName", move.sourceName());
                gen.writeStringField("targetName", move.targetName());
                gen.writeObjectField("value", move.value());
            }
        }
        gen.writeEndObject();
    }
}
