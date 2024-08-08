package shvetsov.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Comments dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private Long tasks_id;
    private Long users_id;
    private String content;
}
