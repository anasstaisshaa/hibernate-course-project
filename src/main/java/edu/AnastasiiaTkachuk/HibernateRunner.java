package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.entity.Birthday;
import edu.AnastasiiaTkachuk.entity.PersonalInfo;
import edu.AnastasiiaTkachuk.entity.Role;
import edu.AnastasiiaTkachuk.entity.User;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
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
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.configure();
        User user = User.builder()
                .username("petr2@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Petr")
                        .birthday(new Birthday(LocalDate.of(2000, 1, 19)))
                        .build())
                .role(Role.ADMIN)
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                session1.merge(user);
                log.trace("User is in persistent state: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is close{}", user, session1);
        } catch (Exception e){
            log.error("Exception occurred", e);
            throw e;
        }
    }
}
