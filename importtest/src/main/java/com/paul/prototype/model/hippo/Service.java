package com.paul.prototype.model.hippo;

import com.paul.prototype.misc.HtmlHelper;
import com.paul.prototype.model.goss.GossContent;

import java.util.ArrayList;
import java.util.List;

public class Service extends HippoImportable {

    private String seoSummary;
    private String title;
    private String summary;
    private String shortSummary;

    // Do not initialise HippoRichText objects.  Template needs nulls to decide on commas in list seperation
    private List<HippoRichText> topTasks;
    private HippoRichText introduction;
    private List<Section> sections;

    public Service(GossContent gossContent) {
        super(gossContent.getHeading(), gossContent.getJcrPath());
        seoSummary = gossContent.getSummary();
        title = gossContent.getHeading();
        summary = gossContent.getIntroduction();
        shortSummary = gossContent.getSummary();
        // TODO
        //topTasks = new ArrayList<>();
        //topTasks.add(new HippoRichText());
        ParsedArticleText parsedArticleText = HtmlHelper.parseGossArticleText(gossContent.getText());

        // introduction = new HippoRichText(gossContent.getText());
        //sections = new ArrayList<>();
        //sections.add(new Section("A section- not developed yet", "", new HippoRichText()));
    }

    public String getSeoSummary() {
        return seoSummary;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public List<HippoRichText> getTopTasks() {
        return topTasks;
    }

    public HippoRichText getIntroduction() {
        return introduction;
    }

    public List<Section> getSections() {
        return sections;
    }
}