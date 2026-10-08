package za.ac.cput.guiltfreecookie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.domain.CookieCartId;

import java.util.List;

@Repository
public interface CookieCartRepository extends JpaRepository<CookieCart, CookieCartId> {
    List<CookieCart> findByCartId(String cartId);
}