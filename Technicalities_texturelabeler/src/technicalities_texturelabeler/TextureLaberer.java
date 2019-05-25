/*
*  Created by SixKeyStudios
*  Added in project Technicalities_texturelabeler
*  File technicalities_texturelabeler / Technicalities_texturelabeler
*  created on 24.5.2019 , 23:55:37 
 */
package technicalities_texturelabeler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author filip
 */
public class TextureLaberer extends JPanel{

    static int x, y;
    static int width, height;
    
    static int[][] rectInfo;
    static int amountX, amountY;
    
    static BufferedImage texture;
    
    static int zoom = 3;
    
    public static ArrayList<IDInfo> idInfoList;
    
    public static String folder = "C:\\Projects\\Programming\\Java\\Technicalities\\Technicalities_a_001\\res\\textures\\";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        idInfoList = new ArrayList();
        FileManager fm = new FileManager();
        Translator translator = new Translator();
        x = 0;
        y = 0;
        
        JFrame frame = new JFrame();
        frame.setSize(1200, 630);
        frame.setLayout(null);
        
        TextureLaberer tt = new TextureLaberer();
        tt.setBounds(0, 0, 601, 601);
        
        MouseListener ml = new MouseListener() { 
            public void mouseClicked(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == 1) {
                    int ex = Math.floorDiv(e.getX(), width * zoom) + x;
                    int ey = Math.floorDiv(e.getY(), height * zoom) + y;

                    rectInfo[ey][ex] = rectInfo[ey][ex] == 0 ? 1 : rectInfo[ey][ex] == 1 ? 0 : rectInfo[ey][ex];
                }
                
                if(e.getButton() == 3) { 
                    if(e.getX() < 600 / 4) { 
                        x = x <= 0? 0 : x - 1;
                    }
                    if(e.getX() > 600 * 3 / 4) { 
                        x = x >= amountX - 1? amountX - 1 : x + 1;
                    }
                    if(e.getY() < 600 / 4) { 
                        y = y <= 0? 0 : y - 1;
                    }
                    if(e.getY() > 600 * 3 / 4) { 
                        y = y >= amountY - 1? amountY - 1 : y + 1;
                    }
                }
                
                System.out.println(x + " " + y);
                
                tt.repaint();
                
            }
        };
        
        tt.addMouseListener(ml);
        frame.getContentPane().add(tt);
        
        JTextField name = new JTextField("nature_spritesheet.png");
        name.setBounds(650, 50, 500, 50);
        frame.add(name);
        
        JTextField dimensions = new JTextField("32x64");
        dimensions.setBounds(650, 150, 100, 50);
        frame.add(dimensions);
        
        JTextField zoomField = new JTextField("3");
        zoomField.setBounds(800, 150, 100, 50);
        frame.add(zoomField);
        
        ActionListener al = (ActionEvent e) -> {
            System.out.println(name.getText());
            texture = fm.getImageFromFileSource(folder + name.getText());
            tt.repaint();
            
            String words[] = dimensions.getText().split("x");
            width = Integer.parseInt(words[0]);
            height = Integer.parseInt(words[1]);
            
            System.out.println(width + " " + height);
            
            amountX = texture.getWidth() / width;
            amountY = texture.getHeight() / height;
            
            rectInfo = new int[amountY][];
            for(int y = 0; y < amountY; y++) { 
                rectInfo[y] = new int[amountX];
                for(int x = 0; x < amountX; x++) { 
                    rectInfo[y][x] = 0;
                }
            }
            
            zoom = Integer.parseInt(zoomField.getText());
        };
        
        JButton load = new JButton("load");
        load.setBounds(950, 150, 100, 50);
        load.addActionListener(al);
        frame.add(load);
        
        JTextField label = new JTextField("tree");
        label.setBounds(650, 250, 500, 50);
        frame.add(label);
        
        ActionListener set = (ActionEvent e) -> {
            
            ArrayList<Point> points = new ArrayList<>();
            
            if(rectInfo!=null) { 
                for(int y = 0; y < rectInfo.length; y++) { 
                    for(int x = 0; x < rectInfo[y].length; x++) { 
                        if(rectInfo[y][x] == 1) { 
                            points.add(new Point(x, y));
                            rectInfo[y][x] = 2;
                        }
                    }
                }
            }
            
            tt.repaint();
            
            idInfoList.add(new IDInfo(label.getText(), points.toArray(new Point[points.size()])));
        };
        
        JButton setLabel = new JButton("set");
        setLabel.setBounds(650, 350, 100, 50);
        setLabel.addActionListener(set);
        frame.add(setLabel);
        
        ActionListener saveAction = (ActionEvent e) -> {
            translator.translate(folder, idInfoList.toArray(new IDInfo[idInfoList.size()]), name.getText().substring(0, name.getText().length() - 4), dimensions.getText(), amountX, amountY);
        };
        
        JButton saveButton = new JButton("save");
        saveButton.setBounds(650, 450, 500, 50);
        saveButton.addActionListener(saveAction);
        frame.add(saveButton);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        
        
        
    }
    
    @Override
    public void paint(Graphics g) {
        g.fillRect(0, 0, 600, 600);
        g.translate(-this.x * width * zoom, -this.y * height * zoom);
        if(texture!=null) { 
            if(rectInfo!=null) { 
                for(int y = 0; y < rectInfo.length; y++) { 
                    for(int x = 0; x < rectInfo[y].length; x++) { 
                        if(rectInfo[y][x] == 1) { 
                            g.setColor(Color.green);
                            g.fillRect(x * width * zoom, y * height * zoom, width * zoom, height * zoom);
                        } else if(rectInfo[y][x] == 2) { 
                            g.setColor(Color.red);
                            g.fillRect(x * width * zoom, y * height * zoom, width * zoom, height * zoom);
                        }
                    }
                }
            }
            g.drawImage(texture, 0, 0, texture.getWidth() * zoom, texture.getHeight() * zoom, this);
        }
        
    }
}
