package model.functions;

import model.IModel;
import model.pixel.ColorPixel;

/**
 * Function object class for horizontal flips.
 */
public class FlipHorizontal implements IFunctions {
  @Override
  public IModel apply(IModel model) {
    ColorPixel[][] copy = model.getCopy();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        copy[row][col] = model.getPixel(row, model.getCols() - 1 - col);
      }
    }
    return model.makeImage(copy);
  }
}
