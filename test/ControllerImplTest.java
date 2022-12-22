import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;


import controller.ControllerImpl;

import model.IModel;

import view.IView;
import view.View;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Contains tests for the Controller including all relationships to model && view.
 */
public class ControllerImplTest {
  IView view;
  Readable in;
  ControllerImpl controller;

  @Before
  public void init() {
    view = new View();
  }

  @Test
  public void testControllerToModel() {
    in = new StringReader("brighten model model2 30\n"
            + "save res/model.ppm model2 \n");
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output = new String[1];
    output[0] = log.toString();
    assertTrue(output[0].equals("function applied"));

    in = new StringReader("red-component model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output2 = new String[1];
    output2[0] = log.toString();
    assertTrue(output2[0].equals("function applied"));

    in = new StringReader("green-component model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output3 = new String[1];
    output3[0] = log.toString();
    assertTrue(output3[0].equals("function applied"));

    in = new StringReader("blue-component model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output4 = new String[1];
    output4[0] = log.toString();
    assertTrue(output4[0].equals("function applied"));

    in = new StringReader("value model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output5 = new String[1];
    output5[0] = log.toString();
    assertTrue(output5[0].equals("function applied"));

    in = new StringReader("intensity model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output6 = new String[1];
    output6[0] = log.toString();
    assertTrue(output6[0].equals("function applied"));

    in = new StringReader("luma model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output7 = new String[1];
    output7[0] = log.toString();
    assertTrue(output7[0].equals("function applied"));

    in = new StringReader("horizontal-flip model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output8 = new String[1];
    output8[0] = log.toString();
    assertTrue(output8[0].equals("function applied"));

    in = new StringReader("vertical-flip model model2\n"
            + "save res/model.ppm model2 \n");
    log = new StringBuilder();
    model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    controller.edit();
    String[] output9 = new String[1];
    output9[0] = log.toString();
    assertTrue(output9[0].equals("function applied"));
  }

  // ensure that the controller performs the right actions given an input command
  // and adds to the map of stored images
  @Test
  public void testEdit() {
    // good commands, test that the controller is able to apply all the command functions
    // the functionality of these function objects are tested in the model class tests,
    // so here we just want to see if they are called properly
    // also tests that save works and ends the program
    in = new StringReader("load res/image.ppm image\n"
            + "red-component image imagered\n"
            + "green-component imagered imagegreen\n"
            + "blue-component imagegreen imageblue\n"
            + "value imageblue imagevalue\n"
            + "intensity imagevalue imageintensity\n"
            + "luma imageintensity imageluma\n"
            + "horizontal-flip imageluma imagehflip\n"
            + "vertical-flip imagehflip imagevflip\n"
            + "brighten imagevflip imagefinal 10\n"
            + "save res/image-alledits.ppm imagefinal\n");
    controller = new ControllerImpl(view, in);
    try {
      controller.edit();
      // if the program does not throw errors, that means it handled the inputs
      // and performed the commands properly
      // also, by virtue of finding the input name in the map of name to model (called images in
      // the method), we know that the previous commands also add the name to the map properly
    } catch (IllegalArgumentException e) {
      fail("commands were all valid and should not have thrown an exception");
    }

    init();
    // loading from a non-ppm supported file
    in = new StringReader("load res/image.ppm image\n"
            + "vertical-flip image imagevflip\n"
            + "save res/image-alledits.jpg imagevflip\n");
    controller = new ControllerImpl(view, in);
    try {
      controller.edit();
    } catch (IllegalArgumentException e) {
      fail("commands were all valid and should not have thrown an exception. " +
              "also should work saving to a jpg file");
    }

    init();
    // saving to a non-ppm supported file
    in = new StringReader("load res/image.jpg image\n"
            + "vertical-flip image imagevflip\n"
            + "save res/image-alledits.ppm imagevflip\n");
    controller = new ControllerImpl(view, in);
    try {
      controller.edit();
    } catch (IllegalArgumentException e) {
      fail("commands were all valid and should not have thrown an exception. " +
              "also should work saving to a jpg file");
    }


    ////////////////////////////////////////////////////////////////////////
    // test valid inputs that dont work (i.e. command/name doesnt exist)

    init();
    // bad command
    in = new StringReader("commandnotinmap res/image.ppm image\n"
            + "save res/image-save.ppm image\n");
    controller = new ControllerImpl(view, in);
    try {
      controller.edit();
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing; successfully threw exception due to bad command
    }
    init();
    // bad name
    in = new StringReader("load res/image.ppm image\n "
            + "save res/image-save.ppm namenotinmap\n");
    controller = new ControllerImpl(view, in);
    try {
      controller.edit();
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing; successfully threw exception due to bad name
    }

    // testing save exception
    in = new StringReader("save res/model2.ppm model2 \n");
    StringBuilder log = new StringBuilder();
    IModel model = new MockModel(log);
    controller = new ControllerImpl(view, in, model, "model");
    try {
      controller.edit();
      fail("should not work because of invalid file for save");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

  }

}