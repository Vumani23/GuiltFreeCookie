package za.ac.cput.guiltfreecookie.factory;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.guiltfreecookie.domain.Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminFactoryTest {

    @Test
    @Order(1)
    void testCreateAdminSuccess() {
        Admin admin = AdminFactory.createAdmin(
                "AD001",
                "Jane",
                "Doe",
                "jane.doe@guiltfreecookie.com",
                "SecurePass123"
        );

        assertNotNull(admin);
        assertEquals("AD001", admin.getAdminId());
        assertEquals("Jane", admin.getFirstName());
        assertEquals("Doe", admin.getLastName());
        assertEquals("jane.doe@guiltfreecookie.com", admin.getEmail());
        assertEquals("SecurePass123", admin.getPassword());
        assertTrue(admin.isActive(), "A newly-factoried admin should be active by default");
        assertNull(admin.getPasswordExpiresAt(), "A permanent password should have no expiry");
    }

    @Test
    @Order(2)
    void testCreateAdminWithNullFirstName() {
        Admin admin = AdminFactory.createAdmin("AD002", null, "Doe", "jane@guiltfreecookie.com", "pass");

        assertNull(admin);
    }

    @Test
    @Order(3)
    void testCreateAdminWithEmptyFirstName() {
        Admin admin = AdminFactory.createAdmin("AD003", "", "Doe", "jane@guiltfreecookie.com", "pass");

        assertNull(admin);
    }

    @Test
    @Order(4)
    void testCreateAdminWithEmptyLastName() {
        Admin admin = AdminFactory.createAdmin("AD004", "Jane", "", "jane@guiltfreecookie.com", "pass");

        assertNull(admin);
    }

    @Test
    @Order(5)
    void testCreateAdminWithEmptyEmail() {
        Admin admin = AdminFactory.createAdmin("AD005", "Jane", "Doe", "", "pass");

        assertNull(admin);
    }

    @Test
    @Order(6)
    void testCreateAdminWithEmptyPassword() {
        Admin admin = AdminFactory.createAdmin("AD006", "Jane", "Doe", "jane@guiltfreecookie.com", "");

        assertNull(admin);
    }
}
