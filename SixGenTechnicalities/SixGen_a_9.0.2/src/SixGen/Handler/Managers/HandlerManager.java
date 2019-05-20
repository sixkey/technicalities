/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.Handler.Managers;

import SixGen.GameObject.GameObject;
import java.awt.Graphics2D;

/**
 *
 * @author filip
 */
public interface HandlerManager {
    public void tick(GameObject[] tickObjects);
    public void render(Graphics2D g);
}
