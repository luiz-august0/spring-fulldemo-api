package com.springfulldemo.api.infrastructure.deserializers;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class EmptyStringAsNullModule extends SimpleModule {

    public EmptyStringAsNullModule() {
        addDeserializer(String.class, new EmptyStringAsNullDeserializer());
    }
}
