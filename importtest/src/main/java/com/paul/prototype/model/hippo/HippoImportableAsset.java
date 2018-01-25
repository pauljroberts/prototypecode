package com.paul.prototype.model.hippo;

import com.paul.prototype.config.Config;
import com.paul.prototype.config.Constants;

import java.nio.file.Path;
import java.nio.file.Paths;

// public class HippoImportableAsset extends HippoImportable {
public class HippoImportableAsset {

    int assetPathPrefixNameCount = Paths.get(Config.ASSET_SOURCE_FOLDER).getNameCount();

    public HippoImportableAsset(Path fileToExportPath) {
      /*  super();

        Path fromSource = fileToExportPath.subpath(assetPathPrefixNameCount, fileToExportPath.getNameCount());
        setJcrPath(fromSource.toString());
        setJcrNodeName(fromSource.getFileName().toString());
        setLocalizedName(fromSource.getFileName().toString());
        */
    }


}
