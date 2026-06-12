package tile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public BufferedImage house1;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[200];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world2.txt", 0);
        loadMap("/maps/map1.txt", 1);
        loadMap("/maps/map01.txt", 2);
        loadMap("/maps/map3.txt", 3);
        loadMap("/maps/map5.txt", 4);
        loadMap("/maps/map_06.txt", 5);
    }

    public void getTileImage() {

        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "ground", false);
        setup(4, "tree1", true);
        setup(5, "sand", false);

        setup(7, "water_edge", true);
        setup(8, "water_edge_down", true);
        setup(9, "water_edge_left", true);
        setup(10, "water_edge_right", true);
        setup(11, "water_edge_bot_right", true);
        setup(12, "water_edge_bot_left", true);
        setup(13, "water_edge_left_up", true);
        setup(14, "water_edge_right_up", true);
        setup(15, "water_corner_top _right", true);
        setup(16, "water_corner_upper_left", true);
        setup(17, "water_corner_bot_left", true);
        setup(18, "water_corner_bot_right", true);

        setup(19, "hut", false);
        setup(20, "floor", false);// 50
        setup(21, "wall_color", true);
        setup(22, "wall2", true);
        setup(23, "wall1", true);
        setup(24, "wall_1", true);
        setup(25, "wall_1_1", true);
        setup(26, "wall_1_2", true);
        setup(27, "wall_1_4", true);
        setup(28, "wall_1_3", true);
        setup(29, "wall_end_3", true);
        setup(30, "wall_end_4", true);
        setup(31, "wall_end_1", true);
        setup(32, "wall_end_2", true);
        setup(33, "wall_2", true);
        setup(34, "wall_shadow", false);

        // Dungeon
        setup(06, "/dungeon/a71_1", false);
        setup(35, "/dungeon/66", true);
        setup(36, "/dungeon/a23", true);
        setup(37, "/dungeon/a24", true);
        setup(38, "/dungeon/a25", false);
        setup(39, "/dungeon/a26", false);

        setup(40, "/dungeon/a27", false);
        setup(41, "/dungeon/a28", true);
        setup(42, "/dungeon/a29", true);
        setup(43, "/dungeon/a30", true);
        setup(44, "/dungeon/a31", false);
        setup(45, "/dungeon/a32", false);
        setup(46, "/dungeon/a33", false);
        setup(47, "/dungeon/a34", true);
        setup(48, "/dungeon/a35", false);
        setup(49, "/dungeon/a36", true);
        setup(50, "/dungeon/a37", false);
        setup(51, "/dungeon/a38", true);
        setup(52, "/dungeon/a39", false);
        setup(53, "/dungeon/a40", false);
        setup(54, "/dungeon/a41", true);
        setup(55, "/dungeon/a42", false);
        setup(56, "/dungeon/a43", false);
        setup(57, "/dungeon/a44", false);
        setup(58, "/dungeon/a45", false);
        setup(59, "/dungeon/a46", false);
        setup(60, "/dungeon/a47", false);
        setup(61, "/dungeon/a48", false);
        setup(62, "/dungeon/a49", false);
        setup(63, "/dungeon/a50", true);
        setup(64, "/dungeon/a51", false);
        setup(65, "/dungeon/a52", true);
        setup(66, "/dungeon/a43_1", false);
        setup(67, "/dungeon/a20", true);
        setup(68, "/dungeon/a39_1", false);
        setup(69, "/dungeon/a19", false);
        setup(70, "/dungeon/a18", false);
        setup(71, "/dungeon/a18_1", false);
        setup(72, "/dungeon/a17", false);
        setup(73, "/dungeon/a37", true);
        setup(74, "/dungeon/a47", true);
        setup(75, "/dungeon/a27_1", false);
        setup(76, "/dungeon/a16", false);
        setup(77, "/dungeon/a15", false);
        setup(78, "/dungeon/a65", false);
        setup(79, "/dungeon/a66", false);
        setup(80, "/dungeon/a67", true);
        setup(81, "/dungeon/a68", true);
        setup(82, "/dungeon/a69", true);
        setup(83, "/dungeon/a70", true);
        setup(84, "/dungeon/a71", false);
        setup(85, "/dungeon/a72", true);
        setup(86, "/dungeon/a73", false);
        setup(87, "/dungeon/a74", true);
        setup(88, "/dungeon/a75", false);
        setup(89, "/dungeon/a76", true);
        setup(90, "/dungeon/a77", true);
        setup(91, "/dungeon/a78", true);
        setup(92, "/dungeon/a79", true);
        setup(93, "/dungeon/a80", true);
        setup(94, "/dungeon/a81", true);
        setup(95, "/dungeon/a82", false);
        setup(96, "/dungeon/a83", false);
        setup(97, "/dungeon/a84", true);
        setup(98, "/dungeon/a85", true);

        setup(99, "dark", true);
        setup(101, "/house/img-1", true);
        setup(102, "/house/img-2", true);
        setup(103, "/house/img-3", true);
        setup(104, "/house/img-4", true);
        setup(105, "/house/img-5", true);
        setup(106, "/house/img-6", true);
        setup(107, "/house/img-7", true);
        setup(108, "/house/img-8", true);
        setup(109, "/house/img-9", true);
        setup(110, "/house/img-10", true);
        setup(111, "/house/img-11", true);
        setup(112, "/house/img-12", true);
        setup(113, "/house/img-13", true);
        setup(114, "/house/img-14", true);
        setup(115, "/house/img-15", true);
        setup(116, "/house/img-16", true);

    }

    public void setup(int index, String path, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + path + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));// Read data from file;

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine(); // read each line;
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");// input data after space;

                    int num = Integer.parseInt(numbers[col]);// String to integer;

                    mapTileNum[map][col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;// Next line;
                }

            }
            br.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void draw(Graphics2D g2) {

        int wcol = 0;
        int wrow = 0;

        while (wcol < gp.maxWorldCol && wrow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][wcol][wrow];

            int worldX = wcol * gp.tileSize;
            int worldY = wrow * gp.tileSize;

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Stop the camera of the edge
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }

            int right = gp.screenWidth - gp.player.screenX;

            if (right > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bot = gp.screenHeight - gp.player.screenY;

            if (bot > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            if (worldX + gp.tileSize >= gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize <= gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize >= gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize <= gp.player.worldY + gp.player.screenY)
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            else if (gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    bot > gp.worldHeight - gp.player.worldY ||
                    right > gp.worldWidth - gp.player.worldX) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            }
            wcol++;

            if (wcol == gp.maxWorldCol) {
                wcol = 0;
                wrow++;

            }

        }
        // Draw object

    }

}