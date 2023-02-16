package modelo.servicio;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo.Departamento;
import util.SessionFactoryUtil;

public class DepartamentoServicio implements IDepartamentoServicio {

	@Override
	public List<Departamento> getAll() {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		List<Departamento> departamentos = session.createQuery("select d from Departamento d order by d.dname").list();
		session.close();

		return departamentos;
	}



	public Departamento saveOrUpdate(Departamento d) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.saveOrUpdate(d);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una excepción en create Dept: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
		return d;
	}
}
