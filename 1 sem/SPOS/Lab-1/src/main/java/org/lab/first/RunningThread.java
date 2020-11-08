package org.lab.first;

import spos.lab1.demo.DoubleOps;

public class RunningThread extends Thread{
    String functionName;
    int delayF;
    int delayG;
    Result result;
    int testCase;
    public boolean needPause;

    public RunningThread(String functionName, int delayF, int delayG, Result result, int testCase) {
        this.functionName = functionName;
        this.delayF = delayF;
        this.delayG = delayG;
        this.result = result;
        this.testCase = testCase;
    }

    @Override
    public void run() {
        try{
            if(functionName.equals("F")){
                result.resultF = DoubleOps.funcF(testCase);
                synchronized (this){
                    while (needPause){
                        wait();
                    }
                    if(result.resultF == 0){
                        System.out.println("Result is 0, because the function F is 0");
                        System.exit(0);
                    }
                    System.out.println("The result of function F is " + result.resultF);
                }
                Thread.sleep(delayF);
            }
            else if(functionName.equals("G")){
                result.resultG = DoubleOps.funcG(testCase);
                synchronized (this){
                    while (needPause){
                        wait();
                    }
                    if(result.resultG == 0){
                        System.out.println("Result is 0, because the function G is 0");
                        System.exit(0);
                    }
                    System.out.println("The result of function G is " + result.resultG);
                }
                Thread.sleep(delayG);
            } else {
                synchronized(this) {
                    while(needPause) {
                        wait();
                    }
                    System.out.println("Unknown function...");
                }
                return;
            }

            Thread.sleep(2000);
            synchronized (this){
                while (needPause){
                    wait();
                }
                if(result.result == 0){
                    result.calculateResult();
                }
            }
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void suspendThread(){
        needPause = true;
    }

    public synchronized void resumeThread(){
        needPause = false;
        this.notify();
    }
}
