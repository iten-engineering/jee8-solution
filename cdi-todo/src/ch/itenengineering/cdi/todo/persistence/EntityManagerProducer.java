package ch.itenengineering.cdi.todo.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

	@Produces
	@PersistenceContext(unitName = "TestPU")
	private EntityManager em;
	
	
}
