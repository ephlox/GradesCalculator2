package com.lob.gradescalculator;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayoutgesamt;
    ActionBarDrawerToggle drawerToggle;

    int sum = 0;
    int ects = 0;
    float mark = 0;
    NumberFormat numberFormat = new DecimalFormat("0.00");
    Button button1;
    TextView textView1;
    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayoutgesamt = (DrawerLayout) findViewById(R.id.drawerlayoutgesamt);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayoutgesamt, R.string.auf, R.string.zu);
        drawerLayoutgesamt.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        button1 = (Button)findViewById(R.id.button1);
        textView1 = (TextView)findViewById(R.id.textView1);
        editText1 = (EditText)findViewById(R.id.editTextGDI);
        editText2 = (EditText)findViewById(R.id.editTextGDW);
        editText3 = (EditText)findViewById(R.id.editTextProgrammieren1);
        loadSavedPreferences();

        button1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){

                        ViewGroup group = (ViewGroup)findViewById(R.id.activity_main);
                        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
                            View view = group.getChildAt(i);
                            if (view instanceof EditText) {
                                if(view.equals(editText1) && !(editText1.getText().toString().matches(""))){ //GDI
                                    System.out.println("edittext1");
                                    ects += 5;
                                    try{ sum += Integer.parseInt(editText1.getText().toString()) *5; } catch(NumberFormatException n){}
                                }
                                else if(view.equals(editText2)&& !(editText2.getText().toString().matches(""))){ //GDW
                                    System.out.println("edittext2");
                                    ects += 5;
                                    try{ sum += Integer.parseInt(editText2.getText().toString()) *5; } catch(NumberFormatException n){}
                                }
                                else if(view.equals(editText3)&& !(editText3.getText().toString().matches(""))){
                                    System.out.println("edittext3");
                                    ects += 7;
                                    try{ sum += Integer.parseInt(editText3.getText().toString()) *7; } catch(NumberFormatException n){}
                                }
                            }
                        }

                        mark = (float)sum / (float)ects;
                        textView1.setText(String.valueOf(numberFormat.format(mark)));
                        savePreferences("editText1", editText1.getText().toString());
                        savePreferences("editText2", editText2.getText().toString());
                        savePreferences("editText3", editText3.getText().toString());

                        sum = 0;
                        ects = 0;
                        mark = 0;
                    }
                }
        );
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        editText1.setText(sharedPreferences.getString("editText1", ""));
        editText2.setText(sharedPreferences.getString("editText2", ""));
        editText3.setText(sharedPreferences.getString("editText3", ""));
    }

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
           return true;
        }
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(new Configuration());
    }
}
