package com.darkempire.anji.document;

import com.darkempire.anji.annotation.AnjiUnknow;

import java.io.PrintWriter;

/**
 * Create in 19:47
 * Created by siredvin on 08.04.14.
 */
@AnjiUnknow
public interface WriteableNode {
    public void write(PrintWriter pw);
}
