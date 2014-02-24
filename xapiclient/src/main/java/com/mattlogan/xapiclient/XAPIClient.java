package com.mattlogan.xapiclient;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class XapiClient {

    public interface Listener {
        public void onSuccess(JSONArray ways);
        public void onError(String e);
    }

    public static class Type {
        public static final String WAY = "way";
        public static final String NODE = "node";
        public static final String RELATION = "relation";
    }

    static XapiClient singleton;

    final Context context;
    final RequestQueue requestQueue;

    XapiClient(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public static XapiClient with(Context context) {
        if (singleton == null) {
            singleton = new XapiClient(context);
        }
        return singleton;
    }

    public RequestCreator loadType(String type) {
        if (!type.equals(Type.WAY)) {
            throw new IllegalArgumentException("Only supported type is Way.");
        }

        return new RequestCreator(this, type);
    }

    public void enqueueAndSubmit(final String url, final Listener listener) {
        requestQueue.add(new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONArray ways = new ResponseParser().execute(s).get();
                            listener.onSuccess(ways);
                        } catch (Exception e) {
                            listener.onError("Error parsing response: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onError(volleyError.getMessage());
                    }
                }
        ));
    }

}
