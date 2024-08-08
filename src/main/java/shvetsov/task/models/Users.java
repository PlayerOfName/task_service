package shvetsov.task.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    /*
     * id пользователя
     */
    private Long id;

    /*
     * email пользователя
     */
    @Column(name = "email")
    private String email;

    /*
     * пароль пользователя
     */
    @Column(name = "password")
    private String password;

    /*
     * дата создания пользователя
     */
    @Column(name = "created_at")
    private ZonedDateTime created_at;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "users_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id", referencedColumnName="id") })
    private Set<Roles> userRoles = new HashSet<Roles>();

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_tasks",
            joinColumns = { @JoinColumn(name = "users_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "tasks_id", referencedColumnName="id") })
    private Set<Tasks> tasksSetUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_tasks",
            joinColumns = { @JoinColumn(name = "users_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "tasks_id", referencedColumnName="id") })
    private Set<Tasks> tasksSetAuthors = new HashSet<>();

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userRoles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
