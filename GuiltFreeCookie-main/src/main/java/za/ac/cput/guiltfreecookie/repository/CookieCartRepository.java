package za.ac.cput.guiltfreecookie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.domain.CookieCartId;

@Repository
public interface CookieCartRepository
        extends JpaRepository<CookieCart, CookieCartId> {
}