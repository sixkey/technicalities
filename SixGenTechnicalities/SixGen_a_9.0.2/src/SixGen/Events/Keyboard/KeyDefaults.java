// Based on SixGenEngine version b1
// Created by SixKeyStudios
package SixGen.Events.Keyboard;

import SixGen.Events.Keyboard.SixAbstractKeyListener.KeyDefaultsType;
import java.awt.event.KeyEvent;

/**
* KeyDefaults
* Abilities : 
*  holds keycodes of default keyboard presets *see KeyDefaultsType for more info
*/
public class KeyDefaults {

   //VARIABLES

   /*  array of keyCodes for default
       0 - left
       1 - right 
       2 - up
       3 - down
   */
   public int button[] = new int[4];

   //CONSTRUCTORS

   public KeyDefaults(KeyDefaultsType KDT) {
       if (KDT == KeyDefaultsType.wasd) {
           button[0] = KeyEvent.VK_A;
           button[1] = KeyEvent.VK_D;
           button[2] = KeyEvent.VK_W;
           button[3] = KeyEvent.VK_S;
       }
       if (KDT == KeyDefaultsType.arrows) {
           button[0] = KeyEvent.VK_LEFT;
           button[1] = KeyEvent.VK_RIGHT;
           button[2] = KeyEvent.VK_UP;
           button[3] = KeyEvent.VK_DOWN;
       }
   }

   //GETTERS SETTERS
   
   public int getLeft() {
       return button[0];
   }
   public int getRight() { 
       return button[1];
   }
   public int getUp() { 
       return button[2];
   }
   public int getDown() { 
       return button[3];
   }
}
