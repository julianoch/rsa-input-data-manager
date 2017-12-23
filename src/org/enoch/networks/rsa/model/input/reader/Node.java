package org.enoch.networks.rsa.model.input.reader;

import java.util.Set;

/**
 * @author Julian Enoch (julian.enoch@gmail.com)
 *
 */
public class Node {
	private int id;
	private Set<Link> adjacentLinks;

	public Node(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Link> getAdjacentLinks() {
		return adjacentLinks;
	}

	public void setAdjacentLinks(Set<Link> adjacentLinks) {
		this.adjacentLinks = adjacentLinks;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Node) obj).getId() == this.id;
	}

	@Override
	public String toString() {
		return "" + id;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
