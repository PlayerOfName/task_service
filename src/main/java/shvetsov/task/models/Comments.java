package shvetsov.task.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    /*
    * id комментария
    */
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tasks_id", nullable = false)
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users user;

    /*
     * текст комментария
     */
    @Column(name = "content")
    private String content;

    /*
     * дата создания комментария
     */
    @Column(name = "created_at")
    private ZonedDateTime created_at;

    public Comments(Tasks task, Users user, String content) {
        this.task = task;
        this.user = user;
        this.content = content;
    }
}
