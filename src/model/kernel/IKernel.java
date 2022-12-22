package model.kernel;

/**
 * Represents a Kernel, contains getters for basic information.
 */
public interface IKernel {
  /**
   * Getter for the size of a kernel.
   *
   * @return the size (dimensions) of the kernel
   */
  public int getSize();

  /**
   * Getter for the center of a kernel.
   *
   * @return the index of the center entry in the matrix
   */
  public int getCenter();

  /**
   * Getter for the value of a specific entry in the matrix.
   *
   * @param row the row of the desired position (index)
   * @param col the column of the desired position (index)
   * @return the value in the matrix at the given position
   */
  public double getEntry(int row, int col);
}
