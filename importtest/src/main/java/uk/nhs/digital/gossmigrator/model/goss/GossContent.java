package uk.nhs.digital.gossmigrator.model.goss;


import uk.nhs.digital.gossmigrator.misc.GossExportHelper;
import uk.nhs.digital.gossmigrator.misc.TextHelper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.nhs.digital.gossmigrator.config.GossExportFieldNames;

import java.util.Date;

import static uk.nhs.digital.gossmigrator.misc.GossExportHelper.getBoolean;
import static uk.nhs.digital.gossmigrator.misc.GossExportHelper.getLong;
import static uk.nhs.digital.gossmigrator.misc.GossExportHelper.getString;

public class GossContent implements Comparable<GossContent> {
    private final static Logger LOGGER = LoggerFactory.getLogger(GossContent.class);

    public enum ContentType{
        SERVICE
        ,PUBLICATION
    }

    // Fields read from Goss export.
    private Long etcId;
    private String heading;
    private long id;
    private GossContentExtra extra;
    private Long templateId;
    private String summary;
    private String friendlyUrl;
    private String linkText;
    private Long parentId;
    private String introduction;
    private Date date;
    private String text;
    private String display;
    private Date archiveDate;
    // Looks like we don't need the media json array at the moment.
    private Date displayDate;
    private Date displayEndDate;


    // Non Goss sourced variables
    private Integer depth;
    private String jcrParentPath;
    private String jcrNodeName;
    private ContentType contentType;

    public GossContent(JSONObject gossJson) {
        JSONObject extraJson = (JSONObject) gossJson.get(GossExportFieldNames.EXTRA.getName());
        extra = new GossContentExtra();
        etcId = getLong(gossJson, GossExportFieldNames.ETC_ID);
        heading = getString(gossJson, GossExportFieldNames.HEADING);
        id = getLong(gossJson, GossExportFieldNames.ID);
        templateId = getLong(gossJson, GossExportFieldNames.TEMPLATE_ID);
        summary = getString(gossJson, GossExportFieldNames.SUMMARY);
        friendlyUrl = getString(gossJson, GossExportFieldNames.FRIENDLY_URL);
        linkText = getString(gossJson, GossExportFieldNames.LINK_TEXT);
        parentId = getLong(gossJson, GossExportFieldNames.PARENTID);
        introduction = getString(gossJson, GossExportFieldNames.INTRO);
        date = GossExportHelper.getDate(gossJson, GossExportFieldNames.DATE);
        text = getString(gossJson, GossExportFieldNames.TEXT);
        display = getString(gossJson, GossExportFieldNames.DISPLAY);
        archiveDate = GossExportHelper.getDate(gossJson, GossExportFieldNames.ARCHIVE_DATE);
        displayDate = GossExportHelper.getDate(gossJson, GossExportFieldNames.DISPLAY_DATE);
        displayEndDate = GossExportHelper.getDate(gossJson, GossExportFieldNames.DISPLAY_END_DATE);
        extra.setIncludeChildArticles(getBoolean(extraJson, GossExportFieldNames.EXTRA_INCLUDE_CHILD, false));
        extra.setIncludeRelatedArticles(getBoolean(extraJson, GossExportFieldNames.EXTRA_INCLUDE_RELATED, false));

        jcrNodeName = TextHelper.toLowerCaseDashedValue(heading);
        // TODO logic for content type.
        contentType = ContentType.SERVICE;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(GossContent o) {
        if (o.getDepth() > depth) return -1;
        if (depth > o.getDepth()) return 1;
        return 0;
    }

    public long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    /**
     * Reference of the extras object.  Don't think this is of use in import.
     * @return Extras Id
     */
    public Long getEtcId() {
        return etcId;
    }

    public String getHeading() {
        return heading;
    }

    public GossContentExtra getExtra() {
        return extra;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public String getSummary() {
        return summary;
    }

    public String getFriendlyUrl() {
        return friendlyUrl;
    }

    /**
     * Text seen on links to the article.
     * @return Link text.
     */
    public String getLinkText() {
        return linkText;
    }

    public String getIntroduction() {
        return introduction;
    }

    /**
     * Article creation date.
     * @return Creation date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Either set as on or off if displayed or hidden.
     * @return on, off or hidden.
     */
    public String getDisplay() {
        return display;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    /**
     * Display start date. i.e. When published.
     * @return date.
     */
    public Date getDisplayDate() {
        return displayDate;
    }

    public void setJcrParentPath(String jcrParentPath) {
        this.jcrParentPath = jcrParentPath;
    }

    public String getJcrNodeName() {
        return jcrNodeName;
    }

    /**
     * This is the raw string from the database containing each text area.
     * Each text area is separated into textbody tags with the ID property referencing its name.
     * @return Html text.
     */
    public String getText() {
        return text;
    }

    /**
     * Date representing when this article will stop displaying
     * @return
     */
    public Date getDisplayEndDate() {
        return displayEndDate;
    }

    public String getJcrPath() {
        return jcrParentPath + jcrNodeName;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "GossContent{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                '}';
    }
}
