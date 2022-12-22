import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.JFrame;

import controller.ControllerImpl;
import controller.IController;
import controller.GUIControllerImpl;
import controller.IGUIController;
import model.IModel;
import model.Image;
import model.ViewImage;
import model.ViewImageImpl;

import view.IView;
import view.View;
import view.gui.GuiImpl;

/**
 * Contains the main method.
 */
public class DoImageStuff {
  /**
   * The main method where the user can do image processing.
   *
   * @param args command-line arguments
   * @throws IllegalArgumentException when the commandline argument is an invalid input
   */
  public static void main(String[] args) throws IllegalArgumentException {
    // if a script is provided, use the script...
    Iterator<String> iter = Arrays.stream(args).iterator();
    Readable fromReader = null;
    if (iter.hasNext()) {
      String next = iter.next();
      if (next.equals("-file")) {
        try {
          fromReader = new FileReader(iter.next());
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("No file was found by that name");
        } catch (NoSuchElementException e) {
          throw new IllegalArgumentException("No file path was provided");
        }
      } else if (next.equals("-text")) {
        fromReader = new InputStreamReader(System.in);
      } else {
        throw new IllegalArgumentException("invalid command (must start with \"-file\")");
      }
    }
    if (args.length == 0) {
      IModel model = new Image(100, 100, 255);
      ViewImage image = new ViewImageImpl(model);
      GuiImpl gui = new GuiImpl(image);
      IGUIController controller = new GUIControllerImpl(model, gui);
      gui.addController(controller);
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setVisible(true);
    } else {
      IView view = new View();
      IController controller = new ControllerImpl(view, fromReader);
      controller.edit();
    }
  }


}

