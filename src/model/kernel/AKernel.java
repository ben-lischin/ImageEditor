package model.kernel;

/**
 * Abstract class for a kernel.
 */
public abstract class AKernel implements IKernel {
  protected int size; // length, odd
  protected int center; // index of center
  protected double[][] matrix;

  /**
   * Constructs the kernel.
   *
   * @param size the dimension of the square kernel
   * @throws IllegalArgumentException when the size is even
   */
  public AKernel(int size) throws IllegalArgumentException {
    if (size % 2 == 0) {
      throw new IllegalArgumentException("kernel must have odd dimensions");
    }
    this.size = size;
    this.matrix = new double[size][size];
    this.center = this.size / 2;
    this.makeMatrix();
  }

  /**
   * Assigns the values of the kernel matrix that will be used.
   */
  protected abstract void makeMatrix();

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public int getCenter() {
    return this.center;
  }

  @Override
  public double getEntry(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
      throw new IllegalArgumentException("invalid position to get entry from");
    }
    return this.matrix[row][col];
  }


}
