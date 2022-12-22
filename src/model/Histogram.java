package model;

import model.pixel.ColorPixel;

/**
 * Visual representation of the red, green, blue, and intensity components of an image.
 */
public class Histogram {
  private final int[] redFrequency;
  private final int[] greenFrequency;
  private final int[] blueFrequency;
  private final int[] intensityFrequency;
  private double maxRed;
  private double maxGreen;
  private double maxBlue;
  private final int maxVal;
  private double maxIntensity;

  /**
   * Constructs the histogram.
   *
   * @param image the image to be represented as a histogram
   */
  public Histogram(ViewImage image) {
    this.maxVal = image.getMaxVal();
    maxRed = 0;
    maxBlue = 0;
    maxGreen = 0;
    maxIntensity = 0;
    redFrequency = new int[this.maxVal + 1];
    greenFrequency = new int[this.maxVal + 1];
    blueFrequency = new int[this.maxVal + 1];
    intensityFrequency = new int[this.maxVal + 1];

    for (int i = 0; i < image.getMaxVal(); i++) {
      redFrequency[i] = 0;
      greenFrequency[i] = 0;
      blueFrequency[i] = 0;
      intensityFrequency[i] = 0;
    }

    for (int row = 0; row < image.getWidth(); row++) {
      for (int col = 0; col < image.getHeight(); col++) {
        ColorPixel pixel = image.getPixel(row, col);
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        int intensity = (red + green + blue) / 3;
        if (red > maxRed) {
          maxRed = red;
        }
        if (green > maxGreen) {
          maxGreen = green;
        }
        if (blue > maxBlue) {
          maxBlue = blue;
        }
        if (intensity > maxIntensity) {
          maxIntensity = intensity;
        }
        redFrequency[red]++;
        greenFrequency[green]++;
        blueFrequency[blue]++;
        intensityFrequency[intensity]++;
      }
    }
  }

  public int getSize() {
    return this.maxVal;
  }

  /**
   * Gets the value at a position.
   * @param pos the position
   * @param type the type of histogram
   * @return the value at the position
   * @throws IllegalArgumentException if the type is not r/g/b/intensity
   */
  public int getValueAt(int pos, String type) throws IllegalArgumentException {
    if (pos < 0 || pos > getSize()) {
      throw new IllegalArgumentException(
              "Invalid position. Must be between 0 and the maximum value.");
    }
    switch (type) {
      case "red":
        return (int) ((redFrequency[pos] / maxRed) * 100);
      case "green":
        return (int) ((greenFrequency[pos] / maxGreen) * 100);
      case "blue":
        return (int) ((blueFrequency[pos] / maxBlue) * 100);
      case "intensity":
        return (int) ((intensityFrequency[pos] / maxIntensity) * 100);
      default:
        throw new IllegalArgumentException("Invalid type request.");
    }
  }
}
