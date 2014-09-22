package com.darkempire.math.struct;

import com.darkempire.anji.document.tex.ITeXObject;
import com.darkempire.anji.document.tex.TeXEventWriter;
import com.darkempire.math.struct.function.LinearMultiPolynomial;
import com.darkempire.math.struct.logic.SimpleLinearBoundConditional;
import com.darkempire.math.struct.vector.IDoubleVectorProvider;

import java.util.Iterator;
import java.util.List;

/**
 * Created by siredvin on 02.07.14.
 */
public class SimpleLinearOptimizeTask implements Iterable<SimpleLinearBoundConditional>, ITeXObject {
    private boolean isMaximize;
    private LinearMultiPolynomial targetFunction;
    private List<SimpleLinearBoundConditional> conditionals;

    public SimpleLinearOptimizeTask(boolean isMaximize, LinearMultiPolynomial targetFunction, List<SimpleLinearBoundConditional> conditionals) {
        this.isMaximize = isMaximize;
        this.targetFunction = targetFunction;
        this.conditionals = conditionals;
    }

    public double calcTargetFunction(IDoubleVectorProvider x) {
        return targetFunction.calc(x);
    }

    public boolean isBounded(IDoubleVectorProvider x) {
        boolean result = true;
        for (SimpleLinearBoundConditional conditional : conditionals) {
            if (!conditional.calc(x)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<SimpleLinearBoundConditional> iterator() {
        return conditionals.iterator();
    }

    public List<SimpleLinearBoundConditional> getConditionalList() {
        return conditionals;
    }

    public boolean isMaximize() {
        return isMaximize;
    }

    public LinearMultiPolynomial getTargetFunction() {
        return targetFunction;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isMaximize) {
            builder.append("maximize->");
        } else {
            builder.append("minimize->");
        }
        builder.append(targetFunction);
        builder.append('\n');
        for (SimpleLinearBoundConditional conditional : conditionals) {
            builder.append(conditional);
            builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public void write(TeXEventWriter out) {
        out.eqnarray();
        out.text(isMaximize ? "&\\max\\{" : "&\\min\\{");
        targetFunction.write(out);
        out.append("\\}");
        out.newline();
        for (SimpleLinearBoundConditional conditional : conditionals) {
            out.text("&");
            conditional.write(out);
            out.newline();
        }
        out.closeEnvironment();
    }
}
