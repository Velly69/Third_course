package fourth.lab.a;

import fourth.lab.a.instructions.ReaderInstruction;
import fourth.lab.a.instructions.WriterInstruction;
import java.util.Random;
import static java.lang.System.*;

public class App {
    private static final String fileName = "database.txt";

    public static void main(String... args) {
        ReadWriteLock lock = new ReadWriteLock();
        Reader reader = new Reader(fileName, lock);
        Writer writer = new Writer(fileName, lock);

        try {
            out.println("Status of adding operation: " +
                    writer.changeFile(WriterInstruction.ADD, "Name" + new Random().nextInt(100),
                            "11" + (new Random().nextInt(8999) + 1000)));
            out.println("Name with number 26352673: " +
                    reader.completeSearch(ReaderInstruction.FIND_NAME_BY_NUMBER, "26352673"));
            out.println("Number of Alex: " +
                    reader.completeSearch(ReaderInstruction.FIND_NUMBER_BY_NAME, "Alex"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
