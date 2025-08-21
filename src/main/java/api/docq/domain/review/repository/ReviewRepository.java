package api.docq.domain.review.repository;

import api.docq.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.clinicId = :clinicId")
    Page<Review> findAllByClinicId(@Param("clinicId") Long clinicId, Pageable pageable);

    @Query("SELECT AVG(r.starPoint) FROM Review r WHERE r.clinicId = :clinicId")
    Double findAverageStarPointByClinicId(@Param("clinicId") Long clinicId);
}
