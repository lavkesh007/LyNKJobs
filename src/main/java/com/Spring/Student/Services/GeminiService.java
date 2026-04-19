package com.Spring.Student.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeminiService {

    // ✅ FIXED KEY NAME
    @Value("${openai.api.key}")
    private String apiKey;

    public String generateMCQs(String topic) {
        try {
            URL url = new URL(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String prompt = "Generate 5 MCQs on " + topic +
                    " in JSON format like this:\n" +
                    "[{\"question\":\"...\",\"options\":[\"A\",\"B\",\"C\",\"D\"],\"answer\":\"...\"}]";

            String body = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        {\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            // ✅ SEND REQUEST
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            // ✅ FIXED RESPONSE HANDLING
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

            return "Status: " + responseCode + "\n" + response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Gemini Error: " + e.getMessage();
        }
    }
}