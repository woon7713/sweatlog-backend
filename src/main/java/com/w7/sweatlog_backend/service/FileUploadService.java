package com.w7.sweatlog_backend.service;

import com.w7.sweatlog_backend.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 프로필 이미지 업로드
     */
    public String uploadImage(MultipartFile file) throws IOException {
        // 1. 파일 검증
        if (file.isEmpty()) {
            throw new BadRequestException("파일이 비어있습니다");
        }

        // 2. 파일 타입 검증
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("이미지 파일만 업로드 가능합니다");
        }

        // 3. 고유한 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storedFilename = UUID.randomUUID().toString() + extension;

        // 4. 저장 디렉토리 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 5. 파일 저장
        Path filePath = uploadPath.resolve(storedFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 6. 접근 가능한 URL 반환
        return "/api/upload/profiles/" + storedFilename;
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        Path filePath = Paths.get(uploadDir).resolve(filename);

        Files.deleteIfExists(filePath);
    }
}
