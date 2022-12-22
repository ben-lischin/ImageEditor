import java.io.IOException;

import controller.IGUIController;
import model.ViewImage;
import view.gui.Gui;

/**
 * Mock GUI class used for testing.
 */
public class MockGUI implements Gui {
  private Appendable log;

  /**
   * Constructs the mock GUI.
   * @param appendable controlled input log
   */
  public MockGUI(Appendable appendable) {
    this.log = appendable;
  }

  @Override
  public void addToDisplayList(ViewImage image) {
    try {
      log.append("Successfully added to display.\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }
  }

  @Override
  public void addController(IGUIController controller) {
    try {
      log.append("Successfully added controller.\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }
  }

  @Override
  public void paint() {
    try {
      log.append("Successfully painted.\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }
  }
}
