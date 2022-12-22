package model.kernel;

import java.util.Objects;

/**
 * a kernel that is used to sharpen images.
 */
public class SharpenKernel extends AKernel {
  /**
   * takes in no arguments and always constructs the same 5x5 sharpen kernel.
   */
  public SharpenKernel() {
    super(5);
  }

  @Override
  protected void makeMatrix() {
    for (int row = 0; row < this.size; row++) {
      for (int col = 0; col < this.size; col++) {
        if (row == 0 || row == this.size - 1 || col == 0 || col == this.size - 1) {
          this.matrix[row][col] = -.125;
        } else if (row == 1 || row == this.size - 2 || col == 1 || col == this.size - 2) {
          this.matrix[row][col] = .25;
        } else {
          this.matrix[row][col] = 1;
        }
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SharpenKernel)) {
      return false;
    }

    SharpenKernel other = (SharpenKernel) o;
    return this.size == other.size
            && this.center == other.center
            && this.compareMatrix(other);
  }

  // helper for checking kernel equality
  protected boolean compareMatrix(SharpenKernel other) {
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
    return Objects.hash(this.size, this.center);
  }
}
