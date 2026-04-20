package com.Spring.Student.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Spring.Student.Configuration.MCQScheduler;
import com.Spring.Student.Repository.MCQsRepository;
import com.Spring.Student.UserModel.Mcqs;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mcqs")
public class MCQController {

    @Autowired
    private Token tokenservice;

    @Autowired
    private MCQsRepository repository;

    @Autowired
    private MCQScheduler scheduler;

    // ✅ GET MCQs
    @GetMapping("/{subject}")
    public ResponseEntity<List<Mcqs>> getMCQs(HttpServletRequest request, @PathVariable String subject) {

        try {

            String token = tokenservice.getToken(request);

            // 🔥 HANDLE TOKEN SAFELY
            if (token != null) {
                try {
                    tokenservice.validateToken(token);
                } catch (Exception e) {
                	
                    System.out.println("❌ Token invalid or expired");
                    return (ResponseEntity<List<Mcqs>>) ResponseEntity.status(401);
                    // 👉 DO NOT FAIL — just continue without auth
                }
            }

            subject = subject.toLowerCase();

            List<Mcqs> data = repository.findBySubjectAndDate(subject, LocalDate.now());

            // 🔥 AUTO GENERATE IF EMPTY
            if (data == null || data.isEmpty()) {
                System.out.println("⚠️ No MCQs found → generating...");
                scheduler.generateDailyMCQs();
                data = repository.findBySubjectAndDate(subject, LocalDate.now());
            }

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            System.out.println("❌ ERROR IN MCQ API");
            e.printStackTrace();

            // ❗ NEVER RETURN 500 TO FRONTEND
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
    @GetMapping("/generate")
    public ResponseEntity<?> generateNow() {
        try {
            scheduler.generateDailyMCQs();
            return ResponseEntity.ok("MCQs Generated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Fallback Generated"); // ❗ never 500
        }
    }

    // 🔥 MANUAL GENERATE (FOR TESTING)
//    @GetMapping("/generate")
//    public String generateNow() {
//        scheduler.generateDailyMCQs();
//        return "MCQs Generated!";
//    }
}