package br.edu.granbery.core;

public class Graph {
	private int[] nodes;
	private boolean[][] arcs;
	private final int MAX_NODES;
	
	public Graph(int maxNodes) {
		MAX_NODES = maxNodes;
		nodes = new int[maxNodes];
		arcs = new boolean[maxNodes][maxNodes];
	}
	
	public void join(int node1, int node2) {
		if (node1 < MAX_NODES && node2 < MAX_NODES)
			arcs[node1][node2] = true;
	}
	
	public void remove(int node1, int node2) {
		if (node1 < MAX_NODES && node2 < MAX_NODES)
			arcs[node1][node2] = false;
	}
	
	public boolean isAdjacent(int node1, int node2) {
		return arcs[node1][node2];
	}
}
