package za.ac.cput.guiltfreecookie.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ImageStorageService {

    private static final Pattern SAFE_EXTENSION = Pattern.compile("^\\.[a-zA-Z0-9]{1,5}$");

    private final Path rootDir;
    private final String cookieSubdir;

    public ImageStorageService(
            @Value("${app.upload.root-dir}") String rootDir,
            @Value("${app.upload.cookie-subdir}") String cookieSubdir) {
        this.rootDir = Path.of(rootDir).toAbsolutePath().normalize();
        this.cookieSubdir = cookieSubdir;
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("An image file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Uploaded file must be an image");
        }

        try {
            Path targetDir = rootDir.resolve(cookieSubdir);
            Files.createDirectories(targetDir);

            String filename = UUID.randomUUID() + safeExtension(file.getOriginalFilename());
            Path targetFile = targetDir.resolve(filename);
            file.transferTo(targetFile);

            return cookieSubdir + "/" + filename;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to store uploaded image", e);
        }
    }

    public void delete(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }
        try {
            Files.deleteIfExists(rootDir.resolve(relativePath));
        } catch (IOException ignored) {
            // best-effort cleanup; a leftover file is not worth failing the request over
        }
    }

    private String safeExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int dot = originalFilename.lastIndexOf('.');
        if (dot < 0) {
            return "";
        }
        String extension = originalFilename.substring(dot);
        return SAFE_EXTENSION.matcher(extension).matches() ? extension : "";
    }
}
