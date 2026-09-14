package za.ac.cput.guiltfreecookie.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCartFactoryTest {

    @Test
    void testCreateCustomerCart() {
        CustomerCart cart = CustomerCartFactory.createCustomerCart("wazeerg@gmail.com", "cart-001");

        assertNotNull(cart);
        assertEquals("wazeerg@gmail.com", cart.getCustomerEmail());
        assertEquals("cart-001", cart.getCartId());
    }

    @Test
    void testCreateCustomerCartRejectsEmptyEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> CustomerCartFactory.createCustomerCart("", "cart-001"));
    }
}
