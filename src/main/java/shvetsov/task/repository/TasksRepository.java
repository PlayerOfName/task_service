package shvetsov.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Tasks;

import java.util.Optional;

/**
 * Tasks repository.
 */
@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long>, JpaSpecificationExecutor<Tasks> {

    /**
     * Find by id.
     * @param id the id
     * @return the optional
     */
    Optional<Tasks> findById(Long id);

    /**
     * Find all.
     * @param spec     the spec
     * @param pageable the pageable
     * @return the page
     */
    Page<Tasks> findAll(Specification<Tasks> spec, Pageable pageable);
}
