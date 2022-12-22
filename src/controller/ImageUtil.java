package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import model.IModel;
import model.Image;
import model.pixel.ColorPixel;


/**
 * This class contains utility methods to read an image from file and print its contents.
 */
public class ImageUtil {

  /**
   * Loads a file given a file name. if the file is ppm load using ppm method, otherwise load
   * using generic save method
   *
   * @param filename file path to load
   * @return returns an IModel version of loaded image
   * @throws IOException thrown if given invalid file path
   */
  public static IModel load(String filename) throws IOException {
    String[] getExtension = filename.split("\\.");
    String extension = getExtension[getExtension.length - 1];
    if (extension.equals("ppm")) { // ppm filetype
      return loadPPM(filename);
    } else { // non-ppm supported filetype
      return loadOther(filename);
    }
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  private static IModel loadPPM(String filename) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));

      StringBuilder builder = new StringBuilder();
      //read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }

      //now set up the scanner to read from the string we just built
      sc = new Scanner(builder.toString());

      String token;

      token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
      }
      int cols = sc.nextInt();
      System.out.println("Width of image: " + cols);
      int rows = sc.nextInt();
      System.out.println("Height of image: " + rows);
      int maxValue = sc.nextInt();
      System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

      ColorPixel[][] pixels = new ColorPixel[rows][cols];

      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();

          // build pixel in the array of pixels
          pixels[row][col] = new ColorPixel(r, g, b, 1);
        }
      }
      IModel model = new Image(pixels);
      return model;

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
  }


  // load for non-ppm supported filetypes
  private static IModel loadOther(String filename) throws IOException {
    try {
      File file = new File(filename);
      BufferedImage buffer = ImageIO.read(file);
      IModel image = new Image(buffer.getWidth(), buffer.getHeight(), 255);
      for (int row = 0; row < buffer.getWidth(); row++) {
        for (int col = 0; col < buffer.getHeight(); col++) {
          image.setPixelAt(col, row, new ColorPixel(buffer.getRGB(row, col)));
        }
      }
      System.out.println("Width of image: " + buffer.getWidth());
      System.out.println("Height of image: " + buffer.getHeight());
      return image;
    } catch (IOException e) {
      throw new IllegalArgumentException("Error thrown while trying to read file. " +
              "Check file construction");
    }
  }

  /**
   * Saves an image (model to filename).
   * @param filename the location to save to
   * @param model the model to save
   * @throws IllegalArgumentException when a file cannot be made with the given name
   */
  public static void save(String filename, IModel model) throws IllegalArgumentException {
    System.out.println(filename);
    try {
      String[] getExtension = filename.split("\\.");
      String extension = getExtension[getExtension.length - 1];
      File file = new File(filename);
      if (extension.equals("ppm")) {
        PrintWriter outFile = new PrintWriter(file);
        // header
        outFile.println("P3");
        outFile.println(model.getCols());
        outFile.println(model.getRows());
        outFile.println(model.getMaxVal());
        // rgb values
        for (int row = 0; row < model.getRows(); row++) {
          for (int col = 0; col < model.getCols(); col++) {
            outFile.println(model.getPixel(row, col).getRed()); // r
            outFile.println(model.getPixel(row, col).getGreen()); // g
            outFile.println(model.getPixel(row, col).getBlue()); // b
          }
        }
        outFile.close();
      }
      BufferedImage buffer = new BufferedImage(model.getCols(), model.getRows(),
              BufferedImage.TYPE_INT_RGB);
      colorBuffer(buffer, model);
      ImageIO.write(buffer, extension, file);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private static void colorBuffer(BufferedImage buffer, IModel model) {
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        Color pixColor = new Color(model.getPixel(row, col).getRed(),
                model.getPixel(row, col).getGreen(), model.getPixel(row,
                col).getBlue(), model.getPixel(row, col).getAlpha());
        int rgb = pixColor.getRGB();
        buffer.setRGB(col, row, rgb);
      }
    }
  }
}
