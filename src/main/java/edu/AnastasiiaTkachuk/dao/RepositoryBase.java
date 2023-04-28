package edu.AnastasiiaTkachuk.dao;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E> implements Repository<K, E> {
    private final Class<E> clazz;
    private final Session session;

    @Override
    public E save(E entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        session.remove(id);
        session.flush();
    }

    @Override
    public void update(E entity) {
        session.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<E> criteria = cb.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria)
                .getResultList();
    }
}
