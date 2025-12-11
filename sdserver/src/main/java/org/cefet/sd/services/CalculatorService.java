package org.cefet.sd.services;

public class CalculatorService {
    public int calculate(int firstNumber, int secondNumber) {
        while (secondNumber != 0) {
            int tmp = secondNumber;
            secondNumber = firstNumber % secondNumber;
            firstNumber = tmp;
        }
        return firstNumber;
    }
}
