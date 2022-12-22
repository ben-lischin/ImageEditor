package model.kernel;

/**
 * Represents a Kernel that has basic information getter methods.
 */
public interface Kernel {

  /**
   * Gets the dimension of the kernel.
   * @return the length of each side
   */
  public int getSize();

  /**
   * Finds the center of the kernel.
   * @return the index of the center pixel
   */
  public int getCenter();

  /**
   * Gets the value at a position in the kernel.
   * @param row the y index
   * @param col the x index
   * @return the entry in the kernel at (x,y)
   */
  public double getEntry(int row, int col);
}
