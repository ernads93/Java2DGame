package org.ernad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {

    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();

    // This will only work assuming the sprites in the spritesheet have been loaded
    public Tiles(File tilesFile, SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;

        try {
            // Read each line and create a tile
            Scanner scanner = new Scanner(tilesFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("//")) {
                    String[] splitString = line.split(",");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, this.spriteSheet.getSprite(spriteX, spriteY));
                    this.tileList.add(tile);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom) {
        if (tileID >= 0 && this.tileList.size() > tileID) {
            renderer.renderSprite(this.tileList.get(tileID).getSprite(), xPosition, yPosition, xZoom, yZoom, false);
        } else {
            System.out.println("TileID: " + tileID + "is not in range " + this.tileList.size() + ".");
        }
    }

    public int size() {
        return this.tileList.size();
    }

    public Sprite[] getSprites() {
        Sprite[] sprites = new Sprite[size()];

        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = this.tileList.get(i).sprite;
        }

        return sprites;
    }

    // Struct
    class Tile {

        private String tileName;
        private Sprite sprite;

        public Tile(String tileName, Sprite sprite) {
            this.tileName = tileName;
            this.sprite = sprite;
        }

        public String getTileName() {
            return tileName;
        }

        public Sprite getSprite() {
            return sprite;
        }
    }
}
