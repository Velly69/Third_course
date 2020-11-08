package org.lab.first;

public class DefineHang implements Runnable{
    private static final long LONG_DELAY = 20_000;
    Result result;

    public DefineHang(Result result) {
        this.result = result;
    }

    public void run() {
        long time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time < LONG_DELAY) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(System.currentTimeMillis() - time >= LONG_DELAY){
            System.out.println("------Delay detected------");
            if(result.resultF == 0){
                System.out.println("F hangs");
            }
            else if(result.resultG == 0){
                System.out.println("G hangs");
            }
            System.exit(0);
        }
    }
}
