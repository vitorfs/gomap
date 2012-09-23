package br.edu.granbery.gomap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import br.edu.granbery.core.Board;

public class VersusAndroid extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Board board = new Board(this);
		//setContentView(board);
	}

}
