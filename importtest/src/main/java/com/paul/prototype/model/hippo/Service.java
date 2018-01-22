package com.paul.prototype.model.hippo;

import com.paul.prototype.model.goss.GossContent;

public class Service extends HippoImportable {

    private String title;
    private String summary;

    public Service(GossContent gossContent) {
        super(gossContent.getHeading(), gossContent.getJcrPath());
        this.title = gossContent.getHeading();
        this.summary = gossContent.getSummary();
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
}