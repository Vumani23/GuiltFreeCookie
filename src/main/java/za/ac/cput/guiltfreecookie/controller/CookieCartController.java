package za.ac.cput.guiltfreecookie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.service.CookieCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cookie-carts")
@CrossOrigin(origins = "http://localhost:5173")
public class CookieCartController {

    private final CookieCartService cookieCartService;

    public CookieCartController(CookieCartService cookieCartService) {
        this.cookieCartService = cookieCartService;
    }

    @PostMapping
    public ResponseEntity<CookieCart> create(@RequestBody CookieCart cookieCart) {
        try {
            return new ResponseEntity<>(cookieCartService.create(cookieCart), HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CookieCart>> getByCartId(@PathVariable String cartId) {
        return ResponseEntity.ok(cookieCartService.getByCartId(cartId));
    }

    @DeleteMapping("/{cartId}/{cookieId}")
    public ResponseEntity<Void> delete(@PathVariable String cartId, @PathVariable String cookieId) {
        if (!cookieCartService.delete(cookieId, cartId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}