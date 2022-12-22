package model.functions;

import model.IModel;
import model.kernel.IKernel;
import model.pixel.ColorPixel;

/**
 * Function object class for blurring or sharpening an image.
 */
public class BlurSharpen implements IFunctions {
  private final IKernel kernel;

  /**
   * Constructs the BlurSharpen object.
   *
   * @param kernel the kernel to be used for applying the blur or sharpen
   */
  public BlurSharpen(IKernel kernel) {
    if (kernel == null) {
      throw new IllegalArgumentException("null kernel provided");
    }
    this.kernel = kernel;
  }

  @Override
  public IModel apply(IModel model) {
    ColorPixel[][] copy = model.getCopy();
    for (int row = 0; row < model.getRows(); row++) {
      for (int col = 0; col < model.getCols(); col++) {
        copy[row][col] = this.applyKernel(model, row, col);
      }
    }
    return model.makeImage(copy);
  }

  // applies the kernel to a specific pixel in the column
  private ColorPixel applyKernel(IModel model, int row, int col) {
    double redSum = 0;
    double greenSum = 0;
    double blueSum = 0;
    for (int kRow = 0; kRow < this.kernel.getSize(); kRow++) {
      for (int kCol = 0; kCol < this.kernel.getSize(); kCol++) {
        // relative position of kernel center from the pixel it is applied to (index-wise)
        // i.e. what to add to kernel index to get its corresponding pixel array index to overlay
        int xDiff = row - this.kernel.getCenter();
        int yDiff = col - this.kernel.getCenter();
        try {
          redSum = redSum + this.kernel.getEntry(kRow, kCol) *
                  model.getPixel(kRow + xDiff, kCol + yDiff).getRed();
        } catch (IllegalArgumentException e) {
          // if the kernel entry overlays outside the pixel array, dont add to the red sum
        }
        try {
          greenSum += this.kernel.getEntry(kRow, kCol) *
                  model.getPixel(kRow + xDiff, kCol + yDiff).getGreen();
        } catch (IllegalArgumentException e) {
          // if the kernel entry overlays outside the pixel array, dont add to the red sum
        }
        try {
          blueSum += this.kernel.getEntry(kRow, kCol) *
                  model.getPixel(kRow + xDiff, kCol + yDiff).getBlue();
        } catch (IllegalArgumentException e) {
          // if the kernel entry overlays outside the pixel array, dont add to the red sum
        }
      }
    }
    return new ColorPixel((int) redSum, (int) greenSum, (int) blueSum,
            model.getPixel(row, col).getAlpha());

    // needs model as an input because here we need to access the neighboring pixels as well
    // do try catch stuff in case the part of the kernel overlays outside the pixel array
    // (if so, that theoretical pixel results in a 0 value we add to the result of
    // blurring/sharpening the given pixel... do math and multiply overlaying entries and add to
    // the sum for those that overlay inside the pixel array)
  }
}
