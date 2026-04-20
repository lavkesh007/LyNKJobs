package com.Spring.Student.Services;

import com.Spring.Student.UserModel.Mcqs;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MCQParserService {

    public List<Mcqs> parse(String response, String subject) {

        List<Mcqs> list = new ArrayList<>();

        try {

            // 🚨 NULL CHECK
            if (response == null || response.isEmpty()) {
                System.out.println("❌ Empty API response");
                return list;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // 🚨 API ERROR CHECK
            if (root.has("error")) {
                System.out.println("❌ API Error: " + root.get("error"));
                return list;
            }

            JsonNode choices = root.path("choices");

            if (choices.isEmpty() || choices.get(0) == null) {
                System.out.println("❌ No choices found");
                return list;
            }

            String text = choices.get(0)
                    .path("message")
                    .path("content")
                    .asText();

            System.out.println("📄 RAW CONTENT:\n" + text);

            // clean markdown
            text = text.replace("```json", "")
                       .replace("```", "")
                       .trim();

            // extract JSON array
            int start = text.indexOf("[");
            int end = text.lastIndexOf("]");

            if (start == -1 || end == -1) {
                System.out.println("❌ Invalid JSON format");
                return list;
            }

            String jsonArray = text.substring(start, end + 1);

            JsonNode array = mapper.readTree(jsonArray);

            for (JsonNode node : array) {

                Mcqs mcq = new Mcqs();

                mcq.setSubject(subject);
                mcq.setQuestion(node.path("question").asText(""));
                mcq.setOptions(node.path("options").asText(""));
                mcq.setAnswer(node.path("answer").asText(""));
                mcq.setDate(LocalDate.now());

                if (!mcq.getQuestion().isEmpty()) {
                    list.add(mcq);
                }
            }

            System.out.println("✅ Parsed MCQs: " + list.size());

        } catch (Exception e) {
            System.out.println("❌ Parsing failed");
            e.printStackTrace();
        }

        return list;
    }
}