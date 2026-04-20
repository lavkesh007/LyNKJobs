package com.Spring.Student.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class GeminiService {

    // ✅ correct key name
    @Value("${openai.api.key}")
    private String apiKey;

    public String generateMCQs(String topic) {
        try {

            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);

            // ✅ clean prompt (NO newline issues)
            String prompt = "Generate 10 MCQs on " + topic +
                    " strictly in JSON format like this: " +
                    "[{\"question\":\"...\",\"options\":\"A) ... | B) ... | C) ... | D) ...\",\"answer\":\"A\"}]";

            // ✅ USE OBJECTMAPPER (NO JSON BREAK)
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> request = new HashMap<>();
            request.put("model", "llama3-70b-8192");

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> msg = new HashMap<>();
            msg.put("role", "user");
            msg.put("content", prompt);

            messages.add(msg);
            request.put("messages", messages);

            String body = mapper.writeValueAsString(request);

            // ✅ send request
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

            System.out.println("📦 Groq Response Code: " + responseCode);
            System.out.println("📦 Groq Response Body: " + response);

            // ✅ return only if success
            if (responseCode >= 200 && responseCode < 300) {
                return response.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("❌ Groq API failed");
            e.printStackTrace();
            return null;
        }
    }
}