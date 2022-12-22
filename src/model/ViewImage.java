package model;

import model.pixel.ColorPixel;

/**
 * Interface for a ViewImage, another way of handling an image.
 * Contains basic getter methods for information about an image.
 */
public interface ViewImage {

  /**
   * Makes a view image out of a model.
   * @param image the image to be represented
   * @return a view image version of an image.
   */
  ViewImage getImage(String image);

  /**
   * Getter for the width of the image.
   * @return the width
   */
  int getWidth();

  /**
   * Getter for the height of the image.
   * @return the height
   */
  int getHeight();

  /**
   * Getter for a pixel at a given location.
   * @param x the x location
   * @param y the y location
   * @return the pixel at (x,y)
   */
  ColorPixel getPixel(int x, int y);

  /**
   * Getter for the max val in an image.
   * @return the max val
   */
  int getMaxVal();

  /**
   * Getter for the number of columns in the image.
   * @return the number of columns
   */
  int getCols();

  /**
   * Getter for the number of rows in the image.
   * @return the number of rows
   */
  int getRows();
}
