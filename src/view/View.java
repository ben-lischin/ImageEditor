package view;

import java.io.IOException;

/**
 * Represents a view for a PPM.
 */
public class View implements IView {
  private Appendable out;

  /**
   * Constructs the view, using System.out by default.
   */
  public View() {
    this.out = System.out;
  }

  /**
   * Constructs the view using a given output stream.
   * @param out the Appendable output stream
   * @throws IllegalArgumentException when the given output stream is null
   */
  public View(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Must provide a non-null output stream");
    }
    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }

}
