package com.mattlogan.xapiclient;


public class RequestCreator {

    private final XAPIClient xapiClient;
    private final String baseUrl;
    private final String type;

    private String attribute;

    private double minLng;
    private double minLat;
    private double maxLng;
    private double maxLat;

    RequestCreator(XAPIClient xapiClient, String type) {
        this.xapiClient = xapiClient;
        this.baseUrl = "http://www.overpass-api.de/api/xapi";

        if (type == null) {
            throw new IllegalArgumentException("Type must not be null.");
        }
        this.type = type;
    }

    public RequestCreator attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public RequestCreator boundingBox(double minLng, double minLat, double maxLng, double maxLat) {
        if (minLng < -180 || minLng > 180 || maxLng < -180 || maxLng > 180
                || minLat < -90 || minLat > 90 || maxLat < -90 || maxLat > 90) {
            throw new IllegalArgumentException("Coordinates out of range.");
        }

        this.minLng = minLng;
        this.minLat = minLat;
        this.maxLng = maxLng;
        this.maxLat = maxLat;
        return this;
    }

    public void into(XAPIClient.Listener listener) {
        String url = baseUrl + "?" + type;

        if (attribute != null) {
            url += "[" + attribute + "]";
        }

        url += "[bbox=" + minLng + "," + minLat + "," + maxLng + "," + maxLat + "]";

        xapiClient.enqueueAndSubmit(url, listener);
    }

}
