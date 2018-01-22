package com.paul.prototype.model.hippo;

import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Asset extends HippoImportableAsset {

    String filePath;
    String lastModifiedDate;

    public Asset(Path sourceFile) {
        super(sourceFile);
        this.filePath = "file:///" + sourceFile.toString();
        lastModifiedDate = "2018-01-19T10:07:03.592Z";
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getMimeType() {
        try {
            Tika tika = new Tika();
            return tika.detect(Paths.get(getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
