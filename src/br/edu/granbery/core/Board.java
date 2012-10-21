package br.edu.granbery.core;

import android.graphics.Point;

public class Board implements Cloneable {
    
    public int grid[][];
    private Graph gameGraph;
    
    private int score[];
	private int player;
	private int move;    
    
    public static final int GRID_SIZE = 24; // 576
    
    public static final int SMALL_BOARD = 16;  // 36
    public static final int MEDIUM_BOARD = 8; // 72
    public static final int BIG_BOARD = 4; // 144
    public static final int EXTREME_BOARD = 3; // 192
    
    public Board(int boardSize) {
        initBoard();
        initScore();
        gameGraph = new GraphBuilder(GRID_SIZE, boardSize).build();
        player = 0;
        move = 0;
    }
    
    private void initBoard() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
                grid[i][j] = -1;
            }
        }        
    }
    
    private void initScore() {
        score = new int[2];
        score[0] = 0;
        score[1] = 0;    	
    }
    
	public void makeMove(Piece piece) {		
		score[player]++;
		gameGraph.value[piece.getId()] = player;
		
		if (piece != null) {
			for (Point point : piece.coordinates) {
				grid[point.x][point.y] = player;
			}
		}
		
		int opponent = (getPlayer() + 1) % 2;
		for (Piece adjacentPiece : piece.adjacency) {
			
			if (gameGraph.value[adjacentPiece.getId()] == -1)
				score[opponent]++;
			else if (gameGraph.value[adjacentPiece.getId()] == getPlayer()) {
				score[player]--;
				score[opponent]++;
			}
			
			gameGraph.value[adjacentPiece.getId()] = opponent;
			for (Point point : adjacentPiece.coordinates) {
				grid[point.x][point.y] = opponent;
			}

		}	
		player = opponent;
		move++;
	}    
    
	public boolean isGameOver() {
		return score[0] + score[1] == (gameGraph.nodes.length);
	}
	
	public String getWinner() {
		String winner;
		if (score[0] == score[1]) winner = "Draw game!";
		else if (score[0] > score[1]) winner = "Blue wins!";
		else winner = "Red wins!";
		return winner;
	}
	
	public int getPlayer() {
		return player;
	}

	public int[] getScore() {
		return score;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}
	
	public Graph getGameGraph() {
		return gameGraph;
	}

	@Override
    public String toString() {
		String str = "";
        for (int i=0;i<GRID_SIZE;i++){
            for (int j=0;j<GRID_SIZE;j++) {
            	if (grid[j][i]== -1)
            		str += "[ ]";
            	else
            		str += "[" + grid[j][i] + "]";
            }
            str += "\n";
        }
        return str;
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
            
            clone.move = move;
            clone.player = player;
            
        	clone.gameGraph = gameGraph.clone();   		
    		return clone;
    	} catch (Exception e) {
    		return null;
    	}
    }

}
