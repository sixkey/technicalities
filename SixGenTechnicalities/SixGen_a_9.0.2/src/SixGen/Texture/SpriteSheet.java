package SixGen.Texture;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	private int columns , rows;
	private int parcelWidth , parcelHeight;
	private String title;
	
	public SpriteSheet(BufferedImage image, String title) { 
		this.image = image;
		this.title = title;
		columns = 1;
		rows = 1;
		updateParcelBounds();
	}
	public SpriteSheet(BufferedImage image , String title, int columns , int rows) { 
		this.image = image;
		this.title = title;
		this.columns = columns;
		this.rows = rows;
		updateParcelBounds();
	}
	private void updateParcelBounds() { 
		parcelWidth = image .getWidth()/columns;
		parcelHeight = image.getHeight()/rows;
	}
        public BufferedImage[] getTextures() { 
            BufferedImage[] textures = new BufferedImage[columns * rows];
            int counter = 0;
            for(int y = 0 ; y < rows ; y++) { 
                for(int x = 0 ; x < columns ; x++) { 
                    textures[counter] = getTexture(x , y);
                    counter++;
                }
            }
            return textures;
        }
        public BufferedImage[] getTextures(int min, int max) { 
            BufferedImage[] textures = new BufferedImage[max - min + 1];
            int counter = 0;
            int c = 0;
            for(int y = 0 ; y < rows ; y++) { 
                for(int x = 0 ; x < columns ; x++) { 
                    if(counter>=min & counter <= max) {
                        textures[c] = getTexture(x , y);
                        c++;
                    }
                    counter++;
                }
            }
            return textures;
        }
	public BufferedImage getTexture(int column, int row) { 
		BufferedImage result = image.getSubimage((int)column * parcelWidth, (int)row*parcelHeight , (int)parcelWidth, (int)parcelHeight);
		return result;
	}
	public BufferedImage getTexture(float x , float y , float width , float height) { 
		BufferedImage result = image.getSubimage((int)x, (int)y, (int)width, (int)height);
		return result;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public void setRows(int rows) { 
		this.rows = rows;
	}
	public int getColumns() { 
		return columns;
	}
	public int getRows() { 
		return rows;
	}
	public void setTitle(String title) { 
		this.title = title;
	}
	public String getTitle()  { 
		return title;
	}
}
