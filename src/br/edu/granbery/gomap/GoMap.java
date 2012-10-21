package br.edu.granbery.gomap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class GoMap extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		int index = getIntent().getExtras().getInt("Dificuldade");
		int mode = getIntent().getExtras().getInt("Mode");
		Game game = new Game(this, index, mode);
		setContentView(game);
		
	}
}
