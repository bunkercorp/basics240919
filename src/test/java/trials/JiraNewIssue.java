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
import java.net.URL;
import java.util.ArrayList;

public class JiraNewIssue {


    @Test
    public void postIssue() throws IOException {
        //  String payload = "{\"fields\":{\"summary\":\"Show must go on\",\"issuetype\":{\"id\":\"10107\"},\"project\":{\"id\":\"12120\"},\"description\":\"We are the championzz\",\"reporter\":{\"name\":\"samigullin.nikita\"},\"priority\":{\"id\":\"5\"},\"labels\":[\"custom_lbl\"]}}";

        JSONObject payload = new JSONObject();
        JSONObject fields = new JSONObject();
        fields.put("project", new JSONObject().put("id", "12120"));
        fields.put("summary", "Lorem ipsum elit");
        fields.put("description", "Lorem ipsum elit description");
        fields.put("reporter", new JSONObject().put("name", System.getProperty("jiraUser")));
        fields.put("issuetype", new JSONObject().put("id", "10107"));
        fields.put("priority", new JSONObject().put("id", "5"));
        fields.put("labels", new JSONArray().put("custom_lbl"));
        payload.put("fields", fields);

        final HttpsURLConnection httpCon = (HttpsURLConnection) new URL("https://jira.hillel.it/rest/api/2/issue").openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        String creds = String.format("%s:%s", System.getProperty("jiraUser"), System.getProperty("jiraPwd"));
        httpCon.setRequestProperty("Authorization", String.format("Basic %s", new Base64().encodeToString(creds.getBytes())));
        httpCon.setRequestProperty("Content-Type", "application/json");
        httpCon.setRequestProperty("User-Agent", "Aqa study");
        httpCon.setRequestProperty("Content-Length", "" + payload.length());
        httpCon.setRequestProperty("Cookie", "JSESSIONID=8B2B3A0BF2D7BBC91259E9A25C7A2C0E");
        final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        out.write(payload.toString());
        out.close();
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
      JSONObject response = new JSONObject(String.join("", result));

        System.out.println(response.toString(5));
    }
}
