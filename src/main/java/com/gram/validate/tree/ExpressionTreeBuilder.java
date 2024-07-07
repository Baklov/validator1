package com.gram.validate.tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExpressionTreeBuilder {
    public ExpressionNode buildTree(String expression) {
        expression = removeInvalidSpaces(expression);
        Deque<Character> operators = new ArrayDeque<>();
        Deque<ExpressionNode> operands = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder operand = new StringBuilder();
                boolean hasDecimalPoint = false;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    if (expression.charAt(i) == '.') {
                        if (hasDecimalPoint) {
                            throw new IllegalArgumentException("Invalid operand with multiple decimal points");
                        }
                        hasDecimalPoint = true;
                    }
                    operand.append(expression.charAt(i++));
                }
                i--;
                if (operand.charAt(operand.length() - 1) == '.') {
                    throw new IllegalArgumentException("Invalid operand ending with decimal point");
                }
                operands.push(new OperandNode(Double.parseDouble(operand.toString())));
            } else if (currentChar == '(') {
                operators.push(currentChar);
            } else if (currentChar == ')') {
                while (operators.peek() != '(') {
                    operands.push(createOperatorNode(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.pop();
            } else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && precedence(currentChar) <= precedence(operators.peek())) {
                    operands.push(createOperatorNode(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(currentChar);
            } else {
                throw new IllegalArgumentException("Invalid character in expression");
            }
        }

        while (!operators.isEmpty()) {
            operands.push(createOperatorNode(operators.pop(), operands.pop(), operands.pop()));
        }

        return operands.pop();
    }

    private boolean isOperator(char c) {
        return "+-*/".indexOf(c) >= 0;
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    private OperatorNode createOperatorNode(char operator, ExpressionNode right, ExpressionNode left) {
        OperatorNode node = new OperatorNode(operator);
        node.right = right;
        node.left = left;
        return node;
    }

    private String removeInvalidSpaces(String expression) {
        expression = expression.trim();

        final boolean[] state = {   false,  // lastWasDigit
                                    false,  // lastWasSpace,
                                    false}; // lastWasOperator

        return expression.chars()
                .mapToObj(c -> (char) c)
                .reduce(new StringBuilder(), (result, currentChar) -> {
                    if (Character.isDigit(currentChar) || currentChar == '.') {
                        if (state[1] && state[0]) {
                            throw new IllegalArgumentException("Invalid space within number");
                        }
                        result.append(currentChar);
                        state[0] = true;
                        state[1] = false;
                        state[2] = false;
                    } else if (isOperator(currentChar)) {
                        result.append(currentChar);
                        state[0] = false;
                        state[1] = false;
                        state[2] = true;
                    } else if (currentChar == '(') {
                        if (state[1] && !state[2] && result.length() > 0 && result.charAt(result.length() - 1) == '(') {
                        } else if (state[1] && !state[2]) {
                            throw new IllegalArgumentException("Invalid space before parenthesis");
                        }
                        result.append(currentChar);
                        state[0] = false;
                        state[1] = false;
                        state[2] = false;
                    } else if (currentChar == ')') {
                        if (result.length() > 0 && result.charAt(result.length() - 1) == '(') {
                        } else {
                            result.append(currentChar);
                        }
                        state[0] = false;
                        state[1] = false;
                        state[2] = false;
                    } else if (currentChar == ' ') {
                        state[1] = true;
                    } else {
                        throw new IllegalArgumentException("Invalid character in expression");
                    }
                    return result;
                }, StringBuilder::append)
                .toString();
    }
}
