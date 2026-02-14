package org.example.controlador;

import org.example.modelo.Administrador;
import org.example.modelo.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ControladorAdministrador {

    private final SessionFactory sessionFactory;

    public ControladorAdministrador() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void crear(Administrador administrador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(administrador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public Administrador obtenerPorId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Administrador.class, id);
        }
    }

    public List<Administrador> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Administrador", Administrador.class).list();
        }
    }

    public void actualizar(Administrador administrador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(administrador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Administrador administrador) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(administrador);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
