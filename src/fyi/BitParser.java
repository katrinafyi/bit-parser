package fyi.rina;

public class BitParser implements Parser<Boolean> {
    public static final int BUFFER_SIZE = 11;


    private final BaseParser<Byte> byteParser;

    // bit buffer as unsigned integer. oldest bits are in MSB position.
    private long buffer = 0;
    // number of bits remaining in bit buffer.
    private int bufferLen = 0;

    public BitParser(BaseParser<Byte> byteParser) {
        this.byteParser = byteParser;
        refreshBuffer();
    }

    private void refreshBuffer() {
        while (bufferLen + 8 <= BUFFER_SIZE && byteParser.hasNext()) {
            buffer = (buffer << 8) | byteParser.next();
            bufferLen += 8;
        }
    }

    @Override
    public boolean hasNext() {
        return bufferLen > 0;
    }

    @Override
    public Boolean next() {
        return nextManyInt(1) == 1;
    }

    @Override
    public Boolean peek() {
        return peekManyInt(1) == 1;
    }

    public long nextManyInt(int n) {
        long b = peekManyInt(n);
        bufferLen -= n;
        buffer &= (1L << bufferLen) - 1;

        if (bufferLen == 0) {
            refreshBuffer();
        }
        return b;
    }

    public long peekManyInt(int n) {
        assert 0 < n && n <= BUFFER_SIZE - 7;
        if (n > bufferLen) {
            refreshBuffer();
        }
        if (n > bufferLen) {
            throw new RuntimeException(
                    "unable to fetch sufficient bits (required "
                    + n + " have " + bufferLen + ")");
        }

        return buffer >>> (bufferLen - n);
    }

    private Boolean[] intToBools(long x, int len) {
        Boolean[] arr = new Boolean[len];
        for (int i = len-1; i >= 0; i--) {
            arr[i] = (x & 1) == 1;
            x >>>= 1;
        }
        return arr;
    }

    @Override
    public Boolean[] nextMany(int n) {
        return intToBools(nextManyInt(n), n);
    }

    @Override
    public Boolean[] peekMany(int n) {
        return intToBools(peekManyInt(n), n);
    }
}
