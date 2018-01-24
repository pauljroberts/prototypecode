package com.paul.prototype.model.hippo;


import java.util.ArrayList;
import java.util.List;

public class HippoRichText {
    private String content;
    private List<HippoLinkRef> docReferences = new ArrayList<>();

    public HippoRichText(String html) {
        // TODO parse links etc.'
        this.content = html;
    }

    public String getContent() {
        return content;
    }

    public List<HippoLinkRef> getDocReferences() {
        return docReferences;
    }
}
