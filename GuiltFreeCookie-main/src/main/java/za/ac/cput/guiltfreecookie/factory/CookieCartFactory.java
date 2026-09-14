package za.ac.cput.guiltfreecookie.factory;

import za.ac.cput.guiltfreecookie.domain.CookieCart;
import za.ac.cput.guiltfreecookie.util.Helper;

public class CookieCartFactory {

    public static CookieCart createCookieCart(String cookieId, String cartId) {
        if (Helper.isNullOrEmpty(cookieId)) {
            throw new IllegalArgumentException("Cookie ID cannot be empty");
        }

        if (Helper.isNullOrEmpty(cartId)) {
            throw new IllegalArgumentException("Cart ID cannot be empty");
        }

        return new CookieCart.Builder()
                .setCookieId(cookieId)
                .setCartId(cartId)
                .build();
    }
}
