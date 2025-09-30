package synera.centralis.api.notification.interfaces.rest.resources;

import synera.centralis.api.notification.domain.model.valueobjects.NotificationPriority;
import synera.centralis.api.notification.domain.model.valueobjects.NotificationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record NotificationResource(
        UUID id,
        String title,
        String message,
        List<String> recipients, // User UUIDs as strings
        NotificationPriority priority,
        NotificationStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}