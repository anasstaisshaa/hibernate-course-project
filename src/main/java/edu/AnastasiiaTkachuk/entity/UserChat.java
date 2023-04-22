package edu.AnastasiiaTkachuk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "users_chat")
public class UserChat extends BaseEntity<Long>{
    @ManyToOne
    private User user;

    @ManyToOne
    private Chat chat;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    public void setUser(User user){
        this.user = user;
        this.user.getUserChats().add(this);
    }
    public void setChat(Chat chat){
        this.chat = chat;
        this.chat.getUserChats().add(this);
    }
}
