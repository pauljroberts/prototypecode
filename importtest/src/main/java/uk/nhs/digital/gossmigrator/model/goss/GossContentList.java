package uk.nhs.digital.gossmigrator.model.goss;

import uk.nhs.digital.gossmigrator.config.Config;

import java.util.*;

public class GossContentList extends ArrayList<GossContent> {

    private Map<Long, GossContent> contentMetaMap;
    private Stack<Long> stack;
    private boolean sorted = false;

    public void generateJcrStructure() {
        if (!sorted) {

            contentMetaMap = new HashMap<>();
            for (GossContent p : this) {
                contentMetaMap.put(p.getId(), p);
            }

            for (GossContent p : this) {
                if (p.getDepth() == null) {
                    stack = new Stack<>();
                    calculateDepth(p);
                }
            }

            for (GossContent p : this) {
                p.setDepth(contentMetaMap.get(p.getId()).getDepth());
            }
            Collections.sort(this);
        }
        sorted = true;
    }

    private void calculateDepth(GossContent p) {
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
        if (p.getParentId() == null || p.getParentId().intValue() == p.getId()) {
            p.setDepth(1);
            p.setJcrParentPath(Config.JCR_SERVICE_DOC_ROOT);
            return;
        }

        // Not yet calculated and has parent.  Need to go
        // down tree of parents and find longest branch.
        GossContent p1 = contentMetaMap.get(p.getParentId());

        if (null == p1.getDepth()) {
            calculateDepth(p1);
        }

        p.setDepth(p1.getDepth() + 1);
        p.setJcrParentPath(p1.getJcrPath() + "/");
    }

}
