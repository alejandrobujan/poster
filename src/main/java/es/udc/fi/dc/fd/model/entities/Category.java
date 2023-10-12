package es.udc.fi.dc.fd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The Class Category.
 */
@Entity
public class Category {

	/** The id. */
	private Long id;

	/** The category name. */
	private String name;

	/**
	 * Instantiates a new category.
	 */
	public Category() {
	}

	/**
	 * @param name
	 */
	public Category(String name) {
		this.name = name;
	}

	/**
	 * @param id   the id
	 * @param name the category name
	 */
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the category name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the category name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

}
