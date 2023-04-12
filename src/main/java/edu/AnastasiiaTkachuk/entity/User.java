package edu.AnastasiiaTkachuk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @EmbeddedId
    private PersonalInfo personalInfo;
    @Column(unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
}
