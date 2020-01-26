package http;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HttpRequestComposer {
    public static final class HttpResponse {
        public final int rescode;
        public final String resBody;

        public HttpResponse(int rc, String rb) {
            resBody = rb;
            rescode = rc;
        }
    }

    public enum ContentType {
        PLAIN_TEXT("text/plain"),
        JSON("application/json");
        public final String headerContent;

        private ContentType(String s) {
            headerContent = s;
        }
    }

    public enum AuthType {
        BASIC("Basic");
        public final String headerContent;

        private AuthType(String s) {
            headerContent = s;
        }

    }

    public enum HTTPMethod {
        GET(false), POST(true), PUT(true);
        public final boolean acceptsPayload;

        private HTTPMethod(boolean ap) {
            acceptsPayload = ap;
        }
    }


    private Map<String, String> headers = new HashMap<String, String>() {{
        put("User-Agent", "Legit user agent, for sure");

    }};
    private HTTPMethod method = HTTPMethod.GET;
    private String payload = null;

    public HttpRequestComposer withContentType(ContentType ct) {
        headers.put("Content-Type", ct.headerContent);
        return this;
    }

    public HttpRequestComposer auth(AuthType at, String token) {
        headers.put("Authorization", String.format("%s %s", at.headerContent, token));
        return this;
    }

    public HttpRequestComposer via(HTTPMethod m) {
        method = m;
        return this;
    }

    public HttpRequestComposer payload(String p) {
        payload = p;
        return this;
    }

    public HttpRequestComposer userAgent(String ua) {
        if (ua != null && !ua.trim().isEmpty())
            headers.put("User-Agent", ua);
        return this;
    }

    public HttpRequestComposer setHeader(String header, String value) {
        if (!headers.containsKey(header))
            headers.put(header, value);
        return this;
    }

    public HttpResponse fire(String url) throws IOException {
        final HttpsURLConnection httpCon = (HttpsURLConnection) new URL(url).openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod(method.name());
        headers.forEach(httpCon::setRequestProperty);
        if (method.acceptsPayload) {
            httpCon.setRequestProperty("Content-Length", "" + payload.length());
            final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(payload == null ? "" : payload);
            out.close();
        }

        ArrayList<String> result = new ArrayList<String>();
        BufferedReader rd;
        try {
            rd = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        } catch (Exception e) {
            rd = new BufferedReader(new InputStreamReader(httpCon.getErrorStream()));
        }
        String line;
        while (null != (line = rd.readLine())) {
            result.add(line);
        }
        return new HttpResponse(httpCon.getResponseCode(), String.join("", result));
    }

}
