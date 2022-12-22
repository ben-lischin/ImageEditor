package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import model.functions.IFunctions;
import model.pixel.ColorPixel;

/**
 * An implementation of the IModel interface which represents a PPM file and has methods which
 * return information about itself.
 */
public class Image implements IModel {
  protected final int rows;
  protected final int cols;
  protected final int maxVal;
  protected final ColorPixel[][] pixels;
  protected Map<String, IModel> images;

  /**
   * Constructs a PPM object given a 2d array of pixels.
   * Calculates rows and columns given the pixels.
   * maxVal is always 255 because that is the maximum for
   * a 8 bit representation of a pixel in a PPM.
   *
   * @param pixels the 2D array of pixels used to create a PPM.
   * @throws IllegalArgumentException if the 2D array is null
   */
  public Image(ColorPixel[][] pixels) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("array of pixels is null");
    }
    this.rows = pixels.length;
    this.cols = pixels[0].length;
    this.pixels = pixels;
    this.maxVal = 255;
    this.images = new HashMap<String, IModel>();
  }

  /**
   * Constructs a PPM object given a width height and maxval.
   * assigns the colors to be generic until changed later in a copy.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param maxVal the maxVal of thhe image
   * @throws IllegalArgumentException if the 2D array is null
   */
  public Image(int width, int height, int maxVal) throws IllegalArgumentException {
    this.pixels = new ColorPixel[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        this.pixels[row][col] = new ColorPixel(random(), random(), random(), 0);
      }
    }
    this.cols = width;
    this.rows = height;
    this.maxVal = maxVal;
    this.images = new HashMap<String, IModel>();
  }

  private int random() {
    Random num = new Random();
    return 0 + num.nextInt(254);
  }

  public void applyEdit(IFunctions func, String image, String newImage) {
    IModel img = images.getOrDefault(image, null);
    images.put(newImage, img.apply(func));
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
    this.images.put(name, model);
  }

  @Override
  public IModel getImage(String name) {
    return this.images.getOrDefault(name, null);
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getCols() {
    return this.cols;
  }

  @Override
  public int getMaxVal() {
    return this.maxVal;
  }

  @Override
  public ColorPixel getPixel(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.getRows() || col < 0 || col >= this.getCols()) {
      throw new IllegalArgumentException("pixel not in array");
    }
    return this.pixels[row][col];
  }

  @Override
  public ColorPixel[][] getCopy() {
    return new ColorPixel[getRows()][getCols()];
  }

  @Override
  public IModel apply(IFunctions func) {
    return func.apply(this);
  }

  @Override
  public void setPixelAt(int row, int col, ColorPixel pixel) throws IllegalArgumentException {
    if (row < 0 || row >= this.getCols() || row < 0 || row >= this.getRows()) {
      throw new IllegalArgumentException("Pixel is outside of image");
    }
    this.pixels[row][col] = pixel;
  }



  @Override
  public IModel makeImage(ColorPixel[][] pixels) {
    return new Image(pixels);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Image)) {
      return false;
    }

    Image other = (Image) o;
    return this.rows == other.rows
            && this.cols == other.cols
            && this.maxVal == other.maxVal
            && this.comparePixelArrays(other);
  }

  // helper for comparing pixel arrays by checking each pixel equality
  // return true if all pixels are the same
  private boolean comparePixelArrays(Image other) {
    int numSame = 0;
    int totalPixels = this.rows * this.cols;
    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        if (this.pixels[row][col].equals(other.pixels[row][col])) {
          numSame += 1;
        }
      }
    }
    return numSame == totalPixels;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pixels[0][0].getRed(),
            this.pixels[this.rows - 1][this.cols - 1].getBlue());
  }

}