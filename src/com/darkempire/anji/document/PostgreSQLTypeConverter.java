package com.darkempire.anji.document;

import com.darkempire.anji.annotation.AnjiUnknow;

import java.util.HashMap;
import java.util.Map;

/**
 * Create in 12:59
 * Created by siredvin on 08.04.14.
 */
@AnjiUnknow
public class PostgreSQLTypeConverter implements TypeConverter {
    private static Map<String, String> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put("int4", "com.darkempire.anjifx.beans.property.AnjiIntegerProperty");
        typeMap.put("bool", "com.darkempire.anjifx.beans.property.AnjiBooleanProperty");
        typeMap.put("char", "com.darkempire.anjifx.beans.property.AnjiStringProperty");
        typeMap.put("date", "com.darkempire.anjifx.beans.property.AnjiSQLDateProperty");
    }

    @Override
    public String convert(String afterType) {
        return typeMap.get(afterType);
    }
}
