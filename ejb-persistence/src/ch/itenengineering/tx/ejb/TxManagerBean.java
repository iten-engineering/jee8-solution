package ch.itenengineering.tx.ejb;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.itenengineering.tx.domain.TxTest;

@Stateful 
public class TxManagerBean implements TxManager {

	@PersistenceContext(unitName = "TestPU")
	private EntityManager em;

	public boolean isBeanAlive() {
		return true;
	}

	public void testSysException() {
		em.persist(new TxTest(
				"testSysException, rollback & bean destruction expected"));
		throw new SysException();
	}

	public void testSysAsAppWithRollbackTrueException() {
		em.persist(new TxTest(
				"testSysAsAppWithRollbackTrueException, rollback expected"));
		throw new SysAsAppWithRollbackTrueException();
	}

	public void testSysAsAppWithRollbackFalseException() {
		em.persist(new TxTest(
				"testSysAsAppWithRollbackFalseException, commit expected"));
		throw new SysAsAppWithRollbackFalseException();
	}

	public void testAppException() throws Exception {
		em.persist(new TxTest("testAppException, commit expected"));
		throw new AppException();
	}

	public void testAppWithRollbackTrueException() throws Exception {
		em.persist(new TxTest(
				"testAppWithRollbackTrueException, rollback expected"));
		throw new AppWithRollbackTrueException();
	}

	public void testAppWithRollbackFalseException() throws Exception {
		em.persist(new TxTest(
				"testAppWithRollbackFalseException, commit expected"));
		throw new AppWithRollbackFalseException();
	}

} // end of class
