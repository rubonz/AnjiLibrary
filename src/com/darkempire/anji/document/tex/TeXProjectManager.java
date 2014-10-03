package com.darkempire.anji.document.tex;

import com.darkempire.anji.log.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 01.09.14.
 */
public class TeXProjectManager {
    public final static String mathPackageStart = "\\documentclass[a4paper,12pt,oneside,ukrainian]{article}\n" +
            "\\usepackage[T2A]{fontenc}\n" +
            "\\usepackage[utf8]{inputenc}\n" +
            "\\usepackage{amssymb,amsmath,amsthm}\n" +
            "\\usepackage[ukrainian]{babel}\n" +
            "\\usepackage[unicode,colorlinks]{hyperref}\n" +
            "\\setlength{\\parindent}{1.25cm}\n";

    private File projectDirectory;
    private List<File> additionalFiles;
    private List<TeXDocumentManager> additionalFileManagers;
    private File mainFile;
    private TeXDocumentManager mainFileManager;


    public TeXProjectManager(File projectDirectory) {
        this(projectDirectory, "main.tex");
    }

    public TeXProjectManager(File projectDirectory, String name) {
        this.projectDirectory = projectDirectory;
        mainFile = new File(projectDirectory, name);
        if (!projectDirectory.exists()) {
            Log.log(Log.debugIndex, "ASDASDASD");
        }
        if (!mainFile.exists()) {
            try {
                mainFile.createNewFile();
            } catch (IOException e) {
                Log.error(Log.texIndex, e);
            }
        }
        additionalFiles = new ArrayList<>();
        additionalFileManagers = new ArrayList<>();
        try {
            mainFileManager = new TeXDocumentManager(new PrintStream(new FileOutputStream(mainFile, false)));
        } catch (FileNotFoundException e) {
            Log.error(Log.texIndex, e);
        }
    }


    public void insertMathStart() {
        mainFileManager.getEventWriter().append(mathPackageStart).openEnvironment("document");
    }


    public File getProjectDirectory() {
        return projectDirectory;
    }

    public File getAdditionalFile(int index) {
        return additionalFiles.get(index);
    }

    public TeXDocumentManager getAdditionalFileManager(int index) {
        return additionalFileManagers.get(index);
    }

    public int getAdditionalFileCount() {
        return additionalFiles.size();
    }

    public File getMainFile() {
        return mainFile;
    }

    public int createNewPart() {
        int size = additionalFiles.size();
        String name = String.valueOf(size) + ".tex";
        File part = new File(projectDirectory, name);
        mainFileManager.getEventWriter().input(name);
        additionalFiles.add(part);
        try {
            additionalFileManagers.add(new TeXDocumentManager(new PrintStream(new FileOutputStream(part, false))));
        } catch (FileNotFoundException e) {
            Log.error(Log.texIndex, e);
        }
        return size;
    }

    public int createNewPart(String name) {
        int size = additionalFiles.size();
        File part = new File(projectDirectory, name);
        mainFileManager.getEventWriter().input(name);
        additionalFiles.add(part);
        try {
            additionalFileManagers.add(new TeXDocumentManager(new PrintStream(new FileOutputStream(part, false))));
        } catch (FileNotFoundException e) {
            Log.error(Log.texIndex, e);
        }
        return size;
    }

    public TeXDocumentManager getMainFileManager() {
        return mainFileManager;
    }

    public void close() {
        additionalFileManagers.forEach(TeXDocumentManager::close);
        mainFileManager.close();
    }

}
