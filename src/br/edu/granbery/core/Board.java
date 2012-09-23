package br.edu.granbery.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Board extends View {

	Paint paint;
	
	public Board(Context context) {
		super(context);
		paint = new Paint(); 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		paint.setColor(Color.BLACK);
		int squares = canvas.getWidth() / 20;
		for (int i = 0 ; i <= canvas.getWidth() ; i+= squares) {
			canvas.drawLine(i, 0, i, canvas.getWidth(), paint);
			canvas.drawLine(0, i, canvas.getWidth(), i, paint);
		}
	}

}
