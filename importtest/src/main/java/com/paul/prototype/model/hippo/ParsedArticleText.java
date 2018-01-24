package com.paul.prototype.model.hippo;

import java.util.List;

public class ParsedArticleText {
    private HippoRichText introduction;
    private List<Section> sections;
    private HippoRichText contactDetails;
    private List<HippoRichText> topTasks;

    public HippoRichText getIntroduction() {
        return introduction;
    }

    public void setIntroduction(HippoRichText introduction) {
        this.introduction = introduction;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public HippoRichText getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(HippoRichText contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<HippoRichText> getTopTasks() {
        return topTasks;
    }

    public void setTopTasks(List<HippoRichText> topTasks) {
        this.topTasks = topTasks;
    }
}
