package org.lab.first;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Testing {
    public int testCase;
    public boolean cancellationByKey = false; //change to true if you want first type of cancellation
    private static final long DELAY_BETWEEN_PROMPT = 3000;

    public Testing(int x) {
        this.testCase = x - 1;
    }

    public void startTest() throws InterruptedException {
        switch (testCase + 1){
            case 1:
                createFunction(3000, 5000);
                break;
            case 2:
                createFunction(5000, 3000);
                break;
            case 3:
                createFunction(3000, 0);
                break;
            case 4:
                createFunction(0, 3000);
                break;
            case 5:
                createFunction(3000, 0);
                break;
            case 6:
                createFunction(0, 3000);
                break;
            default:
                System.out.println("Input error");
                System.exit(0);
        }
    }
    private void createFunction(int delayF, int delayG) throws InterruptedException {
        if(cancellationByKey){
            runMenuEsc();
        }
        Result result = new Result();
        checkHanging(result);
        RunningThread threadF = new RunningThread("F", delayF, delayG, result, testCase);
        threadF.start();
        RunningThread threadG = new RunningThread("G", delayF, delayG, result, testCase);
        threadG.start();
        if(!cancellationByKey){
            showDialog(threadF, threadG, result);
        }
        while (cancellationByKey) {
            if (!threadF.isAlive() && !threadG.isAlive()) {
                System.exit(0);
            }
        }
    }

    public static void runMenuEsc() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }

    private void showDialog(RunningThread threadF, RunningThread threadG, final Result result) throws InterruptedException {
        System.out.println("--SELECTION WINDOW--");
        Thread promptThread;
        long time1 = System.currentTimeMillis();
        long time2;
        final AtomicInteger choice = new AtomicInteger();
        choice.set(0);
        boolean promptIsRequired = true;

        while (promptIsRequired){
            time2 = System.currentTimeMillis();
            if( promptIsRequired && (time2 - time1 >= DELAY_BETWEEN_PROMPT)){
                System.out.println("Delay between prompts: " + (time2 - time1));
                pauseThread(threadF);
                pauseThread(threadG);
                promptThread = new Thread(() -> {
                    choice.set(Dialog.makeChoice());
                });
                promptThread.start();
                promptThread.join();
                if(choice.get() == 3){
                    System.out.println("Interrupted by choice");
                    if(result.result != 0){
                        System.out.println("Result calculated");
                        System.out.println(result.result + "is the result of " + result.resultF + " * " + result.resultG);
                    }
                    System.exit(0);
                } else if(choice.get() == 2){
                    promptIsRequired = false;
                } else if(choice.get() == 1){
                    time1 = System.currentTimeMillis();
                }
                unpauseThread(threadF);
                unpauseThread(threadG);
            }
            if (!threadF.isAlive() && !threadG.isAlive()) {
                System.exit(0);
            }
        }
    }

    public void pauseThread(RunningThread thread){
        thread.suspendThread();
    }

    public synchronized void unpauseThread(RunningThread thread) {
        thread.resumeThread();
    }

    private void checkHanging(Result result) {
        Thread hang = new Thread(new DefineHang(result));
        hang.start();
    }
}
