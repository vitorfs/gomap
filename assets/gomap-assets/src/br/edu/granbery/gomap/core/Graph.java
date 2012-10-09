package br.edu.granbery.gomap.core;

/**
 * @author Vitor Freitas vitorfs@gmail.com
 */
public class Graph {

    public Piece[] nodes;
    private boolean[][] arcs;
    private final int MAX_NODES;
    
    public Graph(int maxNodes) {
        MAX_NODES = maxNodes;
        nodes = new Piece[maxNodes];
        arcs = new boolean[maxNodes][maxNodes];
    }

    public void join(int node1, int node2) {
        if (node1 < MAX_NODES && node2 < MAX_NODES) {
            arcs[node1][node2] = true;
        }
    }

    public void remove(int node1, int node2) {
        if (node1 < MAX_NODES && node2 < MAX_NODES) {
            arcs[node1][node2] = false;
        }
    }

    public boolean isAdjacent(int node1, int node2) {
        return arcs[node1][node2];
    }
}