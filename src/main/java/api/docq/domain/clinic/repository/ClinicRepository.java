package api.docq.domain.clinic.repository;

import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.enums.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsByAddress(String address);

    @Query("SELECT c.name FROM Clinic c WHERE c.id= :clinicId")
    Optional<String> findClinicNameById(@Param("clinicId") Long clinicId);

    @Query("SELECT c FROM Clinic c")
    Page<Clinic> findAllIsNotDeleted(Pageable pageable);

    @Query("SELECT c FROM Clinic c " +
            "WHERE c.name LIKE CONCAT('%', :query, '%') " +
            "OR c.address LIKE CONCAT('%', :query, '%') " +
            "OR (:department IS NOT NULL AND c.department = :department) ")
    Page<Clinic> searchByQuery(@Param("query") String query, @Param("department") Department department, Pageable pageable);

    @Query(value = "SELECT * FROM clinics WHERE clinics.department = :department ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Clinic> find5RandomClinics(@Param("department") String department);
}
