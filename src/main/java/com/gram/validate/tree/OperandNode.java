package com.gram.validate.tree;

public class OperandNode extends ExpressionNode {
    double value;

    OperandNode(double value) {
        this.value = value;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
