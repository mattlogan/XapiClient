XapiClient
==========

An Android client library for the OpenStreetMap Extended API (Xapi)

Get data from the OpenStreetMap database with a straightforward API that works just like Square's Picasso library.  For example:

    XAPIClient.with(this)
        .loadType(XAPIClient.Type.WAY)
        .attribute("highway=primary")
        .boundingBox(122.5, 37.9, 122.6, 50)
        .into(this);

XapiClient uses Google's Volley library for networking.

The full Xapi documentation can be found here: http://wiki.openstreetmap.org/wiki/Xapi
