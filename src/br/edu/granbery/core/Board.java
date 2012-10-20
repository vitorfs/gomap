package br.edu.granbery.core;

public class Board implements Cloneable {
    
    public int grid[][];
    public Graph game;
    
	public int player = 0;
	public int score[];
	public int jogada = 0;    
    
    public static final int GRID_SIZE = 24; // 576
    
    public static final int SMALL_BOARD = 16;  // 36
    public static final int MEDIUM_BOARD = 8; // 72
    public static final int BIG_BOARD = 4; // 144
    public static final int EXTREME_BOARD = 3; // 192
    
    public Board(int boardSize) {
        initBoard();
        game = new GraphBuilder(GRID_SIZE, boardSize).build();
        score = new int[2];
    }
    
    private void initBoard() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
                grid[i][j] = -1;
            }
        }        
    }
    
	public boolean isGameOver() {
		return score[0] + score[1] == (this.game.nodes.length);
	}
	
    public void print() {
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
            	if (grid[j][i]== -1)
            		System.out.print("[ ]");
            	else
            		System.out.print("[" + grid[j][i] + "]");
            }
            System.out.println();
        }
    }
    
    @Override
    public Board clone() {
    	try {
    		Board clone = (Board) super.clone();
    		
    		int newGrid[][] = new int[GRID_SIZE][GRID_SIZE];
    		int newScore[] = new int[2];
    		
    		newScore[0] = score[0];
    		newScore[1] = score[1];
    		
            for (int i=0;i<GRID_SIZE;i++){
                for (int j=0;j<GRID_SIZE;j++) {
                	newGrid[i][j] = grid[i][j];
                }
            }
            clone.grid = newGrid;
            clone.score = newScore;
            
            clone.jogada = jogada;
            clone.player = player;
            
        	clone.game = game.clone();   		
    		return clone;
    	} catch (Exception e) {
    		return null;
    	}
    }

}
