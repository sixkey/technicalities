package SixGen.Window;

import SixGen.Game.Game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * Window
 * Extends:
 *  Canvas
 * Abilities:
 *  Creates the window that the game is using
 */

public class Window extends JFrame {
    
    //VARIABLES
    
    // serialVersionUID
    private static final long serialVersionUID = 1L;
    // window that the canvases are added to
    private JFrame frame;
    
    private SixCanvas[] components;
    
    //CONSTRUCTORS
    
    public Window(int WIDTH, int HEIGHT, String title, Game game, CanvasManager canvManager) {
        //@Window
        //@Window#frameCreation
        Dimension dim = new Dimension(WIDTH + 6, HEIGHT + 29);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setTitle(title);
        setVisible(true);
        toFront();
        requestFocus();
        //@Window#canvasAdding
        components = new SixCanvas[0];
        frame = this;
        
        refresh(canvManager);
        //@Winodw#gameLoopStarting
        canvManager.setWindow(this);
        game.start();
        
    }
    
    //VOIDS
    
    public void refresh(CanvasManager canvManager) { 
        for (int i = 0; i < this.components.length; i++) { 
            this.components[i].setWindowComponent(false);
        }
        frame.getContentPane().removeAll();
        
        SixCanvas[] components = new SixCanvas[canvManager.getCanavasesAmount()];
        
        for (int i = 0; i < canvManager.getCanavasesAmount(); i++) {
            components[i] = canvManager.getCanvas(i); 
            components[i].setWindowComponent(true);
            add(components[i]);
        }
        
        this.components = components;
    }
    
    public void fullScreen() { 
        frame.dispose();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.setVisible(true);
    }
    
    public void minimize() {
        if(getState() == JFrame.ICONIFIED)  { 
            setState(JFrame.NORMAL);
        } else if(frame.getState() == JFrame.NORMAL) { 
            setState(JFrame.ICONIFIED);
        }
    }
    
    public void minimazeCanvas(SixCanvas canv) { 
        canv.setVisible(false);
//        this.remove(canv);
    }
    public void maximizeCanvas(SixCanvas canv) { 
        canv.setVisible(true);
//        this.add(canv);
    }
}
