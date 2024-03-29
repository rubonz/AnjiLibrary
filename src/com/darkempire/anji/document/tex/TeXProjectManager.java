package com.darkempire.anji.document.tex;

import com.darkempire.anji.log.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by siredvin on 01.09.14.
 */
public class TeXProjectManager {
    public static final int texBuild = Log.addString("TeX-Build");
    private static final String[] pdfLatexRun = {"pdflatex", "-synctex=1", "-interaction=nonstopmode", "main.tex"};
    private File projectDirectory;
    private List<File> additionalFiles;
    private List<TeXDocumentManager> additionalFileManagers;
    private File mainFile;
    private TeXDocumentManager mainFileManager;

    static {
        Log.setAllow(texBuild, false);
    }

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

    public File createResourceFile(String name) {
        return new File(projectDirectory, name);
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

    //TODO:ну хочь якось зкросплатформити
    public void build() throws IOException, TeXCompileException {
        close();
        Process p = Runtime.getRuntime().exec(pdfLatexRun, null, projectDirectory);
        Scanner in = new Scanner(p.getInputStream());
        while (in.hasNextLine()) {
            String log = in.nextLine();
            if (log.startsWith("!")) {
                Log.err(texBuild, log);
                throw new TeXCompileException(log);
            } else {
                Log.log(texBuild, in.nextLine());
            }
        }
    }

    public void buildAndShow() throws IOException, TeXCompileException {
        build();
        Runtime.getRuntime().exec(new String[]{"okular", "--noraise", "--unique", "main.pdf"}, null, projectDirectory);
    }

    public void close() {
        additionalFileManagers.forEach(TeXDocumentManager::close);
        mainFileManager.close();
    }

}
