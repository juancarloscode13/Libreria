package org.example.controlador;

import org.example.modelo.Comprador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ControladorComprador {

    private final SessionFactory sessionFactory;

    public ControladorComprador() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void crear(Comprador comprador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(comprador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public Comprador obtenerPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Comprador.class, id);
        }
    }

    public List<Comprador> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Comprador", Comprador.class).list();
        }
    }

    public void actualizar(Comprador comprador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(comprador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Comprador comprador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(comprador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
