package com.wildt;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MP3Player{

    private final Map<String, MediaPlayer> audioMap;

    public MP3Player(){
        //Creates an audio map for the sound to be placed in and an JFX panel for the sound to run on
        audioMap = new ConcurrentHashMap<>();
        JFXPanel fxPanel = new JFXPanel();
    }

    public void play(String filepath){
        try {
            MediaPlayer mediaPlayer;
            //If the filepath already exists in the audio map, play it.
            if (audioMap.containsKey(filepath)) {
                mediaPlayer = audioMap.get(filepath);
                mediaPlayer.setCycleCount(1);
            }
            //If the filepath has not yet been linked to audio map it adds it
            else {
                mediaPlayer = new MediaPlayer( new Media(new File(filepath).toURI().toString()) );
                mediaPlayer.setCycleCount(1);
                audioMap.put(filepath, mediaPlayer);
            }
            mediaPlayer.play();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Sound failure.");
        }
    }
}