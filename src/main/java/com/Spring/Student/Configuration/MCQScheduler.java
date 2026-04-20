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

    @Scheduled(cron = "0 0 9 * * ?") // ⏰ 9 AM daily
    public void generateDailyMCQs() {

        for (String subject : subjects) {

            // ✅ skip if already generated today
            if (!repository.findBySubjectAndDate(subject, LocalDate.now()).isEmpty()) {
                continue;
            }

            System.out.println("🔥 Generating MCQs for: " + subject);

            String response = null;

            // 🔁 RETRY LOGIC (3 attempts)
            for (int i = 0; i < 3; i++) {
                response = geminiService.generateMCQs(subject);

                if (response != null) break;

                System.out.println("🔁 Retry attempt: " + (i + 1));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<Mcqs> mcqs = parserService.parse(response, subject);

            // 🚨 FALLBACK IF API FAILS
            if (mcqs.isEmpty()) {
                System.out.println("⚠️ Using fallback MCQs for: " + subject);
                mcqs = generateFallbackMCQs(subject);
            }

            repository.saveAll(mcqs);

            System.out.println("✅ Saved MCQs: " + mcqs.size());
        }

        System.out.println("✅ DAILY MCQ JOB COMPLETED");
    }

    // 🔥 FALLBACK QUESTIONS (ALWAYS SAFE)
    private List<Mcqs> generateFallbackMCQs(String subject) {

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