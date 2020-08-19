package org.ernad;

import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.Graphics;

import java.io.File;
import java.io.IOException;

import java.lang.Runnable;
import java.lang.Thread;
import javax.swing.JFrame;
import javax.imageio.ImageIO;


/*
* Main class
*
*
*  */
public class Game extends JFrame implements Runnable {


    private static int alpha = 0xFFFF00DC;  //Sprite background color that render translucent

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;

    private SpriteSheet sheet;
    private SpriteSheet playerSheet;

    private int selectedTileID = 2;

    private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);

    private Tiles tiles;
    private Map map;

    private GameObject[] objects;
    private KeyboardListener keyListener = new KeyboardListener(this);
    private MouseEventListener mouseListener = new MouseEventListener(this);

    private Player player;

    private int xZoom = 3;
    private int yZoom = 3;

    public Game() {

        //When windows closes the program ends
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set windows size
        this.canvas.setBounds(0, 0, 1280, 720);

        //Add our graphics component
        add(this.canvas);
        pack();

        //Put our window in the center of the screen.
        setLocationRelativeTo(null);

        //Make out window visible.
        setVisible(true);

        //Create our object for buffer strategy.
        this.canvas.createBufferStrategy(3);

        pack();

        //Creating the renderer
        this.renderer = new RenderHandler(this.canvas.getWidth(), this.canvas.getHeight());

        //Loading resources
        BufferedImage sheetImage = loadImage("/Tiles1.png");
        this.sheet = new SpriteSheet(sheetImage);
        this.sheet.loadSprites(16, 16);

        BufferedImage playerSheetImage = loadImage("/Player.png");
        this.playerSheet = new SpriteSheet(playerSheetImage);
        this.playerSheet.loadSprites(20, 26);
        //Player animated sprites
        AnimatedSprite playerSprite = new AnimatedSprite(this.playerSheet,5);


        //Load tiles
        this.tiles = new Tiles(new File("C:\\Users\\Ernad Sehic\\IdeaProjects\\JavaGame\\src\\main\\resources\\Tiles.txt"), sheet);
        //Load map
        this.map = new Map(new File("C:\\Users\\Ernad Sehic\\IdeaProjects\\JavaGame\\src\\main\\resources\\Map.txt"), tiles);

        this.testRectangle.generateGraphics(5,12222);

        //Load SDK GUI
        GUIButton[] buttons = new GUIButton[this.tiles.size()];
        Sprite[] tileSprites = this.tiles.getSprites();

        for(int i = 0; i < buttons.length; i++) {
            Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom + 2), 16 * this.xZoom, 16 * this.yZoom);

            buttons[i] = new SDKButton(this, i,tileSprites[i], tileRectangle);
        }

        GUI gui = new GUI(buttons, 5, 5, true);


        //Load Objects
        this.objects = new GameObject[2];
        this.player = new Player(playerSprite);
        this.objects[0] = this.player;
        this.objects[1] = gui;

        //Add Listeners
        canvas.addKeyListener(this.keyListener);
        canvas.addFocusListener(this.keyListener);
        canvas.addMouseListener(this.mouseListener);
        canvas.addMouseMotionListener(this.mouseListener);
        
    }

    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        this.map.render(this.renderer, this.xZoom, this.yZoom);

        for(int i = 0; i < this.objects.length; i++) {
            this.objects[i].render(this.renderer, this.xZoom, this.yZoom);
        }


        //Put the rendered stuff on screen
        this.renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        this.renderer.clear();
    }

    public void changeTile(int tileID) {
        this.selectedTileID = tileID;
    }

    public int getSelectedTile() {
        return this.selectedTileID;
    }

    public void update() {

        for(int i = 0; i < this.objects.length; i++) {
            this.objects[i].update(this);
        }

    }

    //Handles time, currently set at 60FPS
    public void run() {

        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0 / 60;
        double changeInSeconds = 0;

        while(true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;

            while(changeInSeconds >= 1) {
                update();
                changeInSeconds--;
            }

            render();
            lastTime = now;
        }
    }

    //IO Stream handling
    private BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

            return formattedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void leftClick(int x, int y) {
        Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
        boolean stoppedChecking = false;

        for(int i = 0; i < this.objects.length; i++) {
            if(!stoppedChecking) {
                stoppedChecking = this.objects[i].handleMouseClick(mouseRectangle, this.renderer.getCamera(), this.xZoom, this.yZoom);
            }
        }

        if(!stoppedChecking) {
            x = (int) Math.floor((x + this.renderer.getCamera().x) / (16.0 * this.xZoom));
            y = (int) Math.floor((y + this.renderer.getCamera().y) / (16.0 * this.yZoom));
            this.map.setTile(x, y, this.selectedTileID);
        }

    }

    public void rightClick(int x, int y) {
        x = (int) Math.floor((x + this.renderer.getCamera().x) / (16.0 * this.xZoom));
        y = (int) Math.floor((y + this.renderer.getCamera().y) / (16.0 * this.yZoom));
        this.map.removeTile(x, y);
    }

    public void handleCTRL(boolean[] keys) {
        if(keys[KeyEvent.VK_S]) {
            this.map.saveMap();
        }
    }

    public static int getAlpha() {
        return alpha;
    }

    public KeyboardListener getKeyListener() { return this.keyListener; }

    public RenderHandler getRenderer() { return this.renderer; }

    public MouseEventListener getMouseListener() { return this.mouseListener; }

    public static void main(String[] args ) {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
