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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class JiraNewIssue {

    @Test
    public void postIssue() throws IOException {

//        JSONObject payload = new JSONObject();
//
//        JSONObject fields = new JSONObject();
//        fields.put("project", new JSONObject().put("id", "12120"));
//        fields.put("summary", "Lorem ipsum elit");
//        fields.put("description", "Lorem ipsum elit description");
//        fields.put("reporter", new JSONObject().put("name", System.getProperty("jiraUser")));
//        fields.put("issuetype", new JSONObject().put("id", "10107"));
//        fields.put("priority", new JSONObject().put("id", "5"));
//        fields.put("labels", new JSONArray().put("custom_lbl"));
//
//
//        payload.put("fields", fields);

        String payload = "{\"fields\":{\"summary\":\"Lorem ipsum elit\",\"issuetype\":{\"id\":\"10107\"},\"project\":{\"id\":\"12120\"},\"description\":\"Lorem ipsum elit description\",\"reporter\":{\"name\":\"samigullin.nikita\"},\"priority\":{\"id\":\"5\"},\"labels\":[\"custom_lbl\"]}}";

                final HttpsURLConnection httpCon = (HttpsURLConnection) new URL("https://jira.hillel.it/rest/api/2/issue").openConnection();

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        String creds = String.format("%s:%s", System.getProperty("jiraUser"), System.getProperty("jiraPwd"));

        httpCon.setRequestProperty("Authorization", String.format("Basic %s", new Base64().encodeToString(creds.getBytes())));
        httpCon.setRequestProperty("Content-Type", "application/json");
        //      httpCon.setRequestProperty("Content-Length", "" + payload.length());
        httpCon.setRequestProperty("Cookie", "JSESSIONID=FF498E0E920A9EA0B19053C38B0CA73C");
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
        System.out.println(String.join("", result));


    }

}
