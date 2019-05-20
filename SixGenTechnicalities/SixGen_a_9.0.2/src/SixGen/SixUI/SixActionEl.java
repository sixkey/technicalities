/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.SixUI;

import SixGen.Events.ActionElement;

/**
 *
 * @author Filip
 */
public abstract class SixActionEl extends SixAction {
    
    private ActionElement actionElement;
    
    public SixActionEl(ActionElement actionElement) { 
        super();
        this.actionElement = actionElement;
    }
    public SixActionEl(ActionElement actionElement, boolean active) { 
        super(active);
        this.actionElement = actionElement;
    }
    public void method() { 
        method(actionElement);
    }
    public abstract void method(ActionElement actionElement);
        
}
