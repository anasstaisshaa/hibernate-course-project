package edu.AnastasiiaTkachuk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "name")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Companies")
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    //@org.hibernate.annotations.OrderBy(clause = "username DESC, lastname ASC")
    //@OrderBy("username DESC, personalInfo.lastname ASC ")
    @MapKey(name = "username")
    private Map<String, User> users = new HashMap<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale")
    private List<LocaleInfo> locales = new ArrayList<>();

    public void addUser(User user){
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }
}
