package ch.itenengineering.cdi.todo.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ch.itenengineering.cdi.todo.domain.Todo;

/**
 * Das Todo Data Access Objekt bildet die Schnittstelle zur Persistenz. Es
 * stellt alle erforderlichen CRUD Methoden zur Verfuegung und kappselt die
 * Verwendung des Entity Managers.
 */
public class TodoDAO {

	@Inject
	private EntityManager em;

	public void create(Todo todo) {
		em.persist(todo);
	}

	public Todo read(Long id) {
		return em.find(Todo.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Todo> readAllById() {
		Query query = em.createQuery("select t from Todo t order by t.id");
		return query.getResultList();
	}

	public void update(Todo todo) {
		em.merge(todo);
	}

	public void delete(Long id) {
		Todo todo = em.find(Todo.class, id);
		em.remove(todo);
	}

} // end
