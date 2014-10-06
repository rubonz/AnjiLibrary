package com.darkempire.anji.document.diagrams;

import com.darkempire.anji.annotation.AnjiUnknow;
import com.darkempire.anji.document.WriteableNode;

/**
 * Create in 19:41
 * Created by siredvin on 08.04.14.
 */
@AnjiUnknow
public interface DiagramNode extends WriteableNode {
    public String getName();

    public String getId();

    public DiagramNode getParent();
}
