import shvetsov.task.models.Roles;
import shvetsov.task.models.Tasks;
import shvetsov.task.models.Users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    private Users user;
    private Roles role;
    private Tasks task;

    @BeforeEach
    void setUp() {
        user = new Users("test@example.com", "password");
        role = new Roles();
        role.setId(1L);
        role.setTitle("ROLE_USER");

        task = new Tasks();
        task.setId(1L);
        task.setTitle("Test Task");
    }

    @Test
    void testNoArgsConstructor() {
        Users emptyUser = new Users();
        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getPassword());
        assertNull(emptyUser.getCreated_at());
        assertNull(emptyUser.getUpdated_at());
        assertTrue(emptyUser.getUserRoles().isEmpty());
        assertTrue(emptyUser.getTasksSetUsers().isEmpty());
        assertTrue(emptyUser.getTasksSetAuthors().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertNull(user.getCreated_at());
        assertNull(user.getUpdated_at());
        assertTrue(user.getUserRoles().isEmpty());
        assertTrue(user.getTasksSetUsers().isEmpty());
        assertTrue(user.getTasksSetAuthors().isEmpty());
    }

    @Test
    void testGettersAndSetters() {
        user.setId(1L);
        user.setCreated_at(ZonedDateTime.now());
        user.setUpdated_at(ZonedDateTime.now());

        assertEquals(1L, user.getId());
        assertNotNull(user.getCreated_at());
        assertNotNull(user.getUpdated_at());
    }

    @Test
    void testUserRolesAssociation() {
        user.getUserRoles().add(role);

        assertEquals(1, user.getUserRoles().size());
        assertTrue(user.getUserRoles().contains(role));
    }

    @Test
    void testTasksSetUsersAssociation() {
        user.getTasksSetUsers().add(task);

        assertEquals(1, user.getTasksSetUsers().size());
        assertTrue(user.getTasksSetUsers().contains(task));
    }

    @Test
    void testTasksSetAuthorsAssociation() {
        user.getTasksSetAuthors().add(task);

        assertEquals(1, user.getTasksSetAuthors().size());
        assertTrue(user.getTasksSetAuthors().contains(task));
    }

    @Test
    void testGetAuthorities() {
        user.getUserRoles().add(role);

        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(role));
    }

    @Test
    void testGetUsername() {
        assertEquals("test@example.com", user.getUsername());
    }
}