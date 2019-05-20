package SixGen.SixMath;

public class SixVector {
    private Object values[][];
    private int width;
    private int height;
    
    public SixVector(int width, int height) {
        values = new Object[height][];
        this.width = width;
        this.height = height;
        for(int i = 0; i < values.length; i++) { 
            values[i] = new Object[width];
        }
    }
    
    public SixVector(Object values[][]) { 
        this.width = values[0].length;
        this.height = values.length;
        this.values = values;
    }
    
    public void setValue(int x, int y, Object o) { 
        values[y][x] = o;
    }
    
    public void setRow(int y, Object[] v) { 
        for(int i = 0; i < Math.max(v.length, width); i++) { 
            values[y][i] = v[i];
        }
    }
    
    public Object getValue(int x, int y) { 
        return values[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
