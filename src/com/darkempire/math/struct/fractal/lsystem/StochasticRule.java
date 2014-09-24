package com.darkempire.math.struct.fractal.lsystem;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

/**
 * Created with IntelliJ IDEA.
 * User: siredvin
 * Date: 26.10.13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class StochasticRule implements IRule {
    private static class ProbabilisticRule {
        private double p;
        private String rule;

        public ProbabilisticRule(String rule, double p) {
            this.p = p;
            this.rule = rule;
        }

        public String getRule() {
            return rule;
        }

        public double getP() {
            return p;
        }
    }

    private double one;
    private List<ProbabilisticRule> rules;

    public StochasticRule() {
        this.one = 0;
        this.rules = new ArrayList<>();
        ;
    }

    public void addRule(String rule, double p) {
        rules.add(new ProbabilisticRule(rule, p));
        one += p;
    }

    @Override
    public String getRule() {
        double p = random() * one;
        String result = "";
        for (ProbabilisticRule rule : rules) {
            if (p < rule.getP()) {
                result = rule.getRule();
                break;
            } else {
                p -= rule.getP();
            }
        }
        return result;
    }
}
