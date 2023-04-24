package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import edu.AnastasiiaTkachuk.util.TestDataImporter;
import jakarta.persistence.LockModeType;
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

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            TestDataImporter.importData(sessionFactory);
            UserChat userChat = session.find(UserChat.class, 1L, LockModeType.OPTIMISTIC);
            userChat.setCreatedBy("Anastasiia");

            session.getTransaction().commit();
        }
    }
}
