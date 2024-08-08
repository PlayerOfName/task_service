package shvetsov.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Users;

import java.util.Optional;

/**
 * Users repository.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long id);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Users> findByEmail(String email);
}
