XapiClient
==========

An Android client library for the OpenStreetMap Extended API (Xapi)

Get data from the OpenStreetMap database with just one line of code.  For example:

    XAPIClient.with(this)
        .loadType(XAPIClient.Type.WAY)
        .attribute("highway=primary")
        .boundingBox(122.5, 37.9, 122.6, 50)
        .into(this);


