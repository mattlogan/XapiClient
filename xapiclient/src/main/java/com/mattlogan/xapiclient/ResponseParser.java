package com.mattlogan.xapiclient;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

class ResponseParser extends AsyncTask<String, Void, JSONArray> {

    @Override
    protected JSONArray doInBackground(String... response) {

        try {
            Document doc = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(response[0])));

            NodeList waysNodeList = doc.getElementsByTagName("way");
            int numWays = waysNodeList.getLength();

            NodeList coordinatesNodeList = doc.getElementsByTagName("node");
            HashMap<Long, LatLon> coordinatesHashMap = new HashMap<Long, LatLon>();
            Element coordinateElement;
            Long id;
            LatLon latLon;
            int numCoords = coordinatesNodeList.getLength();
            for (int i = 0; i < numCoords; i++) {
                coordinateElement = (Element)coordinatesNodeList.item(i);
                id = Long.parseLong(coordinateElement.getAttribute("id"));
                latLon = new LatLon(Double.parseDouble(coordinateElement.getAttribute("lat")),
                        Double.parseDouble(coordinateElement.getAttribute("lon")));
                coordinatesHashMap.put(id, latLon);
            }

            JSONArray waysJsonArray = new JSONArray();

            Element wayElement;
            JSONObject wayJsonObject;
            for (int i = 0; i < numWays; i++) {
                wayJsonObject = new JSONObject();

                wayElement = (Element) waysNodeList.item(i);

                // Add id
                wayJsonObject.put("id", wayElement.getAttribute("id"));

                // Add tags
                NodeList wayTagsNodeList = wayElement.getElementsByTagName("tag");
                JSONArray tagsJsonArray = new JSONArray();
                Element wayTagElement;
                JSONObject tagJsonObject;
                int numTags = wayTagsNodeList.getLength();
                for (int j = 0; j < numTags; j++) {
                    wayTagElement = (Element) wayTagsNodeList.item(j);
                    tagJsonObject = new JSONObject();
                    tagJsonObject.put(wayTagElement.getAttribute("k"), wayTagElement.getAttribute("v"));
                    tagsJsonArray.put(tagJsonObject);
                }
                wayJsonObject.put("tags", tagsJsonArray);

                // Add points
                NodeList coordinateHashesNodeList = wayElement.getElementsByTagName("nd");
                JSONArray coordinatesJsonArray = new JSONArray();
                Element hashElement;
                JSONObject coordinateJsonObject;
                Long key;
                LatLon coordinate;
                int numHashes = coordinateHashesNodeList.getLength();
                for (int j = 0; j < numHashes; j++) {
                    hashElement = (Element) coordinateHashesNodeList.item(j);
                    key = Long.parseLong(hashElement.getAttribute("ref"));
                    coordinate = coordinatesHashMap.get(key);
                    coordinateJsonObject = new JSONObject();
                    coordinateJsonObject.put("lat", coordinate.getLat());
                    coordinateJsonObject.put("lng", coordinate.getLon());
                    coordinatesJsonArray.put(coordinateJsonObject);
                }
                wayJsonObject.put("coordinates", coordinatesJsonArray);

                waysJsonArray.put(wayJsonObject);
            }

            return waysJsonArray;

        } catch (Exception e) {
            Log.e("ResponseParser", "Exception: " + e);
        }

        return null;
    }
}
