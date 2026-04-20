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

        List<Mcqs> mcqsList = new ArrayList<>();

        try {

            // ✅ 1. HANDLE NULL / EMPTY RESPONSE
            if (response == null || response.isEmpty()) {
                System.out.println("❌ Empty response from API");
                return mcqsList;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // ✅ 2. HANDLE API ERROR RESPONSE
            if (root.has("error")) {
                System.out.println("❌ API Error: " + root.path("error").toString());
                return mcqsList;
            }

            // ✅ 3. SAFE EXTRACTION (NO NULL POINTER)
            JsonNode choices = root.path("choices");

            if (choices.isEmpty() || choices.get(0) == null) {
                System.out.println("❌ No choices found in response");
                return mcqsList;
            }

            String text = choices.get(0)
                    .path("message")
                    .path("content")
                    .asText();

            System.out.println("📄 Raw Content:\n" + text);

            // ✅ 4. CLEAN RESPONSE
            text = text.replace("```json", "")
                       .replace("```", "")
                       .trim();

            // ✅ 5. FIND JSON ARRAY INSIDE TEXT (VERY IMPORTANT)
            int start = text.indexOf("[");
            int end = text.lastIndexOf("]");

            if (start == -1 || end == -1) {
                System.out.println("❌ No valid JSON array found");
                return mcqsList;
            }

            String jsonArray = text.substring(start, end + 1);

            JsonNode array = mapper.readTree(jsonArray);

            // ✅ 6. PARSE EACH MCQ SAFELY
            for (JsonNode node : array) {

                Mcqs mcq = new Mcqs();

                mcq.setSubject(subject);
                mcq.setQuestion(node.path("question").asText(""));
                mcq.setOptions(node.path("options").asText(""));
                mcq.setAnswer(node.path("answer").asText(""));
                mcq.setDate(LocalDate.now());

                // skip empty/broken data
                if (!mcq.getQuestion().isEmpty()) {
                    mcqsList.add(mcq);
                }
            }

            System.out.println("✅ Parsed MCQs: " + mcqsList.size());

        } catch (Exception e) {
            System.out.println("❌ Parsing failed");
            e.printStackTrace();
        }

        return mcqsList;
    }
}