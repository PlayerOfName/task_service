package shvetsov.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shvetsov.task.models.Comments;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;
import shvetsov.task.repository.CommentsRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CommentsRepositoryTests {

    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    public void testFindByContentContaining() {

        Tasks task1 = new Tasks("title", "description", "status", "priority");
        Tasks task2 = new Tasks("title", "description", "status", "priority");

        Users user1 = new Users("user@gmail.com", "password");
        Users user2 = new Users("user@gmail.com", "password");

        Comments comment1 = new Comments(task1, user1, "This is a test comment");
        Comments comment2 = new Comments(task2, user2, "Another comment");
        commentsRepository.save(comment1);
        commentsRepository.save(comment2);


        Pageable pageable = PageRequest.of(0, 10);
        Page<Comments> result = commentsRepository.findByUserIdAndContentContaining(1L, "test", pageable);


        assertEquals(1, result.getTotalElements());
        assertTrue(result.getContent().contains(comment1));
        assertFalse(result.getContent().contains(comment2));
    }
}
