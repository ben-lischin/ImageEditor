package model;

import model.pixel.ColorPixel;

/**
 * Implementation of a ViewImage to be used in the GUI.
 */
public class ViewImageImpl implements ViewImage {
  private final IModel delegate;

  /**
   * Constructs the view image.
   * @param image the image being represented
   */
  public ViewImageImpl(IModel image) {
    this.delegate = image;
  }

  public ViewImage getImage(String image) {
    return new ViewImageImpl(delegate.getImage(image));
  }

  public int getWidth() {
    return delegate.getCols();
  }

  public int getHeight() {
    return delegate.getRows();
  }

  public ColorPixel getPixel(int x, int y) {
    return delegate.getPixel(y,x);
  }

  public int getMaxVal() {
    return delegate.getMaxVal();
  }

  public int getCols() {
    return delegate.getCols();
  }

  public int getRows() {
    return delegate.getRows();
  }

}
