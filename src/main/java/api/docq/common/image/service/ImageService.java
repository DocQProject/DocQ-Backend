package api.docq.common.image.service;

import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class    ImageService {

    private final ImageRepository imageRepository;

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region}")
    private String region;

    public String uploadFile(MultipartFile multipartFile, ReferenceType referenceType, Long referenceId) {
        String filename = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filename)
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));

            String imageURL = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, filename);

            String extractedFileName = extractFileName(imageURL);

            String extension = extractExtension(extractedFileName);

            Image image = Image.of(imageURL, referenceId, referenceType, extension);

            imageRepository.save(image);

            return imageURL;

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void deleteImage(String imageUrl) {
        String key = extractKeyFromUrl(imageUrl);

        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build()
        );

        Image image = imageRepository.findByImageUrl(imageUrl)
                .orElseThrow(() -> new RuntimeException());

        imageRepository.delete(image);
    }

    private String extractKeyFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return url.getPath().substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }

    private String extractFileName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private String extractExtension(String fileName) {
        String extension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase(); // 소문자로
        }

        return extension;
    }



}
