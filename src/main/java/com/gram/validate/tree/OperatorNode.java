package com.gram.validate.tree;

public class OperatorNode extends ExpressionNode {
    char operator;
    ExpressionNode left;
    ExpressionNode right;

    OperatorNode(char operator) {
        this.operator = operator;
    }

    @Override
    public boolean validate() {
        if (left == null || right == null) return false;
        return left.validate() && right.validate();
    }
}
