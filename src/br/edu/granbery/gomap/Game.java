package br.edu.granbery.gomap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import br.edu.granbery.ai.AlphaBeta;
import br.edu.granbery.core.Board;
import br.edu.granbery.core.Piece;

public class Game extends View {

	private Board board;
	private final int mode;
	private final int boardSize;
	private BitmapDrawable bgTile;
	
	public Game(Context context, int dificuldade, int mode) {
		super(context);
		
		bgTile = (BitmapDrawable) getResources().getDrawable(R.drawable.navy_blue);
		switch(dificuldade) {
			case 0: boardSize = Board.SMALL_BOARD;break;
			case 1: boardSize = Board.MEDIUM_BOARD;break;
			case 2: boardSize = Board.BIG_BOARD;break;
			case 3: boardSize = Board.EXTREME_BOARD;break;
			default: boardSize = 0;
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
		
		if (board.isGameOver()) {
			showGameOverDialog();
		}
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!board.isGameOver()) {
			Point p = new Point((int)event.getX(), (int)event.getY());
			setPlayerMove(p);		
			invalidate();
		}
		else {
			showGameOverDialog();
		}
		return super.onTouchEvent(event);
	}
	
	public void showGameOverDialog() {
		final String items[] = { "Nova Partida", "Alterar Mapa", "Retornar à Tela Inicial", "Cancelar" };
		AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
		ab.setTitle(board.getWinner());
		ab.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int choice) {
				if (choice == 0) {
					board = new Board(boardSize);
					if (mode == 1)
						setAndroidMove();
				} else if (choice == 1) {
					Intent i = null;
					if (mode==0) 
						i = new Intent(getContext(), VersusPlayer.class);
					else 
						i = new Intent(getContext(), VersusAndroid.class);
					getContext().startActivity(i);
				} else if (choice == 2) {
					Intent i = new Intent(getContext(), Menu.class);
					getContext().startActivity(i);
				}
			}
		});
		ab.show();		
	}
	
	private int getSquareSize() {
		return getWidth() / Board.GRID_SIZE;
	}
	
	private Paint getPlayerPaint(int player) {
		Paint p = new Paint();	
		if (player == 0) p.setColor(Color.BLUE);
		else if (player == 1) p.setColor(Color.RED);
	    p.setStyle(Style.FILL);
	    p.setAlpha(50);			
		return p;

	}
	
	private void setAndroidMove() {
		AndroidMove androidMove = new AndroidMove(2, getContext());
		androidMove.execute("");
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
				
			}
			else {
				Toast.makeText(getContext(), "Jogada inválida!", Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	private void paintBoard(Canvas canvas) {
		Paint paint = new Paint();
		
		setBackgroundResource(R.drawable.navy_background);
		
		paint.setColor(Color.WHITE);
		paint.setAlpha(70);
		//canvas.drawPaint(paint);
		canvas.drawRect(0, 0, getWidth(), getWidth() + 75, paint);
		
		paint.setColor(Color.BLACK);
		paint.setAlpha(100);
		
		
		
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
		canvas.drawLine(0, getWidth(), getWidth(), getWidth(), paint);
		canvas.drawLine(0, getWidth() + 75, getWidth(), getWidth() + 75, paint);
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		canvas.drawText("Azul: " + board.getScore()[0], 5, getWidth() + 125, paint);
		paint.setColor(Color.RED);
		canvas.drawText("Vermelho: " + board.getScore()[1], 5, getWidth() + 175, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("Jogada: " + board.getMove(), 5, getWidth() + 225, paint);
	}
	
	private class AndroidMove extends AsyncTask<String, Integer, String> {
		
		private AlphaBeta alphaBeta;
		private ProgressDialog dialog;
		private Piece bestMove = null;
		
		public AndroidMove(int depth, Context context) {
			alphaBeta = new AlphaBeta(depth);
			this.dialog = new ProgressDialog(context);
			this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
		          public void onCancel(DialogInterface dialog) {
		              cancel(true);
		              Intent i = new Intent(getContext(), Menu.class);
		              getContext().startActivity(i);
		          }
		    });
		}

		@Override
		protected String doInBackground(String... params) {
			bestMove = alphaBeta.getBestMove(board);
			return "Success";
		}
		
		@Override 
		protected void onPostExecute(String result) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			
			if (bestMove != null) {
				board.makeMove(bestMove);
				invalidate();
			}
			
		}
		
		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Calculando jogada...");
			this.dialog.show();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			
		}
		
	}	

}
