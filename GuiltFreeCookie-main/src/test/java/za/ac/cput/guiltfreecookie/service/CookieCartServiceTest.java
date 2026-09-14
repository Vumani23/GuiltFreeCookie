package za.ac.cput.guiltfreecookie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.domain.CookieCartId;
import za.ac.cput.guiltfreecookie.repository.CookieCartRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CookieCartServiceTest {

    @Mock
    private CookieCartRepository repository;

    @InjectMocks
    private CookieCartService service;

    @Test
    void testRead() {
        CookieCart cart = new CookieCart.Builder()
                .setCookieId("CK001")
                .setCartId("cart-001")
                .build();

        CookieCartId id = new CookieCartId("CK001", "cart-001");
        when(repository.findById(id)).thenReturn(Optional.of(cart));

        CookieCart result = service.read(id);

        assertNotNull(result);
        assertEquals("CK001", result.getCookieId());
    }
}
