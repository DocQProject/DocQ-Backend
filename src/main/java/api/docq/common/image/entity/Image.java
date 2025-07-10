package api.docq.common.image.entity;

import api.docq.common.image.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String imageUrl;

    private Long referenceId;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    private String extension;

    @Builder
    private Image(String imageUrl, Long referenceId, ReferenceType referenceType, String extension) {
        this.imageUrl = imageUrl;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.extension = extension;
    }

    public static Image of(String imageUrl, Long referenceId, ReferenceType referenceType, String extension) {
        return Image.builder()
                .imageUrl(imageUrl)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .extension(extension)
                .build();
    }
}
