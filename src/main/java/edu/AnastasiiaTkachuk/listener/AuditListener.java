package edu.AnastasiiaTkachuk.listener;

import edu.AnastasiiaTkachuk.entity.Chat;
import edu.AnastasiiaTkachuk.entity.UserChat;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;

import java.time.Instant;

public class AuditListener {
    @PostPersist
    public void postPersist(UserChat userChat){
        Chat chat = userChat.getChat();
        chat.setCount(chat.getCount()+ 1);
    }
    @PostRemove
    public void postRemove(UserChat userChat){
        Chat chat = userChat.getChat();
        chat.setCount(chat.getCount()- 1);
    }
    @PrePersist
    public void prePersist(UserChat entity){
        entity.setCreatedAt(Instant.now());
    }
}
