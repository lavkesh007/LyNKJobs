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

            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);

            String prompt = "Generate 10 MCQs on " + topic +
                    " strictly in JSON format like:\n" +
                    "[{\"question\":\"...\",\"options\":\"A) ... | B) ... | C) ... | D) ...\",\"answer\":\"A\"}]";

            String body = "{\n" +
                    "  \"model\": \"llama3-8b-8192\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"" + prompt.replace("\"", "\\\"") + "\"}\n" +
                    "  ]\n" +
                    "}";

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            BufferedReader br;
            int code = conn.getResponseCode();

            if (code >= 200 && code < 300) {
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

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}