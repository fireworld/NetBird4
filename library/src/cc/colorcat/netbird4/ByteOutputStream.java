package cc.colorcat.netbird4;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class ByteOutputStream extends FilterOutputStream {
    private long written = 0L;

    ByteOutputStream(OutputStream out) {
        super(out);
    }

    private void incCount(int value) {
        long temp = written + value;
        if (temp < 0L) {
            temp = Long.MAX_VALUE;
        }
        written = temp;
    }

    @Override
    public synchronized void write(int b) throws IOException {
        out.write(b);
        incCount(1);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        incCount(len);
    }

    public void writeUtf8(String s) throws IOException {
        byte[] bytes = s.getBytes(Utils.UTF8);
        this.write(bytes, 0, bytes.length);
    }

    public void writeByte(char c) throws IOException {
        this.write(c);
    }

    public synchronized long size() {
        return written;
    }
}
