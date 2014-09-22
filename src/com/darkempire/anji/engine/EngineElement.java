package com.darkempire.anji.engine;

/**
 * Created by siredvin on 27.12.13.
 */
public interface EngineElement {
    public void update();

    public void stop();

    public void init();

    public void start();

    public Engine getEngine();
}
