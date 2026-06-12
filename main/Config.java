package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            // Music
            bw.write(String.valueOf(gp.music.volumnScale));
            bw.newLine();
            // SE
            bw.write(String.valueOf(gp.se.volumnScale));
            bw.newLine();

            bw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();
            // Music
            gp.music.volumnScale = Integer.parseInt(s);
            // SE
            s = br.readLine();
            gp.music.volumnScale = Integer.parseInt(s);

            br.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
