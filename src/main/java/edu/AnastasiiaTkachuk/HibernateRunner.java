package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import edu.AnastasiiaTkachuk.util.TestDataImporter;
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
import java.util.List;
import java.util.Set;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) throws SQLException {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()) {

            session.beginTransaction();

//            User user = session.get(User.class, 1L);

            List<User> users = session.createQuery("select u from User u", User.class)
                    .list();
            users.forEach(user -> System.out.println(user.getUserChats().size()));
            users.forEach(user -> System.out.println(user.getCompany().getName()));


            session.getTransaction().commit();
        }
    }
}
