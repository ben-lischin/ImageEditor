package model.kernel;

import java.util.Objects;

/**
 * A kernel that will be used for blurring images.
 */
public class BlurKernel extends AKernel {

  /**
   * Constructs a default BlurKernel.
   */
  public BlurKernel() {
    super(3);
  }

  /**
   * Constructs a BlurKernel with input size.
   * @param size the dimension of the kernel
   */
  public BlurKernel(int size) {
    super(size);
  }

  @Override
  protected void makeMatrix() {
    int rowDist;
    int colDist;
    int totalDist;
    for (int row = 0; row < this.size; row++) {
      for (int col = 0; col < this.size; col++) {
        rowDist = Math.abs(this.center - row);
        colDist = Math.abs(this.center - col);
        totalDist = rowDist + colDist;
        this.matrix[row][col] = Math.pow(.5, totalDist) * 1 / 4;
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BlurKernel)) {
      return false;
    }

    BlurKernel other = (BlurKernel) o;
    return this.size == other.size
            && this.center == other.center
            && this.compareMatrix(other);
  }

  // helper for checking kernel equality
  private boolean compareMatrix(BlurKernel other) {
    for (int row = 0; row < this.size; row++) {
      for (int col = 0; col < this.size; col++) {
        if (this.matrix[row][col] != (other.matrix[row][col])) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.size, this.center, this.matrix[0][0]);
  }
}
