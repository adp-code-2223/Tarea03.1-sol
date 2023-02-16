package modelo.servicio;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import modelo.Departamento;
import util.SessionFactoryUtil;

public class DepartamentoServicio implements IDepartamentoServicio {

	@Override
	public List<Departamento> getAll() {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List<Departamento> departamentos = session.createQuery("select d from Departamento d order by d.dname").list();
		return departamentos;
	}

}
