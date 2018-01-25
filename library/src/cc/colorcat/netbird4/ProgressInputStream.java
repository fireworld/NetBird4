package cc.colorcat.netbird4;

import java.io.*;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class ProgressInputStream extends FilterInputStream {
    static InputStream of(File file, ProgressListener listener) throws FileNotFoundException {
        return of(new FileInputStream(file), file.length(), listener);
    }

    static InputStream of(InputStream input, long contentLength, ProgressListener listener) {
        if (contentLength > 0L && listener != null) {
            return new ProgressInputStream(input, contentLength, listener);
        }
        return input;
    }


    private long contentLength;
    private ProgressListener listener;

    private ProgressInputStream(InputStream in, long contentLength, ProgressListener listener) {
        super(in);
        this.contentLength = contentLength;
        this.listener = listener;
    }

    @Override
    public int read() throws IOException {
        int nextByte = in.read();
        if (nextByte != -1) {
            updateProgress(1);
        }
        return nextByte;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = in.read(b, off, len);
        if (count != -1) {
            updateProgress(count);
        }
        return count;
    }

    private long finished = 0L;
    private int currentPercent = 0;
    private int lastPercent = currentPercent;

    private void updateProgress(int readCount) {
        finished += readCount;
        currentPercent = (int) (finished * 100L / contentLength);
        if (currentPercent > lastPercent) {
            listener.onChanged(finished, contentLength, currentPercent);
            lastPercent = currentPercent;
        }
    }
}
