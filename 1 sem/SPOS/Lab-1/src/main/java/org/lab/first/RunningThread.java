package org.lab.first;

import spos.lab1.demo.DoubleOps;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunningThread extends Thread{
    private static final Logger log = Logger.getLogger(RunningThread.class.getName());
    private final String functionName;
    public OptionalDouble result;
    private final int testCase;
    public boolean calculated = false;
    public boolean notUsed = true;

    public RunningThread(String functionName, int testCase) {
        this.functionName = functionName;
        this.testCase = testCase;
    }

    @Override
    public void run() {
        try{
            if(functionName.equals("F")){
                result = OptionalDouble.of(DoubleOps.funcF(testCase));
                synchronized (this){
                    if(result.isPresent()){
                        calculated = true;
                    }
                }
            }
            else if(functionName.equals("G")){
                result = OptionalDouble.of(DoubleOps.funcG(testCase));
                synchronized (this){
                    if(result.isPresent()){
                        calculated = true;
                    }
                }
            }
        } catch (InterruptedException ex){
            log.log(Level.SEVERE, "Exception: ", ex);
        }
    }
}