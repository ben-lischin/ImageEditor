package model;

import model.functions.IFunctions;
import model.pixel.ColorPixel;

/**
 * Model interface that contains methods to access a model's fields and perform basic actions.
 */
public interface IModel {

  /**
   * Getter for the width of this model.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Getter for the height of this model.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Adds an image (model) associated with its name into the hashmap images.
   * @param name  the input name of the model
   * @param model the model to be stored with the name
   */
  void addImage(String name, IModel model);

  /**
   * Gets the image (model) mapped to the input name in the images hashmap.
   *
   * @param name the name of the model we want to retrieve
   * @return the model associated with the name
   */
  IModel getImage(String name);

  /**
   * Getter for the rows in the 2d array (height of image).
   *
   * @return the number of rows in the IModel's array of Pixels.
   */
  int getRows();

  /**
   * Getter for the columns in the 2d array (width of image).
   *
   * @return the number of columns in the IModel's array of Pixels
   */
  int getCols();

  /**
   * Getter for the maximum value of the model.
   *
   * @return an int of the maxVal of the rgb components of the pixels in the model.
   */
  int getMaxVal();

  /**
   * Getter for a pixel at a given location.
   *
   * @param row the row of the requested pixel.
   * @param col the column of the requested pixel.
   * @return the specified Pixel.
   */
  ColorPixel getPixel(int row, int col);

  /**
   * Creates a copy of the 2D array of Pixels representing the image (of the same dimensions).
   *
   * @return 2D array of Pixels.
   */
  ColorPixel[][] getCopy();

  /**
   * Creates an IModel implementation with the given array of pixels.
   *
   * @param pixels a 2D array of Pixels used to create an IModel.
   * @return a new IModel implementation.
   */
  IModel makeImage(ColorPixel[][] pixels);

  /**
   * Applies a function to an IModel.
   *
   * @param func an IFunction implementation.
   * @return the resulting IModel implementation after performing the given function.
   */
  IModel apply(IFunctions func);

  /**
   * Sets a pixel at a position.
   *
   * @param x     the x position
   * @param y     the y position
   * @param pixel the pixel to set to
   */
  void setPixelAt(int x, int y, ColorPixel pixel);
}
