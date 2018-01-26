package cc.colorcat.netbird4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cxx on 18-1-26.
 * xx.ch@outlook.com
 */
public final class FileParser implements Parser<File> {
    public static FileParser create(File savePath) {
        final File parent = savePath.getParentFile();
        if (parent.exists() || parent.mkdirs()) {
            return new FileParser(savePath);
        }
        throw new RuntimeException("Can't create directory, " + savePath.getAbsolutePath());
    }

    public static FileParser create(String savePath) {
        return create(new File(savePath));
    }

    private final File savePath;

    private FileParser(File savePath) {
        this.savePath = savePath;
    }

    @Override
    public NetworkData<? extends File> parse(Response response) throws IOException {
        OutputStream output = null;
        try {
            output = Utils.buffered(new FileOutputStream(savePath));
            Utils.justDump(response.responseBody.stream(), output);
            return NetworkData.newSuccess(savePath);
        } finally {
            Utils.close(output);
        }
    }
}
