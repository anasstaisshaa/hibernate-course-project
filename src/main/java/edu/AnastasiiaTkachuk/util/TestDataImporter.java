package edu.AnastasiiaTkachuk.util;

import edu.AnastasiiaTkachuk.entity.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Company microsoft = saveCompany(session, "Microsoft");
        Company apple = saveCompany(session, "Apple");
        Company google = saveCompany(session, "Google");

        User billGates = saveUser(session, "Bill", "Gates",
                LocalDate.of(1955, Month.OCTOBER, 28), microsoft);
        User steveJobs = saveUser(session, "Steve", "Jobs",
                LocalDate.of(1955, Month.FEBRUARY, 24), apple);
        User sergeyBrin = saveUser(session, "Sergey", "Brin",
                LocalDate.of(1973, Month.AUGUST, 21), google);
        User timCook = saveUser(session, "Tim", "Cook",
                LocalDate.of(1960, Month.NOVEMBER, 1), apple);
        User dianeGreene = saveUser(session, "Diane", "Greene",
                LocalDate.of(1955, Month.JANUARY, 1), google);

        Chat dmdev = saveChat(session, "dmdev");
        Chat java = saveChat(session, "java");
        Chat youtubeMembers = saveChat(session, "youtube-members");

        addToChat(session, dmdev, billGates, steveJobs, sergeyBrin);
        addToChat(session, java, billGates, steveJobs, timCook, dianeGreene);
        addToChat(session, youtubeMembers, billGates, steveJobs, timCook, dianeGreene);
    }

    private void addToChat(Session session, Chat chat, User... users) {
        Arrays.stream(users)
                .map(user -> UserChat.builder()
                        .chat(chat)
                        .user(user)
                        .build())
                .forEach(session::save);
    }

    private Chat saveChat(Session session, String chatName) {
        Chat chat = Chat.builder()
                .name(chatName)
                .build();
        session.save(chat);

        return chat;
    }

    private Company saveCompany(Session session, String name) {
        Company company = Company.builder()
                .name(name)
                .build();
        session.save(company);

        return company;
    }

    private User saveUser(Session session,
                          String firstName,
                          String lastName,
                          LocalDate birthday,
                          Company company) {
        User user = User.builder()
                .username(firstName + lastName)
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstName)
                        .lastname(lastName)
                        .birthday(new Birthday(birthday))
                        .build())
                .company(company)
                .build();
        session.save(user);

        return user;
    }
}
