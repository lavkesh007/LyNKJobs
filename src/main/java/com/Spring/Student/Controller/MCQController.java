package com.Spring.Student.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

            if (token == null) {
                return ResponseEntity.status(401).body(new ArrayList<>());
            }

            try {
                tokenservice.validateToken(token);
            } catch (Exception e) {
                // 🔥 FIX: HANDLE EXPIRED TOKEN
                return ResponseEntity.status(401).body(new ArrayList<>());
            }

            subject = subject.toLowerCase();

            List<Mcqs> data = repository.findBySubjectAndDate(subject, LocalDate.now());

            if (data == null) {
                data = new ArrayList<>();
            }

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    // 🔥 MANUAL GENERATE (FOR TESTING)
    @GetMapping("/generate")
    public String generateNow() {
        scheduler.generateDailyMCQs();
        return "MCQs Generated!";
    }
}