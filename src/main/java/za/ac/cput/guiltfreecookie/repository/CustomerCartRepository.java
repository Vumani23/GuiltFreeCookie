package za.ac.cput.guiltfreecookie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.domain.CustomerCartId;

import java.util.List;

@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, CustomerCartId> {
    List<CustomerCart> findByCartId(String cartId);
}