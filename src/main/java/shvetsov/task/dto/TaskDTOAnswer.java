package shvetsov.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Task dto answer.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTOAnswer {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
}
