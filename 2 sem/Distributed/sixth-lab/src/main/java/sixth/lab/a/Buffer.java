package sixth.lab.a;

import java.util.concurrent.atomic.AtomicBoolean;

public class Buffer {
    private Integer[][] primary;
    private Integer[][] secondary;
    private final AtomicBoolean wasRead;
    private final AtomicBoolean wasWritten;

    public Buffer() {
        primary = secondary = null;
        wasRead = new AtomicBoolean(true);
        wasWritten = new AtomicBoolean(false);
    }

    public void putInSecondary(Integer[][] data) {
        while (!wasRead.get()) {
            Thread.yield();
        }
        primary = secondary != null ? secondary.clone() : null;
        secondary = data;
        wasRead.set(false);
        wasWritten.set(true);
    }

    public Integer[][] getFromPrimary() {
        while (!wasWritten.get()) {
            Thread.yield();
        }
        wasWritten.set(false);
        wasRead.set(true);
        return primary;
    }
}
