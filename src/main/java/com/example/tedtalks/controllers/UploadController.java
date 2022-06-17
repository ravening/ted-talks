package com.example.tedtalks.controllers;

import com.example.tedtalks.services.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @GetMapping
    public String uploadCsv() {
        return "upload_item";
    }


    @PostMapping("/csv-file")
    public String uploadCsvFile(@RequestParam("file") MultipartFile file, Model model) {
        uploadService.uploadFile(file, model);

        return "file-upload-status";
    }
}
