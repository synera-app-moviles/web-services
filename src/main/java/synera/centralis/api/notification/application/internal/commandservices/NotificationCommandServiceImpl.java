package synera.centralis.api.notification.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synera.centralis.api.notification.domain.model.aggregates.Notification;
import synera.centralis.api.notification.domain.model.commands.CreateNotificationCommand;
import synera.centralis.api.notification.domain.model.commands.UpdateNotificationStatusCommand;
import synera.centralis.api.notification.domain.model.events.NotificationCreatedEvent;
import synera.centralis.api.notification.domain.model.events.NotificationSentEvent;
import synera.centralis.api.notification.domain.services.NotificationCommandService;
import synera.centralis.api.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    
    private final NotificationRepository notificationRepository;
    
    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Override
    @Transactional
    public Optional<Notification> handle(CreateNotificationCommand command) {
        var notification = new Notification(
                command.title(),
                command.message(),
                command.recipients(),
                command.priority()
        );
        
        try {
            var savedNotification = notificationRepository.save(notification);
            
            // Register domain event
            savedNotification.addDomainEvent(new NotificationCreatedEvent(
                    savedNotification.getId(),
                    savedNotification.getTitle(),
                    savedNotification.getMessage(),
                    savedNotification.getRecipients()
            ));
            
            return Optional.of(savedNotification);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    @Override
    @Transactional
    public Optional<Notification> handle(UpdateNotificationStatusCommand command) {
        var notification = notificationRepository.findById(command.notificationId());
        
        if (notification.isEmpty()) {
            return Optional.empty();
        }
        
        var existingNotification = notification.get();
        
        // Update status using business methods
        switch (command.status()) {
            case SENT -> existingNotification.markAsSent();
            case FAILED -> existingNotification.markAsFailed();
            case READ -> existingNotification.markAsRead();
            default -> existingNotification.setStatus(command.status());
        }
        
        try {
            var savedNotification = notificationRepository.save(existingNotification);
            
            // Register domain event
            savedNotification.addDomainEvent(new NotificationSentEvent(
                    savedNotification.getId(),
                    savedNotification.getStatus()
            ));
            
            return Optional.of(savedNotification);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}