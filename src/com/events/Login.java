package com.events;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	;
	HttpClient httpclient;
	HttpPost httppost;
	InputStream inputStream;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	public void buttonlogin(View view) {
		/*
		 * Context context = getApplicationContext(); CharSequence text =
		 * "Email and Passwords field shouldn't be empty"; int duration =
		 * Toast.LENGTH_SHORT;
		 */
		final EditText email;
		final EditText password;

		email = (EditText) findViewById(R.id.email);
		final String e = email.getText().toString();

		password = (EditText) findViewById(R.id.edpassword);
		final String p = password.getText().toString();

		/*
		 * if (emailinput.matches("") && passwordinput.matches("")) { Toast
		 * toast = Toast.makeText(context, text, duration); toast.show();
		 */
		// } else {
		// create a progress bar
		final ProgressDialog dialog = ProgressDialog.show(view.getContext(),
				"Please Wait", "Accessing Server.....");
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					String url = "http://10.0.2.2/DbEventure/Login.php";
					//String url = "http://eventure.devparadim.com/Login.php";
					DefaultHttpClient httpclient = new DefaultHttpClient();
					// Create a HTTp post object to hold our data - url
					HttpPost httppost = new HttpPost(url);
					// use HTTPClient to execute the HTTPPost
					// Execute HTTP Post Request
					// encode URL
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							4);
					nameValuePairs.add(new BasicNameValuePair("email", e));
					nameValuePairs.add(new BasicNameValuePair("password", p));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);

					// use Input stream to read the http client response
					InputStream inputStream = response.getEntity().getContent();

					// Get the response
					// use buffered reader and InputStreamReader to read the
					// input stream
					BufferedReader rd = new BufferedReader(
							new InputStreamReader(inputStream), 4096);
					String line;
					// initialize StringBuilder
					StringBuilder sb = new StringBuilder();
					// read everything from the Buffered reader and append
					// the to the
					// string builder
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
					rd.close();
					// our result
					String result = sb.toString();

					inputStream.close();
					runOnUiThread(new Runnable() {
						public void run() {
							// once response is received - dismiss dialog

						}
					});
					System.out.println(result);
					// check if response is 0
					if (result.equals("0")) {
						// need this because of progress bar
						Login.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Intent loginIntent = new Intent(Login.this,
										NewEvent.class);

								startActivity(loginIntent);
								finish();
								dialog.dismiss();

							}
						});
					}
					// check if response is 1
					else if (result.equals("1")) {

						Login.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(Login.this,
										"Invalid! username and password",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
								email.setText("");
								password.setText("");

							}
						});
					}
					// check if response is 2
					else if (result.equals("2")) {

						Login.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(
										Login.this,
										"Username and password must be present",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();

							}
						});
					}

				}
				// catch exception
				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error inside set:" + e.toString(),
							Toast.LENGTH_LONG).show();
				}
			}
		};
		thread.start();
	}

	public void register(View view) {

		Intent intregister = new Intent(this, Register.class);
		startActivity(intregister);
	}

}
