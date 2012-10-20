package br.edu.granbery.gomap;

import java.util.List;

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
	private Piece bestMove = null;
	private int originalDepth = 2;
	
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
		board.score[0] = 0;
		board.score[1] = 0;
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
	
	private void makeMove(Piece piece, Board board) {		
		board.score[board.player]++;
		board.game.nodes[piece.getId()].setValue(board.player);
		
		if (piece != null) {
			for (Point point : piece.coordinates) {
				board.grid[point.x][point.y] = board.player;
			}
		}
		
		int oponente = (board.player + 1) % 2;
		for (Piece npiece : piece.adjacency) {
			
			if (npiece.getValue() == -1)
				board.score[oponente]++;
			else if (npiece.getValue() == board.player) {
				board.score[board.player]--;
				board.score[oponente]++;
			}
			
			board.game.nodes[npiece.getId()].setValue(oponente);
			for (Point k : npiece.coordinates) {
				board.grid[k.x][k.y] = oponente;
			}

		}	
		board.player = oponente;
		board.jogada++;
	}
	
	private void setPlayerMove(Point p) {
		int x = (int)Math.floor((double)(p.x / getSquareSize()));
		int y = (int)Math.floor((double)(p.y / getSquareSize()));

		if (x < Board.GRID_SIZE && y < Board.GRID_SIZE){
			if (board.grid[x][y] == -1) {
				Piece piece = board.game.getPiece(x, y);
				makeMove(piece, board);
				board.print();
				
				alphaBeta(board, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
				Piece android = bestMove;
				if (bestMove != null) {
					makeMove(android, board);
				}
				bestMove = null;
				
				if (board.isGameOver()) {
					String mensagem;
					if (board.score[0] == board.score[1]) mensagem = "Empate!";
					else if (board.score[0] > board.score[1]) mensagem = "Jogador azul venceu!";
					else mensagem = "Jogador vermelho venceu!";
					Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG).show();
					
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
		canvas.drawText("Jogador azul: " + board.score[0], 5, getWidth() + 50, paint);
		paint.setColor(Color.RED);
		canvas.drawText("Jogador vermelho: " + board.score[1], 5, getWidth() + 100, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("Jogada: " + board.jogada, 5, getWidth() + 150, paint);
	}
	
	
	private int alphaBeta(Board board, int depth, int alpha, int beta) {
		if (depth == 0 || board.isGameOver()) {
			if (board.player == 0)
				return board.score[0] - board.score[1];
			else
				return board.score[1] - board.score[0];
		} else {
			List<Piece> possibleMoves = board.game.getPossibleMoves();
			if (board.player == 0) { // alpha
				for (Piece p : possibleMoves) {
					Board tempBoard = board.clone();
					makeMove(p, tempBoard);
					int result = alphaBeta(tempBoard, depth - 1, alpha, beta);
					if (result > alpha) {
						if (depth == this.originalDepth) {
							this.bestMove = p;
						}
					}
					if (alpha >= beta) {
						break;
					}
				}
				return alpha;
			} else { // beta
				for (Piece p : possibleMoves) {
					Board tempBoard = board.clone();
					makeMove(p, tempBoard);
					int result = alphaBeta(tempBoard, depth - 1, alpha, beta);
					if (result < beta) {
						if (depth == this.originalDepth) {
							this.bestMove = p;
						}
					}
					
					if (beta <= alpha) {
						break;
					}
				}
				return beta;
			}
		}
	}

}
