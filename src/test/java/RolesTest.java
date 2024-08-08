import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shvetsov.task.models.Roles;

import static org.junit.jupiter.api.Assertions.*;

class RolesTest {

    private Roles role;

    @BeforeEach
    void setUp() {
        role = new Roles();
    }

    @Test
    void testNoArgsConstructor() {
        assertNotNull(role);
        assertNull(role.getId());
        assertNull(role.getTitle());
    }

    @Test
    void testAllArgsConstructor() {
        Roles fullRole = new Roles(1L, "ROLE_ADMIN");

        assertNotNull(fullRole);
        assertEquals(1L, fullRole.getId());
        assertEquals("ROLE_ADMIN", fullRole.getTitle());
    }

    @Test
    void testGettersAndSetters() {
        role.setId(2L);
        role.setTitle("ROLE_USER");

        assertEquals(2L, role.getId());
        assertEquals("ROLE_USER", role.getTitle());
    }

    @Test
    void testGetAuthority() {
        role.setTitle("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getAuthority());
    }
}