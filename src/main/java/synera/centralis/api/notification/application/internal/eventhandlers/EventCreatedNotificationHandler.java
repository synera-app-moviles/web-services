package synera.centralis.api.notification.application.internal.eventhandlers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import synera.centralis.api.notification.domain.model.commands.CreateNotificationCommand;
import synera.centralis.api.notification.domain.model.valueobjects.NotificationPriority;
import synera.centralis.api.notification.domain.services.NotificationCommandService;
import synera.centralis.api.shared.domain.events.EventCreatedEvent;

@Component
public class EventCreatedNotificationHandler {

    private static final Logger logger = Logger.getLogger(EventCreatedNotificationHandler.class.getName());

    private final NotificationCommandService notificationCommandService;

    public EventCreatedNotificationHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("eventTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(EventCreatedEvent event) {
        logger.info("📅 Processing event creation notification for: " + event.title());

        try {
            var creatorCommand = new CreateNotificationCommand(
                    "Has creado un evento",
                    "Has creado el evento: " + event.title(),
                    List.of(event.createdBy().toString()),
                    NotificationPriority.HIGH
            );

            notificationCommandService.handle(creatorCommand);
            logger.info("✅ Notification sent to creator: " + event.createdBy());

        } catch (Exception e) {
            logger.severe("❌ Error processing event notification: " + e.getMessage());
        }
    }
}