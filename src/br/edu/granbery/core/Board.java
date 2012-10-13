package br.edu.granbery.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Board extends View {

	private int grid[][];
	
	private int player = 0;
	private static final int GRID_SIZE = 20;
	private static final int EASY_DIFFICULTY = 16;
	private static final int MEDIUM_DIFFICULTY = 8;
	private static final int HARD_DIFFICULTY = 4;
	
	public Board(Context context) {
		super(context);
		initBoard(EASY_DIFFICULTY);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paintBoard(canvas);	
		int squareSize = getSquareSize();		
		for (int i = 0 ; i < GRID_SIZE ; i++) {
			for (int j = 0 ; j < GRID_SIZE ; j++) {
				if (this.grid[i][j] != -1) {
					Rect rt = new Rect(i * squareSize, j * squareSize, (i * squareSize) + squareSize, (j * squareSize) + squareSize);
				    canvas.drawRect(rt, getPlayerPaint(this.grid[i][j]));					
				}
			}
		}
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point p = new Point((int)event.getX(), (int)event.getY());
		setPlayerMove(p);
		invalidate();
		return super.onTouchEvent(event);
	}
	
	private int getSquareSize() {
		return getWidth() / GRID_SIZE;
	}
	
	private Paint getPlayerPaint(int player) {
		Paint p = new Paint();	
		if (player == 0) p.setColor(Color.BLUE);
		else if (player == 1) p.setColor(Color.RED);
	    p.setStyle(Style.FILL);
	    p.setAlpha(80);			
		return p;

	}
	
	private void initBoard(int difficulty) {
		Random rand = new Random();
		this.grid = new int[GRID_SIZE][GRID_SIZE];
		
		int squares = GRID_SIZE * GRID_SIZE;
		int nodes = squares / difficulty;
		
		//Graph graph = new Graph(nodes);
		
		List<Piece> board = new ArrayList<Piece>();
		
		int minSize = difficulty - (difficulty / 2);
		int maxSize = difficulty + (difficulty / 2);
		
//		while (hasEmptySpace(grid)) {
		for (int i = 0 ; i < difficulty ; i++) {
			Piece piece = new Piece();
//			int pieceSize = rand.nextInt(maxSize - minSize + 1) + minSize;
			int pieceSize = difficulty;
			for (int j = 0; j < pieceSize ; j++) {
				piece.put(new Coordinate());
			}
		}
		
		for (int i = 0 ; i < GRID_SIZE ; i++)
			for (int j = 0; j < GRID_SIZE ; j++)
				this.grid[i][j] = -1;		
	}
	
	private boolean hasEmptySpace(int[][] grid) {
		for (int i = 0 ; i < GRID_SIZE ; i++)
			for (int j = 0; j < GRID_SIZE ; j++)
				if (grid[i][j] == 0)
					return true;
		return false;
	}
	
	private void setPlayerMove(Point p) {
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));
		if (x < GRID_SIZE && y < GRID_SIZE){
			if (this.grid[x][y] == -1) {
				int player = (this.player++) % 2;
				
				this.grid[x][y] = player;
				
				for (int i = x - 1 ; i < x + 2 ; i ++ ) 
					for (int j = y - 1 ; j < y + 2 ; j ++){
						if (x!=i || y!=j)
							if (i >= 0 && i < GRID_SIZE && j >=0 && j < GRID_SIZE)
								this.grid[i][j] = (player + 1) % 2;
					}
			}
			else {
				Toast.makeText(getContext(), "Jogada inválida!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	private void paintBoard(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		paint.setColor(Color.BLACK);
		
		int squareSize = getSquareSize();
		
		for (int i = 0 ; i <= getWidth() ; i+= squareSize) {
			canvas.drawLine(i, 0, i, getWidth(), paint);
			canvas.drawLine(0, i, getWidth(), i, paint);
		}
		
		paint.setColor(Color.WHITE);
		canvas.drawLine(squareSize, 1, squareSize, squareSize, paint);
		canvas.drawLine(squareSize * 2, 1, squareSize * 2, squareSize, paint);
		canvas.drawLine(squareSize * 2, 72, squareSize * 2, squareSize, paint);
	}

}
