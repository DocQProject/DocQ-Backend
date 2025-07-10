package api.docq.domain.clinic.repository;

import api.docq.domain.clinic.entity.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsByAddress(String address);

    @Query("SELECT c.name FROM Clinic c WHERE c.isDeleted IS NOT true AND c.id= :clinicId")
    Optional<String> findClinicNameById(@Param("clinicId") Long clinicId);

    @Query("SELECT c FROM Clinic c WHERE c.isDeleted IS NOT true")
    Page<Clinic> findAllIsNotDeleted(Pageable pageable);
}
