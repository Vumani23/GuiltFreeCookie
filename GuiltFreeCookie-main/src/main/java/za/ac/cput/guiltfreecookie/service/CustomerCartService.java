package za.ac.cput.guiltfreecookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.domain.CustomerCartId;
import za.ac.cput.guiltfreecookie.repository.CustomerCartRepository;

import java.util.List;

@Service
public class CustomerCartService implements IService<CustomerCart, CustomerCartId> {

    private final CustomerCartRepository repository;

    @Autowired
    public CustomerCartService(CustomerCartRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerCart create(CustomerCart customerCart) {
        return repository.save(customerCart);
    }

    @Override
    public CustomerCart read(CustomerCartId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CustomerCart update(CustomerCart customerCart) {
        return repository.save(customerCart);
    }

    @Override
    public boolean delete(CustomerCartId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CustomerCart> getAll() {
        return repository.findAll();
    }
}
