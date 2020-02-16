package DAO;

import models.JediBotUser;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO implements DBApi<JediBotUser,Long> {

    @Override
    public JediBotUser findById(Long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(JediBotUser.class, id);
    }

    @Override
    public void save(JediBotUser user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public void update(JediBotUser user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(JediBotUser user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
    }
}
