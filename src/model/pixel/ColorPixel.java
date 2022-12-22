package model.pixel;

import java.awt.Color;
import java.util.Objects;

/**
 * Specific class implementation of a pixel that is represented with a color.
 */
public class ColorPixel {
  private final Color colors;

  /**
   * Constructs a pixel given a specific rgba value.
   *
   * @param pixel the rgba value for the color
   */
  public ColorPixel(int pixel) {
    this.colors = new Color(pixel, true);
  }

  /**
   * Constructs a pixel with given rgba values (clamped to 0-255). Converts those rgba.
   *
   * @param red   red value
   * @param green green value
   * @param blue  blue value
   * @param alpha alpha value
   */
  public ColorPixel(int red, int green, int blue, int alpha) {
    // clamp rgb values between 0-255

    if (red > 255) {
      red = 255;
    } else if (red < 0) {
      red = 0;
    }
    // otherwise, dont change red
    if (green > 255) {
      green = 255;
    } else if (green < 0) {
      green = 0;
    }
    // otherwise, dont change green
    if (blue > 255) {
      blue = 255;
    } else if (blue < 0) {
      blue = 0;
    }
    if (alpha > 1) {
      alpha = 1;
    } else if (alpha < 0) {
      alpha = 0;
    }
    // otherwise, dont change blue
    this.colors = new Color(red, green, blue, alpha);
  }

  /**
   * Getter for the red color value.
   *
   * @return the red component of the given pixel.
   */
  public int getRed() {
    return this.colors.getRed();
  }

  /**
   * Getter for the green color value.
   *
   * @return the green component of a given pixel.
   */
  public int getGreen() {
    return this.colors.getGreen();
  }

  /**
   * Getter for the blue color value.
   *
   * @return the blue component of a given pixel.
   */
  public int getBlue() {
    return this.colors.getBlue();
  }

  public int getAlpha() {
    return this.colors.getAlpha();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ColorPixel)) {
      return false;
    }

    ColorPixel other = (ColorPixel) o;
    return this.colors.equals(other.colors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.colors);
  }
}
