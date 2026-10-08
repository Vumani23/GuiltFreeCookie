package za.ac.cput.guiltfreecookie.service;

import org.springframework.stereotype.Service;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.domain.CustomerCartId;
import za.ac.cput.guiltfreecookie.repository.CustomerCartRepository;
import za.ac.cput.guiltfreecookie.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerCartService {

    private final CustomerCartRepository customerCartRepository;
    private final CustomerRepository customerRepository;

    public CustomerCartService(CustomerCartRepository customerCartRepository, CustomerRepository customerRepository) {
        this.customerCartRepository = customerCartRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerCart create(CustomerCart customerCart) {
        if (customerCart == null || isBlank(customerCart.getCustomerEmail()) || isBlank(customerCart.getCartId())) {
            throw new IllegalArgumentException("Customer email and cart ID are required");
        }
        if (!customerRepository.existsById(customerCart.getCustomerEmail())) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        return customerCartRepository.save(customerCart);
    }

    public List<CustomerCart> getByCartId(String cartId) {
        return customerCartRepository.findByCartId(cartId);
    }

    public boolean delete(String customerEmail, String cartId) {
        CustomerCartId id = new CustomerCartId(customerEmail, cartId);
        if (!customerCartRepository.existsById(id)) return false;
        customerCartRepository.deleteById(id);
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}