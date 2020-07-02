package ch.itenengineering.tx.ejb;

import javax.ejb.ApplicationException;
import javax.ejb.EJBException;

@ApplicationException(rollback = false)
public class SysAsAppWithRollbackFalseException extends EJBException {

	private static final long serialVersionUID = 1L;

}
