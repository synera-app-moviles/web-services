package synera.centralis.api.notification.domain.services;

import synera.centralis.api.notification.domain.model.aggregates.Notification;
import synera.centralis.api.notification.domain.model.commands.CreateNotificationCommand;
import synera.centralis.api.notification.domain.model.commands.UpdateNotificationStatusCommand;

import java.util.Optional;

public interface NotificationCommandService {
    
    /**
     * Handle the creation of a new notification
     * @param command The command containing notification data
     * @return The created notification
     */
    Optional<Notification> handle(CreateNotificationCommand command);
    
    /**
     * Handle updating the status of a notification
     * @param command The command containing notification ID and new status
     * @return The updated notification
     */
    Optional<Notification> handle(UpdateNotificationStatusCommand command);
}