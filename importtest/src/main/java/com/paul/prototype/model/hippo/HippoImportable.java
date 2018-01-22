package com.paul.prototype.model.hippo;

import com.paul.prototype.misc.TextHelper;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import static com.paul.prototype.misc.TextHelper.toLowerCaseDashedValue;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public abstract class HippoImportable {

    private final String localizedName;
    private String jcrNodeName;
    private String jcrPath;

    protected HippoImportable(final String localizedName, final String jcrPath) {
        this.localizedName = localizedName;
        this.jcrNodeName = toLowerCaseDashedValue(getLocalizedName());
        this.jcrPath = jcrPath;
    }

    /**
     * Absolute path locating a node in JCR repository, for example
     * '{@code /content/documents/corporate-website/publication-system/my-publication}'
     */
    public String getJcrPath() {
        return jcrPath;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getJcrNodeName() {
        return jcrNodeName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }
}
