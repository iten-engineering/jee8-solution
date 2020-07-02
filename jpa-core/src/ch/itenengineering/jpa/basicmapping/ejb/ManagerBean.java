package ch.itenengineering.jpa.basicmapping.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ManagerBean implements Manager {

	@PersistenceContext(unitName = "TestPU")
	private EntityManager em;

	public Object persist(Object entity) {
		em.persist(entity);
		return entity;
	}

	public Object merge(Object entity) {
		return em.merge(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object find(Class clazz, Object primaryKey) {
		return em.find(clazz, primaryKey);
	}

	@SuppressWarnings("rawtypes")
	public void remove(Class clazz, Object primaryKey) {
		Object obj = find(clazz, primaryKey);
		if (obj != null) {
			em.remove(obj);
		}
	}

} // end of class
