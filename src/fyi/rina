package fyi.rina;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        InputStream f = Files.newInputStream(Path.of("test.txt"));
        StreamParser sp = new StreamParser(f);

        int i = 0;
        while (sp.hasNext()) {
            byte b = sp.next();
            System.out.println(i + ": " + Integer.toBinaryString(b) + " " + (char)b);
            i++;
        }

        f = Files.newInputStream(Path.of("test.txt"));
        BitParser bp = new BitParser(new StreamParser(f));
        while (true)
            System.out.println(Arrays.toString(bp.nextMany(3)));
    }
}
