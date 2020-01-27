package trials;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class JiraNewIssue {

    public enum Verb{
        GET,
        POST;
    }

    public static JSONObject sendRequest(String endpoint, Verb verb, JSONObject payload) throws IOException {
        //set connect
        String url = String.format("https://jira.hillel.it/rest/api/2/%s", endpoint);
        final HttpsURLConnection httpCon = (HttpsURLConnection) new URL(url).openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod(verb.toString());

        //get creds
        String creds = String.format("%s:%s", System.getProperty("jirauser"), System.getProperty("jirapwd"));
        //set headers
        httpCon.setRequestProperty("Authorization", String.format("Basic %s", new Base64().encodeToString(creds.getBytes())));
        httpCon.setRequestProperty("Content-Type", "application/json");
        httpCon.setRequestProperty("User-Agent", "Aqa study");
        httpCon.setRequestProperty("Content-Length", "" + payload.length());
        httpCon.setRequestProperty("Cookie", "JSESSIONID=8B2B3A0BF2D7BBC91259E9A25C7A2C0E");

        if (verb == Verb.POST){
            final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(payload.toString());
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

        System.out.println(httpCon.getResponseCode());
        return new JSONObject(String.join("", result));

    }

    @Test
    public void postIssue() throws IOException {
//          String payload = "{\"fields\":{\"summary\":\"Show must go on\",\"issuetype\":{\"id\":\"10107\"},
//          \"project\":{\"id\":\"12120\"},\"description\":\"We are the championzz\",
//          \"reporter\":{\"name\":\"samigullin.nikita\"},\"priority\":{\"id\":\"5\"},\"labels\":[\"custom_lbl\"]}}";

        JSONObject payload = new JSONObject();
        JSONObject fields = new JSONObject();
        fields.put("project", new JSONObject().put("id", "12120"));
        fields.put("summary", "Lorem ipsum elit");
        fields.put("description", "Lorem ipsum elit description");
        fields.put("reporter", new JSONObject().put("name", System.getProperty("jirauser")));
        fields.put("issuetype", new JSONObject().put("id", "10107"));
        fields.put("priority", new JSONObject().put("id", "5"));
        fields.put("labels", new JSONArray().put("custom_lbl"));
        payload.put("fields", fields);

        System.out.println(sendRequest("issue", Verb.POST,payload));
    }
}
