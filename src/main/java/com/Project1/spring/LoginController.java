package com.Project1.spring;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String handleloginRequest(@RequestParam String name, ModelMap model) {
        model.put("name", name);
        JsonObject obj = callTwitchApi(name);
        model.put("JsonObject", obj);
        return "welcome";
    }

    private JsonObject callTwitchApi(String username) {
        String a1 = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGetRequest = new HttpGet("https://wind-bow.glitch.me/twitch-api/streams/" + username);
            HttpResponse httpResponse = httpClient.execute(httpGetRequest);

            HttpEntity entity = httpResponse.getEntity();

            byte[] buffer = new byte[1024];
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                try {
                    int bytesRead = 0;
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        String chunk = new String(buffer, 0, bytesRead);
                        a1 += chunk;
                    }
                    JsonElement jelement = new JsonParser().parse(a1);
                    JsonObject jobject = jelement.getAsJsonObject();
                    jobject = jobject.getAsJsonObject("stream");
                    System.out.println(jobject);
                    return jobject;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        inputStream.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
}
