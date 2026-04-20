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

    @Scheduled(cron = "0 0 9 * * ?") // ⏰ 9 AM
    public void generateDailyMCQs() {

        for (String subject : subjects) {

            if (!repository.findBySubjectAndDate(subject, LocalDate.now()).isEmpty()) {
                continue;
            }

            System.out.println("🔥 Generating MCQs for: " + subject);

            String response = null;

            // 🔁 RETRY LOGIC
            for (int i = 0; i < 3; i++) {
                response = geminiService.generateMCQs(subject);

                if (response != null) break;

                System.out.println("🔁 Retry attempt: " + (i + 1));
                try { Thread.sleep(2000); } catch (Exception e) {}
            }

            List<Mcqs> mcqs = parserService.parse(response, subject);

            // 🚨 FALLBACK IF FAIL
            if (mcqs.isEmpty()) {
                System.out.println("⚠️ Using fallback MCQs for: " + subject);
                mcqs = generateFallback(subject);
            }

            repository.saveAll(mcqs);

            System.out.println("✅ Saved MCQs: " + mcqs.size());
        }

        System.out.println("✅ DAILY MCQ JOB COMPLETED");
    }

    // 🔥 FALLBACK QUESTIONS
    private List<Mcqs> generateFallback(String subject) {

        List<Mcqs> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            Mcqs mcq = new Mcqs();
            mcq.setSubject(subject);
            mcq.setQuestion("[" + LocalDate.now() + "] " + subject.toUpperCase() + " Question " + i);
            mcq.setOptions("A) Option 1 | B) Option 2 | C) Option 3 | D) Option 4");
            mcq.setAnswer("A");
            mcq.setDate(LocalDate.now());

            list.add(mcq);
        }

        return list;
    }
}