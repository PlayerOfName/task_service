package shvetsov.task.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    /*
     * id роли
     */
    private Long id;

    /*
     * зоголовок роли
     */
    @Column(name = "title")
    private String title;

    @Override
    public String getAuthority() {
        return this.title;
    }
}
