package shvetsov.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvetsov.task.dto.TaskDTO;
import shvetsov.task.models.Tasks;
import shvetsov.task.services.TaskService;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks API", description = "API для управления задачами")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Создать задачу", description = "Создаёт задачу")
    @PostMapping("/created-task")
    public ResponseEntity<?> createdTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createdTask(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getStatus(),
                taskDTO.getPriority(),
                taskDTO.getEmail()
        );
    }

    @Operation(summary = "Просмотреть задачи исполнителя", description = "Просматривает задачи исполнителя")
    @GetMapping("/watch-task-user")
    public ResponseEntity<?> watchTaskUser(@RequestParam Long user_id,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false) String priority,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){

        return taskService.watchTasksUser(user_id, status, priority, page, size);
    }

    @Operation(summary = "Обновить статус задачи", description = "Обновляет статус задачи")
    @PatchMapping("update-status")
    public ResponseEntity<?> updateStatus(@RequestParam Long task_id, @RequestParam String status){
        return taskService.updateStatus(task_id, status);
    }

    @Operation(summary = "Обновить задачу", description = "Обновляет задачу")
    @PutMapping("update-task")
    public ResponseEntity<?> updateTask(@RequestBody Tasks task){
        return taskService.updateTask(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority()
        );
    }

    @Operation(summary = "Добавить исполнителя", description = "Добавляет исполнителя")
    @PatchMapping("add-executor")
    public ResponseEntity<?> addExecutor(@RequestParam Long task_id, @RequestParam Long executor_id){
        return taskService.addExecutor(task_id, executor_id);
    }

    @Operation(summary = "Удачить задачу", description = "Удаляет задачу")
    @PutMapping("delete-task")
    public ResponseEntity<?> deleteTask(@RequestParam Long task_id){
        return taskService.delTask(task_id);
    }

    @Operation(summary = "Просмотреть задачи авторов", description = "Просматривает задачи автора")
    @GetMapping("/watch-task-author")
    public ResponseEntity<?> watchTaskAuthor(@RequestParam Long author_id,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false) String priority,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){

        return taskService.watchTasksAuthor(author_id, status, priority, page, size);
    }

}
