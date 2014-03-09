XapiClient
==========

An Android client library for the OpenStreetMap Extended API (Xapi)

Get data from the OpenStreetMap database with a straightforward API that works just like Square's Picasso library.  For example:

```java
XapiClient.with(this)
        .loadType(XapiClient.Type.WAY)
        .attribute("highway=primary")
        .boundingBox(122.5, 37.9, 122.6, 50)
        .into(this);
```

## Attribution

XapiClient uses Google's Volley library for networking.

The full Xapi documentation can be found here: http://wiki.openstreetmap.org/wiki/Xapi

## License

The MIT License (MIT)

Copyright (c) 2014 Matthew Logan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
