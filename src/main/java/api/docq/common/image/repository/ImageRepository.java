package api.docq.common.image.repository;

import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByReferenceIdAndReferenceType(Long referenceId, ReferenceType referenceType);
}
