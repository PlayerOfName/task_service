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
import shvetsov.task.repository.TasksRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTests {

    @Autowired
    private TasksRepository tasksRepository;

    @Test
    public void testFindByContentContaining() {

        Tasks task1 = new Tasks("title", "description", "status", "priority");
        Tasks task2 = new Tasks("title", "description", "status", "priority");

        tasksRepository.save(task1);
        tasksRepository.save(task2);


        assertEquals("title", task1.getTitle());
    }
}
