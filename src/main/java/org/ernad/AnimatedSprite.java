package org.ernad;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject {

    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter = 0;

    private int startSprite = 0;
    private int endSprite;

    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) {

        this.sprites = new Sprite[positions.length];
        this.speed = speed;
        this.endSprite = positions.length - 1;

        for (int i = 0; i < positions.length; i++) {
            this.sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].getWidth(),
                    positions[i].getHeight());
        }
    }

    public AnimatedSprite(SpriteSheet sheet, int speed) {

        this.sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = this.sprites.length - 1;
    }

    // @param "speed" represents how many frames pass until the sprite changes
    public AnimatedSprite(BufferedImage[] images, int speed) {
        this.sprites = new Sprite[images.length];
        this.speed = speed;
        this.endSprite = images.length - 1;

        for (int i = 0; i < images.length; i++) {
            this.sprites[i] = new Sprite(images[i]);
        }
    }

    public void setAnimationRange(int startSprite, int endSprite) {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();
    }

    public void reset() {
        this.counter = 0;
        this.currentSprite = this.startSprite;
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
    }

    public void update(Game game) {
        this.counter++;
        if (this.counter >= this.speed) {
            this.counter = 0;
            incrementSprite();
        }
    }

    public void incrementSprite() {
        this.currentSprite++;
        if (this.currentSprite >= this.endSprite) {
            this.currentSprite = this.startSprite;
        }
    }

    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        return false;
    }

    // Getters and setters below this line
    public int getWidth() {
        return this.sprites[this.currentSprite].getWidth();
    }

    public int getHeight() {
        return this.sprites[this.currentSprite].getHeight();
    }

    public int[] getPixels() {
        return this.sprites[this.currentSprite].getPixels();
    }
}
