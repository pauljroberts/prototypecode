package com.paul.prototype;

import com.paul.prototype.config.Config;
import com.paul.prototype.config.Constants;
import com.paul.prototype.model.goss.GossContent;
import com.paul.prototype.model.goss.GossContentList;
import com.paul.prototype.model.hippo.HippoImportable;
import com.paul.prototype.model.hippo.Service;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GossImporter {

    List<HippoImportable> importableItems = new ArrayList<>();
    GossContentList gossContentList = new GossContentList();
    Map<Long, String> gossContentUrlMap = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Options options = new Options();
        Option propertiesFileOption = new Option("p", "properties", true, "Properties file path.");
        propertiesFileOption.setRequired(true);
        options.addOption(propertiesFileOption);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {

            CommandLine cmd = parser.parse(options, args);

            File propertiesFile = Paths.get(cmd.getOptionValue("properties")).toFile();
            Properties properties = new Properties();
            properties.load(new FileReader(propertiesFile));
            Config.parsePropertiesFile(properties);
        }catch(MissingOptionException e){
            System.out.println(e.getMessage());
            formatter.printHelp("GossImporter", options);
            System.exit(1);
        }

        GossImporter test = new GossImporter();

    }

    private GossImporter() throws IOException {

        clean();
        JSONObject rootJsonObject = readGossExport();
        populateGossContent(rootJsonObject, null);
        pupulateGossContentJcrStructure();

        // TODO delete next.
       // for(GossContent content : gossContentList){
       //     HtmlHelper.test1(content.getText(), gossContentUrlMap);
       // }

        populateHippoContent();
        writeContent();

    }

    private void pupulateGossContentJcrStructure() {
        gossContentList.generateJcrStructure();
        for(GossContent content : gossContentList){
            gossContentUrlMap.put(content.getId(), content.getJcrPath() + content.getJcrNodeName());
        }
    }

    private void populateGossContent(JSONObject rootJsonObject, Long limit) {
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");

        int count = 0;
        for (Object childJsonObject : jsonArray) {
            if(null != limit && limit <= count){
                break;
            }
            gossContentList.add(new GossContent((JSONObject) childJsonObject));
            count++;
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
        writer.writeImportableFiles(importableItems, Paths.get(Config.CONTENT_TARGET_FOLDER));
    }

    private JSONObject readGossExport() throws IOException {
        // TODO parameterise file path

        File f = new File(Config.GOSS_CONTENT_SOURCE_FILE);
        if (!f.exists()) {
            throw new RuntimeException("File " + Config.GOSS_CONTENT_SOURCE_FILE + " does not exist.");
        }
        if (!f.isFile()) {
            throw new RuntimeException("Not a file :" + Config.GOSS_CONTENT_SOURCE_FILE);
        }

        JSONParser jsonParser = new JSONParser();
        // TODO split into reading line by line and creating goss model objects one at a time?
        String content = Constants.GOSS_EXTRACT_PREFIX;

        for (String line : Files.readAllLines(Paths.get(Config.GOSS_CONTENT_SOURCE_FILE))) {
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
            FileUtils.deleteDirectory(Paths.get(Config.CONTENT_TARGET_FOLDER).toFile());
            FileUtils.forceMkdir(Paths.get(Config.CONTENT_TARGET_FOLDER).toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not remove directory:" + Config.CONTENT_TARGET_FOLDER);
        }
    }
}
