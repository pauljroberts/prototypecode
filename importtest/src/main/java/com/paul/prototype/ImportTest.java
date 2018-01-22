package com.paul.prototype;

import com.paul.prototype.config.Constants;
import com.paul.prototype.model.goss.GossContent;
import com.paul.prototype.model.goss.GossContentList;
import com.paul.prototype.model.hippo.HippoImportable;
import com.paul.prototype.model.hippo.Service;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportTest {

    List<HippoImportable> importableItems = new ArrayList<>();
    GossContentList gossContentList = new GossContentList();

    public static void main(String[] args) throws Exception {
        ImportTest test = new ImportTest();

    }

    private ImportTest() throws IOException {

        clean();
        JSONObject rootJsonObject = readGossExport();
        populateGossContent(rootJsonObject);
        pupulateGossContentJcrStructure();
        populateHippoContent();
        writeContent();
        //JSONObject rootJsonObject = readSource();
        // populateMeta(rootJsonObject);
        // createContent(rootJsonObject);
        //JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");
        // 5 has some h2's
        //JSONObject a = (JSONObject) jsonArray.get(5);
        //HtmlHelper.test1((String) a.get("ARTICLETEXT"));
        //createAssets();
    }

    private void pupulateGossContentJcrStructure() {
        gossContentList.generateJcrStructure();
        for (GossContent cm : gossContentList) {
            System.out.println(cm.getId() + ":" + cm.getDepth() + cm.getJcrPath());
        }
    }

    private void populateGossContent(JSONObject rootJsonObject) {
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");

        for (Object childJsonObject : jsonArray) {
            gossContentList.add(new GossContent((JSONObject) childJsonObject));
        }
    }


    /*
    private void createAssets() {
        try {
            Files.list(Paths.get(Constants.ASSET_SOURCE_FOLDER)).forEach(this::createAsset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImportableFileWriter writer = new ImportableFileWriter();
        writer.writeImportableFiles(importableItems, Paths.get(Constants.ASSET_TARGET_FOLDER));
    }
    */

    /*
    private Asset createAsset(Path file) {
        Asset a = new Asset(file);
        importableItems.add(a);
        return a;
    }
    */

    private void populateHippoContent() {
        for (GossContent gossContent : gossContentList) {
            HippoImportable hippoContent = new Service(gossContent);
            importableItems.add(hippoContent);
        }
    }

    private void writeContent() {
        ImportableFileWriter writer = new ImportableFileWriter();
        writer.writeImportableFiles(importableItems, Paths.get(Constants.CONTENT_OUTPUT_PATH));
    }

    private JSONObject readGossExport() throws IOException {
        // TODO parameterise file path

        File f = new File(Constants.GOSS_SOURCE_FILE_PATH);
        if (!f.exists()) {
            throw new RuntimeException("File " + Constants.GOSS_SOURCE_FILE_PATH + " does not exist.");
        }
        if (!f.isFile()) {
            throw new RuntimeException("Not a file :" + Constants.GOSS_SOURCE_FILE_PATH);
        }

        JSONParser jsonParser = new JSONParser();
        // TODO split into reading line by line and creating goss model objects one at a time?
        String content = Constants.GOSS_EXTRACT_PREFIX;

        for (String line : Files.readAllLines(Paths.get(Constants.GOSS_SOURCE_FILE_PATH))) {
            content = content + line;
        }

        content = content + Constants.GOSS_EXTRACT_SUFFIX;
        try {
            return (JSONObject) jsonParser.parse(content);
        } catch (ParseException e) {
            throw new RuntimeException("Failed Goss JSON parsing", e);
        }
    }

    private void clean() {
        try {
            FileUtils.deleteDirectory(Paths.get(Constants.CONTENT_OUTPUT_PATH).toFile());
            FileUtils.forceMkdir(Paths.get(Constants.CONTENT_OUTPUT_PATH).toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not remove directory:" + Constants.CONTENT_OUTPUT_PATH);
        }
    }
}
