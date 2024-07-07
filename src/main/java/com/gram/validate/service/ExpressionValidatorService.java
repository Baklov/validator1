package com.gram.validate.service;
import com.gram.validate.tree.ExpressionNode;
import com.gram.validate.tree.ExpressionTreeBuilder;
import org.springframework.stereotype.Service;

@Service
public class ExpressionValidatorService {

    private final ExpressionTreeBuilder treeBuilder = new ExpressionTreeBuilder();

    public boolean validate(String expression) {
        try {
            ExpressionNode root = treeBuilder.buildTree(expression);
            return root.validate();
        } catch (Exception e) {
            return false;
        }

    }

}

