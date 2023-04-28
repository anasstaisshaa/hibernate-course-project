package edu.AnastasiiaTkachuk;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import edu.AnastasiiaTkachuk.dao.CompanyRepository;
import edu.AnastasiiaTkachuk.dao.UserRepository;
import edu.AnastasiiaTkachuk.dto.UserCreateDto;
import edu.AnastasiiaTkachuk.entity.*;
import edu.AnastasiiaTkachuk.mapper.CompanyReadMapper;
import edu.AnastasiiaTkachuk.mapper.UserCreateMapper;
import edu.AnastasiiaTkachuk.mapper.UserReadMapper;
import edu.AnastasiiaTkachuk.service.UserService;
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


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

            //TestDataImporter.importData(sessionFactory);
            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();

            UserRepository userRepository = new UserRepository(session);
            CompanyReadMapper companyReadMapper = new CompanyReadMapper();
            UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
            CompanyRepository companyRepository = new CompanyRepository(session);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);

            UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);
            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Liza")
                            .lastname("Stepanova")
                            .birthday(new Birthday(LocalDate.now()))
                            .build(),
                    "liza@gmail.com",
                    Role.USER,
                    1
            );
            userService.create(userCreateDto);


            session.getTransaction().commit();
        }
    }
}
