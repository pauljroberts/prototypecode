package com.paul.prototype.model.goss;


import com.paul.prototype.config.Constants;
import com.paul.prototype.misc.GossExportHelper;
import com.paul.prototype.misc.TextHelper;
import org.json.simple.JSONObject;

import java.util.Date;

import static com.paul.prototype.config.GossExportFieldNames.*;
import static com.paul.prototype.misc.GossExportHelper.*;

public class GossContent implements Comparable<GossContent> {
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
    private String display;
    private Date archiveDate;
    // Looks like we don't need the media json array at the moment.
    private Date displayDate;


    // Non Goss sourced variables
    private Integer depth;
    private String jcrPath;
    private String jcrNodeName;

    public GossContent(JSONObject gossJson) {
        JSONObject extraJson = (JSONObject) gossJson.get(EXTRA.getName());
        extra = new GossContentExtra();
        etcId = getLong(gossJson, ETC_ID);
        heading = getString(gossJson, HEADING);
        id = getLong(gossJson, ID);
        templateId = getLong(gossJson, TEMPLATE_ID);
        summary = getString(gossJson, SUMMARY);
        friendlyUrl = getString(gossJson, FRIENDLY_URL);
        linkText = getString(gossJson, LINK_TEXT);
        parentId = getLong(gossJson, PARENTID);
        introduction = getString(gossJson, INTRO);
        date = GossExportHelper.getDate(gossJson, DATE);
        display = getString(gossJson, DISPLAY);
        archiveDate = GossExportHelper.getDate(gossJson, ARCHIVE_DATE);
        displayDate = GossExportHelper.getDate(gossJson, DISPLAY_DATE);
        extra.setIncludeChildArticles(getBoolean(extraJson, EXTRA_INCLUDE_CHILD, false));
        extra.setIncludeRelatedArticles(getBoolean(extraJson, EXTRA_INCLUDE_RELATED, false));

        jcrNodeName = TextHelper.toLowerCaseDashedValue(heading);
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

    public String getLinkText() {
        return linkText;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Date getDate() {
        return date;
    }

    public String getDisplay() {
        return display;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public Date getDisplayDate() {
        return displayDate;
    }

    public String getJcrPath() {
        return jcrPath;
    }

    public void setJcrPath(String jcrPath) {
        this.jcrPath = jcrPath;
    }

    public String getJcrNodeName() {
        return jcrNodeName;
    }
}
