package com.paul.prototype.model;

import java.util.List;

public class Publication extends HippoImportableItem {

    private List<Section> sections;
    private final String title;
    private final String summary;
    private String introduction;

    public Publication(final Folder parent,
                       final String name,
                       final String title,
                       final String body,
                       final String summary
    ) {
        super(parent, name);
        this.title = title;
        this.summary = summary;
        introduction = extractIntroduction(body);
        sections = extractSections(body);
    }

    private List<Section> extractSections(String body) {
        return null;
    }

    private String extractIntroduction(String body) {
        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getIntroduction() {
        return introduction;
    }
}