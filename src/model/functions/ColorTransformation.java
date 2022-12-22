package model.functions;

import model.IModel;
import model.pixel.ColorPixel;

/**
 * Function object class for performing color transformations.
 */
public class ColorTransformation implements IFunctions {
  private double[][] matrix; // 3x3

  /**
   * Constructs the ColorTransformation object.
   *
   * @param matrix the matrix used for applying the color transformation
   * @throws IllegalArgumentException if the matrix is null or does not have 3x3 dimensions
   */
  public ColorTransformation(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("input matrix is null");
    }
    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("invalid matrix size; must be 3x3");
    }
    this.matrix = matrix;
  }

  @Override
  public IModel apply(IModel model) {
    // do the matrix multiplication between the matrix and the rgb values of each pixel
    ColorPixel[][] copy = model.getCopy();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        ColorPixel pixel = model.getPixel(row, col);
        double red = this.matrix[0][0] * pixel.getRed()
                + this.matrix[0][1] * pixel.getGreen()
                + this.matrix[1][2] * pixel.getBlue();
        double green = this.matrix[1][0] * pixel.getRed()
                + this.matrix[1][1] * pixel.getGreen()
                + this.matrix[1][2] * pixel.getBlue();
        double blue = this.matrix[2][0] * pixel.getRed()
                + this.matrix[2][1] * pixel.getGreen()
                + this.matrix[2][2] * pixel.getBlue();
        // rgb values rounded down
        copy[row][col] = new ColorPixel((int) red, (int) green,
                (int) blue, model.getPixel(row, col).getAlpha());

      }
    }
    return model.makeImage(copy);
  }
}
