package com.darkempire.anji.database;

import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.log.Log;
import com.darkempire.anjifx.beans.property.AbstractAnjiProperty;
import com.darkempire.anjifx.beans.property.AnjiIntegerProperty;
import com.darkempire.anjifx.beans.property.AnjiStringProperty;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@AnjiUnknow
public class DatabaseAnalyzer {

    /**
     * Словник, який зв’язує пари:
     * id таблиці -- представлення таблиці у аналізаторі
     */
    private Map<String, DBTable_impl> dbTableMap;
    /**
     * Словник, який зв’язує пари:
     * ім’я таблиці -- id таблиці
     */
    private Map<String, String> nameToIdMap;
    /**
     * Словник, який зв’язує пари:
     * id атрибуту супертипу -- його можлиливі варіанті
     */
    private Map<String, List<DBTable_impl>> superTypeMap;
    /**
     * Словний, якй зв’язує пари:
     * атрибут -- його зовнішні ключі
     */
    private Map<DBAttribute_impl, List<DBAttribute_impl>> fkFields;
    /**
     * Словник, який зв’язує пари:
     * id атрибуту -- його представлення
     */
    private Map<String, DBAttribute_impl> dbAttributeMap;
    /**
     * Словник, який зв’язує пари:
     * id атрибуту -- id його зовнішніх ключів
     */
    private Map<String, List<String>> fkList;
    /**
     * Словник, який зв’язує пари:
     * ім’я у представлені -- ім’я для графічного інтерфейсу
     */
    private ResourceBundle resourceBundle;
    /**
     * Словник, який зв’язує пари:
     * id атрибуту числового значення -- список текстових варіантів
     */
    private Map<String, List<String>> domainsMap;
    /**
     * Словник, який зв’язує пари:
     * id таблиці -- список виборів за замовчуванням доменів
     */
    private Map<String, List<DefaultChoose>> defaultChooseMap;
    /**
     * З’єднання з базою даних
     */
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DatabaseAnalyzer(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        dbTableMap = new HashMap<>();
        dbAttributeMap = new HashMap<>();
        nameToIdMap = new HashMap<>();
        fkList = new HashMap<>();
        fkFields = new HashMap<>();
        superTypeMap = new HashMap<>();
        domainsMap = new HashMap<>();
        defaultChooseMap = new HashMap<>();
    }

    /**
     * Додавання списку вибору за замовчуванням
     *
     * @param tableID        id таблиці
     * @param defaultChooses список параметрів за замовчуванням
     */
    public void addDefaultSelect(String tableID, DefaultChoose... defaultChooses) {
        defaultChooseMap.put(tableID, Arrays.asList(defaultChooses));
    }

    /**
     * Додавання списку вибору за замовчуванням
     *
     * @param tableID        id таблиці
     * @param defaultChooses список параметрів за замовчуванням
     */
    public void addDefaultSelect(String tableID, List<DefaultChoose> defaultChooses) {
        defaultChooseMap.put(tableID, defaultChooses);
    }

    /**
     * @param tableID id таблиці
     * @return список виборів за замовчуванням
     */
    public List<DefaultChoose> getDefaultSelect(String tableID) {
        return defaultChooseMap.get(tableID);
    }

    /**
     * Додавання домен
     *
     * @param id      id атрибуту
     * @param domains список доменів
     */
    public void addDomain(String id, List<String> domains) {
        domainsMap.put(id, domains);
    }

    /**
     * @param id id атрибуту
     * @return Чи є атрибут доменом?
     */
    public boolean isDomain(String id) {
        return domainsMap.keySet().contains(id);
    }

    /**
     * Додаваня домену
     *
     * @param id      id атрибуту
     * @param domains список доменів
     */
    public void addDomain(String id, String... domains) {
        domainsMap.put(id, Arrays.asList(domains));
    }

    /**
     * @param id id атрибуту
     * @return список доменів
     */
    public List<String> getDomain(String id) {
        return domainsMap.get(id);
    }

    /**
     * @param id id супертаблиці
     * @return можливі таблиці
     */
    public List<DBTable_impl> getSuperTables(String id) {
        return superTypeMap.get(id);
    }

    /**
     * Знаходить супертаблицю для таблицы
     *
     * @param table таблиця
     * @return супертаблиця для таблиці
     */
    public DBTable_impl isHasSuperTable(DBTable_impl table) {
        DBTable_impl result = null;
        for (Map.Entry<String, List<DBTable_impl>> e : superTypeMap.entrySet()) {
            if (e.getValue().contains(table)) {
                result = getTable(e.getKey());
                break;
            }
        }
        return result;
    }

    /**
     * Додавання списку можливих таблиць до супертаблиці
     *
     * @param id           id супертаблиці
     * @param dbAttributes список можливих таблиць
     */
    public void addSuperTables(String id, DBTable_impl... dbAttributes) {
        superTypeMap.put(id, Arrays.asList(dbAttributes));
    }

    /**
     * @param attributeID id атрибуту-домена
     * @param index       індекс домена
     * @return розшиврування домена
     */
    public String getDomainName(String attributeID, int index) {
        return Optional.of(domainsMap.get(attributeID)).map(l -> l.get(index)).get();
    }

    /**
     * Додавання списку можливих таблиць до супертаблиці
     *
     * @param id  id супертаблиці
     * @param ids id можливих таблиць
     */
    public void addSuperTables(String id, String... ids) {
        superTypeMap.put(id, Arrays.stream(ids).map(this::getTable).collect(Collectors.toList()));
    }

    /**
     * Додавання списку можливих таблиць до супертаблиці
     *
     * @param id    id супертаблиці
     * @param names імена можливих таблиць
     */
    public void addSuperTablesByName(String id, String... names) {
        superTypeMap.put(id, Arrays.stream(names).map(this::getTableByName).collect(Collectors.toList()));
    }

    /**
     * @return множину імен таблиці
     */
    public Set<String> getTableNames() {
        return nameToIdMap.keySet();
    }

    /**
     * @param name    ім’я атрибуту
     * @param dbTable таблиця, до якої належить атрибут
     * @return преставлення атрибуту
     */
    public DBAttribute_impl getAttributeByName(String name, DBTable_impl dbTable) {
        for (DBAttribute_impl dbAttribute : dbTable.attributeList) {
            if (dbAttribute.name.equals(name)) {
                return dbAttribute;
            }
        }
        return null;
    }

    /**
     * @param key ім’я атрибуту або таблиці
     * @return представлення цього імені
     */
    public String getName(String key) {
        return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : key;
    }
    /*
    /**
     * Допомагає додати нове представлення імені
     * @param key ім’я
     * @param name представлення
     *
    public void putName(String key,String name){
        resourceBundle.put(key, name);
    }
    */

    /**
     * @param id id атрибуту
     * @return представлення атрибуту
     */
    public DBAttribute_impl getAttributeById(String id) {
        return dbAttributeMap.get(id);
    }

    /**
     * @param name ім’я таблиці
     * @return представлення таблиці
     */
    public DBTable_impl getTableByName(String name) {
        return getTable(getTableIdByName(name));
    }

    /**
     * Додання до якогось атрибуту списку зовнішніх ключів
     *
     * @param fk     атрибут
     * @param fields список зовнішніх ключів
     */
    public void addFKFields(DBAttribute_impl fk, DBAttribute_impl... fields) {
        List<DBAttribute_impl> list = new ArrayList<>();
        Collections.addAll(list, fields);
        fkFields.put(fk, list);
    }

    /**
     * @param fk атрибут
     * @return список зовнішніх ключів для цього атрибуту
     */
    public List<DBAttribute_impl> getFKFieldS(DBAttribute_impl fk) {
        return fkFields.get(fk);
    }

    /**
     * Генерує SQL-запит INSERT у таблицю.
     *
     * @param dbTable таблиця
     * @param objects масив об’єктів
     * @return результат команди INSERT
     */
    public int[] insertIntoTable(DBTable_impl dbTable, Object... objects) throws SQLException {
        String prep = DatabaseAnalyzerUtil.createPrepareStatement(dbTable);
        Log.log(Log.sqlIndex, "Create prepared insert:", prep);
        PreparedStatement preparedStatement = connection.prepareStatement(prep);
        return DatabaseAnalyzerUtil.fillPrepareStatement(dbTable, preparedStatement, objects).executeBatch();
    }

    /**
     * Оновлює дані у таблиці
     *
     * @param dbTable таблиця
     * @param objects масив об’єктів
     * @return результат операції
     */
    public int[] updateTable(DBTable_impl dbTable, Object... objects) throws SQLException {
        String prep = DatabaseAnalyzerUtil.createPrepareUpdateStatement(dbTable);
        PreparedStatement preparedStatement = connection.prepareStatement(prep);
        Log.log(Log.sqlIndex, "Create prepared update:");
        return DatabaseAnalyzerUtil.fillPrepareUpdateStatement(dbTable, preparedStatement, objects).executeBatch();
    }

    /**
     * Ця функція допомагає додавати одночасно запис у таблицю та її супертаблицю
     *
     * @param table      таблиця
     * @param superTable її супертаблиця
     * @param o          об’єкт, що відповідає запису у таблиці
     * @param superO     об’єкт, що відповідаж запису у супертаблиці
     * @return результат SQL-запиту
     * @see com.darkempire.anjifx.dialog.database.DatabaseRecordEditDialog
     * @see com.darkempire.anjifx.dialog.database.DatabaseRecordEditController
     */
    public int[] insertTableAndSuper(DBTable_impl table, DBTable_impl superTable, Object o, Object superO) throws SQLException {
        String prep = DatabaseAnalyzerUtil.createPrepareStatement(superTable,
                table.attributeList.stream().filter(attribute -> {
                    if (attribute.isPrimaryKey) {
                        DBAttribute_impl superAttr = getAttributeById(attribute.fk_id);
                        return superTable.attributeList.contains(superAttr) && superAttr.isPrimaryKey;
                    }
                    return false;
                }).map(dbAttribute -> getAttributeById(dbAttribute.fk_id).name).collect(Collectors.toList())
        );
        Log.log(Log.sqlIndex, "Create prepared insert", prep);
        int[] result = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(prep);
            ResultSet resultSet = DatabaseAnalyzerUtil.fillPrepareStatement(superTable, preparedStatement, superO).executeQuery();
            resultSet.next();
            for (DBAttribute_impl dbAttribute : table.getPrimaryKey()) {
                switch (dbAttribute.type) {
                    case "int4":
                        ((AnjiIntegerProperty) dbAttribute.field.get(o)).setValue(resultSet.getInt(getAttributeById(dbAttribute.fk_id).name));
                        break;
                    case "char":
                        ((AnjiStringProperty) dbAttribute.field.get(o)).setValue(resultSet.getString(getAttributeById(dbAttribute.fk_id).name));
                        break;
                }
            }
            DatabaseAnalyzerUtil.isShowPrimaryKey.set(true);
            prep = DatabaseAnalyzerUtil.createPrepareStatement(table);
            Log.log(Log.sqlIndex, "Create prepared insert", prep);
            preparedStatement = connection.prepareStatement(prep);
            result = DatabaseAnalyzerUtil.fillPrepareStatement(table, preparedStatement, o).executeBatch();
            DatabaseAnalyzerUtil.isShowPrimaryKey.set(false);
        } catch (IllegalAccessException e) {
            Log.error(Log.coreIndex, e);

        }
        return result;
    }

    public Object getObjectWithPrimaryKey(DBTable_impl table, DBTable_impl superTable, Object o) throws SQLException {
        try {
            List<DBAttribute_impl> primarySuperKey = table.attributeList.stream().filter(pk -> pk.isPrimaryKey).map(pk -> getAttributeById(pk.fk_id)).collect(Collectors.toList());
            List<DBAttribute_impl> primaryKey = table.attributeList.stream().filter(pk -> pk.isPrimaryKey).collect(Collectors.toList());
            String prep = DatabaseAnalyzerUtil.createSelectWithWhere(superTable, primaryKey, primarySuperKey, o);
            Log.log(Log.sqlIndex, "Create prepared select where:", prep);
            PreparedStatement statement = connection.prepareStatement(prep);
            DatabaseAnalyzerUtil.fillPrepareStatement(primarySuperKey, superTable, statement, o);
            ResultSet set = statement.executeQuery();
            List<Object> resultList = DatabaseAnalyzerUtil.fillObjects(set, superTable);
            return resultList.get(0);
        } catch (IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return null;
    }

    /**
     * Дістає з бази даних всі записи з цим атрибутом
     *
     * @param dbAttribute представлення атрибуту
     * @return записи
     */
    @SuppressWarnings("unchecked")
    public Collection<AbstractAnjiProperty> getFields(DBAttribute_impl dbAttribute) throws SQLException {
        List<AbstractAnjiProperty> resultList = new ArrayList<>();
        DBTable_impl dbTable = dbTableMap.get(dbAttribute.table_id);
        try {
            Statement statement = connection.createStatement();
            String result = DatabaseAnalyzerUtil.createSelectFromDBTable(dbTable, dbAttribute);
            Log.log(Log.sqlIndex, "Create select:", result);
            ResultSet resultSet = statement.executeQuery(result);
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                switch (property.getType()) {
                    case INT_TYPE:
                        property.setValue(resultSet.getInt(1));
                        break;
                    case SQLDATE_TYPE:
                        property.setValue(resultSet.getDate(1));
                        break;
                    case BOOLEAN_TYPE:
                        property.setValue(resultSet.getBoolean(1));
                        break;
                    case STRING_TYPE:
                        property.setValue(resultSet.getString(1));
                        break;
                }
                resultList.add(property);
            }
            /*Пекло рефлексії закінчилося*/
            statement.close();
            resultSet.close();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return resultList;
    }

    /**
     * Дістає з бази даних всі записи з заданої таблиці, обмежуючись заданим списком атрибутів
     *
     * @param dbTable      таблиця
     * @param dbAttributes список атрибутів
     * @return записи
     */
    @SuppressWarnings("unchecked")
    public List<Object> getRecords(DBTable_impl dbTable, DBAttribute_impl... dbAttributes) throws SQLException {
        if (dbAttributes == null || dbAttributes.length == 0) {
            return getRecords(dbTable, dbTable.attributeList);
        }
        List<Object> resultList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String result = DatabaseAnalyzerUtil.createSelectFromDBTable(dbTable, dbAttributes);
            Log.log(Log.sqlIndex, "Create select:", result);
            ResultSet resultSet = statement.executeQuery(result);
            int size = dbAttributes.length;
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                for (int i = 0; i < size; i++) {
                    DBAttribute_impl dbAttribute = dbAttributes[i];
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
                resultList.add(o);
            }
            /*Пекло рефлексії закінчилося*/
            statement.close();
            resultSet.close();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return resultList;
    }

    /**
     * Дістає з бази даних всі записи з заданої таблиці, обмежуючись заданим списком атрибутів
     *
     * @param dbTable      таблиця
     * @param dbAttributes список атрибутів
     * @return записи
     */
    @SuppressWarnings("unchecked")
    public List<Object> getRecords(DBTable_impl dbTable, Collection<DBAttribute_impl> dbAttributes) throws SQLException {
        List<Object> resultList = new ArrayList<>();
        if (dbAttributes == null || dbAttributes.size() == 0) {
            dbAttributes = dbTable.attributeList;
        }
        try {
            Statement statement = connection.createStatement();
            String result = DatabaseAnalyzerUtil.createSelectFromDBTable(dbTable, dbAttributes);
            Log.log(Log.sqlIndex, "Create select:", result);
            ResultSet resultSet = statement.executeQuery(result);
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                int i = 1;
                for (DBAttribute_impl dbAttribute : dbAttributes) {
                    AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                    switch (property.getType()) {
                        case INT_TYPE:
                            property.setValue(resultSet.getInt(i));
                            break;
                        case SQLDATE_TYPE:
                            property.setValue(resultSet.getDate(i));
                            break;
                        case BOOLEAN_TYPE:
                            property.setValue(resultSet.getBoolean(i));
                            break;
                        case STRING_TYPE:
                            property.setValue(resultSet.getString(i));
                            break;
                    }
                    i++;
                }
                resultList.add(o);
            }
            /*Пекло рефлексії закінчилося*/
            statement.close();
            resultSet.close();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return resultList;
    }

    /**
     * Дістає з бази даних всі записи з заданої таблиці, обмежуючись заданими обмеженнями
     *
     * @param dbTable таблиця
     * @param name    id атрибуту обмеження
     * @param op      операція обмеження
     * @param value   значення обмеження
     * @return записи
     */
    @SuppressWarnings("unchecked")
    public List<Object> getRecords(DBTable_impl dbTable, String name, String op, Object value) throws SQLException {
        List<Object> resultList = new ArrayList<>();
        Collection<DBAttribute_impl> dbAttributes = dbTable.attributeList;
        try {
            Statement statement = connection.createStatement();
            String result = DatabaseAnalyzerUtil.createSelectFromDBTable(dbTable, dbAttributes, name, op, value);
            Log.log(Log.sqlIndex, "Create select:", result);
            ResultSet resultSet = statement.executeQuery(result);
            while (resultSet.next()) {
            /*Далі починається пекло рефлексії*/
                Object o = dbTable.cl.newInstance();
                int i = 1;
                for (DBAttribute_impl dbAttribute : dbAttributes) {
                    AbstractAnjiProperty property = (AbstractAnjiProperty) dbAttribute.field.get(o);
                    switch (property.getType()) {
                        case INT_TYPE:
                            property.setValue(resultSet.getInt(i));
                            break;
                        case SQLDATE_TYPE:
                            property.setValue(resultSet.getDate(i));
                            break;
                        case BOOLEAN_TYPE:
                            property.setValue(resultSet.getBoolean(i));
                            break;
                        case STRING_TYPE:
                            property.setValue(resultSet.getString(i));
                            break;
                    }
                    i++;
                }
                resultList.add(o);
            }
            /*Пекло рефлексії закінчилося*/
            statement.close();
            resultSet.close();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.error(Log.coreIndex, e);
        }
        return resultList;
    }

    /**
     * Дістає з бази даних всі записи з цим атрибутом
     *
     * @param fieldID id атрибуту
     * @return записи
     */
    public Collection<AbstractAnjiProperty> getFields(String fieldID) throws SQLException {
        DBAttribute_impl dbAttribute = dbAttributeMap.get(fieldID);
        return getFields(dbAttribute);
    }

    /**
     * Додавання деякого View до аналізатора
     *
     * @param viewSQL команда створення View
     * @param dbTable його представлення у Java
     * @return результат виконання операції
     */
    public boolean addView(String viewSQL, DBTable_impl dbTable) throws SQLException {
        dbTableMap.put(dbTable.id, dbTable);
        nameToIdMap.put(dbTable.name, dbTable.id);
        for (DBAttribute_impl dbAttribute : dbTable.attributeList) {
            dbAttributeMap.put(dbAttribute.id, dbAttribute);
            if (dbAttribute.fk_id != null) {
                List<String> ids = fkList.get(dbAttribute.fk_id);
                if (ids == null) {
                    ids = new ArrayList<>();
                    ids.add(dbAttribute.id);
                    fkList.put(dbAttribute.fk_id, ids);
                } else {
                    ids.add(dbAttribute.id);
                }
            }

        }
        Statement statement = connection.createStatement();
        boolean result = statement.execute(viewSQL);
        statement.close();
        return result;
    }

    /**
     * Додавання деякого View до аналізатора
     *
     * @param dbTable його представлення у Java
     * @return результат виконання операції
     */
    public boolean addView(DBTable_impl dbTable) {
        dbTableMap.put(dbTable.id, dbTable);
        nameToIdMap.put(dbTable.name, dbTable.id);
        for (DBAttribute_impl dbAttribute : dbTable.attributeList) {
            dbAttributeMap.put(dbAttribute.id, dbAttribute);
            if (dbAttribute.fk_id != null) {
                List<String> ids = fkList.get(dbAttribute.fk_id);
                if (ids == null) {
                    ids = new ArrayList<>();
                    ids.add(dbAttribute.id);
                    fkList.put(dbAttribute.fk_id, ids);
                } else {
                    ids.add(dbAttribute.id);
                }
            }

        }
        return true;
    }

    /**
     * Основна фунція розбору
     *
     * @param cl клас для розбору
     * @return результат розбору класу
     * @see com.darkempire.anji.database.DBForeignKey
     * @see com.darkempire.anji.database.DBAttribute
     * @see com.darkempire.anji.database.DBTable
     */
    public DBTable_impl parseDBTable(Class cl) {
        DBTable dbTable = (DBTable) cl.getAnnotation(DBTable.class);
        if (dbTable == null)
            return null; //Заглушка на той випадок, якщо переданий клас не таблиці
        DBTable_impl dbTable_impl = new DBTable_impl(cl, dbTable.id(), dbTable.name());
        for (Field f : cl.getDeclaredFields()) {
            DBAttribute dbAttribute = f.getAnnotation(DBAttribute.class);
            if (dbAttribute == null)
                continue;
            DBAttribute_impl dbAttribute_impl = new DBAttribute_impl(f, dbAttribute.id(), dbAttribute.name(), dbTable.id(), dbAttribute.type(), dbAttribute.isIndex(), dbAttribute.isNullable(), dbAttribute.isPrimaryKey());
            dbTable_impl.attributeList.add(dbAttribute_impl);
            DBForeignKey dbForeignKey = f.getAnnotation(DBForeignKey.class);
            if (dbForeignKey != null) {
                dbAttribute_impl.fk_id = dbForeignKey.value();
            }
        }
        return dbTable_impl;
    }

    /**
     * @param name ім’я таблиці
     * @return id таблиці
     */
    public String getTableIdByName(String name) {
        return nameToIdMap.get(name);
    }

    /**
     * @param id id таблиці
     * @return представлення таблиці
     */
    public DBTable_impl getTable(String id) {
        return dbTableMap.get(id);
    }

    /**
     * Метод, який збирає та аналізує інформацію
     *
     * @param connection з’єднання до бази даних
     * @param classes    класи, що усоблюють таблиці бази данних
     * @return аналізатор
     * @see com.darkempire.anji.database.DBForeignKey
     * @see com.darkempire.anji.database.DBAttribute
     * @see com.darkempire.anji.database.DBTable
     */
    public static DatabaseAnalyzer create(Connection connection, ResourceBundle resourceBundle, Class... classes) {
        DatabaseAnalyzer databaseAnalyzer = new DatabaseAnalyzer(resourceBundle);
        databaseAnalyzer.connection = connection;
        DBForeignKey dbForeignKey;
        DBAttribute dbAttribute;
        DBTable dbTable;
        for (Class cl : classes) {
            dbTable = (DBTable) cl.getAnnotation(DBTable.class);
            if (dbTable == null)
                continue; //Заглушка на той випадок, якщо переданий клас не таблиці
            DBTable_impl dbTable_impl = new DBTable_impl(cl, dbTable.id(), dbTable.name());
            databaseAnalyzer.dbTableMap.put(dbTable.id(), dbTable_impl);
            databaseAnalyzer.nameToIdMap.put(dbTable.name(), dbTable.id());
            for (Field f : cl.getDeclaredFields()) {
                dbAttribute = f.getAnnotation(DBAttribute.class);
                if (dbAttribute == null)
                    continue;
                DBAttribute_impl dbAttribute_impl = new DBAttribute_impl(f, dbAttribute.id(), dbAttribute.name(), dbTable.id(), dbAttribute.type(), dbAttribute.isIndex(), dbAttribute.isNullable(), dbAttribute.isPrimaryKey());
                databaseAnalyzer.dbAttributeMap.put(dbAttribute.id(), dbAttribute_impl);
                dbTable_impl.attributeList.add(dbAttribute_impl);
                dbForeignKey = f.getAnnotation(DBForeignKey.class);
                if (dbForeignKey != null) {
                    dbAttribute_impl.fk_id = dbForeignKey.value();
                    List<String> ids = databaseAnalyzer.fkList.get(dbForeignKey.value());
                    if (ids == null) {
                        ids = new ArrayList<>();
                        ids.add(dbAttribute.id());
                        databaseAnalyzer.fkList.put(dbForeignKey.value(), ids);
                    } else {
                        ids.add(dbAttribute.id());
                    }
                }

            }
        }
        return databaseAnalyzer;
    }

    /**
     * Метод, який збирає та аналізує інформацію
     *
     * @param classes класи, що усоблюють таблиці бази данних
     * @return аналізатор
     * @see com.darkempire.anji.database.DBForeignKey
     * @see com.darkempire.anji.database.DBAttribute
     * @see com.darkempire.anji.database.DBTable
     */
    public static DatabaseAnalyzer create(ResourceBundle resourceBundle, Class... classes) {
        DatabaseAnalyzer databaseAnalyzer = new DatabaseAnalyzer(resourceBundle);
        DBForeignKey dbForeignKey;
        DBAttribute dbAttribute;
        DBTable dbTable;
        for (Class cl : classes) {
            dbTable = (DBTable) cl.getAnnotation(DBTable.class);
            if (dbTable == null)
                continue; //Заглушка на той випадок, якщо переданий клас не таблиці
            DBTable_impl dbTable_impl = new DBTable_impl(cl, dbTable.id(), dbTable.name());
            databaseAnalyzer.dbTableMap.put(dbTable.id(), dbTable_impl);
            databaseAnalyzer.nameToIdMap.put(dbTable.name(), dbTable.id());
            for (Field f : cl.getDeclaredFields()) {
                dbAttribute = f.getAnnotation(DBAttribute.class);
                if (dbAttribute == null)
                    continue;
                DBAttribute_impl dbAttribute_impl = new DBAttribute_impl(f, dbAttribute.id(), dbAttribute.name(), dbTable.id(), dbAttribute.type(), dbAttribute.isIndex(), dbAttribute.isNullable(), dbAttribute.isPrimaryKey());
                databaseAnalyzer.dbAttributeMap.put(dbAttribute.id(), dbAttribute_impl);
                dbTable_impl.attributeList.add(dbAttribute_impl);
                dbForeignKey = f.getAnnotation(DBForeignKey.class);
                if (dbForeignKey != null) {
                    dbAttribute_impl.fk_id = dbForeignKey.value();
                    List<String> ids = databaseAnalyzer.fkList.get(dbForeignKey.value());
                    if (ids == null) {
                        ids = new ArrayList<>();
                        ids.add(dbAttribute.id());
                        databaseAnalyzer.fkList.put(dbForeignKey.value(), ids);
                    } else {
                        ids.add(dbAttribute.id());
                    }
                }

            }
        }
        return databaseAnalyzer;
    }

    /**
     * @param connection з’єднання до бази даних
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /*
     * Допоміжні класи
     *
     *
     *
     *
     *
     *
     */

    /**
     * Клас, який відповідає за зберігання даних про таблицю
     */
    public static class DBTable_impl {
        /**
         * Зберігає клас, який відповідає таблиці
         */
        public Class cl;
        /**
         * Зберігає id таблиці
         */
        public String id;
        /**
         * Зберігає ім’я таблиці
         */
        public String name;
        /*Дуже важливо, щоб це поле було впорядкованим. Не в якому разі не використовувати різні
        * невпорядковані колекції.
        * Це можна побачити у класах
        * - DatabaseAnalyzerUtil
        * */
        /**
         * Список атрибутів
         * Важливо, щоб він був впорядкований
         *
         * @see com.darkempire.anji.database.DatabaseAnalyzerUtil
         */
        public List<DBAttribute_impl> attributeList;

        private DBTable_impl(Class cl, String id, String name) {
            this.cl = cl;
            this.id = id;
            this.name = name;
            attributeList = new ArrayList<>();
        }

        /**
         * @return список атрибутів, які належать до первинного ключа
         */
        private Collection<DBAttribute_impl> getPrimaryKey() {
            return attributeList.stream().filter(dbAttribute -> dbAttribute.isPrimaryKey).collect(Collectors.toList());
        }

        /**
         * Знаходить атрибут з заданим ім’ям
         *
         * @param name ім’я атрибуту
         * @return атрибут, або null, якщо такого немає
         */
        public DBAttribute_impl findAttributeByName(String name) {
            return attributeList.stream().filter(attribute -> attribute.name.equals(name)).findFirst().get();
        }
    }

    /**
     * Клас, який зберігає дані про атрибут таблиці
     */
    public static class DBAttribute_impl {
        /**
         * Зберігає поле, що відповідає цьому атрибуту у відповідному класі
         */
        public Field field;
        /**
         * Зберігає id атрибута
         */
        public String id;
        /**
         * Зберігає ім’я атрибута
         */
        public String name;
        /**
         * Зберігає id таблиці, до якої належить атрибут
         */
        public String table_id;
        /**
         * Зберігає тип атрибуту
         */
        public String type;
        /**
         * Зберігає зовнішній ключ, на який посилається цей атрибут.
         * Якщо такого немає, то це null
         */
        public String fk_id;
        /**
         * Чи індексований цей атрибут?
         */
        public boolean isIndex;
        /**
         * Чи може цей атрибут приймати значення null?
         */
        public boolean isNullable;
        /**
         * Чи є цей атрибут первинним ключем?
         */
        public boolean isPrimaryKey;

        private DBAttribute_impl(Field cl, String id, String name, String table_id, String type, boolean isIndex, boolean isNullable, boolean isPrimaryKey, String fk_id) {
            this.field = cl;
            this.id = id;
            this.name = name;
            this.table_id = table_id;
            this.type = type;
            this.isIndex = isIndex;
            this.isNullable = isNullable;
            this.isPrimaryKey = isPrimaryKey;
            this.fk_id = fk_id;
        }

        private DBAttribute_impl(Field cl, String id, String name, String table_id, String type, boolean isIndex, boolean isNullable, boolean isPrimaryKey) {
            this.field = cl;
            this.id = id;
            this.name = name;
            this.table_id = table_id;
            this.type = type;
            this.isIndex = isIndex;
            this.isNullable = isNullable;
            this.isPrimaryKey = isPrimaryKey;
            this.fk_id = null;
        }
    }

    public static class DefaultChoose {
        public DBAttribute_impl attributeWithDomain;
        public int defaultChoose;

        public DefaultChoose(DBAttribute_impl attributeWithDomain, int defaultChoose) {
            this.attributeWithDomain = attributeWithDomain;
            this.defaultChoose = defaultChoose;
        }
    }
}
