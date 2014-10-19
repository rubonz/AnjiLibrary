package com.darkempire.math.solver.geneticsolver;

import net.sourceforge.evoj.core.annotation.ListParams;
import net.sourceforge.evoj.core.annotation.MutationRange;
import net.sourceforge.evoj.core.annotation.Range;

import java.util.List;

/**
 * Created by Сергій on 19.10.2014.
 */
public interface Solution {

    @ListParams(length = "itemsCount",
                elementRange = @Range(min = "minElement", max = "maxElement"),
                elementMutationRange = @MutationRange(value = "10%", gaussian = "false"))
    List<Double> getX();
}
