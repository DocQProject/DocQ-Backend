package api.docq.common.image.controller;

import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{referenceId}")
    public ResponseEntity<String> uploadFile(
            @RequestPart MultipartFile file,
            @RequestParam ReferenceType referenceType,
            @PathVariable Long referenceId
    ) {
        return ResponseEntity.ok(imageService.uploadFile(file, referenceType, referenceId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFile(
            @RequestParam String imageURL
    ) {
        imageService.deleteImage(imageURL);
        return ResponseEntity.ok().build();
    }
}
