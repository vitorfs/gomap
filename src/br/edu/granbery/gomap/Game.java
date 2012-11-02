package br.edu.granbery.gomap;

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
import br.edu.granbery.ai.AlphaBeta;
import br.edu.granbery.core.Board;
import br.edu.granbery.core.Piece;

public class Game extends View {

	private Board board;
	private final int mode;
	
	public Game(Context context, int dificuldade, int mode) {
		super(context);
		int boardSize = 0;
		switch(dificuldade) {
			case 0: boardSize = Board.SMALL_BOARD;break;
			case 1: boardSize = Board.MEDIUM_BOARD;break;
			case 2: boardSize = Board.BIG_BOARD;break;
			case 3: boardSize = Board.EXTREME_BOARD;break;
		}
		
		board = new Board(boardSize);
		this.mode = mode;
		if (mode == 1)
			setAndroidMove();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paintBoard(canvas);	
		int squareSize = getSquareSize();		
		for (int i = 0 ; i < Board.GRID_SIZE ; i++) {
			for (int j = 0 ; j < Board.GRID_SIZE ; j++) {
				if (board.grid[i][j] != -1) {
					Rect rt = new Rect(i * squareSize, j * squareSize, (i * squareSize) + squareSize, (j * squareSize) + squareSize);
				    canvas.drawRect(rt, getPlayerPaint(board.grid[i][j]));					
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
		return getWidth() / Board.GRID_SIZE;
	}
	
	private Paint getPlayerPaint(int player) {
		Paint p = new Paint();	
		if (player == 0) p.setColor(Color.BLUE);
		else if (player == 1) p.setColor(Color.RED);
	    p.setStyle(Style.FILL);
	    p.setAlpha(80);			
		return p;

	}
	
	private void setAndroidMove() {
		AlphaBeta alphaBeta = new AlphaBeta(2);
		Piece androidMove = alphaBeta.getBestMove(board);		
		if (androidMove != null)
			board.makeMove(androidMove);
	}
	
	private void setPlayerMove(Point p) {
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));

		if (x < Board.GRID_SIZE && y < Board.GRID_SIZE){
			if (board.grid[x][y] == -1) {
				Piece piece = board.getGameGraph().getPiece(x, y);
				board.makeMove(piece);
				
				//TODO remove
				//System.out.println(board);
				
				if (mode == 1)
					setAndroidMove();
				
				if (board.isGameOver()) {
					String message = board.getWinner();
					Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
					
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
		
		canvas.drawLine(0, getWidth(), getWidth(), getWidth(), paint);
		
		int grid[][] = board.getGameGraph().getControlGrid();
		int squareSize = getSquareSize();
		// start x, start y, stop x, stop y		
		for (int i=0;i<Board.GRID_SIZE;i++) {
			for (int j=0;j<Board.GRID_SIZE;j++) {
				if (j+1 < Board.GRID_SIZE && grid[j+1][i] != grid[j][i]) {
					canvas.drawLine(squareSize * (j+1), (squareSize * i), squareSize * (j+1), squareSize + (squareSize * i), paint);
				}
				if (i+1 < Board.GRID_SIZE && grid[j][i+1] != grid[j][i]) {
					canvas.drawLine((squareSize * j), squareSize * (i+1), squareSize + (squareSize * j), squareSize * (i+1), paint);
				}				
			}
		}
		
		canvas.drawRect(0, getWidth() + 1, getWidth(), getWidth() + 75, getPlayerPaint(board.getPlayer()));
		paint.setColor(Color.BLACK);
		canvas.drawLine(0, getWidth() + 75, getWidth(), getWidth() + 75, paint);
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		canvas.drawText("Jogador azul: " + board.getScore()[0], 5, getWidth() + 125, paint);
		paint.setColor(Color.RED);
		canvas.drawText("Jogador vermelho: " + board.getScore()[1], 5, getWidth() + 175, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("Jogada: " + board.getMove(), 5, getWidth() + 225, paint);
	}
	
	

}
