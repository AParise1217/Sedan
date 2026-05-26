package com.parisesoftware.sedan.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.parisesoftware.sedan.operation.SedanOperation;

/**
 * Jackson module that registers serializers and deserializers for the Sedan operation hierarchy.
 *
 * Register with ObjectMapper:
 * <pre>{@code
 *   objectMapper.registerModule(new SedanModule());
 * }</pre>
 */
public class SedanModule extends SimpleModule {

    public SedanModule() {
        super("SedanModule");
        addSerializer(SedanOperation.class, new SedanOperationSerializer());
        addDeserializer(SedanOperation.class, new SedanOperationDeserializer());
    }
}
