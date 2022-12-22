package model.functions;

import java.util.function.Function;

import model.IModel;
import model.pixel.ColorPixel;

/**
 * Function object class for greyscale methods.
 * Depending on the Function passed in, GreyScale can support specific implementation methods
 * such as red-component, green-component, blue-component, value, intensity, and luma.
 */
public class GreyScale implements IFunctions {
  private final Function<ColorPixel, Integer> func;

  /**
   * Constructs the greyscale object.
   * @param func the given function to be used to create the greyscale.
   */
  public GreyScale(Function<ColorPixel, Integer> func) {
    this.func = func;
  }

  @Override
  public IModel apply(IModel model) {
    ColorPixel[][] copy = model.getCopy();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        int val = func.apply(model.getPixel(row,col));
        copy[row][col] = new ColorPixel(val, val, val,model.getPixel(row,col).getAlpha());
      }
    }
    return model.makeImage(copy);
  }
}
