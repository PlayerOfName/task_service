package shvetsov.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvetsov.task.dto.CommentsDTO;
import shvetsov.task.services.CommentsService;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments API", description = "API для управления комментариями")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @Operation(summary = "Создать комментарий", description = "Создаёт комментарии пользователя")
    @PostMapping("/created-comments")
    public ResponseEntity<?> createdTask(@RequestBody CommentsDTO comments) {
        return commentsService.createComment(
                comments.getTasks_id(),
                comments.getUsers_id(),
                comments.getContent()
        );
    }

    @Operation(summary = "Просмотреть комментарии к задаче", description = "Просматривает комментарии к задаче")
    @GetMapping("/watch-comments-task-{user_id}")
    public ResponseEntity<?> watchCommentByTask(
            @PathVariable Long user_id,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return commentsService.watchCommentTaskId(user_id, content, page, size);
    }
}
