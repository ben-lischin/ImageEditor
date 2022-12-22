package view.gui;

import controller.IGUIController;
import model.ViewImage;

/**
 * Interface for a GUI interactive window.
 */
public interface Gui {

  /**
   * Displays the given image, as well as its histogram.
   * @param image the image to be displayed
   */
  void addToDisplayList(ViewImage image);

  /**
   * Gives the controller to the panels so that they can call methods.
   * @param controller the controller that is passed to the panels
   */
  void addController(IGUIController controller);

  /**
   * Repaints the window with updated information.
   */
  void paint();
}
