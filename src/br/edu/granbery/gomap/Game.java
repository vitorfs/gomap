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
	private int score[];
	
	public Game(Context context, int dificuldade) {
		super(context);
		int boardSize = 0;
		switch(dificuldade) {
			case 0: boardSize = Board.SMALL_BOARD;break;
			case 1: boardSize = Board.MEDIUM_BOARD;break;
			case 2: boardSize = Board.BIG_BOARD;break;
			case 3: boardSize = Board.EXTREME_BOARD;break;
		}
		
		board = new Board(boardSize);
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
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
	
	private void setPlayerMove(Point p) {
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));

		if (x < Board.GRID_SIZE && y < Board.GRID_SIZE){
			if (board.grid[x][y] == -1) {
				int player = (this.player++) % 2;
				score[player]++;
				board.game.getPiece(x, y).setValue(player);
				Piece piece = board.game.getPiece(x, y);
				//piece.setValue(player);
				if (piece != null) {
					for (Point point : piece.coordinates) {
						board.grid[point.x][point.y] = player;
					}
				}
				
				/*GraphBuilder gp = new GraphBuilder(Board.GRID_SIZE, Board.BIG_BOARD);
				gp.initControlGrid();
				List<Point> adj = gp.getPieceAdjacency(piece);

				for (Point point : adj) {
					Piece npiece = board.game.getPiece(point.x, point.y);
					
					if (piece != null && piece != npiece) {
						for (Point k : npiece.coordinates) {
							board.grid[k.x][k.y] = (player + 1) % 2;
						}
					}					
				}*/
				int oponente = (player + 1) % 2;
				for (Piece npiece : piece.adjacency) {
					
					if (npiece.getValue() == -1)
						score[oponente]++;
					else if (npiece.getValue() == player) {
						score[player]--;
						score[oponente]++;
					}
					
					board.game.nodes[npiece.getId()].setValue(oponente);
					for (Point k : npiece.coordinates) {
						board.grid[k.x][k.y] = oponente;
					}

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
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		canvas.drawText("Jogador azul: " + score[0], 5, getWidth() + 50, paint);
		paint.setColor(Color.RED);
		canvas.drawText("Jogador vermelho: " + score[1], 5, getWidth() + 100, paint);
	}

}
