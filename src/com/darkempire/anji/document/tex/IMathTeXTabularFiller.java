package com.darkempire.anji.document.tex;

import com.darkempire.math.struct.vector.IDoubleVectorProvider;

/**
 * Create in 23:58
 * Created by siredvin on 06.05.14.
 */
@Deprecated
public interface IMathTeXTabularFiller {
    public IMathTeXTabularFiller row(String... strings);

    public IMathTeXTabularFiller row(String row);

    public IMathTeXTabularFiller row(String name, IDoubleVectorProvider set);

    //public IMathTeXTabularFiller row(IVectorProvider<String> vector);

    public IMathTeXTabularFiller hline();

    public IMathTeXTabularFiller cline(int start, int end);

    public IMathTeXTabularFiller addHLine(boolean addHLine);

    public void closeTabular();
}
