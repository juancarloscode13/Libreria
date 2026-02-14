package org.example.controlador;

import org.example.modelo.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ControladorLibro {

    private final SessionFactory sessionFactory;

    public ControladorLibro() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void crear(Libro libro) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(libro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public Libro obtenerPorId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Libro.class, id);
        }
    }

    public List<Libro> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Libro", Libro.class).list();
        }
    }

    public void actualizar(Libro libro) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(libro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public void eliminar(Libro libro) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(libro);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
