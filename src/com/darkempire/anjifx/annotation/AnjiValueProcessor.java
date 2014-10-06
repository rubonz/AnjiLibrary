package com.darkempire.anjifx.annotation;

import com.darkempire.anji.annotation.AnjiRewrite;
import com.darkempire.internal.anji.AnjiInternal;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 01.11.13
 * Time: 7:48
 * To change this template use File | Settings | File Templates.
 */
@AnjiInternal
@SupportedAnnotationTypes({"com.darkempire.anjifx.annotation.AnjiFXValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AnjiRewrite
//Magic:це дійсно чорна магія, яке щястя, що це інтернал фіча
public class AnjiValueProcessor extends AbstractProcessor {
    private static final String packagePattern = "package.+value;";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processAnjiValue(roundEnv);
    }

    //AnjiValueProcessor
    private boolean processAnjiValue(RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(AnjiFXValue.class)) {
            AnjiFXClassConstructor constructor = e.getAnnotation(AnjiFXClassConstructor.class);
            if (e.getAnnotation(AnjiFXProperty.class) == null)
                continue;
            //Отримання імені згенерованого класу
            String genName = e.getSimpleName().toString().replace("Value", "Property");
            TypeElement clazz = (TypeElement) e;
            boolean isPackaged = false;
            String name = e.getSimpleName().toString();
            if (e.getModifiers().contains(Modifier.ABSTRACT))
                continue;
            try {
                //Test:Place-depends code
                File file = new File("/home/siredvin/Програмування/Idea IC/AnjiAPI/src/" + clazz.toString().replace(".", "/") + ".java");
                String fullName = clazz.toString().replace("Value", "Property").replace("value", "property");
                JavaFileObject f = processingEnv.getFiler().createSourceFile(fullName);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Created " + f.toUri());
                Writer w = f.openWriter();
                StringBuilder builder = new StringBuilder();
                try (Scanner scanner = new Scanner(new FileInputStream(file))) {
                    while (scanner.hasNextLine()) {
                        String next = scanner.nextLine();
                        if (!isPackaged) {
                            if (next.matches(packagePattern)) {
                                isPackaged = true;
                                next = next.replace("value", "property");
                            }
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, next);
                        }
                        builder.append(next);
                        builder.append('\n');
                    }
                }
                try (PrintWriter pw = new PrintWriter(w)) {
                    String result = builder.toString().replace("AbstractAnjiValue", "AbstractAnjiProperty").replace(name, genName);
                    Pattern constructorHeader = Pattern.compile(genName + "\\(");
                    Matcher headerMatcher = constructorHeader.matcher(result);
                    List<String> constructorList = new ArrayList<>();
                    while (headerMatcher.find()) {
                        int start = headerMatcher.start();
                        int bracecount = 0;
                        boolean isBraceFound = false;
                        while (bracecount != 0 || !isBraceFound) {
                            char k = result.charAt(start);
                            switch (k) {
                                case '{':
                                    if (!isBraceFound) {
                                        isBraceFound = true;
                                    }
                                    bracecount++;
                                    break;
                                case '}':
                                    if (!isBraceFound) {
                                        isBraceFound = true;
                                    }
                                    bracecount--;
                                    break;
                            }
                            start++;
                        }
                        constructorList.add(result.substring(headerMatcher.start(), start));
                    }
                    for (String s : constructorList) {
                        String s1, s2;
                        if (s.contains(genName + "()")) {
                            s1 = s.replace(genName + "(", genName + "(@org.simpleframework.xml.Attribute(name=\"name\")String name").replaceFirst("\\{", "{\n\t\tsuper(name);");
                            s2 = s.replace(genName + "(", "\tpublic " + genName + "(Object bean,String name").replaceFirst("\\{", "{\n\t\tsuper(bean,name);");
                        } else {
                            s1 = s.replace(genName + "(", genName + "(@org.simpleframework.xml.Attribute(name=\"name\")String name,").replaceFirst("\\{", "{\n\t\tsuper(name);");
                            s2 = s.replace(genName + "(", "\tpublic " + genName + "(Object bean,String name,").replaceFirst("\\{", "{\n\t\tsuper(bean,name);");
                        }
                        result = result.replace(s, s1 + "\n" + s2);
                    }
                    pw.print(result);
                }
            } catch (IOException e1) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e1.toString());
            }
        }
        return true;
    }
}
