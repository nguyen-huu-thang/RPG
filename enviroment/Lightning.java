package enviroment;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.security.AlgorithmParameterGenerator;
import java.security.spec.EllipticCurve;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.RadialGradientPaint;

public class Lightning {
    GamePanel gp;
    BufferedImage darkFilter;
    public int dayCounter = 0;
    public float filterAlpha = 0f;
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lightning(GamePanel gp) {
        this.gp = gp;

        setLightSource();
    }

    public void update() {
        if (gp.player.lightUpdated == true) {
            setLightSource();
            gp.player.lightUpdated = false;

        }
        // Check day
        if (dayState == day) {
            dayCounter++;
            if (dayCounter > 6000) {
                dayState = dusk;
                dayCounter = 0;
            }

        }
        if (dayState == dusk) {
            filterAlpha += 0.001f;

            if (filterAlpha > 0.98f) {

                filterAlpha = 0.98f;
                dayState = night;
            }
        }
        if (dayState == night) {
            dayCounter++;
            if (dayCounter > 6000) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if (dayState == dawn) {
            filterAlpha -= 0.001f;
            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = day;
            }
        }

    }

    public void setLightSource() {
        // Create a buffered image

        darkFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darkFilter.getGraphics();

        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0.1f, 0.95f));

        } else {
            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            // Create a gradation effect
            Color color[] = new Color[10];
            float fraction[] = new float[10];

            color[0] = new Color(0, 0, 0.1f, 0f);
            color[1] = new Color(0, 0, 0.1f, 0.1f);
            color[2] = new Color(0, 0, 0.1f, 0.2f);
            color[3] = new Color(0, 0, 0.1f, 0.3f);
            color[4] = new Color(0, 0, 0.1f, 0.4f);
            color[5] = new Color(0, 0, 0.1f, 0.5f);
            color[6] = new Color(0, 0, 0.1f, 0.6f);
            color[7] = new Color(0, 0, 0.1f, 0.7f);
            color[8] = new Color(0, 0, 0.1f, 0.8f);
            color[9] = new Color(0, 0, 0.1f, 0.97f);

            fraction[0] = 0f;
            fraction[1] = 0.1f;
            fraction[2] = 0.2f;
            fraction[3] = 0.3f;
            fraction[4] = 0.4f;
            fraction[5] = 0.5f;
            fraction[6] = 0.6f;
            fraction[7] = 0.7f;
            fraction[8] = 0.85f;
            fraction[9] = 1f;

            // draw gradation
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius,
                    fraction, color);

            // Set data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void draw(Graphics2D g2) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darkFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        // Debug
        String situation = "";
        switch (dayState) {
            case day:
                situation = "Day";
                break;
            case dusk:
                situation = "Dusk";
                break;
            case night:
                situation = "Night";
                break;
            case dawn:
                situation = "Dawn";
                break;
        }
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30F));
        g2.drawString(situation, 800, 700);

    }

}
