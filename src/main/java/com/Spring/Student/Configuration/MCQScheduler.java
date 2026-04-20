package com.Spring.Student.Configuration;

import com.Spring.Student.Repository.MCQsRepository;
import com.Spring.Student.Services.GeminiService;
import com.Spring.Student.Services.MCQParserService;
import com.Spring.Student.UserModel.Mcqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MCQScheduler {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private MCQParserService parserService;

    @Autowired
    private MCQsRepository repository;

    private List<String> subjects = List.of("java", "sql", "aptitude", "cpp", "python", "javascript");

    @Scheduled(cron = "0 0 9 * * ?")
    public void generateDailyMCQs() {

        for (String subject : subjects) {

            try {

                if (!repository.findBySubjectAndDate(subject, LocalDate.now()).isEmpty()) {
                    continue;
                }

                System.out.println("🔥 Generating: " + subject);

                String response = geminiService.generateMCQs(subject);

                // 🚨 FIX 1: HANDLE NULL RESPONSE
                if (response == null) {
                    System.out.println("❌ API FAILED → Using fallback");
                    repository.saveAll(generateFallback(subject));
                    continue;
                }

                List<Mcqs> mcqs = parserService.parse(response, subject);

                // 🚨 FIX 2: HANDLE EMPTY PARSE
                if (mcqs.isEmpty()) {
                    System.out.println("❌ PARSE FAILED → Using fallback");
                    mcqs = generateFallback(subject);
                }

                repository.saveAll(mcqs);

            } catch (Exception e) {
                System.out.println("❌ ERROR in subject: " + subject);
                e.printStackTrace();

                // 🚨 FIX 3: NEVER BREAK SYSTEM
                repository.saveAll(generateFallback(subject));
            }
        }

        System.out.println("✅ MCQs generation completed");
    }
    private List<Mcqs> generateFallback(String subject) {

        List<Mcqs> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Mcqs mcq = new Mcqs();
            mcq.setSubject(subject);
            mcq.setQuestion("Fallback " + subject + " Question " + i);
            mcq.setOptions("A) Option1 | B) Option2 | C) Option3 | D) Option4");
            mcq.setAnswer("A");
            mcq.setDate(LocalDate.now());
            list.add(mcq);
        }

        return list;
    }
}