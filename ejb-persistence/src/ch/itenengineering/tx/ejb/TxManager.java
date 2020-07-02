package ch.itenengineering.tx.ejb;

import javax.ejb.Remote;

@Remote
public interface TxManager {

	public boolean isBeanAlive();

	public void testSysException();

	public void testSysAsAppWithRollbackTrueException();

	public void testSysAsAppWithRollbackFalseException();

	public void testAppException() throws Exception;

	public void testAppWithRollbackTrueException() throws Exception;

	public void testAppWithRollbackFalseException() throws Exception;
}
