package com.Spring.Student.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeminiService {

    // ✅ FIXED
    @Value("${openai.api.key}")
    private String apiKey;

    public String generateMCQs(String topic) {
        try {

            URL url = new URL(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.0-flash:generateContent?key=" + apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // ✅ MATCH YOUR DB STRUCTURE
            String prompt = "Generate 10 MCQs on " + topic +
                    " strictly in JSON format like this:\n" +
                    "[{\"question\":\"...\",\"options\":\"A) ... | B) ... | C) ... | D) ...\",\"answer\":\"A\"}]";

            String body = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" 
                    + prompt.replace("\"", "\\\"") + "\" }] }] }";

            // ✅ SEND REQUEST
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            BufferedReader br;

            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            // ❌ DO NOT ADD "Status:"
            if (responseCode >= 200 && responseCode < 300) {
                return response.toString(); // ✅ clean JSON
            } else {
                return null; // let scheduler skip
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}