package com.darkempire.anji.document.tex;

/**
 * Created by siredvin on 10.07.14.
 */
public class TeXEnvironmentObject implements ITeXObject {
    private ITeXObject object;
    private String environment;

    public TeXEnvironmentObject(ITeXObject object, String environment) {
        this.object = object;
        this.environment = environment;
    }

    @Override
    public void write(TeXEventWriter out) {
        out.openEnvironment(environment);
        object.write(out);
        out.closeEnvironment();
    }
}
