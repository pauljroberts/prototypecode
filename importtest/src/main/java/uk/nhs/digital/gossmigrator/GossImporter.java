package uk.nhs.digital.gossmigrator;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.nhs.digital.gossmigrator.config.Config;
import uk.nhs.digital.gossmigrator.config.Constants;
import uk.nhs.digital.gossmigrator.model.goss.GossContent;
import uk.nhs.digital.gossmigrator.model.goss.GossContentList;
import uk.nhs.digital.gossmigrator.model.hippo.HippoImportable;
import uk.nhs.digital.gossmigrator.model.hippo.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GossImporter {
    private final static Logger LOGGER = LoggerFactory.getLogger(GossImporter.class);

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
            LOGGER.info("Properties file:{}", propertiesFile);

            Properties properties = new Properties();
            properties.load(new FileReader(propertiesFile));
            Config.parsePropertiesFile(properties);
        } catch (MissingOptionException e) {
            formatter.printHelp("GossImporter", options);
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }

        new GossImporter();

    }

    private GossImporter() throws IOException {

        clean();
        JSONObject rootJsonObject = readGossExport();
        populateGossContent(rootJsonObject, null);
        populateGossContentJcrStructure();
        populateHippoContent();
        writeContent();
    }

    private void populateGossContentJcrStructure() {
        gossContentList.generateJcrStructure();
        for (GossContent content : gossContentList) {
            gossContentUrlMap.put(content.getId(), content.getJcrPath() + content.getJcrNodeName());
        }
    }

    private void populateGossContent(JSONObject rootJsonObject, Long limit) {
        LOGGER.debug("Begin populating GossContent objects.");
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");

        long count = 0;
        for (Object childJsonObject : jsonArray) {
            if (null != limit && limit <= count) {
                break;
            }
            gossContentList.add(new GossContent((JSONObject) childJsonObject, ++count));
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
        LOGGER.debug("Begin populating hippo content from Goss content.");
        for (GossContent gossContent : gossContentList) {
            HippoImportable hippoContent = null;
            switch (gossContent.getContentType()) {
                case SERVICE:
                    hippoContent = new Service(gossContent);
                    break;
                default:
                    LOGGER.warn("Goss ID:{}, Unknown content type:{}", gossContent.getId(), gossContent.getContentType());
            }
            importableItems.add(hippoContent);
        }
    }

    private void writeContent() {
        LOGGER.debug("Begin writeContent");
        ImportableFileWriter writer = new ImportableFileWriter();
        writer.writeImportableFiles(importableItems, Paths.get(Config.CONTENT_TARGET_FOLDER));
    }

    private JSONObject readGossExport() throws IOException {
        LOGGER.info("Reading Goss content file:{}", Config.GOSS_CONTENT_SOURCE_FILE);

        File f = new File(Config.GOSS_CONTENT_SOURCE_FILE);
        if (!f.exists()) {
            LOGGER.error("File " + Config.GOSS_CONTENT_SOURCE_FILE + " does not exist.");
            throw new RuntimeException("File " + Config.GOSS_CONTENT_SOURCE_FILE + " does not exist.");
        }
        if (!f.isFile()) {
            LOGGER.error("Not a file :" + Config.GOSS_CONTENT_SOURCE_FILE);
            throw new RuntimeException("Not a file :" + Config.GOSS_CONTENT_SOURCE_FILE);
        }

        JSONParser jsonParser = new JSONParser();

        String content = Constants.GOSS_EXTRACT_PREFIX;

        for (String line : Files.readAllLines(Paths.get(Config.GOSS_CONTENT_SOURCE_FILE))) {
            content = content + line;
        }

        content = content + Constants.GOSS_EXTRACT_SUFFIX;
        try {
            return (JSONObject) jsonParser.parse(content);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Failed Goss JSON parsing", e);
        }
    }

    private void clean() {
        try {
            LOGGER.debug("Recreating content output folder:{}", Config.CONTENT_TARGET_FOLDER);
            FileUtils.deleteDirectory(Paths.get(Config.CONTENT_TARGET_FOLDER).toFile());
            FileUtils.forceMkdir(Paths.get(Config.CONTENT_TARGET_FOLDER).toFile());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Could not remove directory:" + Config.CONTENT_TARGET_FOLDER);
        }
    }
}
