package synera.centralis.api.notification.infrastructure.messaging.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfiguration {
    
    @Value("${firebase.credentials.path}")
    private Resource credentialsResource;
    
    @Value("${firebase.project.id}")
    private String projectId;
    
    @Value("${firebase.database.url}")
    private String databaseUrl;
    
    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                GoogleCredentials credentials = GoogleCredentials
                        .fromStream(credentialsResource.getInputStream());
                
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(credentials)
                        .setProjectId(projectId) 
                        .setDatabaseUrl(databaseUrl)
                        .build();
                
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
    
    @Bean
    public FirebaseApp firebaseApp() {
        return FirebaseApp.getInstance();
    }
}