package br.edu.granbery.gomap.core;

/**
 * @author Vitor Freitas
 * vitorfs@gmail.com
 */
public class Board {
    
    private int grid[][];
    private Graph game;
    
    private static final int GRID_SIZE = 20;
    public static final int SMALL_BOARD = 16;
    public static final int MEDIUM_BOARD = 8;
    public static final int BIG_BOARD = 8;
    
    public Board(int boardSize) {
        initBoard();
        game = new GraphBuilder(GRID_SIZE, boardSize).build();
    }
    
    private void initBoard() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
                grid[i][j] = -1;
            }
        }        
    }
    
    public void print() {
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
                System.out.print("[" + grid[i][j] + "]");
            }
            System.out.println();
        }    
    }
}
