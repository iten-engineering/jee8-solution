package ch.itenengineering.cdi.todo.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.itenengineering.cdi.todo.domain.Todo;
import ch.itenengineering.cdi.todo.persistence.TodoDAO;

/**
 * Als Facade zur Business Logik dient die Stateless Session Bean TodoService.
 * Hier erfolgt auch die Transaktionssteuerung und das Exception Handling.
 * 
 * Der Persistence Service wird dabei auf einfache Art und Weise per Injection
 * vor dem ersten Business Call zur Verfuegung gestellt.
 */
@Stateless
public class TodoServiceBean implements TodoService {

	@Inject
	TodoDAO dao;

	public List<Todo> list() {
		return dao.readAllById();
	}

	public void save(Todo todo) {
		dao.create(todo);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

}
