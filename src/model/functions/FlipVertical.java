package model.functions;

import model.IModel;
import model.pixel.ColorPixel;

/**
 * Function object class for vertical flips.
 */
public class FlipVertical implements IFunctions {
  @Override
  public IModel apply(IModel model) {
    System.out.println("flippin...");
    ColorPixel[][] copy = model.getCopy();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        copy[row][col] = model.getPixel(model.getRows() - 1 - row, col);
      }
    }
    return model.makeImage(copy);
  }
}
