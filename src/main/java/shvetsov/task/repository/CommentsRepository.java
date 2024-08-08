package shvetsov.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Comments;

import java.util.List;

/**
 * Comments repository.
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {


    /**
     * поиск комментария по задаче и контенту
     *
     * @param taskId   the task id
     * @param content  the content
     * @param pageable the pageable
     * @return the page
     */
    Page<Comments> findByTaskIdAndContentContaining(Long taskId, String content, Pageable pageable);


    /**
     * поиск комментария по задаче
     *
     * @param taskId   the task id
     * @param pageable the pageable
     * @return the page
     */
    Page<Comments> findByTaskId(Long taskId, Pageable pageable);
}
