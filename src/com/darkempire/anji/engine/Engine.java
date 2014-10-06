package com.darkempire.anji.engine;

import com.darkempire.anji.annotation.AnjiUnknow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siredvin on 26.12.13.
 */
@AnjiUnknow
public abstract class Engine {
    private List<Process> processList;
    private List<EngineElement> elementList;

    public Engine() {
        processList = new ArrayList<>();
        elementList = new ArrayList<>();
    }

    public void addProcess(Process p) {
        this.processList.add(p);
    }

    public void addElementList(EngineElement element) {
        elementList.add(element);
    }

    public void process() {
        processList.forEach(Process::process);
    }

    public void update() {
        elementList.forEach(EngineElement::update);
    }

    public void init() {
        elementList.forEach(EngineElement::init);
    }

    public void start() {
        elementList.forEach(EngineElement::start);
    }

    public void stop() {
        elementList.forEach(EngineElement::stop);
    }
}
