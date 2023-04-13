package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;
import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) throws SQLException {

        Company company = Company.builder()
                .name("Amazon")
                .build();

        User user = User.builder()
                .username("ivan@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Petr")
                        .birthday(new Birthday(LocalDate.of(2000, 1, 19)))
                        .build())
                .role(Role.ADMIN)
                .company(company)
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

//                session1.persist(company);
                session1.persist(user);

//                User user1 = session1.get(User.class, 1L);
//                session1.evict(user1);

                session1.getTransaction().commit();
            }
        }
    }
}
