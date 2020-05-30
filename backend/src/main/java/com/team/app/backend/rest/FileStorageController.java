package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.Achievement;
import com.team.app.backend.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/storage/")
public class FileStorageController {

    private final AmazonClient amazonClient;

    FileStorageController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) throws Exception {
        return ResponseEntity.ok().body(this.amazonClient.uploadFile(file));
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestPart(value = "url") String fileUrl) throws Exception {
        return ResponseEntity.ok().body(this.amazonClient.deleteFileFromS3Bucket(fileUrl));
    }
}
