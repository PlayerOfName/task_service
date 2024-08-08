import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shvetsov.task.models.Comments;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentsTest {

    private Comments comment;
    private Tasks task;
    private Users user;

    @BeforeEach
    void setUp() {
        task = new Tasks();
        task.setId(1L);
        task.setTitle("Test Task");

        user = new Users();
        user.setId(1L);
        user.setEmail("test@example.com");

        comment = new Comments(task, user, "Test Comment");
    }

    @Test
    void testNoArgsConstructor() {
        Comments emptyComment = new Comments();
        assertNotNull(emptyComment);
        assertNull(emptyComment.getId());
        assertNull(emptyComment.getTask());
        assertNull(emptyComment.getUser());
        assertNull(emptyComment.getContent());
        assertNull(emptyComment.getCreated_at());
    }

    @Test
    void testAllArgsConstructor() {
        ZonedDateTime now = ZonedDateTime.now();
        Comments fullComment = new Comments(1L, task, user, "Test Comment", now);

        assertNotNull(fullComment);
        assertEquals(1L, fullComment.getId());
        assertEquals(task, fullComment.getTask());
        assertEquals(user, fullComment.getUser());
        assertEquals("Test Comment", fullComment.getContent());
        assertEquals(now, fullComment.getCreated_at());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(comment);
        assertEquals(task, comment.getTask());
        assertEquals(user, comment.getUser());
        assertEquals("Test Comment", comment.getContent());
        assertNull(comment.getCreated_at()); // created_at не установлен в конструкторе
    }

    @Test
    void testGettersAndSetters() {
        comment.setId(2L);
        comment.setContent("Updated Comment");
        ZonedDateTime now = ZonedDateTime.now();
        comment.setCreated_at(now);

        assertEquals(2L, comment.getId());
        assertEquals("Updated Comment", comment.getContent());
        assertEquals(now, comment.getCreated_at());
    }

    @Test
    void testTaskAssociation() {
        Tasks newTask = new Tasks();
        newTask.setId(2L);
        newTask.setTitle("New Task");

        comment.setTask(newTask);
        assertEquals(newTask, comment.getTask());
    }

    @Test
    void testUserAssociation() {
        Users newUser = new Users();
        newUser.setId(2L);
        newUser.setEmail("new@example.com");

        comment.setUser(newUser);
        assertEquals(newUser, comment.getUser());
    }
}