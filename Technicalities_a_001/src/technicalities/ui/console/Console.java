/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File technicalities.ui.console / ConsoleUi
*  created on 30.5.2019 , 22:04:41 
 */
package technicalities.ui.console;

import SixGen.SixUI.Components.SixLabel;
import SixGen.SixUI.SixUI;
import java.awt.Font;
import technicalities.variables.idls.UIDL;

/**
 *
 * @author filip
 */
public class Console extends SixUI{
    
    private final int lineHeight = 20;
    private final int linesAm;
    
    private static SixLabel[] labels;
    
    public Console(int x, int y, int width, int height) { 
        super(UIDL.console);
        linesAm = Math.floorDiv(height, lineHeight);
        labels = new SixLabel[linesAm];
        for(int i = 0; i < linesAm; i++) { 
            labels[i] = new SixLabel(0, lineHeight * i, width, lineHeight, "");
            labels[i].setTextLocation(TextLocation.left);
            labels[i].setFontSize(15);
            this.addComponent(labels[i]);
        }
        
        setX(x);
        setY(y);
    }
    
    public static void log(String s) { 
        for(int i = 0; i < labels.length; i++) { 
            if(i < labels.length - 1) {
                labels[i].setText(labels[i + 1].getText());
            } else { 
                labels[i].setText(s);
            }
        }
    }
    
}
