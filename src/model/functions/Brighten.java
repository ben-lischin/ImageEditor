package model.functions;

import model.IModel;
import model.pixel.ColorPixel;

/**
 * Function object class for changing the brightness of an image (brighten/darken).
 */
public class Brighten implements IFunctions {
  private final int val;

  /**
   * Constructs the brighten object.
   *
   * @param val the amount to be brightened by (if positive) or darkened by (if negative).
   *            rgb values always between 0-255.
   */
  public Brighten(int val) {
    this.val = val;
  }

  @Override
  public IModel apply(IModel model) {
    ColorPixel[][] copy = model.getCopy();

    // brighten/darken each individual pixel
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        int red = model.getPixel(row, col).getRed() + val;
        int green = model.getPixel(row, col).getGreen() + val;
        int blue = model.getPixel(row, col).getBlue() + val;
        copy[row][col] = new ColorPixel(red, green, blue, model.getPixel(row,col).getAlpha());
      }
    }

    return model.makeImage(copy);
  }
}
