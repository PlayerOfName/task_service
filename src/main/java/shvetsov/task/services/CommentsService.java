package shvetsov.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shvetsov.task.dto.CommentDTOAnswer;
import shvetsov.task.dto.TaskDTOAnswer;
import shvetsov.task.models.Comments;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;
import shvetsov.task.repository.CommentsRepository;
import shvetsov.task.repository.TasksRepository;
import shvetsov.task.repository.UsersRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommentsService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    public ResponseEntity<?> createComment(Long tasks_id, Long users_id, String content) {
        if ((usersRepository.findById(users_id).isPresent()) && (tasksRepository.findById(tasks_id).isPresent())){
            if ((content != null && content.length() > 5)){
                Tasks task = tasksRepository.findById(tasks_id).orElseThrow(() -> new RuntimeException("Task not found"));
                Users user = usersRepository.findById(users_id).orElseThrow(() -> new RuntimeException("User not found"));

                Comments comment = new Comments(task, user, content);
                comment.setCreated_at(ZonedDateTime.now(ZoneOffset.UTC).plusHours(3));
                commentsRepository.save(comment);

                CommentDTOAnswer commentDTOAnswer = new CommentDTOAnswer(comment.getId(), comment.getContent(), comment.getCreated_at());

                return new ResponseEntity<>(commentDTOAnswer, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> watchCommentTaskId(Long user_id, String content, int page, int size){
        if (usersRepository.findById(user_id).isPresent()){

            Pageable pageable = PageRequest.of(page, size);

            Page<Comments> commentsPage;

            if (!content.isEmpty()) {
                commentsPage = commentsRepository.findByUserIdAndContentContaining(user_id, content, pageable);
            } else {
                commentsPage = commentsRepository.findByUserId(user_id, pageable);
            }

            List<CommentDTOAnswer> comDTOs = commentsPage.stream()
                    .map(comm -> new CommentDTOAnswer(comm.getId(), comm.getContent(), comm.getCreated_at()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(comDTOs, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> deleteComment(Long comment_id){
        if (commentsRepository.findById(comment_id).isPresent()){
            Comments comment = commentsRepository.findById(comment_id).get();
            commentsRepository.delete(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateComment(Long comment_id, String content){
        if (commentsRepository.findById(comment_id).isPresent()){
            Comments comment = commentsRepository.findById(comment_id).get();
            comment.setContent(content);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
