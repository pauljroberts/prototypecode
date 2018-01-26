package uk.nhs.digital.gossmigrator.model.hippo;

public class HippoLinkRef {
    private String nodeName;
    private String jcrPath;

    public HippoLinkRef(String jcrPath, String nodeName) {
        this.nodeName = nodeName;
        this.jcrPath = jcrPath;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getJcrPath() {
        return jcrPath;
    }
}
