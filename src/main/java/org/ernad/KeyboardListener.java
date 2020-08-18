package org.ernad;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener, FocusListener {

    private boolean[] keys = new boolean[120];
    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode < this.keys.length) {
            this.keys[keyCode] = true;
        }

        if (this.keys[KeyEvent.VK_CONTROL]) {
            this.game.handleCTRL(this.keys);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode < this.keys.length) {
            this.keys[keyCode] = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        for(int i = 0; i < this.keys.length; i++) {
            this.keys[i] = false;
        }
    }

    public boolean up() {
        return this.keys[KeyEvent.VK_W] || this.keys[KeyEvent.VK_UP];
    }

    public boolean down() {
        return this.keys[KeyEvent.VK_S] || this.keys[KeyEvent.VK_DOWN];
    }

    public boolean left() {
        return this.keys[KeyEvent.VK_A] || this.keys[KeyEvent.VK_LEFT];
    }

    public boolean right() {
        return this.keys[KeyEvent.VK_D] || this.keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void focusGained(FocusEvent e) { }

}
