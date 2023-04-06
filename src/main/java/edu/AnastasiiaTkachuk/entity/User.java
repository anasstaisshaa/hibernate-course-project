package edu.AnastasiiaTkachuk.entity;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;

    //@Convert(converter = BirthdayConverter.class)
    private Birthday birthday;

    @Enumerated(EnumType.STRING)
    private Role role;
}
