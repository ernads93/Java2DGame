package org.ernad;

public class SDKButton extends GUIButton {

    private Game game;
    private int tileID;
    private boolean isGreen = false;

    public SDKButton(Game game, int tileID, Sprite tileSprite, Rectangle rect) {
        super(tileSprite, rect, true);
        this.rect.generateGraphics(0xE6E6E6);
        this.game = game;
        this.tileID = tileID;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect) {
        renderer.renderRectangle(this.rect, interfaceRect, 1, 1, this.fixed);
        renderer.renderSprite(this.sprite,
                this.rect.x + interfaceRect.x + (xZoom - (xZoom - 1)) * this.rect.getWidth()/2/xZoom,
                this.rect.y + interfaceRect.y + (yZoom - (yZoom - 1)) * this.rect.getHeight()/2/yZoom,
                xZoom - 1, yZoom - 1, this.fixed);
    }

    public void activate() {
        this.game.changeTile(this.tileID);
    }

    @Override
    public void update(Game game) {
        if(this.tileID == game.getSelectedTile()) {
            if(!this.isGreen) {
                this.rect.generateGraphics(0x67FF3D);
                this.isGreen = true;
            }
        } else {
            if(this.isGreen) {
                this.rect.generateGraphics(0xE6E6E6);
                this.isGreen = false;
            }
        }
    }

}
