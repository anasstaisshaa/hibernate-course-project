package edu.AnastasiiaTkachuk.dao;

import edu.AnastasiiaTkachuk.entity.Company;
import edu.AnastasiiaTkachuk.entity.User;
import org.hibernate.Session;

public class UserRepository extends RepositoryBase<Long, User> {
    public UserRepository(Session session){
        super(User.class, session);
    }
}
