package com.Spring.Student.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.Student.Services.GeminiService;


@RestController
@RequestMapping("/mcqs")
public class MCQController {
	@Autowired
    private GeminiService service;

    @GetMapping("/{topic}")
    public String getMCQs(@PathVariable String topic) {
        return service.generateMCQs(topic);
    }
}
