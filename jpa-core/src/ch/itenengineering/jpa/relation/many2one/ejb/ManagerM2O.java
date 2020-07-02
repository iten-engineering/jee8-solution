package ch.itenengineering.jpa.relation.many2one.ejb;

import javax.ejb.Local;

@Local
public interface ManagerM2O {

	public Object persist(Object entity);

	public Object merge(Object entity);

	public Object find(Class clazz, Object primaryKey);

	public void remove(Class clazz, Object primaryKey);

} // end
