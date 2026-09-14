package za.ac.cput.guiltfreecookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.domain.CookieCartId;
import za.ac.cput.guiltfreecookie.repository.CookieCartRepository;

import java.util.List;

@Service
public class CookieCartService implements IService<CookieCart, CookieCartId> {

    private final CookieCartRepository repository;

    @Autowired
    public CookieCartService(CookieCartRepository repository) {
        this.repository = repository;
    }

    @Override
    public CookieCart create(CookieCart cookieCart) {
        return repository.save(cookieCart);
    }

    @Override
    public CookieCart read(CookieCartId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CookieCart update(CookieCart cookieCart) {
        return repository.save(cookieCart);
    }

    @Override
    public boolean delete(CookieCartId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CookieCart> getAll() {
        return repository.findAll();
    }
}
