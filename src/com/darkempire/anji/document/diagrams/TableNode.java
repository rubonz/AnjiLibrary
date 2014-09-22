package com.darkempire.anji.document.diagrams;

import com.darkempire.anji.document.PostgreSQLTypeConverter;
import com.darkempire.anji.document.TypeConverter;
import com.darkempire.anji.util.AnjiStringUtil;
import com.darkempire.anjifx.beans.AnjiBeanUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Create in 19:42
 * Created by siredvin on 08.04.14.
 */
public class TableNode implements DiagramNode, Iterable<ColumnNode> {
    private String name;
    private String id;
    private String pack;
    private List<ColumnNode> columnNodeList;
    private TypeConverter converter;

    public TableNode(String name, String id, String pack, TypeConverter converter) {
        this.name = name;
        this.id = id;
        this.pack = pack;
        this.converter = converter;
        columnNodeList = new ArrayList<>();
    }

    public TableNode(String name, String id, String pack) {
        this(name, id, pack, new PostgreSQLTypeConverter());
    }

    public List<ColumnNode> getChildren() {
        return columnNodeList;
    }

    public void addColumn(ColumnNode columnNode) {
        columnNodeList.add(columnNode);
        columnNode.setParent(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DiagramNode getParent() {
        return null;
    }

    public String getPack() {
        return pack;
    }

    @Override
    public Iterator<ColumnNode> iterator() {
        return columnNodeList.iterator();
    }


    public File createFile(File directory) {
        return new File(directory, AnjiStringUtil.fromSlashSplitToCAMEL(name) + ".java");
    }

    public TypeConverter getConverter() {
        return converter;
    }

    @Override
    public void write(PrintWriter pw) {
        pw.print("package ");
        pw.print(pack);
        pw.println(";\n");
        pw.println("@javax.annotation.Generated(\"com.darkempire.anji.document.diagrams.XMLDatabaseRecoder\")");
        pw.print("@com.darkempire.anji.database.DBTable(name=\"");
        pw.print(name);
        pw.print("\",id=\"");
        pw.print(id);
        pw.println("\")");
        pw.print("public class ");
        pw.print(AnjiStringUtil.fromSlashSplitToCAMEL(name));
        pw.println("{");
        for (ColumnNode node : columnNodeList) {
            node.write(pw);
        }
        /*Конструктор з параметрами*/
        pw.print("\tpublic ");

        pw.print(AnjiStringUtil.fromSlashSplitToCAMEL(name));
        pw.print(" (");
        ColumnNode last = columnNodeList.get(columnNodeList.size() - 1);
        for (ColumnNode node : columnNodeList) {
            pw.print(AnjiBeanUtil.getTypeFromProperty(converter.convert(node.getType())));
            pw.print(" ");
            pw.print(node.getName());
            if (node != last) {
                pw.print(",");
            } else {
                pw.println("){");
            }
        }
        for (ColumnNode node : columnNodeList) {
            pw.print("\t\tthis.");
            pw.print(node.getName());
            pw.print("Property");
            pw.print("=new ");
            pw.print(converter.convert(node.getType()));
            pw.print("(this,\"");
            pw.print(node.getName());
            pw.print("\",");
            pw.print(node.getName());
            pw.println(");");
        }
        pw.println("\t}");

        pw.print("\tpublic ");
        /*Конструктор без параметрів*/
        pw.print(AnjiStringUtil.fromSlashSplitToCAMEL(name));
        pw.println(" (){");
        for (ColumnNode node : columnNodeList) {
            pw.print("\t\tthis.");
            pw.print(node.getName());
            pw.print("Property");
            pw.print("=new ");
            pw.print(converter.convert(node.getType()));
            pw.print("(this,\"");
            pw.print(node.getName());
            pw.println("\");");
        }
        pw.println("\t}");
        pw.print("}");
    }
}
