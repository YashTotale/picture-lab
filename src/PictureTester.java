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
    System.out.println("Testing Picture");
    deleteDirectory();

    section(ZERO_COLORS);
    testZeroBlue();
    testZeroGreen();
    testZeroRed();

    section(ONE_COLOR);
    testKeepOnlyBlue();
    testKeepOnlyRed();
    testKeepOnlyGreen();

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

    // testChromakey();
    // testEncodeAndDecode();  // use png, gif or bmp because of compression
    // testGetCountRedOverValue(250);
    // testSetRedToHalfValueInTopHalf();
    // testClearBlueOverValue(200);
    // Color avgColor = testGetAverageForColumn(pic, col);// specified column
  }

  static String getRandomImage() {
    int random = (int) (PICS.length * Math.random());
    return PICS_INPUT + PICS[random];
  }

  static boolean deleteDirectory() {
    return deleteDirectory(new File(PICS_OUTPUT));
  }

  static boolean deleteDirectory(File dir) {
    File[] allContents = dir.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return dir.delete();
  }

  public static void section(String folder) {
    String[] words = folder.replaceAll("-", " ").replaceAll("/", "").split("\\s");
    for(int i = 0; i < words.length; i++) {
      String word = words[i];
      words[i] = word.substring(0,1).toUpperCase() + word.substring(1);
    }

    String title = String.join(" ", words);
    System.out.println("\n-----");
    System.out.println(title + "\n");
    new File(PICS_OUTPUT + folder).mkdirs();
  }

  public static void writeImage(Picture pic, String path) {
    path = PICS_OUTPUT + path;
    pic.write(path);
    System.out.println("Wrote to: " + path);
  }

  public static void testZeroBlue() {
    System.out.println("Testing Zero Blue!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setBlue(0);
    }

    String title = "zero-blue.jpg";

    writeImage(pic, ZERO_COLORS + title);
  }

  public static void testZeroGreen() {
    System.out.println("Testing Zero Green!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setGreen(0);
    }

    String title = "zero-green.jpg";

    writeImage(pic, ZERO_COLORS + title);
  }

  public static void testZeroRed() {
    System.out.println("Testing Zero Red!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setRed(0);
    }

    String title = "zero-red.jpg";

    writeImage(pic, ZERO_COLORS + title);
  }

  private static void testKeepOnlyBlue() {
    System.out.println("Testing Keep Only Blue!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setGreen(0);
      pixel.setRed(0);
    }


    String title = "only-blue.jpg";

    writeImage(pic, ONE_COLOR + title);
  }

  private static void testKeepOnlyGreen() {
    System.out.println("Testing Keep Only Green!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setBlue(0);
      pixel.setRed(0);
    }

    String title = "only-green.jpg";

    writeImage(pic, ONE_COLOR + title);
  }

  private static void testKeepOnlyRed() {
    System.out.println("Testing Keep Only Red!");
    Picture pic = new Picture(IMAGE);

    Pixel[] pixels = pic.getPixels();
    for (Pixel pixel : pixels) {
      pixel.setGreen(0);
      pixel.setBlue(0);
    }

    String title = "only-red.jpg";

    writeImage(pic, ONE_COLOR + title);
  }

  private static void testNegate() {
    System.out.println("Testing Negate!");
    Picture pic = new Picture(IMAGE);
    pic.negate();

    String title = "negate.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  private static void testGrayscale() {
    System.out.println("Testing Grayscale!");
    Picture pic = new Picture(IMAGE);
    pic.grayscale();

    String title = "grayscale.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  public static void testEdgeDetection() {
    System.out.println("Testing Edge Detection!");
    Picture pic = new Picture(IMAGE);
    pic.edgeDetection(20);

    String title = "edge-detection.jpg";
    writeImage(pic, COLOR_MODIFICATIONS + title);
  }

  public static void testMirrorVertical() {
    System.out.println("Testing Mirror Vertical!");
    Picture pic = new Picture(IMAGE);
    pic.mirrorVertical();

    String title = "mirror-vertical.jpg";
    writeImage(pic, ORIENTATION_MODIFICATIONS + title);
  }

  public static void testCollage() {
    System.out.println("Testing Collage!");
    Picture pic = new Picture(PICS_INPUT + "640x480.jpg");

    String[] pictures = new String[10];
    for (int i = 0; i < pictures.length; i++) {
      pictures[i] = getRandomImage();
    }
    pic.createCollage(pictures);

    String title = "collage.jpg";
    writeImage(pic, MISC + title);
  }


  /*so, you have a choice for this one and the methods that follow.  You can write the
   * code in the methods below or you can add functionality to the picture class.  Sometimes
   * it makes sense to add it to the class for reusability reasons.  Sometimes it is too unique
   * a need to add to the class.
   */

  // So, you can create a Picture Object and find the average value of
  // the component in that column
  private static Color testGetAverageForColumn(Picture pic, int col) {
    Color avg = null;

    return avg;
  }

  // so for this one, any pixels that have blue over a certain value are set
  // to no blue at all.  Or for a different effect, have those pixels set to black.
  private static void testClearBlueOverValue(int i) {


  }

  // goes to each pixel in the top half and cuts the red component in half
  // So, bottom half of pic should look normal
  private static void testSetRedToHalfValueInTopHalf() {


  }

  // displays the number of pixels in the pic that have a red component
  // greater than the specifies int.
  private static void testGetCountRedOverValue(int i) {


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
