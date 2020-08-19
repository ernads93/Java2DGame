package org.ernad;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {

    private BufferedImage view;
    private int[] pixels;
    private Rectangle camera;

    public RenderHandler(int width, int height) {
        //Create a BufferedImage that will represent our view.
        this.view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //Create a camera
        this.camera = new Rectangle(0,0, width, height);
        //Create an array for the pixels
        this.pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    }

    //Renders our pixel array to our screen
    public void render(Graphics graphics) {
        graphics.drawImage(this.view, 0, 0, this.view.getWidth(), this.view.getHeight(), null);
    }

    //Render our image to our array of pixels
    public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset, int width, int height) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed, xOffset, yOffset, width, height);
    }

    //Render our rectangle to our array of pixels
    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean fixed) {
        int[] rectanglePixels = rectangle.getPixels();
        if(rectanglePixels != null) {
            renderArray(rectanglePixels, rectangle.getWidth(), rectangle.getHeight(), rectangle.x, rectangle.y, xZoom, yZoom, fixed);
        }
    }

    public void renderRectangle(Rectangle rectangle, Rectangle offset, int xZoom, int yZoom, boolean fixed) {
        int[] rectanglePixels = rectangle.getPixels();
        if(rectanglePixels != null) {
            renderArray(rectanglePixels, rectangle.getWidth(), rectangle.getHeight(), rectangle.x + offset.x, rectangle.y + offset.y, xZoom, yZoom, fixed);
        }
    }

    /*
     * Method renderArray: Renders the array of pixels.
     *
     *
     * @param renderPixels: entire pixels of the image/sprite/rectangle/ec.
     * @param imageWith: width of the entire image
     * @param imageHeight: height of the entire image
     * @param xPosition: x position of the render on screen
     * @param yPosition: y position of the render on screen
     * @param xZoom: horizontal zoom
     * @param yZoom: vertical zoom
     * @param fixed: if the render should offset by camera position
     * @param xOffset: horizontal offset into the image
     * @param yOffset: vertical offset into the image
     * @param renderWidth: width of the image to render
     * @param renderHeight: height od the image to render
     *
     */
    public void renderArray(int[] renderPixels, int imageWidth, int imageHeight,
                            int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset, int renderWidth, int renderHeight) {

        for (int y = yOffset; y < yOffset + renderHeight; y++) {
            for (int x = xOffset; x < xOffset + renderWidth; x++) {
                for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
                    for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                        setPixel(renderPixels[y * imageWidth + x], ((x - xOffset) * xZoom) + xPosition + xZoomPosition, ((y - yOffset) * yZoom) + yPosition + yZoomPosition, fixed);
                    }
                }
            }
        }
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        renderArray(renderPixels, renderWidth, renderHeight, xPosition, yPosition, xZoom, yZoom, fixed, 0, 0, renderWidth, renderHeight);
    }

    private void setPixel(int pixel, int x, int y, boolean fixed) {

        int pixelIndex = 0;
        if(!fixed) {
            if((x >= this.camera.x) && (y >= this.camera.y) && (x <= this.camera.x + this.camera.getWidth()) && (y <= this.camera.y + this.camera.getHeight())) {
                pixelIndex = (y - this.camera.y) * this.view.getWidth() + (x - this.camera.x);
            }
        } else {
            if(x >= 0 && y >= 0 && x <= this.camera.getWidth() && y <= this.camera.getHeight()) {
                pixelIndex = x + y * this.view.getWidth();
            }
        }
        if((this.pixels.length > pixelIndex) && (pixel != Game.getAlpha())) {
            this.pixels[pixelIndex] = pixel;
        }

    }

    public void clear() {
        for(int i = 0; i < this.pixels.length; i++) {
            this.pixels[i] = 0;
        }
    }

    public Rectangle getCamera() {
        return this.camera;
    }

}
