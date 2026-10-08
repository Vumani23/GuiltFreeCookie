package za.ac.cput.guiltfreecookie.domain;

import java.io.Serializable;
import java.util.Objects;

public class CustomerCartId implements Serializable {

    private String customerEmail;
    private String cartId;

    public CustomerCartId() {}

    public CustomerCartId(String customerEmail, String cartId) {
        this.customerEmail = customerEmail;
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CustomerCartId that)) return false;
        return Objects.equals(customerEmail, that.customerEmail)
                && Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerEmail, cartId);
    }
}
