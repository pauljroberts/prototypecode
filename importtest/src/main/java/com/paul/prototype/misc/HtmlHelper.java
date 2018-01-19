package com.paul.prototype.misc;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class HtmlHelper {

    public static String testString = "<html><!--textbody id=\\\"__DEFAULT\\\"--><!--\\/textbody--><!--textbody id=\\\"CTA\\\"--><p><a href=\\\"mailto:media@hscic.gov.uk\\\">Contact the press office<\\/a>&nbsp;or call <span style=\\\"font-weight:bold\\\">0300 303 3888<\\/span> (between 9am and 5pm and for urgent out-of-hours calls)<\\/p><!--\\/textbody--><!--textbody id=\\\"COMPONENT\\\"--><!--\\/textbody--><!--textbody id=\\\"UPPERBODY\\\"--><!--\\/textbody--></html>";
    public static String testString2 = "<!--textbody id=\\\"__DEFAULT\\\"--><p>Beverley Bryant, Director of Digital Transformation, NHS Digital, will be the keynote speaker on 19 October.<\\/p><!--\\/textbody--><!--textbody id=\\\"CTA\\\"--><!--\\/textbody--><!--textbody id=\\\"COMPONENT\\\"--><!--\\/textbody--><!--textbody id=\\\"UPPERBODY\\\"--><p><strong><a href=\\\"http:\\/\\/itecconf.org.uk\\/\\\">Visit the event website<\\/a><\\/strong><\\/p><!--\\/textbody-->";

    public static void test1(String html){
        System.out.println("Before:");
        System.out.println(html);
        // .replace("\"", "&quot;")
        Document doc = Jsoup.parse(html);
        removeComments(doc);
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
