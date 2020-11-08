package org.lab.first;

public class Result {
    public double resultF;
    public double resultG;
    public double result;

    public Result() {}

    public double binaryOperation(double f, double g){
        return f * g;
    }

    public synchronized void calculateResult() throws InterruptedException {
        while((resultF == 0) || (resultG == 0)){
            this.wait();
        }
        result = binaryOperation(resultF, resultG);
        System.out.println(result + " is the result of " + resultF + " * " + resultG);
        this.notify();
    }
}
