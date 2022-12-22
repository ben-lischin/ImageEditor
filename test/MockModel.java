import java.util.Arrays;

import model.IModel;
import model.functions.IFunctions;
import model.pixel.ColorPixel;

/**
 * Represents a potential IModel used for testing the controller.
 */
public class MockModel implements IModel {
  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public void addImage(String name, IModel model) {
    // do nothing because void
    name = "";
  }

  @Override
  public IModel getImage(String name) {
    return null;
  }

  @Override
  public int getRows() {
    return 0;
  }

  @Override
  public int getCols() {
    return 0;
  }

  @Override
  public int getMaxVal() {
    return 0;
  }

  @Override
  public ColorPixel getPixel(int row, int col) {
    log.append(" " + row + " " + col + " ");
    return null;
  }

  @Override
  public ColorPixel[][] getCopy() {
    return null;
  }

  @Override
  public IModel makeImage(ColorPixel[][] pixels) {
    log.append(" " + Arrays.toString(pixels));
    return new MockModel(log);
  }

  @Override
  public IModel apply(IFunctions func) {
    log.append("function applied");
    return new MockModel(log);
  }

  @Override
  public void setPixelAt(int x, int y, ColorPixel pixel) throws IllegalArgumentException {
    // do nothing
  }
}