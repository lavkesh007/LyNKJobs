package com.Spring.Student.Configuration;




import com.Spring.Student.Repository.MCQsRepository;
import com.Spring.Student.Services.GeminiService;
import com.Spring.Student.Services.MCQParserService;
import com.Spring.Student.UserModel.Mcqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MCQScheduler {
	@Autowired
    private GeminiService geminiService;

    @Autowired
    private MCQParserService parserService;

    @Autowired
    private MCQsRepository repository;

    private List<String> subjects = List.of("java", "sql", "aptitude");

    @Scheduled(cron = "0 0 9 * * ?") // 9 AM
    public void generateDailyMCQs() {

        for (String subject : subjects) {

            // ✅ avoid duplicates
            if (!repository.findBySubjectAndDate(subject, LocalDate.now()).isEmpty()) {
                continue;
            }
            System.out.println("🔥 Calling Gemini for: " + subject);
            String response = geminiService.generateMCQs(subject);
            System.out.println("📦 RAW RESPONSE:\n" + response);

            List<Mcqs> mcqs = parserService.parse(response, subject);
            System.out.println("✅ Parsed count: " + mcqs.size());
            repository.saveAll(mcqs);
        }

        System.out.println("✅ MCQs generated and stored");
    }

}
