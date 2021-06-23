/*
 * Copyright (c) 2020. ALIS.
 * Proprietary source code; any copy or modification is prohibited.
 *
 *
 *
 */

package com.alis.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @PostMapping
    public ResponseEntity<TheResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("param1") String param1) {
        TheResponse res = new TheResponse(file.getOriginalFilename(), param1, file.getContentType());
        return ResponseEntity.ok(res);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TheResponse {
        private String filename;
        private String param1;
        private String type;
    }


}
