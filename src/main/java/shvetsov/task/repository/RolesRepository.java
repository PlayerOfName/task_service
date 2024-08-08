package shvetsov.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Roles;

import java.util.Optional;

/**
 * Roles repository.
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    /**
     * поиск роли по заголовку.
     *
     * @param title the title
     * @return the roles
     */
    Roles findByTitle(String title);
}
