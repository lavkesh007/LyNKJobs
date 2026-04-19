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
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            String text = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // clean markdown
            text = text.replace("```json", "")
                       .replace("```", "")
                       .trim();

            JsonNode array = mapper.readTree(text);

            for (JsonNode node : array) {

                Mcqs mcq = new Mcqs();
                mcq.setSubject(subject);
                mcq.setQuestion(node.get("question").asText());
                mcq.setOptions(node.get("options").asText());
                mcq.setAnswer(node.get("answer").asText());
                mcq.setDate(LocalDate.now());

                mcqsList.add(mcq);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mcqsList;
    }
}