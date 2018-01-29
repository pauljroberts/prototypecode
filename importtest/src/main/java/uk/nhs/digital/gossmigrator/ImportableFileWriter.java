package uk.nhs.digital.gossmigrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.nhs.digital.gossmigrator.config.Constants;
import uk.nhs.digital.gossmigrator.model.hippo.HippoImportable;
import freemarker.core.JSONOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import uk.nhs.digital.gossmigrator.misc.TextHelper;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static uk.nhs.digital.gossmigrator.config.Constants.OUTPUT_FILE_TYPE_SUFFIX;

public class ImportableFileWriter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImportableFileWriter.class);

    private static Configuration cfg;

    void writeImportableFiles(final List<? extends HippoImportable> importableItems,
                              final Path targetDir) {
        LOGGER.info("Writing content to:{}", targetDir);

        for (int i = 1; i <= importableItems.size(); i++) {

            final HippoImportable importableItem = importableItems.get(i - 1);

            writeImportableFile(
                    importableItem,
                    getFileName(i, importableItem),
                    targetDir
            );
        }
        LOGGER.info("Wrote {} files.", importableItems.size());
    }

    private void writeImportableFile(final HippoImportable importableItem,
                                     final String fileName,
                                     final Path targetDir) {

        try {

            Path targetFilePath = Paths.get(targetDir.toString(), fileName);

            final String itemTypeName = importableItem.getClass().getSimpleName().toLowerCase();

            final Template template = getFreemarkerConfiguration()
                    .getTemplate(itemTypeName + ".json.ftl");

            final Writer writer = new StringWriter();

            template.process(new HashMap<String, Object>() {{
                put(itemTypeName, importableItem);
            }}, writer);

            final String importableFileContent = writer.toString();

            LOGGER.debug("Writing:{}", targetFilePath);
            Files.write(targetFilePath, importableFileContent.getBytes());

        } catch (final Exception e) {
            // If we fail with one file, make a note of the document that failed and carry on
            //   migrationReport.logError(e, "Failed to write out item:", "Item will not be imported", importableItem.toString());

            LOGGER.error("Failed writing file:{}" + importableItem.toString(), e);
        }
    }

    private static Configuration getFreemarkerConfiguration() {
        if (cfg == null) {
            cfg = new Configuration(Configuration.VERSION_2_3_26);
            cfg.setClassForTemplateLoading(ImportableFileWriter.class, "/templates");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setOutputFormat(JSONOutputFormat.INSTANCE);
        }

        return cfg;
    }

    private static String getFileName(final int i, final HippoImportable importableItem) {
        return String.format(
                "%06d%s_%s%s_%s" + OUTPUT_FILE_TYPE_SUFFIX,
                i,
                StringUtils.leftPad("", 1, '_'),
                importableItem.getClass().getSimpleName().toUpperCase(),
                "",
                TextHelper.toLowerCaseDashedValue(importableItem.getLocalizedName())
        );
    }

}
