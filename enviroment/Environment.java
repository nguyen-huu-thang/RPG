package enviroment;

import main.GamePanel;
import main.GamePanel;
import java.awt.image.BufferedImage;
import java.security.spec.EllipticCurve;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.Graphics2D;

public class Environment {
    GamePanel gp;
    public Lightning lightning;

    public Environment(GamePanel gp) {
        this.gp = gp;

    }

    public void setup() {
        lightning = new Lightning(gp);

    }

    public void draw(Graphics2D g2) {
        lightning.draw(g2);
    }

    public void update() {
        lightning.update();
    }
}
