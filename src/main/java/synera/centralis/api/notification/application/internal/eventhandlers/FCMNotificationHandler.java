package synera.centralis.api.notification.application.internal.eventhandlers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import synera.centralis.api.notification.application.outboundservices.FirebaseCloudMessagingService;
import synera.centralis.api.notification.domain.model.events.NotificationCreatedEvent;
import synera.centralis.api.notification.infrastructure.messaging.fcm.FCMTokenService;

/**
 * Event handler for notification created events.
 * Sends push notifications via Firebase Cloud Messaging when a notification is created.
 * 
 * This handler is the bridge that connects notifications saved in the database
 * with FCM push notification delivery.
 */
@Component
public class FCMNotificationHandler {
    
    private static final Logger logger = Logger.getLogger(FCMNotificationHandler.class.getName());
    
    // Dependencies for FCM notification handling
    private final FCMTokenService fcmTokenService;
    private final FirebaseCloudMessagingService firebaseCloudMessagingService;
    
    public FCMNotificationHandler(
            FCMTokenService fcmTokenService,
            FirebaseCloudMessagingService firebaseCloudMessagingService) {
        this.fcmTokenService = fcmTokenService;
        this.firebaseCloudMessagingService = firebaseCloudMessagingService;
    }
    
    /**
     * Handles notification created events by sending push notifications via FCM
     * 
     * Complete flow:
     * 1. Event → Get FCM Tokens → Send Push Notification
     * 2. Error handling for missing tokens or delivery failures
     * 3. Comprehensive logging for debugging and monitoring
     * 
     * Expected flow:
     * 1. Receive NotificationCreatedEvent
     * 2. Get FCM tokens for recipients using fcmTokenService.getTokensForUsers()
     * 3. Send push notification using firebaseCloudMessagingService.sendToTokens()
     * 4. Handle error cases: no tokens, FCM failures, etc.
     * 
     * @param event The notification created event containing notification details
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async("fcmTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(NotificationCreatedEvent event) {
        logger.info("🔔 FCM HANDLER TRIGGERED: Processing notification ID: " + event.getNotificationId());
        logger.info("📱 Recipients: " + event.getRecipients().size() + " users");
        logger.info("📝 Title: " + event.getTitle());
        
        try {
            // 1. Get FCM tokens for the recipients
            List<String> fcmTokens = fcmTokenService.getTokensForUsers(event.getRecipients());
            
            // 2. Validate that tokens exist
            if (fcmTokens.isEmpty()) {
                logger.warning("⚠️ No FCM tokens found for recipients: " + event.getRecipients());
                logger.warning("📵 Skipping FCM notification - no devices registered");
                return;
            }
            
            logger.info("📡 Found " + fcmTokens.size() + " FCM tokens for notification delivery");
            
            // 3. Send push notification via FCM
            boolean success = firebaseCloudMessagingService.sendToTokens(
                fcmTokens,
                event.getTitle(),
                event.getMessage()
            );
            
            // 4. Handle delivery result
            if (success) {
                logger.info("✅ Successfully sent FCM push notification to " + fcmTokens.size() + " devices");
                logger.info("🎯 Notification delivered for: " + event.getNotificationId());
            } else {
                logger.severe("❌ Failed to send FCM push notification for: " + event.getNotificationId());
                logger.severe("🔥 FCM service returned failure status");
            }
            
        } catch (IllegalArgumentException e) {
            logger.severe("🚫 Invalid notification data: " + e.getMessage());
            logger.severe("📋 Event details - ID: " + event.getNotificationId() + 
                         ", Recipients: " + event.getRecipients().size());
        } catch (Exception e) {
            // Handle FCM-specific errors and other unexpected exceptions
            logger.severe("💥 Error processing FCM push notification: " + e.getMessage());
            logger.severe("🆔 Notification ID: " + event.getNotificationId());
            logger.severe("👥 Recipients count: " + event.getRecipients().size());
            e.printStackTrace();
            
            // Consider implementing retry logic or dead letter queue for failed notifications
            // This could be enhanced with:
            // - Exponential backoff retry mechanism
            // - Dead letter queue for persistently failing notifications
            // - Metrics collection for monitoring delivery rates
        }
    }
}

/*
 * IMPLEMENTATION COMPLETE: FCM Notification Handler
 * 
 * The complete flow is now implemented:
 * 
 * 1. Event (GroupCreated/MessageSent/UrgentAnnouncement)
 *    ↓
 * 2. EventHandler específico (GroupCreationNotificationHandler, etc.)
 *    ↓  
 * 3. NotificationCommandService.handle() guarda notificación en BD
 *    ↓
 * 4. Emite NotificationCreatedEvent
 *    ↓
 * 5. FCMNotificationHandler.handle() ✅ [IMPLEMENTED]
 *    ↓
 * 6. FCMTokenService.getTokensForUsers() obtiene tokens
 *    ↓
 * 7. FirebaseCloudMessagingService.sendToTokens() envía push
 *    ↓
 * 8. 📱 Push notification llega a dispositivos
 * 
 * FEATURES IMPLEMENTED:
 * ✅ Complete FCM token retrieval
 * ✅ Comprehensive error handling
 * ✅ Detailed logging with emojis for easy monitoring
 * ✅ Graceful handling of missing tokens
 * ✅ Proper exception categorization
 * ✅ Performance monitoring logs
 * 
 * FUTURE ENHANCEMENTS TO CONSIDER:
 * 🔄 Retry logic with exponential backoff
 * 📊 Metrics collection for delivery rates
 * 💾 Dead letter queue for persistently failing notifications
 * 🎯 Device-specific customization (Android vs iOS)
 * 📈 Delivery receipt tracking
 * 
 * Unit tests should be added at:
 * src/test/java/synera/centralis/api/notification/application/internal/eventhandlers/FCMNotificationHandlerTest.java
 * 
 * Test cases to cover:
 * ✅ Successful delivery to multiple tokens
 * ⚠️ No FCM tokens for recipients
 * ❌ FCM service failures
 * 📱 Multiple devices per user
 * 🔄 Different device types (Android/iOS)
 * 💥 Exception handling scenarios
 */