package uk.nhs.digital.gossmigrator.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static uk.nhs.digital.gossmigrator.config.Config.PropertiesEnum.*;

/**
 * Config from properties file.
 */
public class Config {
    private final static Logger LOGGER = LoggerFactory.getLogger(Config.class);

    enum PropertiesEnum{
        JCR_ASSET_ROOT_PROP("jcr.asset.root", "Root jcr path to assets. e.g. /content/assets/", false, "/content/assets/")
        ,JCR_SERVICE_DOC_ROOT_PROP("jcr.service.doc.root", "Root jcr path to services. e.g. /content/documents/corporate-website/service/", false, "/content/documents/corporate-website/service/")
        ,ASSET_SOURCE_FOLDER_PROP("assets.source.folder", "File system folder holding assets to process.", true, "")
        ,ASSET_TARGET_FOLDER_PROP("assets.target.folder", "File system folder to hold created asset json hippo import files.", true, "")
        ,GOSS_CONTENT_SOURCE_FILE_PROP("goss.content.source.file", "Path including filename to Goss export. e.g. /home/xyz/goss1.json", true, "")
        ,CONTENT_TARGET_FOLDER_PROP("content.target.folder", "File system folder to hold created content json hippo import files.", true, "")
        ;

        final String key;
        final String help;
        final boolean isMandatory;
        final String defaultValue;

        PropertiesEnum(String key, String help, boolean isMandatory, String defaultValue) {
            this.key = key;
            this.help = help;
            this.isMandatory = isMandatory;
            this.defaultValue = defaultValue;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "key='" + key + '\'' +
                    ", help='" + help + '\'' +
                    ", isMandatory=" + isMandatory +
                    ", defaultValue='" + defaultValue + '\'' +
                    '}';
        }
    }

    public static String JCR_ASSET_ROOT;
    public static String JCR_SERVICE_DOC_ROOT;
    public static String ASSET_SOURCE_FOLDER;
    public static String ASSET_TARGET_FOLDER;
    public static String GOSS_CONTENT_SOURCE_FILE;
    public static String CONTENT_TARGET_FOLDER;

    public static void parsePropertiesFile(Properties propertiesMap){
        LOGGER.info("Properties used:");
        JCR_ASSET_ROOT = getConfig(JCR_ASSET_ROOT_PROP, propertiesMap);
        JCR_SERVICE_DOC_ROOT = getConfig(JCR_SERVICE_DOC_ROOT_PROP, propertiesMap);
        ASSET_SOURCE_FOLDER = getConfig(ASSET_SOURCE_FOLDER_PROP, propertiesMap);
        ASSET_TARGET_FOLDER = getConfig(ASSET_TARGET_FOLDER_PROP, propertiesMap);
        GOSS_CONTENT_SOURCE_FILE = getConfig(GOSS_CONTENT_SOURCE_FILE_PROP, propertiesMap);
        CONTENT_TARGET_FOLDER = getConfig(CONTENT_TARGET_FOLDER_PROP, propertiesMap);
    }

    private static String getConfig(PropertiesEnum propertiesEnum, Properties propertiesMap) {
        String propertyValue = propertiesMap.getProperty(propertiesEnum.key);
        if(propertiesEnum.isMandatory && StringUtils.isEmpty(propertyValue)){
            printPropertiesHelp();
            throw new RuntimeException("Properties file must contain:" + propertiesEnum.key);
        }else if(!propertiesEnum.isMandatory && StringUtils.isEmpty(propertyValue)){
            propertyValue = propertiesEnum.defaultValue;
        }
        LOGGER.info("{}: {}", propertiesEnum.key, propertyValue);
        return propertyValue;
    }

    private static void printPropertiesHelp(){
        for(PropertiesEnum property : PropertiesEnum.values()){
            System.out.println(property.toString());
        }
    }
}
