package com.darkempire.anji.annotation;

import com.darkempire.anji.log.Log;
import com.darkempire.internal.anji.AnjiInternal;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by siredvin on 06.10.14.
 */
@AnjiInternal
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"*"})
public class AnjiTODOProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Log.log(Log.debugIndex, "Test1");
            URL url = AnjiTODOProcessor.class.getResource("/com/darkempire/res/alertDialog.fxml");
            Pattern pattern = Pattern.compile("file.+!");
            String path = url.toExternalForm();
            Matcher matcher = pattern.matcher(path);
            matcher.find();
            File f = new File(matcher.group().replace("/out/artifacts/AnjiLibrary/AnjiLibrary.jar!", "").replace("file:", "").replace("%20", " ") + "/todo.md");
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            pw.println("Автоматично згенерований TODO файл");
            pw.println("==================================");
            Set<? extends Element> anjiUnknow = roundEnv.getElementsAnnotatedWith(AnjiUnknow.class);
            if (anjiUnknow.size() > 0) {
                pw.println("Класи та методи, які потрібно розбірати та вирішити, що з ними робити");
                pw.println("---------------------------------------------------------------------");
                pw.println();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "Кількість AnjiUnknow елементів - " + anjiUnknow.size());
                for (Element e : anjiUnknow) {
                    pw.print("* ");
                    pw.println(parseElement(e));
                }
                pw.println();
            }
            Set<? extends Element> documentWork = roundEnv.getElementsAnnotatedWith(AnjiDocumentWork.class);
            if (documentWork.size() > 0) {
                pw.println("Класи та методи, які потрібно задокументувати");
                pw.println("---------------------------------------------------------------------");
                pw.println();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "Кількість AnjiDocumentWork елементів - " + documentWork.size());
                for (Element e : documentWork) {
                    pw.print("* ");
                    pw.println(parseElement(e));
                }
                pw.println();
            }
            Set<? extends Element> rewrire = roundEnv.getElementsAnnotatedWith(AnjiRewrite.class);
            if (rewrire.size() > 0) {
                pw.println("Класи та методи, які потрібно переписати");
                pw.println("---------------------------------------------------------------------");
                pw.println();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "Кількість AnjiRewrite елементів - " + rewrire.size());
                for (Element e : rewrire) {
                    AnjiRewrite ar = e.getAnnotation(AnjiRewrite.class);
                    String reason = ar.reason();
                    pw.print("* ");
                    if (reason.length() > 0) {
                        pw.print(parseElement(e));
                        pw.print(" : ");
                        pw.println(reason);
                    } else {
                        pw.println(parseElement(e));
                    }
                }
            }
            Set<? extends Element> experimental = roundEnv.getElementsAnnotatedWith(AnjiExperimental.class);
            if (experimental.size() > 0) {
                pw.println();
                pw.println("Класи та методи, які були введені експериментально і вимагають допрацювання");
                pw.println("---------------------------------------------------------------------");
                pw.println();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "Кількість AnjiExperimental елементів - " + experimental.size());
                for (Element e : experimental) {
                    pw.print("* ");
                    pw.println(parseElement(e));
                }
                pw.println();
            }
            Set<? extends Element> standartized = roundEnv.getElementsAnnotatedWith(AnjiStandartize.class);
            if (standartized.size() > 0) {
                pw.println();
                pw.println("Класи та методи, які потрібно стандартизувати та ввести у систему");
                pw.println("---------------------------------------------------------------------");
                pw.println();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "Кількість AnjiStandartized елементів - " + standartized.size());
                for (Element e : standartized) {
                    pw.print("* ");
                    pw.println(parseElement(e));
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage());
        }
        return true;
    }

    private String parseElement(Element e) {
        StringBuilder builder = new StringBuilder();
        switch (e.getKind()) {
            case ANNOTATION_TYPE:
            case CLASS:
            case INTERFACE:
                builder.append("Клас: ");
                TypeElement typeElement = (TypeElement) e;
                builder.append(typeElement.getQualifiedName().toString());
                break;
            case METHOD:
            case CONSTRUCTOR:
                builder.append("Метод: ");
                builder.append(((TypeElement) e.getEnclosingElement()).getQualifiedName().toString());
                builder.append(':');
                builder.append(e.getSimpleName());
                break;
        }
        return builder.toString();
    }
}
