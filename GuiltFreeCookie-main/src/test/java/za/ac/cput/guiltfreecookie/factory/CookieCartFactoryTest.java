package za.ac.cput.guiltfreecookie.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.guiltfreecookie.domain.CookieCart;

import static org.junit.jupiter.api.Assertions.*;

class CookieCartFactoryTest {

    @Test
    void testCreateCookieCart() {
        CookieCart cart = CookieCartFactory.createCookieCart("CK001", "cart-001");

        assertNotNull(cart);
        assertEquals("CK001", cart.getCookieId());
        assertEquals("cart-001", cart.getCartId());
    }

    @Test
    void testCreateCookieCartRejectsEmptyCookieId() {
        assertThrows(IllegalArgumentException.class,
                () -> CookieCartFactory.createCookieCart("", "cart-001"));
    }
}
