package za.ac.cput.guiltfreecookie.service;

import org.springframework.stereotype.Service;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.domain.CookieCartId;
import za.ac.cput.guiltfreecookie.repository.CookieCartRepository;
import za.ac.cput.guiltfreecookie.repository.CookieRepository;

import java.util.List;

@Service
public class CookieCartService {

    private final CookieCartRepository cookieCartRepository;
    private final CookieRepository cookieRepository;

    public CookieCartService(CookieCartRepository cookieCartRepository, CookieRepository cookieRepository) {
        this.cookieCartRepository = cookieCartRepository;
        this.cookieRepository = cookieRepository;
    }

    public CookieCart create(CookieCart cookieCart) {
        if (cookieCart == null || isBlank(cookieCart.getCookieId()) || isBlank(cookieCart.getCartId())) {
            throw new IllegalArgumentException("Cookie ID and cart ID are required");
        }
        if (!cookieRepository.existsById(cookieCart.getCookieId())) {
            throw new IllegalArgumentException("Cookie does not exist");
        }
        return cookieCartRepository.save(cookieCart);
    }

    public List<CookieCart> getByCartId(String cartId) {
        return cookieCartRepository.findByCartId(cartId);
    }

    public boolean delete(String cookieId, String cartId) {
        CookieCartId id = new CookieCartId(cookieId, cartId);
        if (!cookieCartRepository.existsById(id)) return false;
        cookieCartRepository.deleteById(id);
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}