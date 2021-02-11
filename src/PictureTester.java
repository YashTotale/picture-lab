package src;

import java.awt.*;
import java.io.File;

/**
 * This class contains class (static) methods
 * that will help you test the Picture class
 * methods.  Uncomment the methods and the code
 * in the main to test.  This is a great lesson for learning
 * about 2D arrays and the Color class.
 *
 * @author Barbara Ericson and altered by Richard Hanson
 */
public class PictureTester {
  public static final String PICS_INPUT = System.getProperty("user.dir") + "/images/";
  public static final String PICS_OUTPUT = System.getProperty("user.dir") + "/processed/";

  public static final String[] PICS = new File(PICS_INPUT).list();

  public static final String IMAGE = getRandomImage();

  public static final String ZERO_COLORS = "zero-colors/";
  public static final String ONE_COLOR = "one-color/";
  public static final String COLOR_MODIFICATIONS = "color-modifications/";
  public static final String ORIENTATION_MODIFICATIONS = "orientation-modifications/";
  public static final String MISC = "misc/";


  public static void main(String[] args) {
    deleteDirectory();
    writeImage(new Picture(IMAGE), "original.jpg");

    section(ZERO_COLORS, true);
    testZeroRed();
    testZeroGreen();
    testZeroBlue();

    section(ONE_COLOR);
    testKeepOnlyRed();
    testKeepOnlyGreen();
    testKeepOnlyBlue();

    section(COLOR_MODIFICATIONS);
    testNegate();
    testGrayscale();
    testEdgeDetection();

    section(ORIENTATION_MODIFICATIONS);
    testMirrorVertical();
    // testFaceDetect();
    // testFixUnderwater();
    // testMirrorArms();
    // testMirrorGull();
    // testMirrorDiagonal();
    // testCopy();

    section(MISC);
    testCollage();
    testGetAverageForColumn();
    testGetAverageForRow();
    testGetCountColorsOverValue();
    testClearColorsOverValue();

    // testChromakey();
    // testEncodeAndDecode();  // use png, gif or bmp because of compression
    // testSetRedToHalfValueInTopHalf();
    System.out.println("-----");
  }

  private static String getRandomImage() {
    int random = (int) (PICS.length * Math.random());
    return PICS_INPUT + PICS[random];
  }

  private static boolean deleteDirectory() {
    return deleteDirectory(new File(PICS_OUTPUT));
  }

  private static boolean deleteDirectory(File dir) {
    File[] allContents = dir.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return dir.delete();
  }

  private static String capitalize(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  private static void section(String folder) {
    section(folder, false);
  }

  private static void section(String folder, boolean first) {
    String[] words = folder.replaceAll("-", " ").replaceAll("/", "").split("\\s");
    for (int i = 0; i < words.length; i++) {
      words[i] = capitalize(words[i]);
    }

    String title = String.join(" ", words);
    if(!first) {
      System.out.println("-----");
      System.out.println();
    }
    System.out.println("-----");
    System.out.println("\u001b[1m\u001b[7m" + title + "\u001b[0m");
    new File(PICS_OUTPUT + folder).mkdirs();
  }

  private static void test(String testName) {
    System.out.println();
    System.out.println("\u001b[34;1mTesting " + testName + "!\u001b[0m");
  }

  private static void writeImage(Picture pic, String path) {
    new File(PICS_OUTPUT).mkdirs();
    path = PICS_OUTPUT + path;
    pic.write(path);
    System.out.println("Wrote to: " + path);
  }

  private static void testZeroColors(String color) {
    test("Zero " + capitalize(color));
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      switch (color) {
        case "red": {
          pixel.setRed(0);
          break;
        }
        case "green": {
          pixel.setGreen(0);
          break;
        }
        case "blue": {
          pixel.setBlue(0);
          break;
        }
      }
    }

    String title = "zero-" + color + ".jpg";
    writeImage(pic, ZERO_COLORS + title);
  }

  public static void testZeroRed() {
    testZeroColors("red");
  }

  public static void testZeroGreen() {
    testZeroColors("green");
  }

  public static void testZeroBlue() {
    testZeroColors("blue");
  }

  private static void testKeepOnlyColor(String color) {
    test("Keep Only " + capitalize(color));
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      switch (color) {
        case "red": {
          pixel.setGreen(0);
          pixel.setBlue(0);
          break;
        }
        case "green": {
          pixel.setRed(0);
          pixel.setBlue(0);
          break;
        }
        case "blue": {
          pixel.setRed(0);
          pixel.setGreen(0);
          break;
        }
      }
    }

    String title = "only-" + color + ".jpg";
    writeImage(pic, ONE_COLOR + title);
  }


  public static void testKeepOnlyRed() {
    testKeepOnlyColor("red");
  }

  public static void testKeepOnlyGreen() {
    testKeepOnlyColor("green");
  }

  public static void testKeepOnlyBlue() {
    testKeepOnlyColor("blue");
  }

  public static void testNegate() {
    test("Negate");
    Picture pic = new Picture(IMAGE);
    pic.negate();

    String title = "negate.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  public static void testGrayscale() {
    test("Grayscale");
    Picture pic = new Picture(IMAGE);
    pic.grayscale();

    String title = "grayscale.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  public static void testEdgeDetection() {
    test("Edge Detection");
    Picture pic = new Picture(IMAGE);
    pic.edgeDetection(20);

    String title = "edge-detection.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  public static void testMirrorVertical() {
    test("Mirror Vertical");
    Picture pic = new Picture(IMAGE);
    pic.mirrorVertical();

    String title = "mirror-vertical.jpg";
    writeImage(pic, ORIENTATION_MODIFICATIONS + title);
  }

  public static void testCollage() {
    test("Collage");
    Picture pic = new Picture(PICS_INPUT + "640x480.jpg");

    String[] pictures = new String[10];
    for (int i = 0; i < pictures.length; i++) {
      pictures[i] = getRandomImage();
    }
    pic.createCollage(pictures);

    String title = "collage.jpg";
    writeImage(pic, MISC + title);
  }

  private static void testGetAverageForColumn() {
    test("Get Average For Column");
    Picture pic = new Picture(IMAGE);
    int col = (int) (Math.random() * pic.getWidth());

    Color avg = pic.getAverageForColumn(col);
    System.out.println("Average Color for column " + col + " was " + avg);
  }

  private static void testGetAverageForRow() {
    test("Get Average For Row");
    Picture pic = new Picture(IMAGE);
    int row = (int) (Math.random() * pic.getHeight());

    Color avg = pic.getAverageForRow(row);
    System.out.println("Average Color for row " + row + " was " + avg);
  }

  private static void testGetCountColorsOverValue() {
    test("Get Count Colors Over Value");
    Picture pic = new Picture(IMAGE);

    int value = (int) (Math.random() * 256);

    int redCount = pic.getCountColorOverValue("red", value);
    int greenCount = pic.getCountColorOverValue("green", value);
    int blueCount = pic.getCountColorOverValue("blue", value);

    System.out.println("Number of pixels with red value over " + value + " was " + redCount);
    System.out.println("Number of pixels with green value over " + value + " was " + greenCount);
    System.out.println("Number of pixels with blue value over " + value + " was " + blueCount);
  }

  private static void testClearColorsOverValue() {
    test("Clear Colors Over Value");
    int value = (int) (Math.random() * 256);

    Picture redPic = new Picture(IMAGE);
    Picture greenPic = new Picture(IMAGE);
    Picture bluePic = new Picture(IMAGE);

    redPic.clearColorOverValue("red", value);
    greenPic.clearColorOverValue("green", value);
    bluePic.clearColorOverValue("blue", value);

    writeImage(redPic, MISC + "clear-red-over-" + value + ".jpg");
    writeImage(greenPic, MISC + "clear-green-over-" + value + ".jpg");
    writeImage(bluePic, MISC + "clear-blue-over-" + value + ".jpg");
  }

  // goes to each pixel in the top half and cuts the red component in half
  // So, bottom half of pic should look normal
  private static void testSetRedToHalfValueInTopHalf() {

  }


  /**
   * The method below is really cool!!  Use the following approach:
   * go through the entire Picture (the one to carry the message),
   * and make the red component of every pixel an even number.
   * To do this, mod by 2 and see if remainder>0.  If so, add or
   * subtract one (subtracting is safer. Why?)
   * Then, using a Picture of a message (one color on white background),
   * any pixel from Picture of message that is not white causes you to
   * add one to the corresponding pixel's red component of the
   * encoded picture (the one with all even red components).
   * <p>
   * Then you can send along the encoded picture to someone else and they should
   * be able to decode it by looking for Pixels with odd red components.
   */
  private static void testEncodeAndDecode() {

    Picture before = new Picture("blueMotorcycle.jpg"),
      message = new Picture("msg3.PNG");
    before.explore();
    message.explore();
    // the code below scales the message so that it fits in original image
    double mw = message.getWidth(), pw = before.getWidth(),
      mh = message.getHeight(), ph = before.getHeight();

    if (mw > pw || mh > ph) {
      System.out.println(message.getWidth() + " " + message.getHeight());
      System.out.println("Scaling " + ph / mh + " by " + pw / mw);
      message = message.scale(pw / mw, ph / mh);
      message.explore();
      System.out.println(message.getWidth() + " " + message.getHeight());
    }
    // so now the message is small enough to fit in the image

    // alters the original image in a way it that will be
    // visually indetectable see method documentation below
    normalize(before);

    // now slightly alter pixels in original image embedding the message into
    // the original image in an indetectable way
    hide(before, message);
    before.explore();
    // Examine each pixel to determine if it should be white or black
    decode(before).explore();
  }

  // Visit each pixel in the copy of the specified Picture and if it has an odd
// red component, make the pixel black, ohterwise make it white.
  private static SimplePicture decode(Picture pic) {
    Picture copy = new Picture(pic);


    return copy;
  }

  // visit each pixel in the message.  If the message pixel is pretty close to black
// then change the corresponding Pixel in the pic to be odd.  Thus, when this process
// is complete, the pic will have odd red components where the message is
  private static void hide(Picture pic, Picture msg) {


  }

  // visit each pixel and change any odd red components to even
  private static void normalize(Picture moto) {


  }

  /**
   * chroma key means to superimpose one image over another.  The image to be	drawn over the other one, only draws the pixels that aren't the chroma key The common name for this is green screen
   */

  private static void testChromakey() {

  }

  /**
   * use edge detection and search for an oval... HARD!!!
   */
  private static void testFaceDetect() {

  }

  // copies a picture onto another
  private static void testCopy() {


  }

  // Creates a new Picture that creates a mirror image along one diagonal
  private static void testMirrorDiagonal() {

  }

  private static void testMirrorGull() {
    // creates a mirror image of a bird making image look like bird is over water

  }

  private static void testMirrorArms() {
    // TODO Auto-generated method stub

  }

  /**
   * This method is an effort to make a Picture taken underwater look
   * more like it would be if the water were drained
   */
  private static void testFixUnderwater() {
    Picture pic = new Picture("water.jpg");
    pic.explore();
  }

  /**
   * Checks to see if row and col are within the Picture pic
   *
   * @param pic Picture we are checking
   * @param row Row in pic
   * @param col Col in pic
   * @return true if row and col are valid for pic, false otherwise
   */
  public boolean inbounds(Picture pic, int row, int col) {
    return false;
  }

  /**
   * Uses Chromakey to "paste" front onto back, excluding Colors that are
   * closer to rem than threshold.
   *
   * @param back      The Picture onto which front is pasted
   * @param front     The Picture to be pasted onto back
   * @param rowStart  Row where the top of front is pasted onto back
   * @param colStart  Col where the left side of front is pasted onto back
   * @param rem       Color to use as transparency.  Colors closer than threshold to
   *                  rem are not copied onto back
   * @param threshold Color distance beyond which the colors of front will be
   *                  copied onto back.
   */
  public void chromakey(Picture back, Picture front, int rowStart, int colStart, Color rem, double threshold) {


  }
}
