package za.ac.cput.guiltfreecookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "customer_cart")
@IdClass(CustomerCartId.class)
public class CustomerCart {

    @Id
    private String customerEmail;

    @Id
    private String cartId;

    protected CustomerCart() {
    }

    private CustomerCart(Builder builder) {
        this.customerEmail = builder.customerEmail;
        this.cartId = builder.cartId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCartId() {
        return cartId;
    }

    @Override
    public String toString() {
        return "CustomerCart{" +
                "customerEmail='" + customerEmail + '\'' +
                ", cartId='" + cartId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerCart)) return false;

        CustomerCart that = (CustomerCart) obj;

        return Objects.equals(customerEmail, that.customerEmail)
                && Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerEmail, cartId);
    }

    public static class Builder {

        private String customerEmail;
        private String cartId;

        public Builder setCustomerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public Builder setCartId(String cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder copy(CustomerCart customerCart) {
            this.customerEmail = customerCart.customerEmail;
            this.cartId = customerCart.cartId;
            return this;
        }

        public CustomerCart build() {
            return new CustomerCart(this);
        }
    }
}