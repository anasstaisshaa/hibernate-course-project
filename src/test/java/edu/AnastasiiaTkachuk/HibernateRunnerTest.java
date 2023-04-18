package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
    @Test
    void localeInfo(){
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 1);
//            company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
//            company.getLocales().add(LocaleInfo.of("en", "English description"));
            company.getUsers().forEach((k, v) -> System.out.println(v));
            session.getTransaction().commit();
        }
    }

    @Test
    void checkManyToMany() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = session.get(User.class, 9L);
            var chat = session.get(Chat.class, 1L);

            var userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();
            userChat.setUser(user);
            userChat.setChat(chat);

            session.persist(userChat);

//            user.getChats().clear();

//            var chat = Chat.builder()
//                    .name("dmdev")
//                    .build();
//            user.addChat(chat);
//
//            session.save(chat);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();

            User user = User.builder()
                    .username("test4@gmail.com")
                    .build();

            Profile profile = Profile.builder()
                    .street("jfjii 112")
                    .language("UK")
                    .build();
            profile.setUser(user);

            session.persist(user);

            session.getTransaction().commit();
        }
    }
    @Test
    void checkOrhanRemoval(){
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Company company = session.get(Company.class, 7);
            //company.getUsers().removeIf(user -> user.getId().equals(7L));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization(){
        Company company = null;
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()){
            session.beginTransaction();

            company = session.get(Company.class, 6);

            session.getTransaction().commit();
        }
//        Set<User> users = company.getUsers(); //exception
//        System.out.println(users.size());

    }

    @Test
    void deleteCompany(){
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 6);
        session.remove(company);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany(){
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = Company.builder()
                .name("Facebook")
                .build();

        User user = User.builder()
                .username("sveta@gmail.com")
                .build();

        company.addUser(user);
        session.persist(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToMany(){
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 1);
        out.println("");

        session.getTransaction().commit();
    }
    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getString("username");
        resultSet.getString("lastname");
        resultSet.getString("lastname");

        Class<User> clazz = User.class;
        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();

        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(user, resultSet.getString("username"));
    }
    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = """
                insert 
                into
                %s
                (%s)
                values
                (%s)
                """;
        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(joining(", "));

        out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            preparedStatement.setObject(1, declaredField.get(user));
        }

    }

}