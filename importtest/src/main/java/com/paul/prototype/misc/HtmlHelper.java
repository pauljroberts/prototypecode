package com.paul.prototype.misc;


import com.paul.prototype.model.goss.GossContent;
import com.paul.prototype.model.hippo.Section;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

public class HtmlHelper {

    private enum TextSections{
        TOPTASKS("UPPERBODY")
        , CONTACT_INFO("CTA")
        , INTRO_AND_SECTIONS("__DEFAULT")
        // TODO Don't have a use for this one yet.  It does have rich data though.
        , COMPONENT("COMPONENT")
        ;

        private String id;

        TextSections(String id) {
            this.id = id;
        }
    }

    public class ParsedArticleText{
        private String introduction;
        private List<Section> sections;
        private String contactDetails;
        private List<String> topTasks;

        public ParsedArticleText(String introduction, List<Section> sections, String contactDetails, List<String> topTasks) {
            this.introduction = introduction;
            this.sections = sections;
            this.contactDetails = contactDetails;
            this.topTasks = topTasks;
        }

        public String getIntroduction() {
            return introduction;
        }

        public List<Section> getSections() {
            return sections;
        }

        public String getContactDetails() {
            return contactDetails;
        }

        public List<String> getTopTasks() {
            return topTasks;
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

    private List<String> splitTasks(Element source){
        return null;
    }

    private List<Section> splitSections(Element source){
        return null;
    }

    private String extractInroduction(Element source){
        return null;
    }

    public static void test1(String html, Map<Long, String> gossUrlMap){
        // Turn the comments into elements (so can parse)
        html = html.replace("!--", "").replace("--", "");
        Document doc = Jsoup.parse(html);
        // Jsoup library adds html + head + body tags.  Only care about body.
        Element body = doc.selectFirst("body");

        parseLinks(doc, gossUrlMap);

     //   removeComments(doc);
        // Note .wholeText removes all html tags (use this on the h2 title!).
        System.out.println("After:");
        System.out.println(doc.body().html());
        System.out.println("1st h2:");
     /*
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
        */

    }


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
