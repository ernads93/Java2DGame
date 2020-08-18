package org.ernad;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private int[] pixels;
    private BufferedImage image;
    private final int SIZEX;
    private final int SIZEY;
    private Sprite[] loadedSprites = null;
    private boolean spritesLoaded = false;
    private int spriteSizeX;

    public SpriteSheet(BufferedImage sheetImage) {
        this.image = sheetImage;
        this.SIZEX = sheetImage.getWidth();
        this.SIZEY = sheetImage.getHeight();

        this.pixels = new int[this.SIZEX*this.SIZEY];
        this.pixels = sheetImage.getRGB(0, 0, this.SIZEX, this.SIZEY, this.pixels, 0, this.SIZEX);
    }

    //Load single Sprites from a SpriteSheet
    public void loadSprites(int spriteSizeX, int spriteSizeY) {
        this.spriteSizeX = spriteSizeX;
        this.loadedSprites = new Sprite[(this.SIZEX / spriteSizeX) * (this.SIZEY / spriteSizeY)];
        int spriteID = 0;
        for(int y = 0; y < this.SIZEY; y += spriteSizeY) {  //add + 1 to spriteSizeY if dealing with SpriteSheet padding
            for(int x = 0; x < this.SIZEX; x += spriteSizeX) { //add + 1 to spriteSizeX if dealing with SpriteSheet padding
                this.loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;
            }
        }
        this.spritesLoaded = true;
    }

    public Sprite getSprite(int x, int y) {
        if(this.spritesLoaded) {
            int spriteID = (y * (this.SIZEX / this.spriteSizeX)) + x;
            if(spriteID < this.loadedSprites.length) {
                return this.loadedSprites[spriteID];
            } else {
                System.out.println("SpriteID out of bounce " + spriteID);
            }
        } else {
            System.out.println("No sprites loaded.");
        }
        return null;
    }

    public Sprite[] getLoadedSprites() {
        return this.loadedSprites;
    }

    //Getters and Setters below this line
    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }
}
