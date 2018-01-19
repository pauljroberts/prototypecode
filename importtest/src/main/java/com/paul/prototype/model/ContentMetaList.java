package com.paul.prototype.model;

import java.util.*;

public class ContentMetaList extends ArrayList<ContentMeta> {

    private Map<Long, ContentMeta> contentMetaMap;
    private Stack<Long> stack;
    private boolean sorted = false;


    public void add(Object articleId, Object articleParentId) {
        // TODO Test articleid not null and is numeric
        // TODO migration report
        Long articleIdInt = null;
        Long articleParentIdInt = null;
        if (null != articleId) {
            articleIdInt = (Long) articleId;
        }
        if (null != articleParentId) {

            if (articleParentId instanceof Long) {
                articleParentIdInt = (Long) articleParentId;
            } else {
                articleParentIdInt = null;
            }
        }

        ContentMeta contentMeta = new ContentMeta(articleIdInt, articleParentIdInt);
        this.add(contentMeta);
    }

    public void calculateDepths() {
        if (!sorted) {

            contentMetaMap = new HashMap<>();
            for (ContentMeta p : this) {
                contentMetaMap.put(p.getId(), p);
            }

            for (ContentMeta p : this) {
                if (p.getDepth() == null) {
                    stack = new Stack<>();
                    calculateDepth(p);
                }
            }

            for (ContentMeta p : this) {
                p.setDepth(contentMetaMap.get(p.getId()).getDepth());
            }
            Collections.sort(this);
        }
        sorted = true;
    }

    private void calculateDepth(ContentMeta p) {
        stack.push(p.getId());
        // Check for possible circular dependency and output something useful rather than stack overflow
        if (stack.size() > 30) {
            //log.error("Circlular dependancy");
            StringBuilder errorText = new StringBuilder();
            while (!stack.empty()) {
                errorText.append(stack.pop()).append(" : ");
            }
            //log.error(errorText.toString());
            //   throw new EtlException("Circular prerequisite dependance:" + errorText.toString());
        }

        // Already calculated as is dependency of another node
        if (p.getDepth() != null) {
            return;
        }

        // No parent
        if (p.getParentId() == null) {
            p.setDepth(1);
            return;
        }

        // Not yet calculated and has parent.  Need to go
        // down tree of parents and find longest branch.
        ContentMeta p1 = contentMetaMap.get(p.getParentId());

        if (null == p1.getDepth()) {
            calculateDepth(p1);
        }

        p.setDepth(p1.getDepth() + 1);
    }

}
