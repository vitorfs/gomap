package br.edu.granbery.gomap.core;

import java.awt.Point;
import java.util.Random;

/**
 * @author Vitor Freitas
 * vitorfs@gmail.com
 */
public class GraphBuilder {
    
    private int gridSize;
    private int boardSize;
    private int[][] controlGrid;
    private Random rand;
    
    public GraphBuilder(int gridSize, int boardSize) {
        this.gridSize = gridSize;
        this.boardSize = boardSize;
        rand = new Random();
    }
    
    public Graph build() {
        int maxNodes = getMaxNodes();
        Graph graph = new Graph(maxNodes);
        
        initControlGrid();
        
        int minSize = boardSize - (boardSize / 2);
        int maxSize = boardSize + (boardSize / 2);
                
        for (int i=0;i<maxNodes;i++) {
            graph.nodes[i] = generatePiece(minSize, maxSize, i);
        }
        
        print();
        for (int i=0;i<graph.nodes.length;i++) {
            printPieceAdjacencyOnMatrix(graph.nodes[i]);
        }
        return graph;
    }
    
    private Piece generatePiece(int minSize, int maxSize, int id) {
        Piece p = new Piece(id);
        int pieceSize = rand.nextInt(maxSize - minSize + 1) + minSize;
        
        int x;
        int y;
        
        // Define start point
        do {
            x = rand.nextInt(gridSize);
            y = rand.nextInt(gridSize);
        } while (controlGrid[x][y] != -1);
        p.add(x, y);
        controlGrid[x][y] = id;
        
        // Match adjacency
        //for (int j=0;j<minSize-1;j++) {

            /*for (int k=x-1;k<x+2;k++) {
                for (int l=y-1;l<y+2;l++) {
                    if (x!=k || y!=l) {
                        if (k >= 0 && k < gridSize && l >=0 && l < gridSize) {
                            if (controlGrid[k][l] == -1) {
                                controlGrid[k][l] = id;
                            }
                        }
                    }
                }
            }*/

        //}
        return p;
    }
    
    private int getMaxNodes() {
        int squareNumber = gridSize * gridSize;
        int nodes = squareNumber / boardSize;
        return nodes;
    }    
    
    private void initControlGrid() {
        controlGrid = new int[gridSize][gridSize];
        for (int i=0;i<gridSize;i++) {
            for (int j=0;j<gridSize;j++) {
                controlGrid[i][j] = -1;
            }
        }
    }
    
    private void print() {
        for (int i=0;i<gridSize;i++){
            for (int j=0;j<gridSize;j++) {
                String str;
                str = String.valueOf(controlGrid[i][j]);
                if (str.equals("-1")) str = "   ";                
                if (str.length() == 1) str = "00" + str;
                if (str.length() == 2) str = "0" + str;
                System.out.print("[" + str + "]");
            }
            System.out.println();
        }        
    }
    
    private void printPieceAdjacencyOnMatrix(Piece piece) {
        for (Point p : piece.coordinates) {
            System.out.print(p + " ["+piece.getId()+"] => ");
            for (int i = p.x - 1 ; i < p.x + 2 ; i ++ ) 
                for (int j = p.y - 1 ; j < p.y + 2 ; j ++)
                    if (i >= 0 && i < gridSize && j >=0 && j < gridSize)
                        System.out.print("["+controlGrid[i][j]+"]");
            System.out.println();
        }
    }

}
