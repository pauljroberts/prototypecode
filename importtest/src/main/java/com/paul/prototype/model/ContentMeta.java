package com.paul.prototype.model;


import java.util.Comparator;

public class ContentMeta implements Comparable<ContentMeta>{
    long id;
    Long parentId;
    Integer depth;

    public ContentMeta(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    @Override
    public int compareTo(ContentMeta o) {
        if (o.getDepth() > depth) return -1;
        if (depth > o.getDepth()) return 1;
        return 0;
    }
}
