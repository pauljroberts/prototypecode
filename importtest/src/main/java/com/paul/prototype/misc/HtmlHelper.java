package com.paul.prototype.misc;


import com.paul.prototype.model.goss.GossContent;
import com.paul.prototype.model.hippo.HippoRichText;
import com.paul.prototype.model.hippo.ParsedArticleText;
import com.paul.prototype.model.hippo.Section;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlHelper {

    private enum TextSections {
        TOPTASKS("UPPERBODY"), CONTACT_INFO("CTA"), INTRO_AND_SECTIONS("__DEFAULT")
        // TODO Don't have a use for this one yet.  It does have rich data though.
        , COMPONENT("COMPONENT");

        private String id;

        TextSections(String id) {
            this.id = id;
        }
    }

    private static Element parseButtons(Element source){
        // Get anchor buttons and remove button class
        List<Element> anchors = source.select("a.button");
        for (Element anchor : anchors){
            System.out.println(anchor.html());
            anchor.removeClass("button");
        }
        return source;
    }

    private static Element parseLinks(Element source, Map<Long, String> gossUrlMap){
        // Get spans with attribute data-icm-arg2
        List<Element> links = source.select("span[data-icm-arg2]");
        for(Element link : links){
            System.out.println(link.html());
            Long gossId = Long.parseLong(link.attributes().get("data-icm-arg2"));
            String text = link.attributes().get("data-icm-arg2name");
            Element newLink = new Element("a").text(text).attr("href", gossUrlMap.get(1L));;
            link.replaceWith(newLink);
        }
        return source;
    }


    public static ParsedArticleText parseGossArticleText(String gossArticleText){
        ParsedArticleText result = new ParsedArticleText();

        // Turn the comments into elements (so can parse)
        gossArticleText = gossArticleText.replace("!--", "").replace("--", "");
        Document doc = Jsoup.parse(gossArticleText)
                ;
        // Jsoup library adds html + head + body tags.  Only care about body.
        Element body = doc.selectFirst("body");

        result.setIntroduction(extractIntroduction(body));
        result.setSections(extractSections(body));
        return result;
    }

    private static List<Section> extractSections(Element body) {
        Element defaultNode = body.selectFirst("#__DEFAULT");
        promoteH3s(defaultNode);

        boolean haveSections = false;
        List<Section> sections = null;
        Section section;

        while(true){
            section = extractSection(defaultNode);
            if(section != null){
                if(!haveSections){
                    sections = new ArrayList<>();
                }
                sections.add(section);
                haveSections = true;
            }else{
                // Finished with __DEFAULT node
                defaultNode.remove();
                break;
            }
        }

        return sections;
    }

    private static Section extractSection(Element defaultNode) {
        boolean haveSection = false;
        String title = null;
        StringBuilder content = new StringBuilder();

        for(Element element : defaultNode.children()){
            if("h2".equals(element.tagName()) && !haveSection){
                title = element.ownText();
                haveSection = true;
                element.remove();
            }else if("h2".equals(element.tagName())){
                // Start of new section
                break;
            }else{
                // Part of section to be processed.
                content.append(element.toString());
                element.remove();
            }
        }

        return haveSection ? new Section(title, null, new HippoRichText(content.toString())) : null;
    }

    private static void promoteH3s(Element body){
        if(body.selectFirst("h2") == null){
            // No h2's so promote any h3s
            for(Element h3 : body.select("h3")){
                h3.tagName("h2");
            }
        }
    }


    /**
     * ARTICLETEXT <textbody id="__DEFAULT">body lives here<textbody>
     Any stuff that appears before a h2 inside a textbody with id __DEFAULT. Most won't have these. (From spec).
     * @param body The Goss ARTICLETEXT.
     * @return Introduction html.
     */
    private static HippoRichText extractIntroduction(Element body) {
        Element intro = body.selectFirst("#__DEFAULT");

        boolean haveIntro = false;
        // TODO check assumption.
        // Going to assume any h2 or h3 is an immediate child of this for now.
        // Code a check.
        StringBuilder result = new StringBuilder();
        for(Element child : intro.children()){
            if ("h2".equals(child.tagName()) || "h3".equals(child.tagName())){
                // Found first h2 or h3
                break;
            }
            haveIntro = true;
            result.append(child.toString());
            // Remove the node so does not get processed as part of other sections.
            child.remove();
        }

        if(haveIntro){
            return new HippoRichText(result.toString());
        }
        return null;
    }

    /*
    public static void test1(String html, Map<Long, String> gossUrlMap){


        parseLinks(doc, gossUrlMap);

     //   removeComments(doc);
        // Note .wholeText removes all html tags (use this on the h2 title!).
        System.out.println("After:");
        System.out.println(doc.body().html());
        System.out.println("1st h2:");

        Element firstH2 = doc.selectFirst("h2");
        System.out.println(firstH2.html());

        boolean hasSibling = true;
        Element nextSibling = firstH2.nextElementSibling();
        while (hasSibling){

            if(null != nextSibling){
                if(nextSibling.is("h2")){
                    System.out.println("NEXT H2 --------------------------------------------");
                }
                System.out.println(nextSibling.html());
                nextSibling = nextSibling.nextElementSibling();
            }else{
                hasSibling = false;
            }

        }


    }*/


    public static String extractTextBeforeFirstH2OrH3(String html){
        return null;
    }


    private static void removeComments(Node node) {
        for (int i = 0; i < node.childNodeSize();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }
}
