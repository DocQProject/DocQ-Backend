package api.docq.domain.user.repository;

import api.docq.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUserId(@Param("userId") Long UserId);

    @Query("SELECT u FROM User u WHERE u.isDeleted IS NOT true AND u.loginId = :loginId")
    Optional<User> findByLoginId(@Param("loginId") String loginId);
}
