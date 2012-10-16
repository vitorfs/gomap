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
import br.edu.granbery.core.Board;
import br.edu.granbery.core.Piece;

public class Game extends View {

	private Board board;
	private int player = 0;
	
	public Game(Context context) {
		super(context);
		board = new Board(Board.BIG_BOARD);
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
		//setPlayerMove(p);
		
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));

		if (x < Board.GRID_SIZE && y < Board.GRID_SIZE){
			if (board.grid[x][y] == -1) {
				int player = (this.player++) % 2;
				Piece piece = board.game.getPiece(x, y);
				
				if (piece != null) {
					for (Point point : piece.coordinates) {
						board.grid[point.x][point.y] = player;
					}
				}
			}
			else {
				Toast.makeText(getContext(), "Jogada inválida!", Toast.LENGTH_SHORT).show();
			}
		}
		
		
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
	
	private void setPlayerMove(Point p) {
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));
		if (x < Board.GRID_SIZE && y < Board.GRID_SIZE){
			if (board.grid[x][y] == -1) {
				int player = (this.player++) % 2;
				
				board.grid[x][y] = player;
				
				for (int i = x - 1 ; i < x + 2 ; i ++ ) 
					for (int j = y - 1 ; j < y + 2 ; j ++){
						if (x!=i || y!=j)
							if (i >= 0 && i < Board.GRID_SIZE && j >=0 && j < Board.GRID_SIZE)
								board.grid[i][j] = (player + 1) % 2;
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
		
		int grid[][] = board.game.getControlGrid();
		// start x, start y, stop x, stop y		
		for (int i=0;i<Board.GRID_SIZE;i++) {
			for (int j=0;j<Board.GRID_SIZE;j++) {
				if (j+1 < Board.GRID_SIZE && grid[j+1][i] == grid[j][i]) {
					canvas.drawLine(squareSize * (j+1), (squareSize * i) + 1, squareSize * (j+1), squareSize + (squareSize * i), paint);
				}
				if (i+1 < Board.GRID_SIZE && grid[j][i+1] == grid[j][i]) {
					canvas.drawLine((squareSize * j) + 1, squareSize * (i+1), squareSize + (squareSize * j), squareSize * (i+1), paint);
				}				
			}
		}
				
		//if (grid[0][0] == grid[1][0]) {
			//canvas.drawLine(squareSize, 1, squareSize, squareSize, paint);
		//}
		//if (grid[1][0] == grid[2][0]) {
			//canvas.drawLine(squareSize * 2, 1, squareSize * 2, squareSize, paint);
		//}		
		
/*		paint.setColor(Color.WHITE);
		canvas.drawLine(squareSize, 1, squareSize, squareSize, paint);
		canvas.drawLine(squareSize * 2, 1, squareSize * 2, squareSize, paint);
		canvas.drawLine(squareSize * 2, 72, squareSize * 2, squareSize, paint);*/
	}

}
