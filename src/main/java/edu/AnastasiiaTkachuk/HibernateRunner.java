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
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) throws SQLException {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()) {

            session.beginTransaction();
//            session.enableFetchProfile("withCompanyAndChats");
//
            Map<String, Object> properties = Map.of(
                    GraphSemantic.LOAD.getJakartaHintName(), session.getEntityGraph("WithCompanyAndChat")
            );
            User user = session.find(User.class, 1L, properties);
            System.out.println(user.getCompany().getName());
            System.out.println(user.getUserChats().size());

            List<User> users = session.createQuery(
                    "select u from User u " +
                            "join fetch u.userChats " +
                            "join fetch u.company " +
                            "where 1 = 1", User.class)
                    .setHint(GraphSemantic.LOAD.getJakartaHintName(), session.getEntityGraph("WithCompanyAndChat"))
                    .list();
            users.forEach(it -> System.out.println(it.getUserChats().size()));
            users.forEach(it -> System.out.println(it.getCompany().getName()));


            session.getTransaction().commit();
        }
    }
}
