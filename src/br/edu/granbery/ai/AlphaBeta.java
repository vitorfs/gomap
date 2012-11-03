package br.edu.granbery.ai;

import java.util.List;

import br.edu.granbery.core.Board;
import br.edu.granbery.core.Piece;

public class AlphaBeta {
	
	private Piece bestMove;
	private int depth;
	
	public AlphaBeta() {
		bestMove = null;
	}
	
	public AlphaBeta(int depth) {
		this.depth = depth;
		bestMove = null;
	}
	
	private int calculate(Board board, int depth, int alpha, int beta) {
		if (depth == 0 || board.isGameOver()) {
			if (board.getPlayer() == 0)
				return board.getScore()[0] - board.getScore()[1];
			else
				return board.getScore()[1] - board.getScore()[0];
		} else {
			List<Piece> possibleMoves = board.getGameGraph().getPossibleMoves();
			if (board.getPlayer() == 0) { // Alpha
				for (Piece piece : possibleMoves) {
					Board tempBoard = board.clone();
					tempBoard.makeMove(piece);
					int result = calculate(tempBoard, depth - 1, alpha, beta);
					if (result > alpha) {
						if (depth == this.depth) {
							bestMove = piece;
						}
					}
					if (alpha >= beta) {
						break;
					}
				}
				return alpha;
			} else { // Beta
				for (Piece piece : possibleMoves) {
					Board tempBoard = board.clone();
					tempBoard.makeMove(piece);
					int result = calculate(tempBoard, depth - 1, alpha, beta);
					if (result < beta) {
						if (depth == this.depth) {
							bestMove = piece;
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
	
	public Piece getBestMove(Board board) {
		calculate(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		/*Piece bestMove = null;
		List<Piece> pieces = board.getGameGraph().getPossibleMoves();
		if (!pieces.isEmpty()) {
			Random rand = new Random();
			bestMove = pieces.get(rand.nextInt(pieces.size()));
		}*/
		return bestMove;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

}
