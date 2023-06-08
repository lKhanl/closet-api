package dev.oguzhanercelik.service;

import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.error.ErrorEnum;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private static final List<String> SUPPORTED_EXTENSIONS = List.of("png", "jpg", "jpeg", "heic");
    private static final String SEPARATOR = "/";
    private static final String DOT = ".";

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final MinioClient minioClient;

    public String uploadFile(String path, MultipartFile file) {

        if (file.isEmpty()) {
            throw new ApiException(ErrorEnum.VALIDATION_ERROR);
        }

        final String extension = getExtensionName(file.getOriginalFilename());
        if (!SUPPORTED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new ApiException(ErrorEnum.VALIDATION_ERROR);
        }

        final String directory = buildDirectory(path, buildFileNameWithExtension(extension));
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(directory)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);

            log.info("File upload to path {}", path);
            return directory;
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.VALIDATION_ERROR);
        }
    }

    public void deleteFile(String path) {
        final RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            throw new ApiException(ErrorEnum.VALIDATION_ERROR);
        }
        log.info("File delete to path {}", path);
    }

    private String buildFileNameWithExtension(String extension) {
        return UUID.randomUUID().toString().replace("-", "") + DOT + extension;
    }

    private String buildDirectory(String path, String fileNameWithExtension) {
        return path + SEPARATOR + fileNameWithExtension;
    }

    private String getExtensionName(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElseThrow(() -> new ApiException(ErrorEnum.VALIDATION_ERROR));
    }
}
