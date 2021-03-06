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
    super();
  }

  /**
   * Constructor that takes a file name and creates the picture
   *
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   *
   * @param height the height of the desired picture
   * @param width  the width of the desired picture
   */
  public Picture(int height, int width) {
    super(width, height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   *
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture) {
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
    Pixel[] pixels = this.getPixels();

    for (Pixel pixel : pixels) {
      pixel.setBlue(255 - pixel.getBlue());
      pixel.setGreen(255 - pixel.getGreen());
      pixel.setRed(255 - pixel.getRed());
    }
  }

  public void grayscale() {
    Pixel[] pixels = this.getPixels();

    for (Pixel pixel : pixels) {
      int gray = (int) pixel.getAverage();
      pixel.setColor(new Color(gray, gray, gray));
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

  public void createCollage(String[] pictures) {
    for (int i = 0; i < pictures.length; i++) {
      Picture pic = new Picture(pictures[i]);
      int row = (int) (Math.random() * this.getWidth());
      int col = (int) (Math.random() * this.getHeight());
      this.copy(pic, row / 2, col / 2);
    }
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
          if (change > edgeDist) {
            leftPixel.setColor(Color.BLACK);
          } else {
            leftPixel.setColor(Color.WHITE);
          }
        }
        leftPixel = pixel;
      }
    }
  }


  public void pixelate(int blockSize) {
    Pixel[][] pixels = this.getPixels2D();
    int width = pixels.length; // Don't use this.getWidth() because it doesnt work sometimes
    int height = pixels[0].length; // Don't use this.getHeight() because it doesnt work sometimes

    int vertNum = (int) Math.ceil(1.0 * width / blockSize);
    int horNum = (int) Math.ceil(1.0 * height / blockSize);

    // Divide into vertical strips of block size length
    for (int v = 0; v < vertNum; v++) {
      int rowStart = blockSize * v;
      int rowEnd = Math.min(width, rowStart + blockSize);
      // Divide into horizontal strips of block size length
      for (int h = 0; h < horNum; h++) {
        int colStart = blockSize * h;
        int colEnd = Math.min(height, colStart + blockSize);
        int times = (rowEnd - rowStart) * (colEnd - colStart);
        int red = 0;
        int green = 0;
        int blue = 0;

        // Loop through each row in the block
        for (int r = rowStart; r < rowEnd; r++) {
          // Loop through each column of the row in the block
          for (int c = colStart; c < colEnd; c++) {
            red += pixels[r][c].getRed();
            green += pixels[r][c].getGreen();
            blue += pixels[r][c].getBlue();
          }
        }

        // Loop again and set the average color
        for (int r = rowStart; r < rowEnd; r++) {
          for (int c = colStart; c < colEnd; c++) {
            pixels[r][c].setColor(new Color(red / times, green / times, blue / times));
          }
        }
      }
    }
  }

  public void quadrants() {
    Pixel[][] pixels = this.getPixels2D();

    for (int r = 0; r < pixels.length; r++) {
      for (int c = 0; c < pixels[r].length; c++) {
        if (r > pixels.length / 2) {
          if (c > pixels[r].length / 2) {
            pixels[r][c].setBlue(0);
          } else {
            int gray = (int) pixels[r][c].getAverage();
            pixels[r][c].setColor(new Color(gray, gray, gray));
          }
        } else {
          if (c > pixels[r].length / 2) {
            pixels[r][c].setBlue(255 - pixels[r][c].getBlue());
            pixels[r][c].setGreen(255 - pixels[r][c].getGreen());
            pixels[r][c].setRed(255 - pixels[r][c].getRed());
          } else {
            pixels[r][c].setGreen(0);
            pixels[r][c].setRed(0);
          }
        }
      }
    }
  }

  public void stripes(int stripeSize) {
    Pixel[][] pixels = this.getPixels2D();
    String[] methods = {"negate", "grayscale", "onlyRed"};

    for (int r = 0; r < pixels.length; r++) {
      for (int c = 0; c < pixels[r].length; c++) {
        int index = ((c + r) / stripeSize) % methods.length;
        String method = methods[index];
        switch (method) {
          case "negate": {
            pixels[r][c].setBlue(255 - pixels[r][c].getBlue());
            pixels[r][c].setGreen(255 - pixels[r][c].getGreen());
            pixels[r][c].setRed(255 - pixels[r][c].getRed());
            break;
          }
          case "grayscale": {
            int gray = (int) pixels[r][c].getAverage();
            pixels[r][c].setColor(new Color(gray, gray, gray));
            break;
          }
          case "onlyRed": {
            pixels[r][c].setBlue(0);
            pixels[r][c].setGreen(0);
            break;
          }
        }
      }
    }
  }

  public Color getAverageForColumn(int col) {
    Pixel[][] pixels = this.getPixels2D();
    int red = 0;
    int green = 0;
    int blue = 0;

    for (Pixel[] rowArray : pixels) {
      Pixel pixel = rowArray[col];
      red += pixel.getRed();
      green += pixel.getGreen();
      blue += pixel.getBlue();
    }

    return new Color(red / pixels.length, green / pixels.length, blue / pixels.length);
  }

  public Color getAverageForRow(int row) {
    Pixel[][] pixels = this.getPixels2D();
    int red = 0;
    int green = 0;
    int blue = 0;

    for (Pixel pixel : pixels[row]) {
      red += pixel.getRed();
      green += pixel.getGreen();
      blue += pixel.getBlue();
    }

    return new Color(red / pixels[row].length, green / pixels[row].length, blue / pixels[row].length);
  }

  public int getCountColorOverValue(String color, int value) {
    int count = 0;

    Pixel[] pixels = this.getPixels();

    for (Pixel pixel : pixels) {
      switch (color) {
        case "red": {
          if (pixel.getRed() > value) count++;
          break;
        }
        case "green": {
          if (pixel.getGreen() > value) count++;
          break;
        }
        case "blue": {
          if (pixel.getBlue() > value) count++;
          break;
        }
      }
    }

    return count;
  }

  public void clearColorOverValue(String color, int value) {
    Pixel[] pixels = this.getPixels();

    for (Pixel pixel : pixels) {
      switch (color) {
        case "red": {
          if (pixel.getRed() > value) pixel.setRed(0);
          break;
        }
        case "green": {
          if (pixel.getGreen() > value) pixel.setGreen(0);
          break;
        }
        case "blue": {
          if (pixel.getBlue() > value) pixel.setBlue(0);
          break;
        }
      }
    }
  }
}
