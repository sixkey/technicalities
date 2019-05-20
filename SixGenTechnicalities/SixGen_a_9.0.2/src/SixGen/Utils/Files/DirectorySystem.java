/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SixGen.Utils.Files;

/**
 *
 * @author Filip
 */
public class DirectorySystem {
    //VARIABLES
    protected DirectoryNode[] nodes;
    protected String relativeText;
    //CONSTRUCTORS
    public DirectorySystem() {
        relativeText = "";
    }
    public void setNodes(DirectoryNode[] nodes) { 
        this.nodes = nodes;
    }
    public DirectoryNode getNode(String title) { 
        for(DirectoryNode n : nodes) { 
            if(n.getTitle().equals(title)) { 
                return n;
            }
        }
        return null;
    }
    public String getPath(String title) { 
        String result = "";
        for(DirectoryNode n : nodes) { 
            if(n.getTitle().equals(title)) { 
                result = n.getPath();
            }
        }
        return relativeText + result;
    }
}
