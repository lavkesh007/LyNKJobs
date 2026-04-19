package com.Spring.Student.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OpenAIService {
	@Value("${openai.api.key}")
    private String apiKey;

    public String generateMCQs(String topic) {
        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String prompt = "Generate 5 multiple choice questions (MCQs) on " + topic +
                    " in JSON format with fields: question, options, answer.";

            String body = "{\n" +
                    "  \"model\": \"gpt-4.1-mini\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"" + prompt + "\"}\n" +
                    "  ]\n" +
                    "}";

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            return response.toString();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
