package org.ernad;

import java.awt.image.BufferedImage;

public class Sprite {

    protected int width;
    protected int height;
    protected int[] pixels;

    public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width*height];
        sheet.getImage().getRGB(startX, startY, width, height, this.pixels, 0, width);
    }

    public Sprite(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixels = new int[this.width*this.height];
        image.getRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
    }

    public Sprite() {}

    //Getters and setters below this line
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getPixels() {
        return this.pixels;
    }
}
