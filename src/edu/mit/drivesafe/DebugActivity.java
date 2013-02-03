package edu.mit.drivesafe;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class DebugActivity extends Activity {
	//public static lastSlow 
	public static boolean challengePassed = false;
	private static boolean fast = false;
	public static Date lastSlow = new Date(0);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_debug, menu);
		return true;
	}
	
	public void setSlow(View view) {
		fast = false;
		lastSlow = new Date();
	}

	public void setFast(View view) {
		fast = true;
	}
	
	public void reset(View view) {
		challengePassed = false;
		lastSlow = new Date(0);
	}
	
	public void start(View view) {
		Intent intent;
		long diff = ((new Date()).getTime() - lastSlow.getTime())/1000;
		/*  We go straight to messages if the challenge has been passed
		 *  or if we're going slow and we have been for a while
		 */
		if (challengePassed || (!fast && diff > 15)) {
			intent = new Intent(this, MessageActivity.class);
		/*  We go to the timeout screen if we're going slow, but
		 *  haven't been going slow for long
		 */
		} else if (!fast && diff <= 15) { 
			intent = new Intent(this, TimeoutActivity.class); //new class with timer
		/*  The only remaining case is when we're going fast.
		 *  We also know the challenge hasn't been passed.
		 *  Therefore, we go to the challenge.
		 */
		} else { 
			intent = new Intent(this, MainActivity.class);
		}
		startActivity(intent);
	}
}
