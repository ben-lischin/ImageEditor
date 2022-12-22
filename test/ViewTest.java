import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import view.IView;
import view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the View class.
 */
public class ViewTest {
  IView viewDefault;
  IView viewOut;
  Appendable in;
  IView inputView;
  AppendableMock badOut;
  IView viewBadOut;

  @Before
  public void init() {
    this.viewDefault = new View();
    this.viewOut = new View(System.out);
    this.in = new StringBuilder();
    this.inputView = new View(this.in);
    this.badOut = new AppendableMock();
    this.viewBadOut = new View(this.badOut);
  }

  @Test
  public void testValidConstruction() {
    try {
      this.viewDefault = new View();
      this.viewOut = new View(System.out);
      // do nothing; test successfully constructed the view object
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown an IllegalArgumentException");
    }
  }

  @Test
  public void testInvalidConstruction() {
    try {
      this.viewOut = new View(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //do nothing; test successfully threw an exception
    }
  }

  @Test
  public void testRenderMessage() {
    // valid
    try {
      this.inputView.renderMessage("hi");
      assertEquals("hi", this.in.toString());
    } catch (IOException e) {
      fail("should not have thrown IO exception");
    }

    // invalid (throws IOException)
    try {
      this.viewBadOut.renderMessage("hi");
      fail("should have thrown IOException");
    } catch (IOException e) {
      // do nothing; successfully threw IO exception
    }

  }
}