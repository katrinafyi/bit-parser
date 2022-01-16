package fyi.rina;

import java.io.IOException;
import java.io.InputStream;

public class StreamParser implements BaseParser<Byte> {
    public static final int BUFFER_SIZE = 3;

    private final InputStream input;

    private byte[] buffer;
    private int index;


    public StreamParser(InputStream input) {
        this.input = input;
        refreshBuffer();
    }

    private void refreshBuffer() {
        try {
            buffer = input.readNBytes(BUFFER_SIZE);
            index = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return index < buffer.length;
    }

    @Override
    public Byte next() {
        Byte b = peek();
        index++;
        if (index == buffer.length) {
            refreshBuffer();
        }
        return b;
    }

    @Override
    public Byte peek() {
        return buffer[index];
    }
}
