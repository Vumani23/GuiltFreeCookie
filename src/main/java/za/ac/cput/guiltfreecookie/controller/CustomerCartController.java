package za.ac.cput.guiltfreecookie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.service.CustomerCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-carts")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerCartController {

    private final CustomerCartService customerCartService;

    public CustomerCartController(CustomerCartService customerCartService) {
        this.customerCartService = customerCartService;
    }

    @PostMapping
    public ResponseEntity<CustomerCart> create(@RequestBody CustomerCart customerCart) {
        try {
            return new ResponseEntity<>(customerCartService.create(customerCart), HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CustomerCart>> getByCartId(@PathVariable String cartId) {
        return ResponseEntity.ok(customerCartService.getByCartId(cartId));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> delete(
            @PathVariable String cartId,
            @RequestParam String customerEmail) {
        if (!customerCartService.delete(customerEmail, cartId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}