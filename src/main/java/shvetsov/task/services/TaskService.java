package shvetsov.task.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import shvetsov.task.dto.TaskDTO;
import shvetsov.task.dto.TaskDTOAnswer;
import shvetsov.task.errors.UserNotFoundException;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;
import shvetsov.task.repository.TasksRepository;
import shvetsov.task.repository.UsersRepository;

import javax.management.openmbean.TabularDataSupport;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TasksRepository tasksRepository;

    public ResponseEntity<?> createdTask(String title, String description, String status, String priority, String email){
        if (title != null && description != null && status != null && priority != null && email != null){
            Set<Users> usersSet = new HashSet<>();
            Users user = usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
            Tasks task = new Tasks(title, description, status, priority);
            usersSet.add(user);
            task.setCreated_at(ZonedDateTime.now(ZoneOffset.UTC).plusHours(3));
            task.setTasksAuthors(usersSet);
            tasksRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> watchTasksAuthor(Long author_id, String status, String priority, int page, int size){
        if (usersRepository.findById(author_id).isPresent()){

            Users user = usersRepository.findById(author_id).get();
            Set<Tasks> tasksSet = user.getTasksSetAuthors();

            // Преобразуем Set в Stream для фильтрации
            Stream<Tasks> tasksStream = tasksSet.stream();

            // Фильтрация по статусу (если передан)
            if (status != null && !status.isEmpty()) {
                tasksStream = tasksStream.filter(task -> task.getStatus().equalsIgnoreCase(status));
            }

            // Фильтрация по приоритету (если передан)
            if (priority != null && !priority.isEmpty()) {
                tasksStream = tasksStream.filter(task -> task.getPriority().equalsIgnoreCase(priority));
            }

            // Преобразуем отфильтрованный Stream обратно в List
            List<Tasks> filteredTasks = tasksStream.collect(Collectors.toList());

            // Пагинация
            Pageable pageable = PageRequest.of(page, size);
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), filteredTasks.size());
            Page<Tasks> tasksPage = new PageImpl<>(filteredTasks.subList(start, end), pageable, filteredTasks.size());

            List<TaskDTOAnswer> taskDTOs = tasksPage.stream()
                    .map(task -> new TaskDTOAnswer(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> delTask(Long task_id){
        Tasks tasks = tasksRepository.findById(task_id).orElseThrow(() -> new RuntimeException("Task not found"));
        tasksRepository.delete(tasks);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<?> addExecutor(Long task_id, Long executor_id){
        Tasks task = tasksRepository.findById(task_id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Users user = usersRepository.findById(executor_id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Set<Users> userSet = task.getTasksUsers();
        userSet.add(user);
        task.setTasksUsers(userSet);
        tasksRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    public ResponseEntity<?> updateTask(Long id, String title, String description, String status, String priority){
        Optional<Tasks> task = tasksRepository.findById(id);
        if (task.isPresent()){
            if (title != null && description != null && status != null && priority != null){
                Tasks updateTask = task.get();
                updateTask.setTitle(title);
                updateTask.setDescription(description);
                updateTask.setStatus(status);
                updateTask.setPriority(priority);
                tasksRepository.save(updateTask);
                return new ResponseEntity<>(updateTask, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateStatus(Long task_id, String status){
        Tasks task = tasksRepository.findById(task_id).orElseThrow(() -> new RuntimeException("Task not found"));
        if (status != null){
            task.setStatus(status);
            tasksRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> watchTasksUser(Long user_id, String status, String priority, int page, int size){
        if (usersRepository.findById(user_id).isPresent()){

            Users user = usersRepository.findById(user_id).get();
            Set<Tasks> tasksSet = user.getTasksSetUsers();

            // Преобразуем Set в Stream для фильтрации
            Stream<Tasks> tasksStream = tasksSet.stream();

            // Фильтрация по статусу (если передан)
            if (status != null && !status.isEmpty()) {
                tasksStream = tasksStream.filter(task -> task.getStatus().equalsIgnoreCase(status));
            }

            // Фильтрация по приоритету (если передан)
            if (priority != null && !priority.isEmpty()) {
                tasksStream = tasksStream.filter(task -> task.getPriority().equalsIgnoreCase(priority));
            }

            // Преобразуем отфильтрованный Stream обратно в List
            List<Tasks> filteredTasks = tasksStream.collect(Collectors.toList());

            // Пагинация
            Pageable pageable = PageRequest.of(page, size);
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), filteredTasks.size());
            Page<Tasks> tasksPage = new PageImpl<>(filteredTasks.subList(start, end), pageable, filteredTasks.size());

            List<TaskDTOAnswer> taskDTOs = tasksPage.stream()
                    .map(task -> new TaskDTOAnswer(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
