import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadersWriterLockTest {

    ReadersWriterLock readersWriterLock = new ReadersWriterLock();
    Integer val = new Integer(0);


    @Test
    void startRead() {
        val = new Integer(50);

        readersWriterLock.startRead();
        assertEquals(1, readersWriterLock.getWriterReaders().intValue());

        for (int i = 0; i < 9; ++i)
            readersWriterLock.startRead();
        assertEquals(10, readersWriterLock.getWriterReaders().intValue());
        for (int i = 0; i < 10; ++i)
            readersWriterLock.endRead();
    }

    @Test
    void startWrite() {
        readersWriterLock.startWrite();
        assertEquals(1 << 30, readersWriterLock.getWriterReaders().intValue());
        readersWriterLock.endWrite();
    }

    @Test
    void endRead() {
        readersWriterLock.startRead();
        readersWriterLock.startRead();
        readersWriterLock.endRead();
        readersWriterLock.endRead();
        assertEquals(0, readersWriterLock.getWriterReaders().intValue());
    }

    @Test
    void endWrite() {
        readersWriterLock.startWrite();
        readersWriterLock.endWrite();
        assertEquals(0, readersWriterLock.getWriterReaders().intValue());
    }
}