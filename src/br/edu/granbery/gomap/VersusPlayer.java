package br.edu.granbery.gomap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

public class VersusPlayer extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		
		Button btnStart = (Button) findViewById(R.id.btnStart);
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupDificulty);
				int id = rg.getCheckedRadioButtonId();
				View radioButton = rg.findViewById(id);
				int idx = rg.indexOfChild(radioButton);

				Intent intent = new Intent(getApplicationContext(), GoMap.class);
				intent.putExtra("Dificuldade", idx);
				intent.putExtra("Mode", 0);
				startActivity(intent);
				
			}
		});		
	}
}
