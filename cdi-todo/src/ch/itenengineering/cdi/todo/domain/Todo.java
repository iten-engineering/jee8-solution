package ch.itenengineering.cdi.todo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Die Klasse Todo bildet unsere Domäne ab. Da es ein kleines Beispiel ist, wird
 * diese auch gleich (anstelle eines Transfer Objektes) dem Client zur Verfügung
 * gestellt.
 */
@Entity
@Table(name = "TODO")
public class Todo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	@Version
	private int version;

	public Todo() {
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return this.version;
	}

} // end of class
