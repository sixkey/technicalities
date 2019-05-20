package SixGen.Window;

import SixGen.Utils.Utils;
import SixGen.Game.GameLoop.GameLoopType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Console
 * Abilities:
 *  Console displays iformation about FPS , TPS , title and message
 *  Console can be displayed in all four corners
 */

public class Console extends Utils {
    
    // VARIABLES
    
    // ConsolePosition is enum that holds 4 values about console position 
    public enum ConsolePosition { 
        // Console will be in the left bottom corner
        leftBottom , 
        // Console will be in the left top corner
        leftTop ,
        // Console will be in the right bottom corner
        rightBottom ,
        // Console will be in the right top corner
        rightTop;
    }
    
    // Boolean that controls the console's ability to show FPS 
    private boolean FPSS;
    // Boolean that controls the console's ability to show FPS 
    private boolean TPSS;
    // Boolean that controls the console's ability to show title of the game
    private boolean titleS;
    // Boolean that controls the console's ability to show message  
    private boolean messageS;
    // Boolean that controls the console's ability to show engine type of the game
    private boolean engineTypeS;

    // FPS value that will be displayed by console
    private int FPS;
    // TPS value that will be displayed by console
    private int TPS;
    // title of the game that will be displayed by console
    private String title;
    // message that will be displayed by console
    private String message = "";
    // engineType that will be displayed by console
    private GameLoopType engineType;

    // dimensions of single line
    int rectWidth = 200;
    int rectHeight = 20;
    
    // default size of the background rect
    int backGroundHeight = 0;

    // Bounds of the line with FPS data
    private Rectangle FPSRect = new Rectangle(0, 0, rectWidth, rectHeight);
    // Bounds of the line with FPS data
    private Rectangle TPSRect = new Rectangle(0, rectHeight, rectWidth, rectHeight);
    // Bounds of the line with FPS data
    private Rectangle titleRect = new Rectangle(0, rectHeight * 2, rectWidth, rectHeight);
    // Bounds of the line with FPS data
    private Rectangle messageRect = new Rectangle(0,rectHeight * 3 , rectWidth , rectHeight);
    // Bounds of the line with FPS data
    private Rectangle engineTypeRect = new Rectangle(0, rectHeight*4 , rectWidth , rectHeight);
    // Bounds of the line with FPS data
    private Rectangle backGround;
    
    // Position of the console *for more info read ConsolePosition's description
    private ConsolePosition consolePosition = ConsolePosition.leftTop;
    
    // CONSTRUCTOR
    
    public Console() {
        //@Console
        //@Console#defaultSetters
        FPSS = false;
        TPSS = false;
        titleS = false;
        messageS = false;
        engineTypeS = false;
    }

    public Console(boolean FPSS, boolean TPSS, boolean titleS , boolean messageS , boolean engineTypeS) {
        //@Console
        //@Console#constructorSetters
        this.FPSS = FPSS;
        this.TPSS = TPSS;
        this.titleS = titleS;
        this.messageS = messageS;
        this.engineTypeS = engineTypeS;
        if(FPSS) { 
            backGroundHeight+=rectHeight;
        }
        if(TPSS) { 
            backGroundHeight+=rectHeight;
        }
        if(titleS) { 
            backGroundHeight+=rectHeight;
        }
        if(messageS) { 
            backGroundHeight+=rectHeight;
        }
        if(engineTypeS) { 
            backGroundHeight+=rectHeight;
        }
        backGround = new Rectangle(0, 0, rectWidth, backGroundHeight);
    }

    // VOIDS
    
    public void render(Graphics g) {
        //@render
        g.setColor(Color.black);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(getAlphaComposite(0.5f));
        g2d.fill(backGround);
        g2d.setComposite(getAlphaComposite(1));
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(14f));
        String string;
        if (FPSS) {
            string = "FPS : " + FPS;
            drawTextWithAlign(g, string, 10, FPSRect, TextLocation.left);
        }
        if (TPSS) {
            string = "TPS : " + TPS;
            drawTextWithAlign(g, string, 10, TPSRect, TextLocation.left);
        }
        if (titleS) {
            string = "TITLE : " + title;
            drawTextWithAlign(g, string, 10, titleRect, TextLocation.left);
        }
        if(messageS) { 
            drawTextWithAlign(g , message, 10 , messageRect , TextLocation.left);
        }
        if(engineTypeS) { 
            string = "ENGINE TYPE : " + engineType;
            drawTextWithAlign(g , string, 10 , engineTypeRect , TextLocation.left);
        }
    }

    //GETTERS SETTERS
    
    public void setFPSS(boolean FPSS) {
        this.FPSS = FPSS;
    }

    public boolean getFPSS() {
        return FPSS;
    }

    public void setTPSS(boolean TPSS) {
        this.TPSS = TPSS;
    }

    public boolean getTPSS() {
        return TPSS;
    }

    public void setTitleS(boolean titleS) {
        this.titleS = titleS;
    }

    public boolean getTitleS() {
        return titleS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public void setTPS(int TPS) {
        this.TPS = TPS;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setMessage(String message) { 
        this.message = message;
    }
    public String getMessage() { 
        return message;
    }
    public void setPosition(ConsolePosition consolePosition , int frameWidth , int frameHeight) {
        this.consolePosition = consolePosition;
        if(consolePosition == ConsolePosition.leftTop) {
            backGround = new Rectangle(0, 0, rectWidth, backGroundHeight);
            FPSRect = new Rectangle(0, 0, rectWidth, rectHeight);
            TPSRect = new Rectangle(0, rectHeight, rectWidth, rectHeight);
            titleRect = new Rectangle(0, rectHeight * 2, rectWidth, rectHeight);
            messageRect = new Rectangle(0,rectHeight * 3 , rectWidth , rectHeight);
            engineTypeRect = new Rectangle(0, rectHeight*4 , rectWidth , rectHeight);
    
        } else if(consolePosition == ConsolePosition.leftBottom) { 
            backGround = new Rectangle(0, frameHeight - rectHeight * 5, rectWidth, backGroundHeight);
            FPSRect = new Rectangle(0, frameHeight - rectHeight * 5, rectWidth, rectHeight);
            TPSRect = new Rectangle(0, frameHeight - rectHeight * 4, rectWidth, rectHeight);
            titleRect = new Rectangle(0, frameHeight - rectHeight * 3, rectWidth, rectHeight);
            messageRect = new Rectangle(0,frameHeight - rectHeight * 2, rectWidth , rectHeight);
            engineTypeRect = new Rectangle(0,frameHeight - rectHeight , rectWidth , rectHeight);
        } else if(consolePosition == ConsolePosition.rightTop) { 
            backGround = new Rectangle(frameWidth - rectWidth, 0, rectWidth, backGroundHeight);
            FPSRect = new Rectangle(frameWidth - rectWidth, 0, rectWidth, rectHeight);
            TPSRect = new Rectangle(frameWidth - rectWidth, rectHeight, rectWidth, rectHeight);
            titleRect = new Rectangle(frameWidth - rectWidth, rectHeight * 2, rectWidth, rectHeight);
            messageRect = new Rectangle(frameWidth - rectWidth,rectHeight * 3 , rectWidth , rectHeight);
            engineTypeRect = new Rectangle(frameWidth - rectWidth, rectHeight*4 , rectWidth , rectHeight);
        } else if(consolePosition == ConsolePosition.rightBottom) {
            backGround = new Rectangle(frameWidth - rectWidth, frameHeight - rectHeight * 5, rectWidth, backGroundHeight);
            FPSRect = new Rectangle(frameWidth - rectWidth, frameHeight - rectHeight * 5, rectWidth, rectHeight);
            TPSRect = new Rectangle(frameWidth - rectWidth, frameHeight - rectHeight * 4, rectWidth, rectHeight);
            titleRect = new Rectangle(frameWidth - rectWidth, frameHeight - rectHeight * 3, rectWidth, rectHeight);
            messageRect = new Rectangle(frameWidth - rectWidth,frameHeight - rectHeight * 2, rectWidth , rectHeight);
            engineTypeRect = new Rectangle(frameWidth - rectWidth, frameHeight - rectHeight , rectWidth , rectHeight);
        }
    }
    public ConsolePosition getPosition() { 
        return consolePosition;
    }

    public boolean isMessageS() {
        return messageS;
    }

    public void setMessageS(boolean messageS) {
        this.messageS = messageS;
    }

    public boolean isEngineTypeS() {
        return engineTypeS;
    }

    public void setEngineTypeS(boolean engineTypeS) {
        this.engineTypeS = engineTypeS;
    }

    public GameLoopType getEngineType() {
        return engineType;
    }

    public void setEngineType(GameLoopType engineType) {
        this.engineType = engineType;
    }

    public ConsolePosition getConsolePosition() {
        return consolePosition;
    }

    public void setConsolePosition(ConsolePosition consolePosition) {
        this.consolePosition = consolePosition;
    }
    
    
}
