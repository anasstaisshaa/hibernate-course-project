package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.BlockingDeque;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        //BlockingDeque<Connection> pool = null;  // SessionFactory
        //Connection connection1 = pool.take();
        //Connection connection = DriverManager.getConnection("db.url", "db.username", "db.password");  //Session

        Configuration configuration = new Configuration();
        //configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            User user = User.builder()
                    .username("test@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthday(LocalDate.of(2000, 1, 19))
                    .age(23)
                    .build();
            session.persist(user);
            session.getTransaction().commit();
        }
    }
}
