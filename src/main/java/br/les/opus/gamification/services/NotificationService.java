package br.les.opus.gamification.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

//classe de notificação, você poderá usar os métodos sendNotificationId para enviar mensagem para um usuário em específico, ou sendNotificationTopic para vários usuários conectados ao tópico
@Service
public class NotificationService {

    public NotificationService(){
        try {

            Resource resource = new ClassPathResource("firebase.json");
            InputStream inputStream = resource.getInputStream();

            //arquivo de configurações do google
            //FileInputStream serviceAccount = new FileInputStream(inputStream);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            if(FirebaseApp.getApps().isEmpty())
                FirebaseApp.initializeApp(options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendNotificationId(Map<String,String> payload, String id){

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putAllData(payload)
                .setToken(id)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return false;
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return true;
    }

    public boolean sendNotificationTopic(Map<String,String> payload, String topic){

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putAllData(payload)
                .setTopic(topic)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return false;
        }

        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return true;
    }
}