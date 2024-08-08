package shvetsov.task.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    /*
     * id задачи
     */
    private Long id;

    /*
     * заголовок задачи
     */
    @Column(name = "title")
    private String title;

    /*
     * описание задачи
     */
    @Column(name = "description")
    private String description;

    /*
     * статус задачи
     */
    @Column(name = "status")
    private String status;

    /*
     * приоритет задачи
     */
    @Column(name = "priority")
    private String priority;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comments> comments = new HashSet<>();

    /*
     * дата создания задачи
     */
    @Column(name = "created_at")
    private ZonedDateTime created_at;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_tasks",
            joinColumns = { @JoinColumn(name = "tasks_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "users_id", referencedColumnName="id") })
    private Set<Users> tasksUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_tasks",
            joinColumns = { @JoinColumn(name = "tasks_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "users_id", referencedColumnName="id") })
    private Set<Users> tasksAuthors = new HashSet<>();

    public Tasks(String title, String description, String status, String priority){
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }
}
