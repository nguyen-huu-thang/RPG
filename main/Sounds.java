package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

public class Sounds {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumnScale = 4;
    float volumn;

    public Sounds() {
        soundURL[0] = getClass().getResource("/sounds/Undertale.wav");
        soundURL[1] = getClass().getResource("/sounds/unlock.wav");
        soundURL[2] = getClass().getResource("/sounds/pow.wav");
        soundURL[3] = getClass().getResource("/sounds/pickup.wav");
        soundURL[5] = getClass().getResource("/sounds/damaged.wav");
        soundURL[4] = getClass().getResource("/sounds/Yeet.wav");
        soundURL[6] = getClass().getResource("/sounds/sword.wav");
        soundURL[7] = getClass().getResource("/sounds/level_up.wav");
        soundURL[8] = getClass().getResource("/sounds/uh.wav");
        soundURL[9] = getClass().getResource("/sounds/heal.wav");
        soundURL[10] = getClass().getResource("/sounds/throw.wav");
        soundURL[11] = getClass().getResource("/sounds/Moved by the Ocean Breeze.wav");
        soundURL[12] = getClass().getResource("/sounds/click.wav");
        soundURL[13] = getClass().getResource("/sounds/buy.wav");
        soundURL[14] = getClass().getResource("/sounds/chest.wav");
        soundURL[15] = getClass().getResource("/sounds/start.wav");
    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolumn();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {
        clip.stop();
    }

    public void checkVolumn() {
        switch (volumnScale) {
            case 0:
                volumn = -80f;
                break;
            case 1:
                volumn = -50f;
                break;
            case 2:
                volumn = -20f;
                break;
            case 3:
                volumn = -15f;
                break;
            case 4:
                volumn = -10f;
                break;
            case 5:
                volumn = -8f;
                break;
            case 6:
                volumn = -5;
                ;
                break;
            case 7:
                volumn = -3f;
                break;
            case 8:
                volumn = -1f;
                break;
            case 9:
                volumn = 2f;
                break;
            case 10:
                volumn = 6f;
                break;
        }
        fc.setValue(volumn);
    }
}