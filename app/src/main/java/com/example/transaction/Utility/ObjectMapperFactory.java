package com.example.transaction.Utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectMapperFactory {

    private Factory factory;

    public ObjectMapperFactory() {
        factory = new SingletonFactory();
    }

    public ObjectMapperFactory(Factory factory) {
        this.factory = factory;
    }

    public ObjectMapper getObjectMapper() {
        return factory.createInstance();
    }

    private static class SingletonFactory implements Factory {

        private ObjectMapper mapper = configure(new ObjectMapper());

        private static ObjectMapper configure(ObjectMapper mapper) {
            mapper.setConfig(mapper.getDeserializationConfig()
                            .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            );

            mapper.setConfig(mapper.getSerializationConfig()
                            .with(JsonGenerator.Feature.IGNORE_UNKNOWN)
            );

            return mapper;
        }

        public ObjectMapper createInstance() {
            return mapper;
        }
    }

    public interface Factory {
        ObjectMapper createInstance();
    }
}