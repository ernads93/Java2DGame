package org.ernad;

public class Rectangle {

    public int x;
    public int y;
    private int width;
    private int height;
    private int[] pixels;

    //Rectangle constructor 1
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Rectangle constructor 2
    public Rectangle() {
        this(0,0,0,0);
    }

    //Generates a solid rectangle
    public void generateGraphics(int color) {
        this.pixels = new int[this.width*this.height];
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.pixels[y * this.width + x] = color;
            }
        }
    }

    //Creates empty rectangle with a border
    public void generateGraphics(int borderWidth, int color) {
        this.pixels = new int[this.width*this.height];

        for(int i = 0; i < this.pixels.length; i++) {
            pixels[i] = Game.getAlpha();
        }

        //Top side
        for(int y = 0; y < borderWidth; y++) {
            for(int x = 0; x < this.width; x++) {
                this.pixels[y * this.width + x] = color;
            }
        }
        //Left side
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < borderWidth; x++) {
                pixels[y * this.width + x] = color;
            }
        }
        //Right side
        for(int y = 0; y < this.height; y++) {
            for(int x = this.width - borderWidth; x < this.width; x++) {
                pixels[y * this.width + x] = color;
            }
        }
        //Bottom side
        for(int y = this.height-borderWidth; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                pixels[y * this.width + x] = color;
            }
        }
    }

    public boolean intersects(Rectangle otherRectangle) {
        if (this.x > otherRectangle.x + otherRectangle.getWidth() || otherRectangle.x > this.x + this.width) {
            return false;
        }
        if(this.y > otherRectangle.y + otherRectangle.getHeight() || otherRectangle.y > this.y + this.height) {
            return false;
        }

        return true;
    }


    //Getter and setters below this line
    public int[] getPixels() {
        if(this.pixels != null) {
            return this.pixels;
        } else {
            System.out.println("Attempted to retrive pixels from a Rectangle without generated graphics.");
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        return "[" + this.x + "," + this.y + "," + "," + this.width + "," + this.width + "].";
    }
}
