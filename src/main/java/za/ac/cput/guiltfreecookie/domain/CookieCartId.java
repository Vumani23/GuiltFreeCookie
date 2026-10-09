package za.ac.cput.guiltfreecookie.domain;

import java.io.Serializable;
import java.util.Objects;

public class CookieCartId implements Serializable {

    private String cookieId;
    private String cartId;

    public CookieCartId() {}

    public CookieCartId(String cookieId, String cartId) {
        this.cookieId = cookieId;
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CookieCartId that)) return false;
        return Objects.equals(cookieId, that.cookieId)
                && Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookieId, cartId);
    }
}
