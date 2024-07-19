package com.springfulldemo.api.infrastructure.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.springfulldemo.api.utils.StringUtil;

import java.io.IOException;

public class EmptyStringAsNullDeserializer extends StdDeserializer<String> {

    public EmptyStringAsNullDeserializer() {
        this(null);
    }

    public EmptyStringAsNullDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = jp.getValueAsString();

        if (StringUtil.isNullOrEmpty(value)) {
            return null;
        }

        return value;
    }
}
