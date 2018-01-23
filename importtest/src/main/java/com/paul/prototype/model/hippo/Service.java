package com.paul.prototype.model.hippo;

import com.paul.prototype.model.goss.GossContent;

import java.util.ArrayList;
import java.util.List;

public class Service extends HippoImportable {

    // TODO move this into own class
    public class RichText{
        private String content;
        private List<String> docReferences = new ArrayList<>();

        public RichText() {
            content = "Rich content not developed yet";
            docReferences.add("\\content\\stuff");
            docReferences.add("\\2ndref");
        }

        public String getContent() {
            return content;
        }

        public List<String> getDocReferences() {
            return docReferences;
        }
    }

    private String seoSummary;
    private String title;
    private String summary;
    private String shortSummary;
    private List<RichText> topTasks = new ArrayList<>();


    public Service(GossContent gossContent) {
        super(gossContent.getHeading(), gossContent.getJcrPath());
        seoSummary = gossContent.getSummary();
        title = gossContent.getHeading();
        summary = gossContent.getIntroduction();
        shortSummary = gossContent.getSummary();
        // TODO
        topTasks.add(new RichText());
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

    public List<RichText> getTopTasks() {
        return topTasks;
    }
}