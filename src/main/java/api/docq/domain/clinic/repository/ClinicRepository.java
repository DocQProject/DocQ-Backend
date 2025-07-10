package api.docq.domain.clinic.repository;

import api.docq.domain.clinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsByAddress(String address);
}
