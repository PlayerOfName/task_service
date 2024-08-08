import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shvetsov.task.models.Comments;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    private Tasks task;
    private Users author;
    private Users assignee;
    private Comments comment;

    @BeforeEach
    void setUp() {
        task = new Tasks("Test Task", "Test Description", "PENDING", "HIGH");
        author = new Users();
        author.setId(1L);
        author.setEmail("author@example.com");

        assignee = new Users();
        assignee.setId(2L);
        assignee.setEmail("assignee@example.com");

        comment = new Comments();
        comment.setId(1L);
        comment.setContent("Test Comment");
    }

    @Test
    void testNoArgsConstructor() {
        Tasks emptyTask = new Tasks();
        assertNotNull(emptyTask);
        assertNull(emptyTask.getId());
        assertNull(emptyTask.getTitle());
        assertNull(emptyTask.getDescription());
        assertNull(emptyTask.getStatus());
        assertNull(emptyTask.getPriority());
        assertNull(emptyTask.getCreated_at());
        assertTrue(emptyTask.getComments().isEmpty());
        assertTrue(emptyTask.getTasksUsers().isEmpty());
        assertTrue(emptyTask.getTasksAuthors().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        ZonedDateTime now = ZonedDateTime.now();
        Set<Users> authors = new HashSet<>();
        authors.add(author);

        Set<Users> assignees = new HashSet<>();
        assignees.add(assignee);

        Set<Comments> comments = new HashSet<>();
        comments.add(comment);

        Tasks fullTask = new Tasks(1L, "Test Task", "Test Description", "PENDING", "HIGH", comments, now, assignees, authors);

        assertNotNull(fullTask);
        assertEquals(1L, fullTask.getId());
        assertEquals("Test Task", fullTask.getTitle());
        assertEquals("Test Description", fullTask.getDescription());
        assertEquals("PENDING", fullTask.getStatus());
        assertEquals("HIGH", fullTask.getPriority());
        assertEquals(now, fullTask.getCreated_at());
        assertEquals(1, fullTask.getComments().size());
        assertEquals(1, fullTask.getTasksUsers().size());
        assertEquals(1, fullTask.getTasksAuthors().size());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals("PENDING", task.getStatus());
        assertEquals("HIGH", task.getPriority());
        assertNull(task.getCreated_at());
        assertTrue(task.getComments().isEmpty());
        assertTrue(task.getTasksUsers().isEmpty());
        assertTrue(task.getTasksAuthors().isEmpty());
    }

    @Test
    void testGettersAndSetters() {
        task.setId(1L);
        task.setCreated_at(ZonedDateTime.now());
        task.setStatus("IN_PROGRESS");
        task.setPriority("MEDIUM");

        assertEquals(1L, task.getId());
        assertNotNull(task.getCreated_at());
        assertEquals("IN_PROGRESS", task.getStatus());
        assertEquals("MEDIUM", task.getPriority());
    }

    @Test
    void testCommentsAssociation() {
        task.getComments().add(comment);
        comment.setTask(task);

        assertEquals(1, task.getComments().size());
        assertEquals(task, comment.getTask());
    }

    @Test
    void testTasksUsersAssociation() {
        task.getTasksUsers().add(assignee);

        assertEquals(1, task.getTasksUsers().size());
        assertTrue(task.getTasksUsers().contains(assignee));
    }

    @Test
    void testTasksAuthorsAssociation() {
        task.getTasksAuthors().add(author);

        assertEquals(1, task.getTasksAuthors().size());
        assertTrue(task.getTasksAuthors().contains(author));
    }
}