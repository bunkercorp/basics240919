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
import java.util.List;

public class JiraIssue {
    public enum  Verb{
        GET,POST,PUT,DELETE;
    }


    public static void main(String[] args) {

    }

//    public static JSONObject sendRequest(String endpoint, Verb verb, String payload) throws IOException {
    public static JSONObject sendRequest(String endpoint, Verb verb) throws IOException {
        //set connect
        String payload = "";
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
        httpCon.setRequestProperty("Cookie", "JSESSIONID=B66B503CAAC8AF422596D3486627D21C");

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

        JSONArray array =  new JSONObject(String.join("",result)).getJSONArray();



//        System.out.println(result.toString());
//
//        if (verb.equals(Verb.POST))
//        return new JSONObject(String.join("", result));
//        else return null;
    }


    public void createTicket(JiraIssue jiraIssue){
        // формируется запрос на создание готового тикета

    }
@Test
    public void withProject() throws IOException {

    System.out.println("------START------");
        String projectId = new String();
        String projectName = "AQA240919";
//        //GET

//        fields.put("project", new JSONObject().put("id", "12120"));
//        fields.put("summary", "Lorem ipsum elit");
//        fields.put("description", "Lorem ipsum elit description");
//        fields.put("reporter", new JSONObject().put("name", System.getProperty("jirauser")));
//        fields.put("issuetype", new JSONObject().put("id", "10107"));
//        fields.put("priority", new JSONObject().put("id", "5"));
//        fields.put("labels", new JSONArray().put("custom_lbl"));
//        payload.put("fields", fields);


        System.out.println(sendRequest("project", Verb.GET));
    System.out.println("---FINISH---");


    }
}
