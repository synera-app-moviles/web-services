package synera.centralis.api.shared.infrastructure.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of ImageUploadService using Cloudinary.
 * Handles image upload, deletion, and validation operations.
 */
@Slf4j
@Service
public class CloudinaryImageUploadServiceImpl implements ImageUploadService {

    private final Cloudinary cloudinary;
    
    private static final List<String> ALLOWED_FORMATS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public CloudinaryImageUploadServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Optional<String> uploadImage(MultipartFile file, String folder) {
        if (!isValidImage(file)) {
            log.error("Invalid image file: {}", file.getOriginalFilename());
            return Optional.empty();
        }

        try {
            String publicId = folder + "/" + UUID.randomUUID().toString();
            
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folder,
                    "public_id", publicId,
                    "resource_type", "image",
                    "quality", "auto:good",
                    "fetch_format", "auto"
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
            String imageUrl = (String) uploadResult.get("secure_url");
            
            log.info("Successfully uploaded image to Cloudinary: {}", imageUrl);
            return Optional.of(imageUrl);
            
        } catch (IOException e) {
            log.error("Error uploading image to Cloudinary", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> uploadImageFromUrl(String imageUrl, String folder) {
        if (!isValidImageUrl(imageUrl)) {
            log.error("Invalid image URL: {}", imageUrl);
            return Optional.empty();
        }

        try {
            String publicId = folder + "/" + UUID.randomUUID().toString();
            
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folder,
                    "public_id", publicId,
                    "resource_type", "image",
                    "quality", "auto:good",
                    "fetch_format", "auto"
            );

            Map uploadResult = cloudinary.uploader().upload(imageUrl, uploadParams);
            String resultUrl = (String) uploadResult.get("secure_url");
            
            log.info("Successfully uploaded image from URL to Cloudinary: {}", resultUrl);
            return Optional.of(resultUrl);
            
        } catch (IOException e) {
            log.error("Error uploading image from URL to Cloudinary", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return false;
        }

        try {
            // Extract public ID from Cloudinary URL
            String publicId = extractPublicIdFromUrl(imageUrl);
            if (publicId == null) {
                log.error("Could not extract public ID from URL: {}", imageUrl);
                return false;
            }

            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String status = (String) result.get("result");
            
            boolean success = "ok".equals(status);
            if (success) {
                log.info("Successfully deleted image from Cloudinary: {}", imageUrl);
            } else {
                log.warn("Failed to delete image from Cloudinary: {}, result: {}", imageUrl, status);
            }
            
            return success;
            
        } catch (IOException e) {
            log.error("Error deleting image from Cloudinary: {}", imageUrl, e);
            return false;
        }
    }

    @Override
    public boolean isValidImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            log.error("File size exceeds maximum allowed: {} bytes", file.getSize());
            return false;
        }

        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            log.error("Invalid content type: {}", contentType);
            return false;
        }

        // Check file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_FORMATS.contains(extension)) {
            log.error("Invalid file extension: {}", extension);
            return false;
        }

        return true;
    }

    @Override
    public boolean isValidImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(imageUrl);
            String protocol = url.getProtocol();
            if (!"http".equals(protocol) && !"https".equals(protocol)) {
                return false;
            }

            String path = url.getPath().toLowerCase();
            return ALLOWED_FORMATS.stream().anyMatch(format -> path.endsWith("." + format));
            
        } catch (Exception e) {
            log.error("Invalid URL format: {}", imageUrl);
            return false;
        }
    }

    /**
     * Extracts the public ID from a Cloudinary URL.
     */
    private String extractPublicIdFromUrl(String imageUrl) {
        try {
            // Cloudinary URLs have format: https://res.cloudinary.com/{cloud_name}/image/upload/{transformations}/{public_id}.{format}
            if (!imageUrl.contains("cloudinary.com")) {
                return null;
            }

            int uploadIndex = imageUrl.indexOf("/upload/");
            if (uploadIndex == -1) {
                return null;
            }

            String afterUpload = imageUrl.substring(uploadIndex + 8);
            
            // Remove transformations if present (they contain slashes and commas)
            String[] parts = afterUpload.split("/");
            if (parts.length == 0) {
                return null;
            }

            String lastPart = parts[parts.length - 1];
            
            // Remove file extension
            int dotIndex = lastPart.lastIndexOf('.');
            return dotIndex > 0 ? lastPart.substring(0, dotIndex) : lastPart;
            
        } catch (Exception e) {
            log.error("Error extracting public ID from URL: {}", imageUrl, e);
            return null;
        }
    }

    /**
     * Gets the file extension from a filename.
     */
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(dotIndex + 1) : "";
    }
}