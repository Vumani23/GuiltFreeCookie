package za.ac.cput.guiltfreecookie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.domain.CustomerCartId;
import za.ac.cput.guiltfreecookie.repository.CustomerCartRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerCartServiceTest {

    @Mock
    private CustomerCartRepository repository;

    @InjectMocks
    private CustomerCartService service;

    @Test
    void testRead() {
        CustomerCart cart = new CustomerCart.Builder()
                .setCustomerEmail("wazeerg@gmail.com")
                .setCartId("cart-001")
                .build();

        CustomerCartId id = new CustomerCartId("wazeerg@gmail.com", "cart-001");
        when(repository.findById(id)).thenReturn(Optional.of(cart));

        CustomerCart result = service.read(id);

        assertNotNull(result);
        assertEquals("cart-001", result.getCartId());
    }
}
