package edu.AnastasiiaTkachuk.util;

import edu.AnastasiiaTkachuk.converter.BirthdayConverter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        //configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
