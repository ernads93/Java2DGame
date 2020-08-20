package org.ernad;

public class Player implements GameObject {

    private Rectangle playerRectangle;
    private int speed = 10;
    private Sprite sprite;
    private AnimatedSprite animatedSprite = null;

    // 0 = RIGHT, 1 = LEFT, 2 = UP, 3 = DOWN
    private int playerDirection = 0;

    public Player(Sprite sprite) {
        this.sprite = sprite;
        if (sprite instanceof AnimatedSprite) {
            this.animatedSprite = (AnimatedSprite) sprite;
        }

        updateDirection();
        this.playerRectangle = new Rectangle(32, 16, 16, 32);
        this.playerRectangle.generateGraphics(0x33D0FF);

    }

    private void updateDirection() {
        if (this.animatedSprite != null) {
            this.animatedSprite.setAnimationRange(this.playerDirection * 8, this.playerDirection * 8 + 7);
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if (this.animatedSprite != null) {
            renderer.renderSprite(this.animatedSprite, this.playerRectangle.x, this.playerRectangle.y, xZoom, yZoom,
                    false);
        } else if (this.sprite != null) {
            renderer.renderSprite(this.sprite, this.playerRectangle.x, this.playerRectangle.y, xZoom, yZoom, false);
        } else {
            renderer.renderRectangle(this.playerRectangle, xZoom, yZoom, false);
        }
    }

    public void update(Game game) {
        KeyboardListener keyListener = game.getKeyListener();

        boolean moving = false;
        int newDirection = this.playerDirection;

        if (keyListener.up()) {
            newDirection = 2;
            moving = true;
            this.playerRectangle.y -= this.speed;
        }
        if (keyListener.down()) {
            newDirection = 3;
            moving = true;
            this.playerRectangle.y += this.speed;
        }
        if (keyListener.left()) {
            newDirection = 1;
            moving = true;
            this.playerRectangle.x -= this.speed;
        }
        if (keyListener.right()) {
            newDirection = 0;
            moving = true;
            this.playerRectangle.x += this.speed;
        }

        if (newDirection != this.playerDirection) {
            this.playerDirection = newDirection;
            updateDirection();
        }

        if (!moving) {
            this.animatedSprite.reset();
        }

        updateCamera(game.getRenderer().getCamera());
        if (moving) {
            this.animatedSprite.update(game);
        }

    }

    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        return false;
    }

    public void updateCamera(Rectangle camera) {
        camera.x = playerRectangle.x - (camera.getWidth() / 2);
        camera.y = playerRectangle.y - (camera.getHeight() / 2);
    }
}
