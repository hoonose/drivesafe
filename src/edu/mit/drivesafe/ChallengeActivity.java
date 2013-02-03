package edu.mit.drivesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ChallengeActivity extends Activity {
	private static boolean challengeStarted = false;
	private static CountDownTimer timer = null;
	private static TextView countdown;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge);
		countdown = (TextView) findViewById(R.id.countdown);
		if (DebugActivity.challengePassed) {
			makeSkipButton();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_challenge, menu);
		return true;
	}

	private void startMessaging() {
    	Intent intent = new Intent(this, MessageActivity.class);
    	startActivity(intent);
	}
	
	private void makeSkipButton() {
		Button button = new Button(this);
		button.setTextSize(40);
		button.setText("Thanks for not texting and driving!\n\n Touch to continue to messaging");
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				startMessaging();
			}
		});
		setContentView(button);
		
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if (!DebugActivity.challengePassed){
			int fingers = event.getPointerCount();
			if (fingers >= 8 && !challengeStarted) {
				challengeStarted = true;
				timer = new CountDownTimer(5000,100){
				     public void onTick(long millisUntilFinished) {
				    	 long seconds = millisUntilFinished/1000;
				    	 long deciseconds = millisUntilFinished/100 - 10*seconds;
				         countdown.setText(Long.toString(seconds) + "." + Long.toString(deciseconds));
				     }

				     public void onFinish() {
							startMessaging();
							countdown.setText("");
							DebugActivity.challengePassed = true;
							makeSkipButton();
							Log.e("X","Done timer");
				     }
				}.start();
				Log.e("X","Starting Timer");
			} else if (fingers <= 7 && challengeStarted) {
				countdown.setText("5");
				timer.cancel();
				challengeStarted = false;
				Log.e("X","Canceled Timer");
			}
		}
	    // tell the system that we handled the event and no further processing is required
	    return true; 
	}
	
}
