package shvetsov.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Tasks;

import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long>, JpaSpecificationExecutor<Tasks> {
    Optional<Tasks> findById(Long id);

    Page<Tasks> findAll(Specification<Tasks> spec, Pageable pageable);
}
