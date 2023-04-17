package edu.AnastasiiaTkachuk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "profile", "chats"})
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PersonalInfo personalInfo;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne()
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "users_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> chats = new HashSet<>();

    public void addChat(Chat chat){
        chats.add(chat);
        chat.getUsers().add(this);
    }
}
