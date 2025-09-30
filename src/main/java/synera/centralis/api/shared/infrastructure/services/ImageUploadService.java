package synera.centralis.api.shared.infrastructure.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Interface for image upload service.
 * Provides contract for uploading images to cloud storage.
 */
public interface ImageUploadService {

    /**
     * Uploads an image file to cloud storage.
     * @param file the image file to upload
     * @param folder the folder/directory to store the image
     * @return the URL of the uploaded image, or empty if upload failed
     */
    Optional<String> uploadImage(MultipartFile file, String folder);

    /**
     * Uploads an image from a URL to cloud storage.
     * @param imageUrl the URL of the image to upload
     * @param folder the folder/directory to store the image
     * @return the URL of the uploaded image, or empty if upload failed
     */
    Optional<String> uploadImageFromUrl(String imageUrl, String folder);

    /**
     * Deletes an image from cloud storage.
     * @param imageUrl the URL of the image to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteImage(String imageUrl);

    /**
     * Validates if the provided file is a valid image.
     * @param file the file to validate
     * @return true if file is a valid image, false otherwise
     */
    boolean isValidImage(MultipartFile file);

    /**
     * Validates if the provided URL is a valid image URL.
     * @param imageUrl the URL to validate
     * @return true if URL is valid, false otherwise
     */
    boolean isValidImageUrl(String imageUrl);
}