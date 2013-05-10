package com.events;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Home extends Activity implements OnItemSelectedListener {
    /** Called when the activity is first created. */

	private static final String[] items={"Or browse Events here:","Entertainment","Sports","Church"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        
        //userSelection=(TextView)findViewById(R.id.user_selection);
        Spinner my_spin=(Spinner)findViewById(R.id.main_spinner);
        my_spin.setOnItemSelectedListener(this);
        @SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter<?> aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
        my_spin.setAdapter(aa);
    }
    
    public void ButtonClick(View view){
    	switch (view.getId()) {
		case R.id.buttoncreate_main:
			Intent newevent = new Intent(this, Login.class);
    		startActivity(newevent);
			break;

		default:
			break;
		}
    	
    }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		
		//String selected = items[position];
		if(position==0){
			
		}
		else if(position==2){
			Intent sports = new Intent(this, EventInfo.class);
			//Bundle b = new Bundle();
			//b.putString("s", selected);
			//sports.putExtras(b);
			startActivity(sports);
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
    

	
	
}