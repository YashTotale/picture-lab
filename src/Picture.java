package src;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
  /**
   * Constructor that takes no arguments
   */
  public Picture() {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor
     */
    super();
  }

  /**
   * Constructor that takes a file name and creates the picture
   *
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   *
   * @param height the height of the desired picture
   * @param width  the width of the desired picture
   */
  public Picture(int height, int width) {
    // let the parent class handle this width and height
    super(width, height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   *
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }

  /**
   * Constructor that takes a buffered image
   *
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }

  public static void main(String[] args) {
    PictureTester.main(args);
  }

  /**
   * Method to return a string with information about this picture.
   *
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() +
      " height " + getHeight()
      + " width " + getWidth();
    return output;

  }

  public void negate() {
    Pixel[][] pixels = super.getPixels2D();

    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setBlue(255 - pixelObj.getBlue());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setRed(255 - pixelObj.getRed());
      }
    }
  }

  /**
   * converts a color image into grayscale.  There are many algorithms
   * for this.  The most common is to find the mean of the red, green
   * and blue components and set each component to that average
   */
  public void grayscale() {
    Pixel[][] pixels = super.getPixels2D();

    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        int gray = (int) pixelObj.getAverage();
        pixelObj.setBlue(gray);
        pixelObj.setGreen(gray);
        pixelObj.setRed(gray);
      }
    }
  }

  public void mirrorVertical() {
    Pixel[][] pixels = this.getPixels2D();

    for(int r = 0; r < pixels.length / 2; r++) {
      int opposite = pixels.length - r - 1;
      for(int c = 0; c < pixels[r].length; c++) {
        Pixel temp = pixels[r][c];
        pixels[r][c].setColor(pixels[opposite][c].getColor());
        pixels[opposite][c].setColor(temp.getColor());
      }
    }
  }

  /**
   * copy from the passed fromPic to the
   * specified startRow and startCol in the
   * current picture
   *
   * @param fromPic  the picture to copy from
   * @param startRow the start row to copy to
   * @param startCol the start col to copy to
   */
  public void copy(Picture fromPic,
                   int startRow, int startCol) {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow;
         fromRow < fromPixels.length && toRow < toPixels.length;
         fromRow++, toRow++) {
      for (int fromCol = 0, toCol = startCol;
           fromCol < fromPixels[0].length &&
             toCol < toPixels[0].length;
           fromCol++, toCol++) {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }
  }

  /**
   * Method to create a collage of several pictures
   */
  public void createCollage() {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1, 0, 0);
    this.copy(flower2, 100, 0);
    this.copy(flower1, 200, 0);
    Picture flowerNoBlue = new Picture(flower2);

    this.copy(flowerNoBlue, 300, 0);
    this.copy(flower1, 400, 0);
    this.copy(flower2, 500, 0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }

  /**
   * Method to show large changes in color
   * This method traverses this picture and changes to pixels to
   * black and white, depending on the color to each pixel's right.
   * If the color change is large, then the pixel on the left is set to
   * black, otherwise, if the color is close, then the pixel is set to
   * white. The result is an image with black pixels where it detected
   * a significant change in color.
   *
   * @param edgeDist the distance for finding edges
   */
  public void edgeDetection(int edgeDist) {
    Pixel leftPixel = null;

    Pixel[][] pixels = this.getPixels2D();

    for (Pixel[] rowArray : pixels) {
      for(int c = 0; c < rowArray.length; c++) {
        Pixel pixel = rowArray[c];
        if(leftPixel != null) {
          double change = pixel.colorDistance(leftPixel.getColor());
          if(change > edgeDist) {
            leftPixel.setColor(Color.BLACK);
          } else {
            leftPixel.setColor(Color.WHITE);
          }
        }
        leftPixel = pixel;
      }
    }
  }
}
