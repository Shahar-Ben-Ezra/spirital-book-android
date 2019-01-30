package com.example.shaharben_ezra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main extends AppCompatActivity {

    public  static  String userName="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);
        setTitle("MAIN");

    }

    /**
     * a func that to gather all event clicked
     * @param v
     */
    public void PressBtbActivity(View v) {
        Intent intent;

        if (v.getId() == R.id.user_1) {
            intent = new Intent(this,MainActivity.class);
             userName=getString(R.string.user1);
            startActivity(intent);

        } else if (v.getId() == R.id.user_2) {
            intent = new Intent(this, MainActivity.class);
            userName=getString(R.string.user2);
            startActivity(intent);

        } else if (v.getId() == R.id.user_3) {
            intent = new Intent(this, MainActivity.class);
            userName=getString(R.string.user3);
            startActivity(intent);

        } else if (v.getId() == R.id.user_4) {
            intent = new Intent(this, MainActivity.class);
            userName=getString(R.string.user4);
            startActivity(intent);


        }
    }


}
