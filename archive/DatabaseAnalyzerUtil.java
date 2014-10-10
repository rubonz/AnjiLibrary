package com.darkempire.anji.database;

import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.log.Log;
import com.darkempire.anji.annotation.AnjiUtil;
import com.darkempire.anji.util.Util;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Create in 15:45
 * Created by siredvin on 14.04.14.
 */
@AnjiUtil
@AnjiUnknow
public final class DatabaseAnalyzerUtil {

    private DatabaseAnalyzerUtil() {
    }

    public static final AtomicBoolean isShowPrimaryKey = new AtomicBoolean(false);

    public static Collection<Field> getTableFields(String tableId, DatabaseAnalyzer analyzer) {
        return getTableFields(analyzer.getTable(tableId));
    }

    public static Collection<Field> getTableFields(DatabaseAnalyzer.DBTable_impl dbTable) {
        Collection<Field> fields = new ArrayList<>();
        if (isShowPrimaryKey.get()) {
            fields.addAll(dbTable.attributeList.stream().map(attr -> attr.field).collect(Collectors.toList()));
        } else {
            fields.addAll(dbTable.attributeList.stream().filter(attr -> !attr.isPrimaryKey).map(attr -> attr.field).collect(Collectors.toList()));
        }
        return fields;
    }

    public static String createSelectFromDBTable(DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl... dbAttributes) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (dbAttributes == null || dbAttributes.length == 0) {
            builder.append("* ");
        } else {
            for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbAttributes) {
                builder.append(dbAttribute.name);
                builder.append(",");
            }
            Util.removeLastChar(builder);
        }
        builder.append(" FROM ");
        builder.append(dbTable.name);
        builder.append(";");
        return builder.toString();
    }

    public static String createSelectWithWhere(DatabaseAnalyzer.DBTable_impl dbTable, List<DatabaseAnalyzer.DBAttribute_impl> primaryKeys, List<DatabaseAnalyzer.DBAttribute_impl> primarySuperKeys, Object o) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ");
        builder.append(dbTable.name);
        builder.append(" WHERE ");
        int size = primaryKeys.size();
        for (int i = 0; i < size; i++) {
            builder.append('(');
            builder.append(primarySuperKeys.get(i).name);
            builder.append("=");
            builder.append(((AbstractAnjiProperty) primaryKeys.get(i).field.get(o)).getValue());
            builder.append(") AND ");
        }
        Util.removeLastChars(builder, 5);
        return builder.toString();
    }

    public static String createSelectFromDBTable(DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> dbAttributes) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (dbAttributes == null || dbAttributes.size() == 0) {
            builder.append("* ");
        } else {
            for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbAttributes) {
                builder.append(dbAttribute.name);
                builder.append(",");
            }
            Util.removeLastChar(builder);
        }
        builder.append(" FROM ");
        builder.append(dbTable.name);
        builder.append(";");
        return builder.toString();
    }

    public static String createSelectFromDBTable(DatabaseAnalyzer.DBTable_impl dbTable, Collection<DatabaseAnalyzer.DBAttribute_impl> dbAttributes, String name, String op, Object value) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (dbAttributes == null || dbAttributes.size() == 0) {
            builder.append("* ");
        } else {
            for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbAttributes) {
                builder.append(dbAttribute.name);
                builder.append(",");
            }
            Util.removeLastChar(builder);
        }
        builder.append(" FROM ");
        builder.append(dbTable.name);
        builder.append(" WHERE ");
        DatabaseAnalyzer.DBAttribute_impl dbAttribute = dbTable.findAttributeByName(name);
        builder.append(name);
        if (dbAttribute.type.equals("char") && op.equals("=")) {
            builder.append(" LIKE ");
            builder.append('\'');
            builder.append(value.toString());
            builder.append("%'");
        } else {
            builder.append(op);
            if (dbAttribute.type.equals("char") || dbAttribute.type.equals("date")) {
                builder.append('\'');
                builder.append(value);
                builder.append('\'');
            } else {
                builder.append(value);
            }
        }
        builder.append(";");
        return builder.toString();
    }

    public static String createPrepareUpdateStatement(DatabaseAnalyzer.DBTable_impl dbTable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ");
        stringBuilder.append(dbTable.name);
        stringBuilder.append(" SET ");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append(dbAttribute.name);
            stringBuilder.append("=?,");
        }
        Util.removeLastChar(stringBuilder);
        stringBuilder.append(" WHERE ");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (!dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append("(");
            stringBuilder.append(dbAttribute.name);
            stringBuilder.append("=?) AND ");
        }
        Util.removeLastChars(stringBuilder, 4);
        stringBuilder.append(";");
        Log.log(Log.debugIndex, stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String createPrepareStatement(DatabaseAnalyzer.DBTable_impl dbTable, String... returnRows) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(dbTable.name);
        stringBuilder.append(" (");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append(dbAttribute.name);
            stringBuilder.append(",");
        }
        Util.removeLastChar(stringBuilder);
        stringBuilder.append(" ) VALUES ");
        stringBuilder.append(" (");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append("?,");
        }
        Util.removeLastChar(stringBuilder);
        stringBuilder.append(")");
        if (returnRows.length != 0) {
            stringBuilder.append(" RETURNING ");
            for (String s : returnRows) {
                stringBuilder.append(s);
                stringBuilder.append(',');
            }
            Util.removeLastChar(stringBuilder);
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public static String createPrepareStatement(DatabaseAnalyzer.DBTable_impl dbTable, Collection<String> returnRows) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(dbTable.name);
        stringBuilder.append(" (");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append(dbAttribute.name);
            stringBuilder.append(",");
        }
        Util.removeLastChar(stringBuilder);
        stringBuilder.append(" ) VALUES ");
        stringBuilder.append(" (");
        for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                continue;
            stringBuilder.append("?,");
        }
        Util.removeLastChar(stringBuilder);
        stringBuilder.append(")");
        if (returnRows.size() != 0) {
            stringBuilder.append(" RETURNING ");
            for (String s : returnRows) {
                stringBuilder.append(s);
                stringBuilder.append(',');
            }
            Util.removeLastChar(stringBuilder);
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    public static PreparedStatement fillPrepareStatement(DatabaseAnalyzer.DBTable_impl dbTable, PreparedStatement statement, Object... objects) {
        try {
            for (Object o : objects) {
                if (o.getClass() == dbTable.cl) {
                    int i = 1;
                    for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
                        if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                            continue;
                        AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                        switch (property.getType()) {
                            case INT_TYPE:
                                statement.setInt(i, (Integer) property.getValue());
                                break;
                            case SQLDATE_TYPE:
                                statement.setDate(i, (java.sql.Date) property.getValue());
                                break;
                            case STRING_TYPE:
                                statement.setString(i, (String) property.getValue());
                                break;
                            case BOOLEAN_TYPE:
                                statement.setBoolean(i, (Boolean) property.getValue());
                                break;
                        }
                        i++;
                    }
                    statement.addBatch();
                }
            }
        } catch (IllegalAccessException | SQLException e) {
            Log.error(Log.coreIndex, e);
        }
        return statement;
    }

    public static PreparedStatement fillPrepareStatement(Collection<DatabaseAnalyzer.DBAttribute_impl> collection, DatabaseAnalyzer.DBTable_impl dbTable, PreparedStatement statement, Object... objects) {
        try {
            for (Object o : objects) {
                if (o.getClass() == dbTable.cl) {
                    int i = 1;
                    for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : collection) {
                        if (!isShowPrimaryKey.get() && dbAttribute.isPrimaryKey)
                            continue;
                        AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                        switch (property.getType()) {
                            case INT_TYPE:
                                statement.setInt(i, (Integer) property.getValue());
                                break;
                            case SQLDATE_TYPE:
                                statement.setDate(i, (java.sql.Date) property.getValue());
                                break;
                            case STRING_TYPE:
                                statement.setString(i, (String) property.getValue());
                                break;
                            case BOOLEAN_TYPE:
                                statement.setBoolean(i, (Boolean) property.getValue());
                                break;
                        }
                        i++;
                    }
                    statement.addBatch();
                }
            }
        } catch (IllegalAccessException | SQLException e) {
            Log.error(Log.coreIndex, e);
        }
        return statement;
    }


    public static PreparedStatement fillPrepareUpdateStatement(DatabaseAnalyzer.DBTable_impl dbTable, PreparedStatement statement, Object... objects) {
        try {
            for (Object o : objects) {
                if (o.getClass() == dbTable.cl) {
                    int i = 1;
                    for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
                        if (dbAttribute.isPrimaryKey)
                            continue;
                        AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                        switch (property.getType()) {
                            case INT_TYPE:
                                statement.setInt(i, (Integer) property.getValue());
                                break;
                            case SQLDATE_TYPE:
                                statement.setDate(i, (java.sql.Date) property.getValue());
                                break;
                            case STRING_TYPE:
                                statement.setString(i, (String) property.getValue());
                                break;
                            case BOOLEAN_TYPE:
                                statement.setBoolean(i, (Boolean) property.getValue());
                                break;
                        }
                        i++;
                    }
                    for (DatabaseAnalyzer.DBAttribute_impl dbAttribute : dbTable.attributeList) {
                        if (!dbAttribute.isPrimaryKey)
                            continue;
                        AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                        switch (property.getType()) {
                            case INT_TYPE:
                                statement.setInt(i, (Integer) property.getValue());
                                break;
                            case SQLDATE_TYPE:
                                statement.setDate(i, (java.sql.Date) property.getValue());
                                break;
                            case STRING_TYPE:
                                statement.setString(i, (String) property.getValue());
                                break;
                            case BOOLEAN_TYPE:
                                statement.setBoolean(i, (Boolean) property.getValue());
                                break;
                        }
                        i++;
                    }
                    statement.addBatch();
                }
            }
        } catch (IllegalAccessException | SQLException e) {
            Log.error(Log.coreIndex, e);
        }
        return statement;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> fillObjects(ResultSet resultSet, DatabaseAnalyzer.DBTable_impl dbTable, DatabaseAnalyzer.DBAttribute_impl... dbAttributes) {
        List<Object> list = new ArrayList<>();
        try {
            int size = dbAttributes.length;
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                for (int i = 0; i < size; i++) {
                    DatabaseAnalyzer.DBAttribute_impl dbAttribute = dbAttributes[i];
                    AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                    switch (property.getType()) {
                        case INT_TYPE:
                            property.setValue(resultSet.getInt(i + 1));
                            break;
                        case SQLDATE_TYPE:
                            property.setValue(resultSet.getDate(i + 1));
                            break;
                        case BOOLEAN_TYPE:
                            property.setValue(resultSet.getBoolean(i + 1));
                            break;
                        case STRING_TYPE:
                            property.setValue(resultSet.getString(i + 1));
                            break;
                    }
                }
                list.add(o);
            }
            /*Пекло рефлексії закінчилося*/
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> fillObjects(ResultSet resultSet, DatabaseAnalyzer.DBTable_impl dbTable) throws SQLException {
        List<Object> list = new ArrayList<>();
        try {
            int size = dbTable.attributeList.size();
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                for (int i = 0; i < size; i++) {
                    DatabaseAnalyzer.DBAttribute_impl dbAttribute = dbTable.attributeList.get(i);
                    AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                    switch (property.getType()) {
                        case INT_TYPE:
                            property.setValue(resultSet.getInt(i + 1));
                            break;
                        case SQLDATE_TYPE:
                            property.setValue(resultSet.getDate(i + 1));
                            break;
                        case BOOLEAN_TYPE:
                            property.setValue(resultSet.getBoolean(i + 1));
                            break;
                        case STRING_TYPE:
                            property.setValue(resultSet.getString(i + 1));
                            break;
                    }
                }
                list.add(o);
            }
            /*Пекло рефлексії закінчилося*/
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<Object> fillObjects(ResultSet resultSet, DatabaseAnalyzer.DBTable_impl dbTable, List<DatabaseAnalyzer.DBAttribute_impl> dbAttributes) throws SQLException {
        List<Object> list = new ArrayList<>();
        try {
            int size = dbAttributes.size();
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                for (int i = 0; i < size; i++) {
                    DatabaseAnalyzer.DBAttribute_impl dbAttribute = dbAttributes.get(i);
                    AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                    switch (property.getType()) {
                        case INT_TYPE:
                            property.setValue(resultSet.getInt(i + 1));
                            break;
                        case SQLDATE_TYPE:
                            property.setValue(resultSet.getDate(i + 1));
                            break;
                        case BOOLEAN_TYPE:
                            property.setValue(resultSet.getBoolean(i + 1));
                            break;
                        case STRING_TYPE:
                            property.setValue(resultSet.getString(i + 1));
                            break;
                    }
                }
                list.add(o);
            }
            /*Пекло рефлексії закінчилося*/
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return list;
    }

}
