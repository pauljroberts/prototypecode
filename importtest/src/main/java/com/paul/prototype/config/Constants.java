package com.paul.prototype.config;

public interface Constants {
    String JCR_ASSET_ROOT = "/content/assets/paul-assets/";
    String JCR_DOC_ROOT = "/content/documents/corporate-website/paul-root/";
    String ASSET_SOURCE_FOLDER = "/home/paul/assets/";
    String ASSET_TARGET_FOLDER = "/home/paul/assetsexport/";
    String GOSS_EXTRACT_PREFIX = "{ \"docs\":";
    String GOSS_EXTRACT_SUFFIX = "}";
    // TODO CLI param or properties file.
    String GOSS_SOURCE_FILE_PATH = "/home/paul/importtest/ExportExampleAlpha1.js";
    String CONTENT_OUTPUT_PATH = "/home/paul/testout/";
}
