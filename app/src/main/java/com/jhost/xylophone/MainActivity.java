package com.jhost.xylophone;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout[] notes;
    private SoundPool soundPool;
    private int[] rawSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        SoundPool.Builder spb = getSoundPoolBuilder();

        soundPool = spb.build();

        setNotesEvents(soundPool);
    }

    private void init(){
        initiateNotes();
        initiateRawSounds();
    }

    private void initiateNotes(){
        this.notes = new RelativeLayout[] {
                findViewById(R.id.note_d),
                findViewById(R.id.note_e),
                findViewById(R.id.note_f),
                findViewById(R.id.note_g),
                findViewById(R.id.note_a),
                findViewById(R.id.note_b),
                findViewById(R.id.note_c)
        };
    }

    private void initiateRawSounds(){
        this.rawSounds = new int[] {
                R.raw.note1_c,
                R.raw.note2_d,
                R.raw.note3_e,
                R.raw.note4_f,
                R.raw.note5_g,
                R.raw.note6_a,
                R.raw.note7_b,
        };
    }

    private SoundPool.Builder getSoundPoolBuilder(){
        AudioAttributes.Builder aab = new AudioAttributes.Builder();
        aab.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        AudioAttributes aa = aab.build();

        return getSoundPoolBuilder(notes.length, aa);
    }

    private  SoundPool.Builder getSoundPoolBuilder(int maxStreams, AudioAttributes audioAttributes){
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setMaxStreams(maxStreams);
        spb.setAudioAttributes(audioAttributes);
        return spb;
    }

    private void setNotesEvents(SoundPool sp){
        for (int i = 0; i < notes.length; i++) {
            setOnClickPlaySound(notes[i], sp.load(getApplicationContext(), rawSounds[i], 1));
        }
    }

    private void setOnClickPlaySound(RelativeLayout rl, int soundID) {
        setOnClickPlaySound(rl, soundID, 1f);
    }

    private void setOnClickPlaySound(RelativeLayout rl, int soundID, float volume){
        setOnClickPlaySound(rl, soundID, volume, volume, 1f);
    }

    private void setOnClickPlaySound(RelativeLayout rl, int soundID, float lVolume, float rVolume, float rate){
        rl.setOnClickListener(event -> {
            soundPool.play(soundID, lVolume, rVolume, 1, 0, rate);
        });
    }
}