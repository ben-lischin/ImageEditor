import org.junit.Test;

import java.io.IOException;

import controller.ImageUtil;
import controller.GUIControllerImpl;
import model.IModel;
import model.functions.BlurSharpen;
import model.functions.Brighten;
import model.functions.ColorTransformation;
import model.functions.FlipHorizontal;
import model.functions.FlipVertical;
import model.functions.GreyScale;
import model.kernel.BlurKernel;
import model.kernel.SharpenKernel;
import view.gui.Gui;

import static org.junit.Assert.assertEquals;


/**
 * Test for GUI controller functionality.
 */
public class GUIControllerTest {
  IModel model;
  IModel expected;
  Gui view;
  StringBuilder log;
  GUIControllerImpl controller;
  GUIControllerImpl expectedController;


  private void init() {
    log = new StringBuilder();
    view = new MockGUI(log);
    try {
      model = ImageUtil.load("res/image.ppm");
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot read image");
    }
    controller = new GUIControllerImpl(model, view);
  }

  @Test
  public void brighten() {
    init();
    controller.brighten(10); // controller with brightened image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new Brighten(10).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);
  }

  @Test
  public void flip() {
    init();
    controller.flip("horizontal"); // controller with flipped image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new FlipHorizontal().apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);


    init();
    controller.flip("vertical"); // controller with flipped image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new FlipVertical().apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);
  }

  @Test
  public void blurSharpen() {
    init();
    controller.blur(); // controller with blurred image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new BlurSharpen(new BlurKernel()).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);


    init();
    controller.sharpen(); // controller with sharpened image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new BlurSharpen(new SharpenKernel()).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);
  }

  @Test
  public void greyScale() {
    init();
    controller.greyscale("Red-component"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> p.getRed()).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);

    init();
    controller.greyscale("Green-component"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> p.getGreen()).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);
    init();
    controller.greyscale("Blue-component"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> p.getBlue()).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);

    init();
    controller.greyscale("Luma"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> (int) Math.round(
            (.2126 * p.getRed()) + (.7152 * p.getGreen()) + (.0722 * p.getBlue()))).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);

    init();
    controller.greyscale("Sepia"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());
    double[][] sepiaMatrix = new double[3][3];
    sepiaMatrix[0][0] = .393;
    sepiaMatrix[0][1] = .769;
    sepiaMatrix[0][2] = .189;
    sepiaMatrix[1][0] = .349;
    sepiaMatrix[1][1] = .686;
    sepiaMatrix[1][2] = .168;
    sepiaMatrix[2][0] = .272;
    sepiaMatrix[2][1] = .534;
    sepiaMatrix[2][2] = .131;
    expected = new ColorTransformation(sepiaMatrix).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);

    init();
    controller.greyscale("Value"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> Math.max(p.getRed(), Math.max(p.getGreen(),
            p.getBlue()))).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);

    init();
    controller.greyscale("Intensity"); // controller with greyscaled image (Actual)
    assertEquals("Successfully added controller.\n" +
            "Successfully added to display.\n", log.toString());

    expected = new GreyScale(p -> (p.getRed() + p.getGreen() + p.getBlue()) / 3).apply(model);
    // expected controller
    expectedController = new GUIControllerImpl(expected, view);
    assertEquals(expectedController, controller);
  }
}