package SixGen.Utils;

import SixGen.GameObject.GameObject;
import SixGen.Window.SixCanvas;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public abstract class Utils {

    public final int leftButton = 1;
    public final int rightButton = 3;
    public final int middleButton = 2;

    public Utils() {

    }

    public enum Direction {
        left, right, up, down;
    }

    public enum TextLocation {
        center, top, left, right, bottom, topleft, bottomleft;
    }

    public static Point getLineIntersection(Line2D.Double pLine1, Line2D.Double pLine2) {
        Point result = null;

        double s1_x = pLine1.x2 - pLine1.x1,
                s1_y = pLine1.y2 - pLine1.y1,
                s2_x = pLine2.x2 - pLine2.x1,
                s2_y = pLine2.y2 - pLine2.y1,
                s = (-s1_y * (pLine1.x1 - pLine2.x1) + s1_x * (pLine1.y1 - pLine2.y1)) / (-s2_x * s1_y + s1_x * s2_y),
                t = (s2_x * (pLine1.y1 - pLine2.y1) - s2_y * (pLine1.x1 - pLine2.x1)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            result = new Point(
                    (int) (pLine1.x1 + (t * s1_x)),
                    (int) (pLine1.y1 + (t * s1_y)));
        }   // end if

        return result;
    }

    //line going to right is line with 0 angle
    public Line2D.Double getLine(float x, float y, float length, float angle) {
        return new Line2D.Double(x, y, x + Math.sin(angle) * length, y + Math.cos(angle) * length);
    }

    public float getDiagonal(float width, float height) {
        float z = width * width + height * height;
        return (float) Math.sqrt(z);
    }

    public float getDiagonal(GameObject obj1, GameObject obj2) {
        return getDiagonal(obj1.getCenterX(), obj1.getCenterY(), obj2.getCenterX(), obj2.getCenterY());
    }

    public float getDiagonal(float x, float y, float xx, float yy) {
        float diffX = x - xx;
        float diffY = y - yy;
        return getDiagonal(diffX, diffY);
    }

    /**
     * returns angle in degrees top is zero and it goes as a clock
     *
     * @param x
     * @param y
     * @return
     */
    public double getAngle(float x, float y) {
        double result = Math.toDegrees(Math.atan(Math.abs(y) / Math.abs(x)));
        if (x < 0 && y < 0) {
            result = 180 - result;
        }
        if (x <= 0 && y >= 0) {
            result = 180 + result;
        }
        if (x > 0 && y > 0) {
            result = 360 - result;
        }
        result = 90 - result;
        return result;
    }

    public float[] getSidesFromDiagonal(float x, float y, float xx, float yy, float accMiss, float diag) {
        float[] result = new float[2];
        result[0] = 0;
        result[1] = 0;
        float diffX = x - xx;
        float diffY = y - yy;
        if (diffX >= 0 - accMiss && diffX <= accMiss && diffY >= 0 - accMiss && diffY <= accMiss) {
            result[0] = 0;
            result[1] = 0;
            return result;
        } else if (diffY >= 0 - accMiss && diffY <= accMiss) {
            if (diffX < 0) {
                result[0] = diag;
            } else {
                result[0] = -1 * diag;
            }
            result[1] = 0;
            return result;
        } else if (diffX >= 0 - accMiss && diffX <= accMiss) {
            result[0] = 0;
            if (diffY < 0) {
                result[1] = diag;
            } else {
                result[1] = -1 * diag;
            }
            return result;
        } else {
            float finalNumber;
            float diffRatio;
            float diffXX;
            float diffYY;
            //@getVels#getDiff
            if (diffX > 0) {
                diffXX = diffX;
            } else {
                diffXX = diffX * -1;
            }
            if (diffY > 0) {
                diffYY = diffY;
            } else {
                diffYY = diffY * -1;
            }
            //@getVels#getDiffRatio
            if (diffXX > diffYY) {
                diffRatio = diffX / diffY;
            } else {
                diffRatio = diffY / diffX;
            }
            //@getVels#getFinalSpeedUnit
            if (diffRatio != 0) {
                finalNumber = (float) Math.sqrt(((diag * diag) / (diffRatio * diffRatio + 1)));
                //@getVels#setResult
                if (diffXX > diffYY) {
                    result[0] = diffRatio * finalNumber;
                    result[1] = finalNumber;
                } else {
                    result[1] = diffRatio * finalNumber;
                    result[0] = finalNumber;
                }
                if (diffX > 0) {
                    if (result[0] > 0) {
                        result[0] *= -1;
                    }
                } else if (result[0] < 0) {
                    result[0] *= -1;
                }
                if (diffY > 0) {
                    if (result[1] > 0) {
                        result[1] *= -1;
                    }
                } else if (result[1] < 0) {
                    result[1] *= -1;
                }
            }
        }
        //@getVels#return
        return result;

    }

    public void antiAliasing(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public float[] getSidesFromDiagonal(float diffX, float diffY, float accMiss, float diag) {
        float[] result = new float[2];
        result[0] = 0;
        result[1] = 0;
        if (diffX >= 0 - accMiss && diffX <= accMiss && diffY >= 0 - accMiss && diffY <= accMiss) {
            result[0] = 0;
            result[1] = 0;
            return result;
        } else if (diffY >= 0 - accMiss && diffY <= accMiss) {
            if (diffX < 0) {
                result[0] = diag;
            } else {
                result[0] = -1 * diag;
            }
            result[1] = 0;
            return result;
        } else if (diffX >= 0 - accMiss && diffX <= accMiss) {
            result[0] = 0;
            if (diffY < 0) {
                result[1] = diag;
            } else {
                result[1] = -1 * diag;
            }
            return result;
        } else {
            float finalNumber;
            float diffRatio;
            float diffXX;
            float diffYY;
            //@getVels#getDiff
            if (diffX > 0) {
                diffXX = diffX;
            } else {
                diffXX = diffX * -1;
            }
            if (diffY > 0) {
                diffYY = diffY;
            } else {
                diffYY = diffY * -1;
            }
            //@getVels#getDiffRatio
            if (diffXX > diffYY) {
                diffRatio = diffX / diffY;
            } else {
                diffRatio = diffY / diffX;
            }
            //@getVels#getFinalSpeedUnit
            if (diffRatio != 0) {
                finalNumber = (float) Math.sqrt(((diag * diag) / (diffRatio * diffRatio + 1)));
                //@getVels#setResult
                if (diffXX > diffYY) {
                    result[0] = diffRatio * finalNumber;
                    result[1] = finalNumber;
                } else {
                    result[1] = diffRatio * finalNumber;
                    result[0] = finalNumber;
                }
                if (diffX > 0) {
                    if (result[0] > 0) {
                        result[0] *= -1;
                    }
                } else if (result[0] < 0) {
                    result[0] *= -1;
                }
                if (diffY > 0) {
                    if (result[1] > 0) {
                        result[1] *= -1;
                    }
                } else if (result[1] < 0) {
                    result[1] *= -1;
                }
            }
        }
        //@getVels#return
        return result;
    }

    public float[] getSidesFromDiagonal(GameObject obj1, GameObject obj2, float accMiss, float diag) {
        float diffX = obj1.getCenterX() - obj2.getCenterX();
        float diffY = obj1.getCenterY() - obj2.getCenterY();
        return getSidesFromDiagonal(diffX, diffY, accMiss, diag);
    }

    //angle 0 = right
    public float[] getSidesFromDiagonal(double angle, float length, float accMiss) {
        float rad = (float) Math.toRadians(angle + 90);
        float diffX = (float) Math.sin(rad) * length;
        float diffY = (float) Math.cos(rad) * length;
        return getSidesFromDiagonal(diffX, diffY, accMiss, length);
    }

    public Velocity getVelFromDiagonal(float x, float y, float xx, float yy, float accMiss, float diag) {
        float[] vels = getSidesFromDiagonal(x, y, xx, yy, accMiss, diag);
        return new Velocity(vels[0], vels[1]);
    }

    public void fillRect(Color c, Rectangle rect, Graphics g, float alpha) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        g2d.setComposite(getAlphaComposite(alpha));
        g2d.fill(rect);
        g2d.setComposite(getAlphaComposite(1f));
    }

    public void drawImage(Graphics g, BufferedImage image, Dimension resizeDims, Rectangle cuttingBounds) {
        int width = (int) (cuttingBounds.getWidth() / resizeDims.getWidth() * image.getWidth());
        int height = (int) (cuttingBounds.getHeight() / resizeDims.getHeight() * image.getHeight());
        g.drawImage(image.getSubimage(0, 0, width, height), (int) cuttingBounds.getX(), (int) cuttingBounds.getY(), (int) cuttingBounds.getWidth(), (int) cuttingBounds.getHeight(), null);
    }

    public void drawString(Graphics g, String s, float x, float y) {
        for (String line : s.split("\n")) {
            g.drawString(line, (int) x, (int) (y += g.getFontMetrics().getHeight()));
        }
    }
    
    public static Font scaleFont(String text, Rectangle rect, Font pFont) {
        BufferedImage b = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.createGraphics();
        g.setFont(pFont);
        float fontSize = 20.0f;
        Font font = pFont;
        font = g.getFont().deriveFont(fontSize);
        if (text != null) {
            String[] s = text.split("\n");
            float width = 0;
            for (int i = 0; i < s.length; i++) {
                float w = g.getFontMetrics(font).stringWidth(s[i]);
                if (w >= width) {
                    width = w;
                }
            }
            fontSize = (rect.width / width) * fontSize;
            return g.getFont().deriveFont(fontSize);
        } else {
            return pFont;
        }
    }

    public void drawCenteredString(Graphics g, String s, Rectangle rect) {

        String[] lines = s.split("\n");
        FontMetrics fm = g.getFontMetrics(g.getFont());

        int fontHeight = (int) getStringBounds(g, s).getHeight();
        int height = fm.getHeight() * (lines.length - 1);

        int x;
        int y;
        for (int i = 0; i < lines.length; i++) {
            x = (int) (rect.getX() + (rect.getWidth() / 2 - fm.stringWidth(lines[i]) / 2));
            y = (int) (rect.getY() + rect.getHeight() / 2 - height / 2) + fontHeight / 2 + fm.getHeight() * i;
            g.drawString(lines[i], x, y);
        }
    }

    public void drawCenteredString(Graphics g, String s, Rectangle rect, float fontSize) {
        Font font = g.getFont();
        g.setFont(g.getFont().deriveFont(fontSize));
        String[] lines = s.split("\n");
        FontMetrics fm = g.getFontMetrics(g.getFont());

        int fontHeight = (int) getStringBounds(g, s).getHeight();
        int height = fm.getHeight() * (lines.length - 1);

        int x;
        int y;
        for (int i = 0; i < lines.length; i++) {
            x = (int) (rect.getX() + (rect.getWidth() / 2 - fm.stringWidth(lines[i]) / 2));
            y = (int) (rect.getY() + rect.getHeight() / 2 - height / 2) + fontHeight / 2 + fm.getHeight() * i;
            g.drawString(lines[i], x, y);
        }
        g.setFont(font);
    }

    public void drawTextWithAlign(Graphics g, String s, Font font, Rectangle rect, int margin, TextLocation textLocation) {
        g.setFont(font);
        int x = 0, y = 0;
        FontMetrics fm = g.getFontMetrics(g.getFont());
        String[] lines = s.split("\n");
        int fontHeight = (int) getStringBounds(g, s).getHeight();
        int height = fm.getHeight() * (lines.length - 1);

        int currentYY = (int) rect.getY();

        if (textLocation == TextLocation.center) {
            drawCenteredString(g, s, rect);
            return;
        } else if (textLocation == TextLocation.left) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + margin);
                y = (int) (rect.getY() + rect.getHeight() / 2 - height / 2) + fontHeight / 2 + fm.getHeight() * i;
                g.drawString(lines[i], x, y);
            }
        } else if (textLocation == TextLocation.topleft) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + margin);
                y = currentYY + (int) getStringBounds(g, "M").getHeight() * 2;
                currentYY = y;
                g.drawString(lines[i], x, y);
            }
        } else if (textLocation == TextLocation.bottomleft) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + margin);
                y = (int) (rect.getY() + rect.getHeight() - height + fm.getHeight() * i - margin);
                g.drawString(lines[i], x, y);
            }
        } else if (textLocation == TextLocation.right) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + (rect.getWidth() - fm.stringWidth(lines[i])) - margin);
                y = (int) (rect.getY() + rect.getHeight() / 2 - height / 2) + fontHeight / 2 + fm.getHeight() * i;
                g.drawString(lines[i], x, y);
            }
        } else if (textLocation == TextLocation.top) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + (rect.getWidth() / 2 - fm.stringWidth(lines[i]) / 2));
                y = (int) (rect.getY() + margin);
                g.drawString(lines[i], x, y);
            }
        } else if (textLocation == TextLocation.bottom) {
            for (int i = 0; i < lines.length; i++) {
                x = (int) (rect.getX() + (rect.getWidth() / 2 - fm.stringWidth(lines[i]) / 2));
                y = (int) (rect.getY() + rect.getHeight() - height + fm.getHeight() * i - margin);
                g.drawString(lines[i], x, y);
            }
        }
    }

    public void drawTextWithAlign(Graphics g, String s, int fontSize, Rectangle rect, int margin, TextLocation textLocation) {
        drawTextWithAlign(g, s, g.getFont().deriveFont(fontSize), rect, margin, textLocation);
    }

    public void drawTextWithAlign(Graphics g, String s, Font font, Rectangle rect, TextLocation textLocation) {
        drawTextWithAlign(g, s, font, rect, 0, textLocation);
    }

    public void drawTextWithAlign(Graphics g, String s, int fontSize, Rectangle rect, TextLocation textLocation) {
        drawTextWithAlign(g, s, g.getFont().deriveFont(fontSize), rect, 0, textLocation);
    }

    public void drawRotatedRect(Graphics g, Rectangle rect, float centerX, float centerY, double angle) {
        Graphics2D g2d = (Graphics2D) g;
        Path2D.Double path = new Path2D.Double();
        path.append(rect, false);
        AffineTransform t = new AffineTransform();
        t.rotate(Math.toRadians(angle), centerX, centerY);
        path.transform(t);
        g2d.fill(path);
    }

//    public void drawRotatedImage(Graphics g , BufferedImage image , float centerX , float centerY , double angle) { 
//        AffineTransform at = new AffineTransform();
//        at.rotate(Math.toRadians(angle) , centerX , centerY);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(image, at, null);
//    }
    public void drawImage(Graphics g, BufferedImage image, Rectangle rect) {
        g.drawImage(image, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), null);
    }

    public BufferedImage getHorSwapedImage(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int xx = 0; xx < image.getWidth(); xx++) {
            for (int yy = 0; yy < image.getHeight(); yy++) {
                result.setRGB(image.getWidth() - xx - 1, yy, image.getRGB(xx, yy));
            }
        }
        return result;
    }

    public BufferedImage getRotatedImage(BufferedImage image, int angle) {

        System.out.println(angle);

        double cos = Math.abs(Math.cos(Math.toRadians(angle)));
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double ow = image.getWidth();
        double oh = image.getHeight();
        int nw = (int) (Math.floor(ow * cos + oh * sin));
        int nh = (int) (Math.floor(oh * cos + ow * sin));
        BufferedImage result = new BufferedImage(nw, nh, image.getType());
        Graphics2D g = (Graphics2D) result.createGraphics();
        AffineTransform t = new AffineTransform();
        t.translate(-ow / 2 + nw / 2, -oh / 2 + nh / 2);
        t.rotate(Math.toRadians(angle), ow / 2, oh / 2);
        g.setTransform(t);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    public static BufferedImage rescale(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();

        return resized;
    }

    public static BufferedImage rescale(BufferedImage image, double ratio) {
        int newWidth = (int) (image.getWidth() * ratio);
        int newHeight = (int) (image.getHeight() * ratio);
        return rescale(image, newWidth, newHeight);
    }

    public BufferedImage rotateLeft(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        for (int xx = 0; xx < image.getWidth(); xx++) {
            for (int yy = 0; yy < image.getHeight(); yy++) {
                result.setRGB(yy, xx, image.getRGB(xx, yy));
            }
        }
        return result;
    }

    public BufferedImage getChangedImage(BufferedImage image, int brigthtnessChange) {
        Random r = new Random();
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int bc;
        if (brigthtnessChange > 0) {
            bc = 0 - brigthtnessChange + r.nextInt(brigthtnessChange * 2);
        } else {
            bc = brigthtnessChange;
        }
        for (int xx = 0; xx < image.getWidth(); xx++) {
            for (int yy = 0; yy < image.getHeight(); yy++) {
                result.setRGB(xx, yy, addBrightness(new Color(image.getRGB(xx, yy)), bc).getRGB());
            }
        }
        return result;
    }

//    public void drawRotatedImage(Graphics g , BufferedImage image , Rectangle bounds , float centerX , float centerY , double angle) { 
//        double newW = bounds.getWidth();
//        double newH = bounds.getHeight();
//        float[] cor = getSidesFromDiagonal(angle, (float)newH / 2 , 3);
//        angle*=-1;
//        AffineTransform at = new AffineTransform();
//        Point origin = new Point((int)centerX , (int)centerY);
//        Point point = extendPoint(origin , (float)newW /2 , angle - 90);
//        float size = (float)(centerY - (bounds.getY() + bounds.getHeight()/2));
//        Point point2 = extendPoint(origin , (float)size , angle*-1);
//        at.translate(centerX + cor[0] + (point.getX() - origin.getX()) + (point2.getX() - origin.getX()), centerY + cor[1] + origin.getY() - point.getY() + (point2.getY() - origin.getY()));
//        at.rotate(Math.toRadians(angle));
//        at.scale(newW / image.getWidth(), newH / image.getHeight());
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawImage(image ,at , null);
//    }
    public void drawRotatedImage(Graphics g, BufferedImage image, Rectangle bounds, float centerX, float centerY, double angle) {
        AffineTransform at = new AffineTransform();
        at.translate(bounds.getX(), bounds.getY());
        at.rotate(Math.toRadians(angle * -1), centerX - bounds.getX(), centerY - bounds.getY());
        at.scale(bounds.getWidth() / image.getWidth(), bounds.getHeight() / image.getHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);
    }

    public Rectangle getRescaledRectangle(Rectangle rect, Rectangle rectTo) {

        Rectangle result;

        if (rect.getWidth() / rectTo.getWidth() > rect.getHeight() / rectTo.getHeight()) {
            result = new Rectangle((int) (rect.getWidth() / rect.getWidth() * rectTo.getWidth()),
                    (int) (rect.getHeight() / rect.getWidth() * rectTo.getWidth()));
        } else {
            result = new Rectangle((int) (rect.getWidth() / rect.getHeight() * rectTo.getHeight()),
                    (int) (rect.getHeight() / rect.getHeight() * rectTo.getHeight()));
        }

        return result;
    }

    public float biggestLine(Graphics g, String s) {
        String[] string = s.split("\n");
        FontMetrics fm = g.getFontMetrics();
        float width = fm.stringWidth(string[0]);
        for (int i = 0; i < string.length; i++) {
            if (fm.stringWidth(string[i]) > width) {
                width = fm.stringWidth(string[i]);
            }
        }
        return width;
    }

    public float biggestLine(Graphics g, Font font, String s) {
        String[] string = s.split("\n");
        FontMetrics fm = g.getFontMetrics(font);
        float width = fm.stringWidth(string[0]);
        for (int i = 0; i < string.length; i++) {
            if (fm.stringWidth(string[i]) > width) {
                width = fm.stringWidth(string[i]);
            }
        }
        return width;
    }

    public float widestString(Graphics g, Font font[], String string[]) {
        float result = biggestLine(g, string[0]);
        for (int i = 0; i < string.length; i++) {
            if (biggestLine(g, string[i]) > result) {
                result = biggestLine(g, font[i], string[i]);
            }
        }
        return result;
    }

    public float widestString(Graphics g, String string[]) {
        float result = biggestLine(g, string[0]);
        for (int i = 0; i < string.length; i++) {
            if (biggestLine(g, string[i]) > result) {
                result = biggestLine(g, string[i]);
            }
        }
        return result;
    }

    public float stringHeight(Graphics g, String s) {
        String[] string = s.split("\n");
        FontMetrics fm = g.getFontMetrics();
        return string.length * fm.getHeight();
    }


    public String splitString(Graphics g, String s, Rectangle r) {
        String[] wholeLines = s.split("\n");
        for (int ii = 0; ii < wholeLines.length; ii++) {
            String[] words = wholeLines[ii].split(" ");
            LinkedList<String> lines = new LinkedList<String>();
            String line = "";

            FontMetrics fm = g.getFontMetrics();
            for (int i = 0; i < words.length; i++) {
                if (fm.stringWidth(line + words[i]) < r.getWidth()) {
                    line += words[i] + " ";
                } else {
                    lines.add(line);
                    line = words[i] + " ";
                }
                if (i == words.length - 1) {
                    lines.add(line);
                }
            }
            String result = "";
            for (int i = 0; i < lines.size(); i++) {
                if (i < lines.size() - 1) {
                    result += lines.get(i) + "\n";
                } else {
                    result += lines.get(i);
                }
            }
            wholeLines[ii] = result;
        }
        String finalResult = "";
        for (int i = 0; i < wholeLines.length; i++) {
            if (i < wholeLines.length - 1) {
                finalResult += wholeLines[i] + "\n";
            } else {
                finalResult += wholeLines[i];
            }
        }
        return finalResult;
    }

    private Rectangle getStringBounds(Graphics g, String str) {
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = g2d.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, 0, 0);
    }

    public int getKeyboardValue(String string) {
        char ch;
        if (string.length() == 1) {
            ch = string.charAt(0);
            if (ch == 'a' || ch == 'A') {
                return KeyEvent.VK_A;
            } else if (ch == 'b' || ch == 'B') {
                return KeyEvent.VK_B;
            } else if (ch == 'c' || ch == 'C') {
                return KeyEvent.VK_C;
            } else if (ch == 'd' || ch == 'D') {
                return KeyEvent.VK_D;
            } else if (ch == 'e' || ch == 'E') {
                return KeyEvent.VK_E;
            } else if (ch == 'f' || ch == 'F') {
                return KeyEvent.VK_F;
            } else if (ch == 'g' || ch == 'G') {
                return KeyEvent.VK_G;
            } else if (ch == 'h' || ch == 'H') {
                return KeyEvent.VK_H;
            } else if (ch == 'i' || ch == 'I') {
                return KeyEvent.VK_I;
            } else if (ch == 'j' || ch == 'J') {
                return KeyEvent.VK_J;
            } else if (ch == 'k' || ch == 'K') {
                return KeyEvent.VK_K;
            } else if (ch == 'l' || ch == 'L') {
                return KeyEvent.VK_L;
            } else if (ch == 'm' || ch == 'M') {
                return KeyEvent.VK_M;
            } else if (ch == 'n' || ch == 'N') {
                return KeyEvent.VK_N;
            } else if (ch == 'o' || ch == 'O') {
                return KeyEvent.VK_O;
            } else if (ch == 'p' || ch == 'P') {
                return KeyEvent.VK_P;
            } else if (ch == 'q' || ch == 'Q') {
                return KeyEvent.VK_Q;
            } else if (ch == 'r' || ch == 'R') {
                return KeyEvent.VK_R;
            } else if (ch == 's' || ch == 'S') {
                return KeyEvent.VK_I;
            } else if (ch == 't' || ch == 'T') {
                return KeyEvent.VK_T;
            } else if (ch == 'u' || ch == 'U') {
                return KeyEvent.VK_U;
            } else if (ch == 'v' || ch == 'V') {
                return KeyEvent.VK_V;
            } else if (ch == 'w' || ch == 'W') {
                return KeyEvent.VK_W;
            } else if (ch == 'x' || ch == 'X') {
                return KeyEvent.VK_X;
            } else if (ch == 'y' || ch == 'Y') {
                return KeyEvent.VK_Y;
            } else if (ch == 'z' || ch == 'Z') {
                return KeyEvent.VK_Z;
            } else {
                return 0;
            }
        } else if (string.equals("spacebar")) {
            return KeyEvent.VK_SPACE;
        } else if (string.equals("backspace")) {
            return KeyEvent.VK_BACK_SPACE;
        } else if (string.equals("shift")) {
            return KeyEvent.VK_SHIFT;
        } else if (string.equals("control")) {
            return KeyEvent.VK_CONTROL;
        } else {
            return 0;
        }
    }

    public float clamp(float min, float max, float value) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public int clamp(int min, int max, int value) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public AlphaComposite getAlphaComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    public Color randomColor() {
        Random r = new Random();
        return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    public Color randomColor(int min, int max) {
        Random r = new Random();
        min = (int) clamp(0, 255, min);
        max = (int) clamp(0, 255, max);
        return new Color(r.nextInt(max - min) + min, r.nextInt(max - min) + min, r.nextInt(max - min) + min);
    }

    public Color addBrightness(Color color, int brightness) {
        return clampedColor(color.getRed() + brightness, color.getGreen() + brightness, color.getBlue() + brightness);
    }

    public Color addRandomBrightness(Color color, int value) {
        if (value < 1) {
            value = 1;
        }
        Random random = new Random();
        return addBrightness(color, random.nextInt(value) - value * 2);
    }

    public Color clampedColor(int r, int g, int b) {
        if (r > 255) {
            r = 255;
        }
        if (r < 0) {
            r = 0;
        }
        if (g > 255) {
            g = 255;
        }
        if (g < 0) {
            g = 0;
        }
        if (b > 255) {
            b = 255;
        }
        if (b < 0) {
            b = 0;
        }
        return new Color(r, g, b);
    }

    public Color clampedColor(float r, float g, float b) {
        if (r > 255) {
            r = 255;
        } else if (r < 0) {
            r = 0;
        }
        if (g > 255) {
            g = 255;
        } else if (g < 0) {
            g = 0;
        }
        if (b > 255) {
            b = 255;
        } else if (b < 0) {
            b = 0;
        }
        return new Color((int) r, (int) g, (int) b);
    }

    public Color clampedColor(int rgb) {
        int r = (rgb << 16) & 0x000000FF;
        int g = (rgb << 8) & 0x000000FF;
        int b = (rgb << 0) & 0x000000FF;
        if (r > 255) {
            r = 255;
        } else if (r < 0) {
            r = 0;
        } else if (g > 255) {
            g = 255;
        } else if (g < 0) {
            g = 0;
        } else if (b > 255) {
            b = 255;
        } else if (b < 0) {
            b = 0;
        }
        return new Color((int) r, (int) g, (int) b);
    }

    public void fill(Graphics g, Shape shape) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(shape);
    }

    public void fill(Graphics g, Color c, Shape shape) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        g2d.fill(shape);
    }

    public void draw(Graphics g, Shape shape) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(shape);
    }

    public void fillPoint(Graphics g, float x, float y, Color c, int size) {
        g.setColor(c);
        g.fillOval((int) (x - size), (int) (y - size), size * 2, size * 2);
    }

    public void drawPoint(Graphics g, float x, float y, Color c, int rad) {
        g.setColor(c);
        g.drawOval((int) (x - rad), (int) (y - rad), rad * 2, rad * 2);
    }

    public boolean doesStringContainString(String task, String find) {
        boolean result = false;
        int counter = 0;
        for (int i = 0; i < task.length(); i++) {
            for (int f = 0; f < find.length(); f++) {
                if (task.charAt(i + f) == find.charAt(f)) {
                    counter++;
                } else {
                    counter = 0;
                    break;
                }
                if (counter == find.length()) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    public int max(int[] n) {
        int max = n[0];
        for (int i = 1; i < n.length; i++) {
            if (n[i] > max) {
                max = n[i];
            }
        }
        return max;
    }

    public int min(int[] n) {
        int min = n[0];
        for (int i = 1; i < n.length; i++) {
            if (n[i] < min) {
                min = n[i];
            }
        }
        return min;
    }

    public float max(float[] n) {
        float max = n[0];
        for (int i = 1; i < n.length; i++) {
            if (n[i] > max) {
                max = n[i];
            }
        }
        return max;
    }

    public float max(float f1, float f2) {
        if (f1 > f2) {
            return f1;
        } else {
            return f2;
        }
    }

    public float min(float[] n) {
        float min = n[0];
        for (int i = 1; i < n.length; i++) {
            if (n[i] < min) {
                min = n[i];
            }
        }
        return min;
    }

    public float min(float f1, float f2) {
        if (f1 > f2) {
            return f2;
        } else {
            return f1;
        }
    }

    public Rectangle getMouseBounds(MouseEvent e) {
        return new Rectangle(e.getX() - 1, e.getY() - 1, 3, 3);
    }

    public Rectangle getRealMouseBounds(MouseEvent e, SixCanvas canv) {
        return new Rectangle((int) canv.getRealMouseX(e) - 1, (int) canv.getRealMouseY(e), 3, 3);
    }

    public Rectangle floatRectangle(float x, float y, float width, float height) {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public void fillOval(Graphics g, Rectangle rect) {
        g.fillOval((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void fillOvalWithFadedBorder(Graphics g, Rectangle rect, int rad, Color color, Color fading) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(fading);
        float temp = (float) 1 / (float) rad;
        g2d.setComposite(getAlphaComposite(temp));
        for (int i = 0; i < rad; i++) {
            g.fillOval((int) rect.getX() - i, (int) rect.getY() - i, (int) rect.getWidth() + 2 * i, (int) rect.getHeight() + 2 * i);
        }
        g2d.setComposite(getAlphaComposite(1));
        g.setColor(color);
        fillOval(g, rect);
    }

    public Color divideColor(Color color, float divider) {
        return clampedColor(color.getRed() / divider, color.getGreen() / divider, color.getBlue() / divider);
    }

    public Color subColor(Color color, float value) {
        return clampedColor(color.getRed() - value, color.getGreen() - value, color.getBlue() - value);
    }

    public Color subColor(Color color, Color color2) {
        return clampedColor(color.getRed() - color2.getRed(), color.getGreen() - color2.getGreen(), color.getBlue() - color2.getBlue());
    }

    public Color multColor(Color color, float value) {
        return clampedColor(color.getRed() * value, color.getGreen() * value, color.getBlue() * value);
    }

    public Color addColorAverage(Color color, Color color2) {
        return clampedColor((color.getRed() + color2.getRed()) / 2, (color.getGreen() + color2.getGreen()) / 2, (color.getBlue() + color2.getBlue()) / 2);
    }

    public Color addColor(Color color, Color color2) {
        return clampedColor(max(color.getRed(), color2.getRed()), max(color.getGreen(), color2.getGreen()), max(color.getBlue(), color2.getBlue()));
    }

    public Velocity velSum(Velocity vel[]) {
        Velocity result = new Velocity(0, 0);
        for (int i = 0; i < vel.length; i++) {
            result.setVelX(result.getVelX() + vel[i].getVelX());
            result.setVelY(result.getVelY() + vel[i].getVelY());
        }
        return result;
    }

    public Velocity velSumAverage(Velocity vel[]) {
        Velocity result = new Velocity(0, 0);
        for (int i = 0; i < vel.length; i++) {
            result.setVelX((result.getVelX() + vel[i].getVelX()) / 2);
            result.setVelY((result.getVelY() + vel[i].getVelY()) / 2);
        }
        return result;
    }

    public float[] getRandomPointOnDiagonal(float centerX, float centerY, float angle, float length) {
        Random r = new Random();
        int nez = (int) (Math.floor(angle / 90)) % 4;
        float y = (int) (Math.tan(Math.toRadians(angle)) * 100);
        float x = 100;
        if (nez == 1 || nez == 2) {
            y *= -1;
            x *= -1;
        }
        float dims[] = getSidesFromDiagonal(x, y, 3, length);
        float yy;
        if (dims[1] < 0) {
            dims[1] *= -1;
        }
        if (dims[0] < 0) {
            dims[0] *= -1;
        }
        if (dims[1] != 0) {
            yy = centerY + r.nextInt((int) dims[1]) - dims[1] / 2;
        } else {
            yy = centerY;
        }
        float xx;
        if (dims[1] != 0) {
            xx = centerX + (dims[0] / dims[1]) * (yy - centerY);;
        } else {
            xx = centerX + r.nextInt((int) dims[0]) - dims[0] / 2;
        }
        float[] result = {xx, yy};
        return result;
    }

    public Point extendPoint(Point origin, Point original, float extender) {
        double x = original.getX() - origin.getX();
        double y = original.getY() - original.getY();
        return new Point((int) (origin.getX() + x * extender), (int) (origin.getY() + y * extender));
    }

    /**
     *
     * @param origin origin point
     * @param length length of the extension
     * @param angle angle (in degrees)
     * @return
     */
    public Point extendPoint(Point origin, float length, double angle) {
        float rad = (float) Math.toRadians(angle + 90);
        float diffX = (float) Math.sin(rad) * length;
        float diffY = (float) Math.cos(rad) * length;
        return new Point((int) (origin.getX() + diffX), (int) (origin.getY() + diffY));
    }
}
