package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import edu.AnastasiiaTkachuk.util.TestDataImporter;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.jdbc.Work;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HibernateRunner {
    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            TestDataImporter.importData(sessionFactory);
            User user = null;
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                user.getCompany().getName();
                user.getUserChats().size();
                var user1 = session.find(User.class, 1L);

                List<Company> companyId = session.createQuery("select c from Company c where c.id = :companyId", Company.class)
                        .setParameter("companyId", 1L)
                        .setCacheable(true)
                        .list();



                session.getTransaction().commit();
            }
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                var user2 = session.find(User.class, 1L);
                user2.getCompany().getName();
                user2.getUserChats().size();

                List<Company> companyId = session.createQuery("select c from Company c where c.id = :companyId", Company.class)
                        .setParameter("companyId", 1L)
                        .setCacheable(true)
                        .list();

                session.getTransaction().commit();
            }
        }
    }
}
