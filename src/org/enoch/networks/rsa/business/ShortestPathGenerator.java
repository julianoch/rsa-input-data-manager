package org.enoch.networks.rsa.business;

import java.util.ArrayList;
import java.util.List;

import org.enoch.networks.rsa.model.input.reader.Input;
import org.enoch.networks.rsa.model.input.reader.Link;
import org.enoch.networks.rsa.model.input.reader.Node;
import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author Julian Enoch (julian.enoch@gmail.com)
 *
 */
public class ShortestPathGenerator {
	private WeightedGraph<Node, Link> graph;
	private GraphPath<Node, Link> dijkstraPath;
	private Input input;

	public ShortestPathGenerator(Input input) {
		if (Input.directed)
			graph = new SimpleDirectedWeightedGraph<>(Link.class);
		else
			graph = new SimpleWeightedGraph<>(Link.class);

		this.input = input;

		for (Node node : input.nodes) {
			graph.addVertex(node);
		}
		for (Link link : input.links) {
			graph.addEdge(link.getVertex1(), link.getVertex2(), link);
		}
	}

	public void populate(double[] linkCosts) {
		for (Link link : input.links) {
			graph.setEdgeWeight(link, linkCosts[link.getId()]);
		}
	}

	public double solve(int k) {
		DijkstraShortestPath<Node, Link> dijkstra = new DijkstraShortestPath<>(graph, input.requests.get(k).getSource(), input.requests.get(k).getDestination());
		dijkstraPath = dijkstra.getPath();
		double cost = dijkstraPath.getWeight();
		return cost;
	}

	public List<Integer> getPath() {
		List<Link> edgeList = dijkstraPath.getEdgeList();
		List<Integer> links = new ArrayList<>(edgeList.size());
		for (Link link : edgeList) {
			links.add(link.getId());
		}
		return links;
	}

}
