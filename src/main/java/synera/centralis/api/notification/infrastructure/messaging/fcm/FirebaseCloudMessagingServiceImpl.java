package synera.centralis.api.notification.infrastructure.messaging.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import synera.centralis.api.notification.application.outboundservices.FirebaseCloudMessagingService;
import synera.centralis.api.notification.domain.model.aggregates.Notification;

import java.util.List;

@Service
public class FirebaseCloudMessagingServiceImpl implements FirebaseCloudMessagingService {
    
    @Value("${fcm.enabled:true}")
    private boolean fcmEnabled;
    
    @Value("${fcm.dry-run:false}")
    private boolean dryRun;
    
    private final FCMMessageBuilder fcmMessageBuilder;
    private final FCMTokenService fcmTokenService;
    
    public FirebaseCloudMessagingServiceImpl(FCMMessageBuilder fcmMessageBuilder, 
                                           FCMTokenService fcmTokenService) {
        this.fcmMessageBuilder = fcmMessageBuilder;
        this.fcmTokenService = fcmTokenService;
    }
    
    @Override
    public boolean sendNotification(Notification notification) {
        if (!fcmEnabled) {
            return false;
        }
        
        try {
            // Get FCM tokens for recipients
            List<String> tokens = fcmTokenService.getTokensForUsers(notification.getRecipients());
            
            if (tokens.isEmpty()) {
                return false;
            }
            
            return sendToTokens(tokens, notification.getTitle(), notification.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sendToTokens(List<String> tokens, String title, String message) {
        if (!fcmEnabled || tokens.isEmpty()) {
            return false;
        }
        
        try {
            MulticastMessage multicastMessage = fcmMessageBuilder
                    .buildMulticastMessage(tokens, title, message);
            
            var response = FirebaseMessaging.getInstance()
                    .sendEachForMulticast(multicastMessage);
            
            return response.getSuccessCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sendToToken(String token, String title, String message) {
        if (!fcmEnabled || token == null || token.isEmpty()) {
            return false;
        }
        
        try {
            Message fcmMessage = fcmMessageBuilder
                    .buildMessage(token, title, message);
            
            String response = FirebaseMessaging.getInstance()
                    .send(fcmMessage);
            
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}