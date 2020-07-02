package ch.itenengineering.jpa.callcenter.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.itenengineering.jpa.callcenter.domain.Customer;

@Stateless
public class CriteriaQueriesBean implements CriteriaQueries {

	@PersistenceContext(unitName = "TestPU")
	private EntityManager em;
	
	@Override
	public List<Customer> selectCustomers() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

		Root<Customer> c = cq.from(Customer.class);
		cq.select(c);
		
		return em.createQuery(cq).getResultList();			
	}

	@Override
	public List<String> selectCustomerFirstNames() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);

		Root<Customer> c = cq.from(Customer.class);
		cq.multiselect(c.get("firstName"));
		
		return em.createQuery(cq).getResultList();			
	}
	
	@Override
	public List<String> selectCustomeFirstNamesOrdered() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);

		Root<Customer> c = cq.from(Customer.class);
		cq.multiselect(c.get("firstName")).orderBy(cb.asc( c.get("firstName") ));
		
		return em.createQuery(cq).getResultList();			
	}
	
	@Override
	public Customer selectCustomerById(int id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

		Root<Customer> c = cq.from(Customer.class);
		cq.select(c).where( cb.equal(c.get("customerId"), 1) );
		
		return em.createQuery(cq).getSingleResult();			
	}

}
