package com.events;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

public class Register extends Activity {

	EditText firstname, lastname, email, password, cpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	}

	public void onRegister(View view) {

		firstname = (EditText) findViewById(R.id.editfname);
		final String fname = firstname.getText().toString();

		lastname = (EditText) findViewById(R.id.editlastname);
		final String lname = lastname.getText().toString();

		email = (EditText) findViewById(R.id.editemail);
		final String mail = email.getText().toString();

		password = (EditText) findViewById(R.id.editpassword);
		final String pswd = password.getText().toString();

		cpassword = (EditText) findViewById(R.id.editcpassword);
		final String cpswd = cpassword.getText().toString();

		if (pswd.equals(cpswd)) {

			final ProgressDialog dialog = ProgressDialog.show(
					view.getContext(), "Please Wait", "Accessing Server.....");
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						String url = "http://10.0.2.2/DbEventure/Register.php";
						DefaultHttpClient httpclient = new DefaultHttpClient();
						// Create a HTTp post object to hold our data - url
						HttpPost httppost = new HttpPost(url);
						// use HTTPClient to execute the HTTPPost
						// Execute HTTP Post Request
						// encode URL
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								4);
						nameValuePairs.add(new BasicNameValuePair("f", fname));
						nameValuePairs.add(new BasicNameValuePair("l", lname));
						nameValuePairs.add(new BasicNameValuePair("e", mail));
						nameValuePairs.add(new BasicNameValuePair("p", pswd));

						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));
						HttpResponse response = httpclient.execute(httppost);

						// use Input stream to read the http client response
						InputStream inputStream = response.getEntity()
								.getContent();

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

						// check if response is 0
						if (result.equals("0")) {
							// need this because of progress bar
							Register.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {

									Intent loginIntent = new Intent(
											Register.this, NewEvent.class);

									startActivity(loginIntent);
									finish();
									dialog.dismiss();

								}
							});
						}
						// check if response is 1
						else if (result.equals("1")) {

							Register.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(
											Register.this,
											"Registration failed please try again!",
											Toast.LENGTH_SHORT).show();
									dialog.dismiss();
									firstname.setText("");
									lastname.setText("");
									email.setText("");
									password.setText("");
									cpassword.setText("");

								}
							});
						}
						// check if response is 2
						else if (result.equals("2")) {

							Register.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(Register.this,
											"All fields must be filled",
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
		} else {
			Toast.makeText(Register.this, "passwords do not match",
					Toast.LENGTH_SHORT).show();
		}
	}

}
