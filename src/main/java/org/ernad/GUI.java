package org.ernad;

public class GUI implements GameObject{

    private Sprite backgroundSprite;
    GUIButton[] buttons;
    private Rectangle rect = new Rectangle();
    private boolean fixed;

    public GUI(Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed) {
        this.backgroundSprite = backgroundSprite;
        this.buttons = buttons;

        this.rect.x = x;
        this.rect.y = y;

        this.fixed = fixed;

        if(backgroundSprite != null) {
            this.rect.setWidth(backgroundSprite.getWidth());
            this.rect.setHeight(backgroundSprite.getHeight());
        }
    }

    public GUI(GUIButton[] buttons, int x, int y, boolean fixed) {
        this(null, buttons, x, y, fixed);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if(this.backgroundSprite != null) {
            renderer.renderSprite(this.backgroundSprite, this.rect.x, this.rect.y, xZoom, yZoom, this.fixed);
        }
        if(this.buttons != null) {
            for(int i = 0; i < this.buttons.length; i++) {
                this.buttons[i].render(renderer, xZoom, yZoom, this.rect);
            }
        }
    }

    @Override
    public void update(Game game) {
        if(this.buttons != null) {
            for(int i = 0; i < this.buttons.length; i++) {
                this.buttons[i].update(game);
            }
        }
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {

        boolean stopChecking = false;

        if(!this.fixed) {
            mouseRectangle = new Rectangle(mouseRectangle.x + camera.x,mouseRectangle.y + camera.y, 1, 1);
        } else {
            mouseRectangle = new Rectangle((mouseRectangle.x), mouseRectangle.y, 1, 1);
        }

        if(this.rect.getWidth() == 0 || this.rect.getHeight() == 0 || mouseRectangle.intersects(this.rect)) {
            mouseRectangle.x -= this.rect.x;
            mouseRectangle.y -= this.rect.y;

            for(int i = 0; i < this.buttons.length; i++) {
                boolean result = this.buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
                if(stopChecking == false) {
                    stopChecking = result;
                }
            }
        }
        return stopChecking;
    }
}
