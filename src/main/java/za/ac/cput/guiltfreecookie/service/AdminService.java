package za.ac.cput.guiltfreecookie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.guiltfreecookie.domain.Admin;
import za.ac.cput.guiltfreecookie.repository.AdminRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService implements IService<Admin, String> {

    private static final String TEMP_PASSWORD_CHARS =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final int TEMP_PASSWORD_LENGTH = 10;
    private static final long TEMP_PASSWORD_VALID_HOURS = 24;

    private final AdminRepository adminRepository;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId()).orElse(null);

        if (existing == null) {
            return null;
        }

        Admin updated = new Admin.Builder()
                .copy(existing)
                .setFirstName(admin.getFirstName())
                .setLastName(admin.getLastName())
                .setEmail(admin.getEmail())
                .build();

        return adminRepository.save(updated);
    }

    @Override
    public boolean delete(String id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);

        if (admin == null || !admin.isActive()) {
            return null;
        }

        if (admin.getPasswordExpiresAt() != null
                && LocalDateTime.now().isAfter(admin.getPasswordExpiresAt())) {
            return null;
        }

        return admin.getPassword().equals(password) ? admin : null;
    }

    public Admin setActive(String id, boolean active) {
        Admin existing = adminRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        Admin updated = new Admin.Builder()
                .copy(existing)
                .setActive(active)
                .build();

        return adminRepository.save(updated);
    }

    public Admin resetPassword(String id) {
        Admin existing = adminRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        Admin updated = new Admin.Builder()
                .copy(existing)
                .setPassword(generateTempPassword())
                .setPasswordExpiresAt(LocalDateTime.now().plusHours(TEMP_PASSWORD_VALID_HOURS))
                .build();

        return adminRepository.save(updated);
    }

    public Admin changePassword(String id, String newPassword) {
        Admin existing = adminRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        Admin updated = new Admin.Builder()
                .copy(existing)
                .setPassword(newPassword)
                .setPasswordExpiresAt(null)
                .build();

        return adminRepository.save(updated);
    }

    private String generateTempPassword() {
        StringBuilder builder = new StringBuilder(TEMP_PASSWORD_LENGTH);
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            builder.append(TEMP_PASSWORD_CHARS.charAt(random.nextInt(TEMP_PASSWORD_CHARS.length())));
        }
        return builder.toString();
    }
}
