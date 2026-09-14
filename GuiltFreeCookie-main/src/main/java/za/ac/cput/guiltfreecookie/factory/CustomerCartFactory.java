package za.ac.cput.guiltfreecookie.factory;

import za.ac.cput.guiltfreecookie.domain.CustomerCart;
import za.ac.cput.guiltfreecookie.util.Helper;

public class CustomerCartFactory {

    public static CustomerCart createCustomerCart(String customerEmail, String cartId) {
        if (Helper.isNullOrEmpty(customerEmail)) {
            throw new IllegalArgumentException("Customer email cannot be empty");
        }

        if (Helper.isNullOrEmpty(cartId)) {
            throw new IllegalArgumentException("Cart ID cannot be empty");
        }

        return new CustomerCart.Builder()
                .setCustomerEmail(customerEmail)
                .setCartId(cartId)
                .build();
    }
}
