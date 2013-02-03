package edu.mit.drivesafe;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.widget.TextView;

public class TimeoutActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeout);
		final TextView timeout = (TextView) findViewById(R.id.timeout);		
		@SuppressWarnings("unused")
		CountDownTimer timer = new CountDownTimer(15000 - (new Date()).getTime() + DebugActivity.lastSlow.getTime(),100){
		     public void onTick(long millisUntilFinished) {
		    	 long seconds = millisUntilFinished/1000;
		    	 long deciseconds = millisUntilFinished/100 - 10*seconds;
		         timeout.setText(Long.toString(seconds) + "." + Long.toString(deciseconds));
		     }

		     public void onFinish() {
	    	 	 timeout.setText("0.0");
		     }
		}.start();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_timeout, menu);
		return true;
	}

}
