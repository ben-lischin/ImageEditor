import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import controller.ImageUtil;
import model.functions.BlurSharpen;
import model.functions.Brighten;
import model.functions.ColorTransformation;
import model.functions.FlipHorizontal;
import model.functions.FlipVertical;
import model.functions.GreyScale;
import model.IModel;
import model.Image;
import model.kernel.BlurKernel;
import model.kernel.IKernel;
import model.kernel.SharpenKernel;
import model.pixel.ColorPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * contains all tests for the model including failures.
 */
public class ModelTest {
  ColorPixel[][] twoByTwo;
  IModel twoByTwoPPM;
  ColorPixel[][] threeByThree;
  IModel threeByThreePPM;
  ColorPixel[][] threeByTwo;
  IModel threeByTwoPPM;
  ColorPixel[][] copy;
  ColorPixel pixel;
  IModel oldModel;
  IModel newModel;


  @Before
  public void init() {
    this.twoByTwo = new ColorPixel[2][2];
    this.twoByTwo[0][0] = new ColorPixel(255, 255, 255, 1);
    this.twoByTwo[0][1] = new ColorPixel(1, 2, 3, 1);
    this.twoByTwo[1][0] = new ColorPixel(4, 5, 6, 1);
    this.twoByTwo[1][1] = new ColorPixel(7, 8, 9, 1);
    this.twoByTwoPPM = new Image(this.twoByTwo);

    this.threeByThree = new ColorPixel[3][3];
    this.threeByThree[0][0] = new ColorPixel(255, 255, 255, 1);
    this.threeByThree[0][1] = new ColorPixel(1, 2, 3, 1);
    this.threeByThree[0][2] = new ColorPixel(4, 5, 6, 1);
    this.threeByThree[1][0] = new ColorPixel(7, 8, 9, 1);
    this.threeByThree[1][1] = new ColorPixel(10, 11, 12, 1);
    this.threeByThree[1][2] = new ColorPixel(13, 14, 15, 1);
    this.threeByThree[2][0] = new ColorPixel(16, 17, 18, 1);
    this.threeByThree[2][1] = new ColorPixel(19, 20, 21, 1);
    this.threeByThree[2][2] = new ColorPixel(22, 23, 24, 1);
    this.threeByThreePPM = new Image(this.threeByThree);

    this.threeByTwo = new ColorPixel[3][2];
    this.threeByTwo[0][0] = new ColorPixel(255, 255, 255, 1);
    this.threeByTwo[0][1] = new ColorPixel(1, 2, 3, 1);
    this.threeByTwo[1][0] = new ColorPixel(4, 5, 6, 1);
    this.threeByTwo[1][1] = new ColorPixel(7, 8, 9, 1);
    this.threeByTwo[2][0] = new ColorPixel(10, 11, 12, 1);
    this.threeByTwo[2][1] = new ColorPixel(13, 14, 15, 1);
    this.threeByTwoPPM = new Image(this.threeByTwo);
  }

  @Test
  public void testValidConstruction() {
    try {
      this.twoByTwoPPM = new Image(this.twoByTwo);
      assertEquals(2, this.twoByTwoPPM.getRows());
      assertEquals(2, this.twoByTwoPPM.getCols());
      this.threeByThreePPM = new Image(this.threeByThree);
      assertEquals(3, this.threeByThreePPM.getRows());
      assertEquals(3, this.threeByThreePPM.getCols());
      this.threeByTwoPPM = new Image(this.threeByTwo);
      assertEquals(3, this.threeByTwoPPM.getRows());
      assertEquals(2, this.threeByTwoPPM.getCols());
    } catch (IllegalArgumentException e) {
      fail("should not have thrown exception; construction was valid");
    }
  }

  @Test
  public void testInvalidConstruction() {
    try {
      this.twoByTwoPPM = new Image(null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing; successfully threw exception upon construction
    }
  }

  @Test
  public void testGetCopy() {
    copy = this.twoByTwoPPM.getCopy();
    // test dimensions
    assertEquals(2, copy.length);
    assertEquals(2, copy[0].length);
    // test that it didn't creat actual pixels yet
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 2; col++) {
        assertEquals(null, copy[row][col]);
      }
    }

    copy = this.threeByThreePPM.getCopy();
    // test dimensions
    assertEquals(3, copy.length);
    assertEquals(3, copy[0].length);
    // test that it didn't creat actual pixels yet
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        assertEquals(null, copy[row][col]);
      }
    }

    copy = this.threeByTwoPPM.getCopy();
    // test dimensions
    assertEquals(3, copy.length);
    assertEquals(2, copy[0].length);
    // test that it didn't creat actual pixels yet
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 2; col++) {
        assertEquals(null, copy[row][col]);
      }
    }
  }

  @Test
  public void testGetRows() {
    assertEquals(2, this.twoByTwoPPM.getRows());
    assertEquals(3, this.threeByThreePPM.getRows());
    assertEquals(3, this.threeByTwoPPM.getRows());
  }

  @Test
  public void testGetCols() {
    assertEquals(2, this.twoByTwoPPM.getCols());
    assertEquals(3, this.threeByThreePPM.getCols());
    assertEquals(2, this.threeByTwoPPM.getCols());
  }

  @Test
  public void testGetMaxVal() {
    assertEquals(255, this.twoByTwoPPM.getMaxVal());
    assertEquals(255, this.threeByThreePPM.getMaxVal());
    assertEquals(255, this.threeByTwoPPM.getMaxVal());
  }

  @Test
  public void testGetPixel() {
    pixel = new ColorPixel(255, 255, 255, 1);
    assertEquals(pixel, this.twoByTwoPPM.getPixel(0, 0));
    pixel = new ColorPixel(1, 2, 3, 1);
    assertEquals(pixel, this.twoByTwoPPM.getPixel(0, 1));
    pixel = new ColorPixel(4, 5, 6, 1);
    assertEquals(pixel, this.twoByTwoPPM.getPixel(1, 0));
    pixel = new ColorPixel(7, 8, 9, 1);
    assertEquals(pixel, this.twoByTwoPPM.getPixel(1, 1));

  }

  @Test
  public void testApply() {
    assertEquals(twoByTwoPPM.apply(new FlipHorizontal()), new FlipHorizontal().apply(twoByTwoPPM));
    assertEquals(twoByTwoPPM.apply(new FlipVertical()), new FlipVertical().apply(twoByTwoPPM));
    assertEquals(twoByTwoPPM.apply(new Brighten(10)), new Brighten(10).apply(twoByTwoPPM));
    assertEquals(twoByTwoPPM.apply(new GreyScale(p -> p.getRed())), new
            GreyScale(p -> p.getRed()).apply(twoByTwoPPM));
  }

  @Test
  public void testMakeImage() {
    this.oldModel = new Image(new ColorPixel[6][4]);
    this.newModel = this.oldModel.makeImage(this.twoByTwo);
    // check that the new model it made looks identical to this.twoByTwoPPM
    assertEquals(this.twoByTwoPPM, this.newModel);

    this.newModel = this.oldModel.makeImage(this.threeByThree);
    // check that the new model it made looks identical to this.threeByThreePPM
    assertEquals(this.threeByThreePPM, this.newModel);


    this.newModel = this.oldModel.makeImage(this.threeByTwo);
    // check that the new model it made looks identical to this.threeByTwoPPM
    assertEquals(this.threeByTwoPPM, this.newModel);
  }

  @Test
  public void testEquals() {
    ColorPixel[][] twoByTwoNew = new ColorPixel[2][2];
    twoByTwoNew[0][0] = new ColorPixel(255, 255, 255, 1);
    twoByTwoNew[0][1] = new ColorPixel(1, 2, 3, 1);
    twoByTwoNew[1][0] = new ColorPixel(4, 5, 6, 1);
    twoByTwoNew[1][1] = new ColorPixel(7, 8, 9, 1);
    Image twoByTwoPPMNew = new Image(twoByTwoNew);
    assertTrue(twoByTwoPPMNew.equals(this.twoByTwoPPM));
    assertTrue(this.twoByTwoPPM.equals(twoByTwoPPMNew));
    assertEquals(this.twoByTwoPPM, twoByTwoPPMNew);


    assertFalse(this.twoByTwoPPM.equals(this.threeByTwoPPM));
  }

  @Test
  public void testHashCode() {
    assertEquals(8875, this.twoByTwoPPM.hashCode());
    assertEquals(8890, this.threeByThreePPM.hashCode());
    assertEquals(8881, this.threeByTwoPPM.hashCode());
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////
  //////// test the functions that are applied to the model on command in the controller //////////
  //////////////////////////////////////////////////////////////////////////////////////////////////

  @Test
  public void testBlurSharpenApply() {
    ColorPixel[][] testTwoBlur = new ColorPixel[2][2];
    testTwoBlur[0][0] = new ColorPixel(64, 65, 65, 1);
    testTwoBlur[0][1] = new ColorPixel(33, 33, 34, 1);
    testTwoBlur[1][0] = new ColorPixel(33, 34, 34, 1);
    testTwoBlur[1][1] = new ColorPixel(18, 18, 19, 1);
    IModel testTwoBlurPPM = new Image(testTwoBlur);
    IModel blurPPM = new BlurSharpen(new BlurKernel()).apply(twoByTwoPPM);
    assertTrue(testTwoBlurPPM.equals(blurPPM));


    ColorPixel[][] testTwoSharpen = new ColorPixel[2][2];
    testTwoSharpen[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwoSharpen[0][1] = new ColorPixel(67, 69, 70, 1);
    testTwoSharpen[1][0] = new ColorPixel(69, 71, 72, 1);
    testTwoSharpen[1][1] = new ColorPixel(72, 73, 75, 1);
    IModel testTwoSharpenPPM = new Image(testTwoSharpen);
    IModel sharpenPPM = new BlurSharpen(new SharpenKernel()).apply(twoByTwoPPM);
    assertTrue(testTwoSharpenPPM.equals(sharpenPPM));
  }

  @Test
  public void testColorTransformationApply() {
    //sepia
    ColorPixel[][] testSepia = new ColorPixel[2][2];
    testSepia[0][0] = new ColorPixel(255, 255, 238, 1);
    testSepia[0][1] = new ColorPixel(2, 2, 1, 1);
    testSepia[1][0] = new ColorPixel(6, 5, 4, 1);
    testSepia[1][1] = new ColorPixel(10, 9, 7, 1);
    IModel testSepiaPPM = new Image(testSepia);
    double[][] sepiaMatrix = new double[3][3];
    sepiaMatrix[0][0] = .393;
    sepiaMatrix[0][1] = .769;
    sepiaMatrix[0][2] = .189;
    sepiaMatrix[1][0] = .349;
    sepiaMatrix[1][1] = .686;
    sepiaMatrix[1][2] = .168;
    sepiaMatrix[2][0] = .272;
    sepiaMatrix[2][1] = .534;
    sepiaMatrix[2][2] = .131;
    IModel sepiaPPM = new ColorTransformation(sepiaMatrix).apply(twoByTwoPPM);
    assertTrue(testSepiaPPM.equals(sepiaPPM));


    //greyscale (luma)
    ColorPixel[][] testLuma = new ColorPixel[2][2];
    testLuma[0][0] = new ColorPixel(254, 254, 254, 1);
    testLuma[0][1] = new ColorPixel(1, 1, 1, 1);
    testLuma[1][0] = new ColorPixel(4, 4, 4, 1);
    testLuma[1][1] = new ColorPixel(7, 7, 7, 1);
    IModel testLumaPPM = new Image(testLuma);
    double[][] lumaMatrix = new double[3][3];
    lumaMatrix[0][0] = .2126;
    lumaMatrix[0][1] = .7152;
    lumaMatrix[0][2] = .0722;
    lumaMatrix[1][0] = .2126;
    lumaMatrix[1][1] = .7152;
    lumaMatrix[1][2] = .0722;
    lumaMatrix[2][0] = .2126;
    lumaMatrix[2][1] = .7152;
    lumaMatrix[2][2] = .0722;
    IModel lumaPPM = new ColorTransformation(lumaMatrix).apply(twoByTwoPPM);
    System.out.println(lumaPPM.getPixel(1, 0).getRed());
    System.out.println(lumaPPM.getPixel(1, 0).getGreen());
    System.out.println(lumaPPM.getPixel(1, 0).getBlue());
    assertTrue(testLumaPPM.equals(lumaPPM));
  }


  @Test
  public void testBrightenApply() {
    ColorPixel[][] testTwo = new ColorPixel[2][2];
    testTwo[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo[0][1] = new ColorPixel(5, 6, 7, 1);
    testTwo[1][0] = new ColorPixel(8, 9, 10, 1);
    testTwo[1][1] = new ColorPixel(11, 12, 13, 1);
    IModel testPPM = new Image(testTwo);
    IModel brightenedPPM = new Brighten(4).apply(twoByTwoPPM);
    assertTrue(testPPM.equals(brightenedPPM));

    ColorPixel[][] testTwo3 = new ColorPixel[2][2];
    testTwo3[0][0] = new ColorPixel(0, 0, 0, 1);
    testTwo3[0][1] = new ColorPixel(0, 0, 0, 1);
    testTwo3[1][0] = new ColorPixel(0, 0, 0, 1);
    testTwo3[1][1] = new ColorPixel(0, 0, 0, 1);
    IModel testPPM3 = new Image(testTwo3);
    IModel brightenedPPM3 = new Brighten(-255).apply(twoByTwoPPM);
    assertTrue(testPPM3.equals(brightenedPPM3));

    ColorPixel[][] testTwo4 = new ColorPixel[2][2];
    testTwo4[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo4[0][1] = new ColorPixel(255, 255, 255, 1);
    testTwo4[1][0] = new ColorPixel(255, 255, 255, 1);
    testTwo4[1][1] = new ColorPixel(255, 255, 255, 1);
    IModel testPPM4 = new Image(testTwo4);
    IModel brightenedPPM4 = new Brighten(255).apply(twoByTwoPPM);
    assertTrue(testPPM4.equals(brightenedPPM4));
  }

  @Test
  public void testFlipHorizontalApply() {
    ColorPixel[][] testTwo5 = new ColorPixel[2][2];
    testTwo5[0][1] = new ColorPixel(255, 255, 255, 1);
    testTwo5[0][0] = new ColorPixel(1, 2, 3, 1);
    testTwo5[1][1] = new ColorPixel(4, 5, 6, 1);
    testTwo5[1][0] = new ColorPixel(7, 8, 9, 1);
    IModel testPPM5 = new Image(testTwo5);
    IModel flippedPPM = new FlipHorizontal().apply(twoByTwoPPM);
    assertTrue(testPPM5.equals(flippedPPM));

  }

  @Test
  public void testFlipVerticalApply() {
    ColorPixel[][] testTwo6 = new ColorPixel[2][2];
    testTwo6[1][0] = new ColorPixel(255, 255, 255, 1);
    testTwo6[1][1] = new ColorPixel(1, 2, 3, 1);
    testTwo6[0][0] = new ColorPixel(4, 5, 6, 1);
    testTwo6[0][1] = new ColorPixel(7, 8, 9, 1);
    IModel testPPM7 = new Image(testTwo6);
    IModel flippedPPM3 = new FlipVertical().apply(twoByTwoPPM);
    assertTrue(testPPM7.equals(flippedPPM3));
  }


  @Test
  public void testGreyScaleApply() {
    //red
    ColorPixel[][] testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo7[0][1] = new ColorPixel(1, 1, 1, 1);
    testTwo7[1][0] = new ColorPixel(4, 4, 4, 1);
    testTwo7[1][1] = new ColorPixel(7, 7, 7, 1);
    IModel testPPM8 = new Image(testTwo7);
    IModel greyScalePPM = new GreyScale(p -> p.getRed()).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);
    //green
    testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo7[0][1] = new ColorPixel(2, 2, 2, 1);
    testTwo7[1][0] = new ColorPixel(5, 5, 5, 1);
    testTwo7[1][1] = new ColorPixel(8, 8, 8, 1);
    testPPM8 = new Image(testTwo7);
    greyScalePPM = new GreyScale(p -> p.getGreen()).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);
    //blue
    testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo7[0][1] = new ColorPixel(3, 3, 3, 1);
    testTwo7[1][0] = new ColorPixel(6, 6, 6, 1);
    testTwo7[1][1] = new ColorPixel(9, 9, 9, 1);
    testPPM8 = new Image(testTwo7);
    greyScalePPM = new GreyScale(p -> p.getBlue()).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);
    //value
    testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo7[0][1] = new ColorPixel(3, 3, 3, 1);
    testTwo7[1][0] = new ColorPixel(6, 6, 6, 1);
    testTwo7[1][1] = new ColorPixel(9, 9, 9, 1);
    testPPM8 = new Image(testTwo7);
    greyScalePPM =
            new GreyScale(p -> Math.max(p.getRed(),
                    Math.max(p.getGreen(), p.getBlue()))).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);
    //intensity
    testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel(255, 255, 255, 1);
    testTwo7[0][1] = new ColorPixel(2, 2, 2, 1);
    testTwo7[1][0] = new ColorPixel(5, 5, 5, 1);
    testTwo7[1][1] = new ColorPixel(8, 8, 8, 1);
    testPPM8 = new Image(testTwo7);
    greyScalePPM =
            new GreyScale(p -> (p.getRed() + p.getGreen() + p.getBlue()) / 3).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);
    //luma
    // checking that the function is applied correctly, not that the math itself works,
    // so we use the formulas here
    testTwo7 = new ColorPixel[2][2];
    testTwo7[0][0] = new ColorPixel((int) Math.round(.2126 * 255 + .7152 * 255 + .0722 * 255),
            (int) Math.round(.2126 * 255 + .7152 * 255 + .0722 * 255),
            (int) Math.round(.2126 * 255 + .7152 * 255 + .0722 * 255), 1);
    testTwo7[0][1] = new ColorPixel((int) Math.round(.2126 * 1 + .7152 * 2 + .0722 * 3),
            (int) Math.round(.2126 * 1 + .7152 * 2 + .0722 * 3),
            (int) Math.round(.2126 * 1 + .7152 * 2 + .0722 * 3), 1);
    testTwo7[1][0] = new ColorPixel((int) Math.round(.2126 * 4 + .7152 * 5 + .0722 * 6),
            (int) Math.round(.2126 * 4 + .7152 * 5 + .0722 * 6),
            (int) Math.round(.2126 * 4 + .7152 * 5 + .0722 * 6), 1);
    testTwo7[1][1] = new ColorPixel((int) Math.round(.2126 * 7 + .7152 * 8 + .0722 * 9),
            (int) Math.round(.2126 * 7 + .7152 * 8 + .0722 * 9),
            (int) Math.round(.2126 * 7 + .7152 * 8 + .0722 * 9), 1);
    testPPM8 = new Image(testTwo7);
    greyScalePPM = new GreyScale(p -> (int) Math.round((.2126 * p.getRed())
            + (.7152 * p.getGreen()) + (.0722 * p.getBlue()))).apply(twoByTwoPPM);
    assertEquals(testPPM8, greyScalePPM);

    //blur
    ColorPixel testPixel = new ColorPixel(65, 65, 66, 1);
    ColorPixel testPixel2 = new ColorPixel(26, 27, 27, 1);

    IModel blurred3x3 = new BlurSharpen(new BlurKernel()).apply(threeByThreePPM);
    assertEquals(testPixel.getRed(), blurred3x3.getPixel(0, 0).getRed());
    assertEquals(testPixel.getGreen(), blurred3x3.getPixel(0, 0).getGreen());
    assertEquals(testPixel.getBlue(), blurred3x3.getPixel(0, 0).getBlue());
    assertEquals(testPixel2.getRed(), blurred3x3.getPixel(1, 1).getRed());
    assertEquals(testPixel2.getGreen(), blurred3x3.getPixel(1, 1).getGreen());
    assertEquals(testPixel2.getBlue(), blurred3x3.getPixel(1, 1).getBlue());
  }

  @Test
  public void testBlurKernel() {
    // test valid construction constructor 1
    try {
      IKernel kernel1 = new BlurKernel();
    } catch (IllegalArgumentException e) {
      fail("Should not throw an error because this is a valid construction");
    }
    // test invalid construction with even
    try {
      IKernel kernel2 = new BlurKernel(2);
      fail("Should throw an error because this is not a valid construction");
    } catch (IllegalArgumentException e) {
      // do nothing cuz it should fail
    }
    // test valid construction with valid input
    try {
      IKernel kernel3 = new BlurKernel(3);
    } catch (IllegalArgumentException e) {
      fail("Should not throw an error because this is a valid construction");
    }
    // test equality
    IKernel kernel1 = new BlurKernel();
    IKernel kernel3 = new BlurKernel(3);
    assertEquals(kernel1, kernel3);

    // test same hashcode
    assertEquals(kernel1.hashCode(), kernel3.hashCode());

    // test diff hashcode
    IKernel kernel2 = new BlurKernel(5);
    assertFalse(kernel2 == kernel3);

    //test non equality
    assertFalse(kernel2.equals(kernel3));

    // test get size
    assertEquals(3, kernel1.getSize());
    assertEquals(5, kernel2.getSize());
    assertEquals(3, kernel3.getSize());

    // test get center
    assertEquals(1, kernel1.getCenter());
    assertEquals(2, kernel2.getCenter());
    assertEquals(1, kernel3.getCenter());

    // test getEntry
    assertEquals(.25, kernel1.getEntry(1,1), .01);
    assertEquals(.25, kernel2.getEntry(2,2), .01);

  }

  @Test
  public void testSharpenKernel() {
    // test valid construction
    try {
      IKernel kernel1 = new SharpenKernel();
    } catch (IllegalArgumentException e) {
      fail("Should not throw an error because this is a valid construction");
    }

    // test equality
    IKernel kernel1 = new SharpenKernel();
    IKernel kernel3 = new SharpenKernel();
    assertEquals(kernel1, kernel3);
    //NOTE: there is only ever one sharpen kernel so there is no such thing as non equality
    // there is also no such thing as different hashcodes

    //test hashcode
    assertEquals(kernel1.hashCode(),kernel3.hashCode());

    // test get size
    assertEquals(5, kernel1.getSize());
    assertEquals(5, kernel3.getSize());

    // test get center
    assertEquals(2, kernel1.getCenter());
    assertEquals(2, kernel3.getCenter());

    // test getEntry
    assertEquals(1.0, kernel1.getEntry(2,2), .01);
    assertEquals(1.0, kernel1.getEntry(2,2), .01);

  }

  //ImageUtil
  @Test
  public void testLoad() throws IOException {
    // make the file to load
    try {
      File file = new File("load.ppm");
      PrintWriter outFile = new PrintWriter(file);

      // header
      outFile.println("P3");
      outFile.println(2);
      outFile.println(2);
      outFile.println(255);
      // rgb values
      outFile.println(255 + " " + 255 + " " + 255);
      outFile.println(1 + " " + 2 + " " + 3);
      outFile.println(4 + " " + 5 + " " + 6);
      outFile.println(7 + " " + 8 + " " + 9);

      outFile.close();
    } catch (FileNotFoundException e) {
      fail();
    }

    // load the file
    this.newModel = ImageUtil.load("load.ppm");

    // check the model made by the load (should look identical to this.twoByTwoPPM)
    assertEquals(this.twoByTwoPPM, this.newModel);


    //load a non-ppm supported type
    // make the file to load
    try {
      File file = new File("load.ppm");
      PrintWriter outFile = new PrintWriter(file);

      // header
      outFile.println("P3");
      outFile.println(2);
      outFile.println(2);
      outFile.println(255);
      // rgb values
      outFile.println(255 + " " + 255 + " " + 255);
      outFile.println(1 + " " + 2 + " " + 3);
      outFile.println(4 + " " + 5 + " " + 6);
      outFile.println(7 + " " + 8 + " " + 9);

      outFile.close();
    } catch (FileNotFoundException e) {
      fail();
    }

    // load the file
    this.newModel = ImageUtil.load("load.ppm");

    // check the model made by the load (should look identical to this.twoByTwoPPM)
    assertEquals(this.twoByTwoPPM, this.newModel);

    // testing load exception
    try {
      ImageUtil.load("nonexistingimage.png");
      fail("Should not pass because Image does not exist.");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    // testing load exception
    try {
      ImageUtil.load("nonexistingimage.ppm");
      fail("Should not pass because Image does not exist.");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

}