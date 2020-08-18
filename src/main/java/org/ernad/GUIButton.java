package org.ernad;

public abstract class GUIButton implements GameObject {

    protected Sprite sprite;
    protected Rectangle rect;
    protected boolean fixed;

    public GUIButton(Sprite sprite, Rectangle rect, boolean fixed) {
        this.sprite = sprite;
        this.rect = rect;
        this.fixed = fixed;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {

    }

    public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect) {
        renderer.renderSprite(this.sprite, this.rect.x + interfaceRect.x, this.rect.y + interfaceRect.y, xZoom, yZoom, this.fixed);
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        if(mouseRectangle.intersects(this.rect)) {
            activate();
            return true;
        }
        return false;
    }

    public abstract void activate();

}
