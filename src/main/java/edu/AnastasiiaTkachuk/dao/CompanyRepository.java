package edu.AnastasiiaTkachuk.dao;

import edu.AnastasiiaTkachuk.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CompanyRepository extends RepositoryBase<Integer, Company> {
    public CompanyRepository(Session session){
        super(Company.class, session);
    }
}
