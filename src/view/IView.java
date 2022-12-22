package view;

import java.io.IOException;

/**
 * Represents a view that can be used to display messages to the viewer.
 */
public interface IView {
  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
