package org.ernad;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map {

    private Tiles tileSet;
    private int fillTileID = -1;

    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
    private HashMap<Integer, String> comments = new HashMap<Integer, String>();

    private File mapFile;

    public Map(File mapFile, Tiles tileSet) {
        this.mapFile = mapFile;
        this.tileSet = tileSet;
        try {
            Scanner scanner = new Scanner(mapFile);
            int currentLine = 0;
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (!line.startsWith("//")) {

                    if (line.contains(":")) {
                        String[] splitString = line.split(":");
                        if (splitString[0].equalsIgnoreCase("Fill")) {
                            fillTileID = Integer.parseInt(splitString[1]);
                            continue;
                        }
                    }

                    String[] splitString = line.split(",");
                    if (splitString.length >= 3) {
                        MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
                                Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                } else {
                    this.comments.put(currentLine, line);
                }
                currentLine++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ;
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        int tileWidth = 16 * xZoom;
        int tileHeight = 16 * yZoom;

        if (this.fillTileID >= 0) {
            Rectangle camera = renderer.getCamera();
            for (int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.y
                    + camera.getHeight(); y += tileHeight) {
                for (int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.x
                        + camera.getWidth(); x += tileWidth) {
                    this.tileSet.renderTile(this.fillTileID, renderer, x, y, xZoom, yZoom);
                }
            }
        }

        for (int tileIndex = 0; tileIndex < this.mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = this.mappedTiles.get(tileIndex);
            this.tileSet.renderTile(mappedTile.getId(), renderer, mappedTile.getX() * tileWidth,
                    mappedTile.getY() * tileHeight, xZoom, yZoom);
        }
    }

    public void setTile(int tileX, int tileY, int tileID) {
        boolean foundTile = false;

        for (int i = 0; i < this.mappedTiles.size(); i++) {
            MappedTile mappedTile = this.mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY) {
                mappedTile.id = tileID;
                foundTile = true;
                break;
            }
        }
        if (!foundTile) {
            this.mappedTiles.add(new MappedTile(tileID, tileX, tileY));
        }
    }

    public void saveMap() {
        try {
            int currentLine = 0;
            if (this.mapFile.exists()) {
                this.mapFile.delete();
            }

            this.mapFile.createNewFile();

            PrintWriter printWriter = new PrintWriter(this.mapFile);

            if (this.fillTileID >= 0) {
                if (this.comments.containsKey(currentLine)) {
                    printWriter.println(this.comments.get(currentLine));
                    currentLine++;
                }
                printWriter.println("Fill:" + this.fillTileID);
            }

            for (int i = 0; i < this.mappedTiles.size(); i++) {
                if (this.comments.containsKey(currentLine)) {
                    printWriter.println(this.comments.get(currentLine));
                }

                MappedTile tile = this.mappedTiles.get(i);
                printWriter.println(tile.id + "," + tile.x + "," + tile.y);
                currentLine++;
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeTile(int tileX, int tileY) {
        for (int i = 0; i < this.mappedTiles.size(); i++) {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY) {
                this.mappedTiles.remove(i);
            }
        }
    }

    // Struct - Tile ID in the TileSet and the position of the tile on the map)
    class MappedTile {

        private int id;
        private int x;
        private int y;

        public MappedTile(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        // Getters and setters below this line
        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
