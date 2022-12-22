package controller;

/**
 * Represents a controller interface that can perform specific image editing actions.
 */
public interface IGUIController {
  /**
   * Brightens the model and updates the GUI view.
   * @param val the amount to brighten by
   */
  void brighten(int val);

  /**
   * Blurs the model and updates the GUI view.
   */
  void blur();


  /**
   * Flips the model and updates the GUI view.
   * @param type "horizontal" or "vertical" flip types
   * @throws IllegalArgumentException when the flip type is invalid
   */
  void flip(String type) throws IllegalArgumentException;

  /**
   * Sharpens the model and updates the GUI view.
   */
  void sharpen();

  /**
   * Greyscales the model and updates the GUI view.
   * @param func the type of greyscale to be applied
   * @throws IllegalArgumentException when the greyscale type is invalid
   */
  void greyscale(String func) throws IllegalArgumentException;

  /**
   * Loads a model and updates the GUI view.
   * @param name the name of the image to load
   * @throws IllegalArgumentException when the name cannot be found
   */
  void load(String name) throws IllegalArgumentException;

  /**
   * Saves the model and updates the GUI view.
   * @param newName the name to save the image to
   * @throws IllegalArgumentException when a file of that name cannot be made
   */
  void save(String newName) throws IllegalArgumentException;
}
