package com.paul.prototype;

import com.paul.prototype.misc.HtmlHelper;
import com.paul.prototype.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportTest {

    List<HippoImportableItem> importableItems = new ArrayList<>();
    ContentMetaList metaList = new ContentMetaList();

    public static void main(String[] args) throws Exception {
        ImportTest test = new ImportTest();

    }

    private ImportTest() throws IOException {

        JSONObject rootJsonObject = readSource();
        // populateMeta(rootJsonObject);
        // createContent(rootJsonObject);
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");
        // 5 has some h2's
        JSONObject a = (JSONObject) jsonArray.get(5);
        HtmlHelper.test1((String) a.get("ARTICLETEXT"));
    }

    private void populateMeta(JSONObject rootJsonObject) {
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");

        for (Object aJsonArray : jsonArray) {
            JSONObject innerObj = (JSONObject) aJsonArray;
            metaList.add(innerObj.get("ARTICLEID"), innerObj.get("ARTICLEPARENTID"));
        }

        metaList.calculateDepths();
        for (ContentMeta cm : metaList) {
            System.out.println(cm.getId() + ":" + cm.getDepth());
        }
    }

    private Folder createFolder() {
        Folder f = new Folder(null, "Paul Root");
        f.setLocalizedName("Paul Root");
        f.setJcrNodeName("paul-root");
        importableItems.add(f);
        return f;
    }

    private void createContent(JSONObject rootJsonObject) {
        Folder f = createFolder();
        JSONArray jsonArray = (JSONArray) rootJsonObject.get("docs");
        int i = 0;
        for (Object aJsonArray : jsonArray) {
            JSONObject innerObj = (JSONObject) aJsonArray;
            Publication p = new Publication(f, "article" + innerObj.get("ARTICLEID")
                    , (String) innerObj.get("ARTICLEHEADING")
                    , (String) innerObj.get("ARTICLETEXT")
                    , (String) innerObj.get("ARTICLESUMMARY"));
            importableItems.add(p);
            //metaList.add(innerObj.get("ARTICLEID"), innerObj.get("ARTICLEPARENTID"));
            // For my test just create a couple...
            i++;
            if (i > 2) {
                break;
            }
        }
        ImportableFileWriter writer = new ImportableFileWriter();
        writer.writeImportableFiles(importableItems, Paths.get("/home/paul/testout/"));
    }

    private JSONObject readSource() throws IOException {
        String path = "/home/paul/ExportExampleAlpha1.js";
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("File " + path + " does not exist");
        }
        if (!f.isFile()) {
            System.out.println("Not a file :" + path);
        }

        FileReader reader = new FileReader(path);
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("docs");
/*
            for (Object aJsonArray : jsonArray) {
                JSONObject innerObj = (JSONObject) aJsonArray;
                System.out.println("ARTICLEPARENT_ID :" + innerObj.get("ARTICLEPARENTID"));
            }
          */
            return jsonObject;

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }


/*
Read input. As huge json or line by line? Think line by line for now.
Put in JSON object.
Map to hippo importable object
Write back to disk.

    */

}
