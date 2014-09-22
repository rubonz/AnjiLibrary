package com.darkempire.anji.document.diagrams;

import com.darkempire.anji.util.AnjiStringUtil;

import java.io.PrintWriter;

/**
 * Create in 19:43
 * Created by siredvin on 08.04.14.
 */
public class ColumnNode implements DiagramNode {
    private String name;
    private String id;
    private String type;
    private boolean isIndex, isPrimaryKey, isNullable;
    private String foreignKey;
    private TableNode parent;

    public ColumnNode(String name, String id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
        isIndex = isNullable = isPrimaryKey = false;
        foreignKey = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setIndex(boolean isIndex) {
        this.isIndex = isIndex;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Override
    public TableNode getParent() {
        return parent;
    }

    public void setParent(TableNode parent) {
        this.parent = parent;
    }

    @Override
    public void write(PrintWriter pw) {
        if (foreignKey != null) {
            pw.print("\t@com.darkempire.anji.database.DBForeignKey(\"");
            pw.print(foreignKey);
            pw.println("\")");
        }
        pw.print("\t@com.darkempire.anji.database.DBAttribute(");
        pw.print("name=\"");
        pw.print(name);
        pw.print("\",type=\"");
        pw.print(type);
        pw.print("\",id=\"");
        pw.print(id);
        pw.print("\"");
        if (isPrimaryKey) {
            pw.print(",isPrimaryKey=true");
        }
        if (isNullable) {
            pw.print(",isNullable=true");
        }
        if (isIndex) {
            pw.print(",isIndex=true");
        }
        name = AnjiStringUtil.fromSlashSplitToCAMEL(name.replace(" ", "_"));
        pw.println(")");
        pw.print("\tpublic ");
        pw.print(parent.getConverter().convert(type));
        pw.print(" ");
        pw.print(name);
        pw.println("Property;");
    }
}
