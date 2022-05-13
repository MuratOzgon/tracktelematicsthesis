package com.example.tracktelematicsthesis.controller;

import com.example.tracktelematicsthesis.model.ObdData;
import com.example.tracktelematicsthesis.model.User;
import com.example.tracktelematicsthesis.service.TTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(TTTController.BASE_URL)
public class TTTController {

    public static final String BASE_URL = "/api/v1/ttt";

    @Autowired
    TTTService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> postData(@RequestBody MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> list = br.lines().collect(Collectors.toList());

        System.out.println(list);

        return ResponseEntity.ok("");
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> uploadData(@RequestBody MultipartFile file) {
        if (service.hasCSVFormat(file)) {
            service.save(file);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a csv file!");
    }

    @GetMapping
    public List<ObdData> getAllObdData() {
        return service.getAllTutorials();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

}
