package za.ac.cput.guiltfreecookie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue
    private String adminId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime passwordExpiresAt;

    protected Admin() {
    }

    private Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.active = builder.active;
        this.passwordExpiresAt = builder.passwordExpiresAt;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getPasswordExpiresAt() {
        return passwordExpiresAt;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }

    public static class Builder {

        private String adminId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private boolean active = true;
        private LocalDateTime passwordExpiresAt;

        public Builder setAdminId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder setPasswordExpiresAt(LocalDateTime passwordExpiresAt) {
            this.passwordExpiresAt = passwordExpiresAt;
            return this;
        }

        public Builder copy(Admin admin) {
            this.adminId = admin.adminId;
            this.firstName = admin.firstName;
            this.lastName = admin.lastName;
            this.email = admin.email;
            this.password = admin.password;
            this.active = admin.active;
            this.passwordExpiresAt = admin.passwordExpiresAt;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}