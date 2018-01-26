package uk.nhs.digital.gossmigrator.misc;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.nhs.digital.gossmigrator.model.goss.enums.GossExportFieldNames;

import java.text.ParseException;
import java.util.Date;

public class GossExportHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(GossExportHelper.class);

    public static Long getLong(JSONObject gossJson, GossExportFieldNames fieldName, long gossId) {
        Object value = gossJson.get(fieldName.getName());

        if (value instanceof Long) {
            return (Long) value;
        } else {
            LOGGER.warn("Goss Id:{}, Field:{}, Value present:{}. Expected Long value, but was not there. "
                    , gossId, fieldName, value.toString());
            return null;
        }
    }

    public static Long getIdOrError(JSONObject gossJson, GossExportFieldNames fieldName) {
        Object value = gossJson.get(fieldName.getName());

        if (value instanceof Long) {
            return (Long) value;
        } else {
            throw new RuntimeException("Missing Id.  Cannot process");
        }
    }

    /**
     * Gets String value of a node in Goss export Json record.
     *
     * @param gossJson  Goss content Json.
     * @param fieldName Node name to parse.
     * @param gossId    Goss article id.  For logging.
     * @return Node value.
     */
    public static String getString(JSONObject gossJson, GossExportFieldNames fieldName, long gossId) {
        Object nodeValue = gossJson.get(fieldName.getName());
        if (nodeValue instanceof String) {
            String stringValue = (String) nodeValue;
            if(fieldName.isMandatory() && StringUtils.isEmpty(stringValue)){
                LOGGER.error("Goss Id:{}, Field:{}. Expected value.  Was Empty.", gossId, fieldName);
            }
            return stringValue;
        } else {
            LOGGER.error("Goss Id:{}, FieldName:{}, Value:{}. Expected String.  Got something else."
                    , gossId, fieldName, nodeValue);
        }
        return null;
    }

    public static final String DF = "MMM, dd yyyy HH:mm:ss Z";

    /**
     * Parses a date String in Goss export format into java Date.
     *
     * @param gossJson  Raw goss export Json.
     * @param fieldName Node in Json to parse.
     * @param gossId    Goss Article Id of row. For logging.
     * @return The parsed date.
     */
    public static Date getDate(JSONObject gossJson, GossExportFieldNames fieldName, long gossId) {
        // May, 23 2016 16:03:33 +0100 (Goss date example)
        String date = (String) gossJson.get(fieldName.getName());
        Date d = null;
        try {
            if(fieldName.isMandatory() && StringUtils.isEmpty(date)){
                LOGGER.error("Goss Id:{}, Field:{}. Was Empty. Expected a value.", gossId, fieldName);
                return null;
            }
            d = DateUtils.parseDate(date, DF);
        } catch (ParseException e) {
            LOGGER.error("Goss Id:{}, Field:{}, Value:{}. Could not parse date.", gossId, fieldName, date);
        }

        return d;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean getBoolean(JSONObject gossJson, GossExportFieldNames fieldName, boolean defaultValue) {
        String value = (String) gossJson.get(fieldName.getName());
        if ("true".equals(value))
            return true;
        if ("false".equals(value))
            return false;
        return defaultValue;
    }


}
