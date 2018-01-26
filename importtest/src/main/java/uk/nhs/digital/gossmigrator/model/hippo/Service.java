package uk.nhs.digital.gossmigrator.model.hippo;

import uk.nhs.digital.gossmigrator.misc.HtmlHelper;
import uk.nhs.digital.gossmigrator.model.goss.GossContent;

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

        ParsedArticleText parsedArticleText = HtmlHelper.parseGossArticleText(gossContent.getText(), gossContent.getId());
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