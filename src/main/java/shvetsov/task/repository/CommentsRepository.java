package shvetsov.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvetsov.task.models.Comments;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    // Пагинация и фильтрация по тексту комментария
    Page<Comments> findByUserIdAndContentContaining(Long taskId, String content, Pageable pageable);

    // Пагинация без фильтрации
    Page<Comments> findByUserId(Long taskId, Pageable pageable);
}
