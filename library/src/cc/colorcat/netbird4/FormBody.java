package cc.colorcat.netbird4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Created by cxx on 18-1-25.
 * xx.ch@outlook.com
 */
final class FormBody extends RequestBody {
    static FormBody create(Parameters parameters, boolean needEncode) {
        if (!needEncode) return new FormBody(parameters);
        final int size = parameters.size();
        MutableParameters encoded = MutableParameters.create(size);
        for (int i = 0; i < size; ++i) {
            String encodedName = Utils.smartEncode(parameters.name(i));
            String encodedValue = Utils.smartEncode(parameters.value(i));
            encoded.add(encodedName, encodedValue);
        }
        return new FormBody(encoded.toParameters());
    }

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    final Parameters parameters;
    private long contentLength = -1L;

    private FormBody(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String contentType() {
        return FormBody.CONTENT_TYPE;
    }

    @Override
    public long contentLength() throws IOException {
        if (contentLength == -1L) {
            long temp = writeOrCountBytes(null, true);
            if (temp > 0L) {
                contentLength = temp;
            }
        }
        return contentLength;
    }

    @Override
    public void writeTo(OutputStream output) throws IOException {
        writeOrCountBytes(output, false);
    }

    private long writeOrCountBytes(OutputStream output, boolean countBytes) throws IOException {
        long byteCount = 0L;
        OutputStream os = countBytes ? new ByteArrayOutputStream() : output;
        ByteOutputStream bos = new ByteOutputStream(os);
        for (int i = 0, size = parameters.size(); i < size; ++i) {
            if (i > 0) bos.writeByte('&');
            bos.writeUtf8(parameters.name(i));
            bos.writeByte('=');
            bos.writeUtf8(parameters.value(i));
        }
        bos.flush();
        if (countBytes) {
            byteCount = bos.size();
            bos.close();
        }
        return byteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormBody formBody = (FormBody) o;
        return Objects.equals(parameters, formBody.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        return "FormBody{" +
                "parameters=" + parameters +
                '}';
    }
}
