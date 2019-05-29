/*
*  Created by SixKeyStudios
*  Added in project Technicalities
*  File configmanagers / IDNumberPair
*  created on 22.5.2019 , 22:21:49 
 */
package technicalities.configmanagers;

/**
 * IDNumberPair 
 * - wrapper around id and amount information, used in config systems
 * @author filip
 */
public class IDNumberPair {
    
    ////// VARIABLES //////
    
    public final String id;
    public final int amount;
    
    ////// CONSTRUCTORS //////
    
    public IDNumberPair(String id, int amount) { 
        this.id = id;
        this.amount = amount;
    }
}
