package edu.fje.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    Handler hnd;
    Runnable rn;
    Button pl;
    boolean p = true;
    Button Emepzar;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Emepzar = findViewById(R.id.bEmpezar);
        pl = (Button) findViewById(R.id.bPlpa);
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == true) {
                    p = false;
                    pl.setText("Play");
                    onPause();
                } else {
                    p = true;
                    pl.setText("Pause");
                    onResume();
                }
            }
        });

        hnd = new Handler();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.song);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playCycle();
                mp.start();
            }
        });

        Emepzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empezar();
            }
        });


    }

    public void playCycle(){
        if(mp.isPlaying()){
            rn = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            hnd.postDelayed(rn, 1000);
        }
    }

    public void onPause(){
        super.onPause();
        mp.pause();
    }

    public void onResume(){
        super.onResume();
        mp.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.release();
        hnd.removeCallbacks(rn);
    }
        public void addFragment(){
        FragmentLogin fragment = new FragmentLogin();
        fragment.setCallbackFragment(this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainer,fragment);
        ft.commit();
    }

    public void replaceFragment(){
        fragment= new FragmentRegister();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragmentContainer,fragment);
        ft.commit();
    }

    public void startFragment(){
        FragmentMain newFragment = new FragmentMain();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }

    @Override
    public void changeFragment() {
        replaceFragment();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public void Empezar(){
        Intent puzzle = new Intent(this, PuzzleActivity.class);
        startActivity(puzzle);
    }
}
