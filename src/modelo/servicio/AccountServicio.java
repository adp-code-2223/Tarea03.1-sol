package modelo.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.Transaction;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;
import modelo.dao.AccMovement.AccMovementDao;
import modelo.dao.AccMovement.AccMovementDaoHibernate;
import modelo.dao.Account.AccountDao;
import modelo.dao.Account.AccountDaoHibernate;
import modelo.util.exceptions.InstanceNotFoundException;
import util.SessionFactoryUtil;

public class AccountServicio implements IAccountServicio{

	private AccountDao accDao;
	private AccMovementDao accMovDao;

	public AccountServicio() {
		this.accDao = new AccountDaoHibernate();
		this.accMovDao = new AccMovementDaoHibernate();
	}

	public Account findAccountById(int accId) throws InstanceNotFoundException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Account acc = accDao.find(session, accId);
		session.close();
		return acc;

	}

	public AccMovement transferir(int accOrigenId, int accDestinoId, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException {
		
		AccMovement accMovement = null;
		Transaction tx = null;
		Account cuentaOrigen = null;
		Session session = null;
		BigDecimal cantidadBD = new BigDecimal(cantidad);
//Si la cantidad a transferir es >0
		if (cantidad > 0) {
			session = SessionFactoryUtil.getSessionFactory().openSession();
			try {
				cuentaOrigen = accDao.find(session, accOrigenId);

				if (cuentaOrigen.getAmount().compareTo(cantidadBD) < 0) {
					throw new SaldoInsuficienteException(
							"No hay suficiente saldo en la cuenta: Cantidad actual: %.2f Cantidad a transferir: %.2f",
							cuentaOrigen.getAmount(), cantidadBD);
				}
			} catch (InstanceNotFoundException infe) {
				System.out.println("Ha ocurrido una exception. La cuenta no existe. " + infe.getMessage());
				throw infe;

			}
			try {

				tx = session.beginTransaction();

				Account cuentaDestino = accDao.find(session, accDestinoId);
				cuentaOrigen.setAmount(cuentaOrigen.getAmount().subtract(cantidadBD));

				cuentaDestino.setAmount(cuentaDestino.getAmount().add(cantidadBD));
				accMovement = new AccMovement(cuentaOrigen, cuentaDestino, cantidadBD, LocalDateTime.now());

				accDao.save(session, cuentaOrigen);
				accDao.save(session, cuentaDestino);
				accMovDao.save(session, accMovement);

				tx.commit();
			}

			catch (InstanceNotFoundException inf) {
				System.out.println("Ha ocurrido una exception. La cuenta no existe. " + inf.getMessage());
				throw inf;
			} catch (Exception ex) {
				System.out.println("Ha ocurrido una exception. " + ex.getMessage());
				if (tx != null) {
					tx.rollback();
				}

				throw ex;
			} finally {
				session.close();
			}

		} else {
			throw new UnsupportedOperationException("Las cantidades a transferir deben ser positivas");
		}

		return accMovement;
	}
}
