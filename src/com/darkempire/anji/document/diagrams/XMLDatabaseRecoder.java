package com.darkempire.anji.document.diagrams;

import com.darkempire.anji.annotation.AnjiConsts;
import com.darkempire.anji.document.PostgreSQLTypeConverter;
import com.darkempire.anji.document.TypeConverter;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Create in 12:19
 * Created by siredvin on 08.04.14.
 */
public class XMLDatabaseRecoder {
    @AnjiConsts
    private static final class XMLQNameParams {
        private static QName DBTable = new QName("DBTable");
        private static QName DBColumn = new QName("DBColumn");
        private static QName nameAttribute = new QName("Name");
        private static QName isPrimaryKey = new QName("PrimaryKey");
        private static QName isNullable = new QName("Nullable");
        private static QName isIndex = new QName("Index");
        private static QName typeAttribute = new QName("Type");
        private static QName models = new QName("Models");
        private static QName masterView = new QName("MasterView");
        private static QName id = new QName("Id");
        private static QName DBForeignKeyConstraint = new QName("DBForeignKeyConstraint");
        private static QName refColumn = new QName("RefColumn");
    }

    public void recode(InputStream stream, String pack, File directory) throws FileNotFoundException, XMLStreamException {
        recode(stream, pack, directory, new PostgreSQLTypeConverter());
    }

    public void recode(InputStream stream, String pack, File directory, TypeConverter typeConverter) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        // configure it to create readers that coalesce adjacent character sections
        factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
        XMLEventReader reader = factory.createXMLEventReader(stream);
        PrintWriter pw = null;
        boolean isTableRead = false;
        boolean isModels = false;
        boolean isMasterView = false;
        TableNode tableNode = null;
        ColumnNode columnNode = null;
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case XMLEvent.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().equals(XMLQNameParams.models)) {
                        isModels = true;
                    }
                    if (startElement.getName().equals(XMLQNameParams.masterView)) {
                        isMasterView = true;
                    }
                    if (isModels && !isMasterView && startElement.getName().equals(XMLQNameParams.DBTable)) {
                        String name = startElement.getAttributeByName(XMLQNameParams.nameAttribute).getValue();
                        String id = startElement.getAttributeByName(XMLQNameParams.id).getValue();
                        tableNode = new TableNode(name, id, pack, typeConverter);
                        isTableRead = true;
                    }
                    if (isTableRead && startElement.getName().equals(XMLQNameParams.DBColumn)) {
                        String name = startElement.getAttributeByName(XMLQNameParams.nameAttribute).getValue();
                        String type = startElement.getAttributeByName(XMLQNameParams.typeAttribute).getValue();
                        String id = startElement.getAttributeByName(XMLQNameParams.id).getValue();
                        columnNode = new ColumnNode(name, id, type);
                        columnNode.setIndex(Boolean.parseBoolean(startElement.getAttributeByName(XMLQNameParams.isIndex).getValue()));
                        columnNode.setNullable(Boolean.parseBoolean(startElement.getAttributeByName(XMLQNameParams.isNullable).getValue()));
                        columnNode.setPrimaryKey(Boolean.parseBoolean(startElement.getAttributeByName(XMLQNameParams.isPrimaryKey).getValue()));
                        tableNode.addColumn(columnNode);
                    }
                    if (columnNode != null && startElement.getName().equals(XMLQNameParams.DBForeignKeyConstraint)) {
                        columnNode.setForeignKey(startElement.getAttributeByName(XMLQNameParams.refColumn).getValue());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().equals(XMLQNameParams.models)) {
                        isModels = false;
                    }
                    if (endElement.getName().equals(XMLQNameParams.masterView)) {
                        isMasterView = false;
                    }
                    if (!isMasterView && endElement.getName().equals(XMLQNameParams.DBTable)) {
                        if (tableNode == null)
                            continue; // Заглушка у деяких дивних випадках
                        pw = new PrintWriter(tableNode.createFile(directory));
                        tableNode.write(pw);
                        pw.flush();
                        pw.close();
                        isTableRead = false;
                        tableNode = null;
                    }
                    if (!isMasterView && endElement.getName().equals(XMLQNameParams.DBColumn)) {
                        columnNode = null;
                    }
                    break;
            }
        }
    }

}
