package controller;

import java.io.IOException;
import java.util.Objects;

import model.IModel;
import model.ViewImageImpl;
import model.functions.BlurSharpen;
import model.functions.Brighten;
import model.functions.ColorTransformation;
import model.functions.FlipHorizontal;
import model.functions.FlipVertical;
import model.functions.GreyScale;
import model.functions.IFunctions;
import model.kernel.BlurKernel;
import model.kernel.SharpenKernel;
import view.gui.Gui;

/**
 * Implementation of the GUI controller.
 */
public class GUIControllerImpl implements IGUIController {

  private IModel model;
  private Gui view;

  /**
   * Constructs the GUI controller.
   *
   * @param model the model to be used
   * @param view  the GUI
   */
  public GUIControllerImpl(IModel model, Gui view) {
    this.model = model;
    this.view = view;
    view.addController(this);
  }

  private void applyEditHelper(IFunctions func) {
    model = model.apply(func);
    view.addToDisplayList(new ViewImageImpl(model));
  }

  @Override
  public void brighten(int val) {
    applyEditHelper(new Brighten(val));
  }

  @Override
  public void greyscale(String func) throws IllegalArgumentException {
    switch (func) {
      case "Red-component":
        applyEditHelper(new GreyScale(p -> p.getRed()));
        break;
      case "Green-component":
        applyEditHelper(new GreyScale(p -> p.getGreen()));
        break;
      case "Blue-component":
        applyEditHelper(new GreyScale(p -> p.getBlue()));
        break;
      case "Luma":
        applyEditHelper(new GreyScale(p -> (int) Math.round(
                (.2126 * p.getRed()) + (.7152 * p.getGreen()) + (.0722 * p.getBlue()))));
        break;
      case "Sepia":
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
        applyEditHelper(new ColorTransformation(sepiaMatrix));
        break;
      case "Value":
        applyEditHelper(new GreyScale(p -> Math.max(p.getRed(), Math.max(p.getGreen(),
                p.getBlue()))));
        break;
      case "Intensity":
        applyEditHelper(new GreyScale(p -> (p.getRed() + p.getGreen()
                + p.getBlue()) / 3));
        break;
      default:
        throw new IllegalArgumentException("Invalid greyscal type");
    }
  }

  @Override
  public void blur() {
    applyEditHelper(new BlurSharpen(new BlurKernel()));
  }

  @Override
  public void flip(String type) throws IllegalArgumentException {
    switch (type) {
      case "horizontal":
        applyEditHelper(new FlipHorizontal());
        break;
      case "vertical":
        applyEditHelper(new FlipVertical());
        break;
      default:
        throw new IllegalArgumentException("Invalid flip type");
    }
  }

  @Override
  public void sharpen() {
    applyEditHelper(new BlurSharpen(new SharpenKernel()));
  }

  @Override
  public void load(String name) {
    IModel image;
    try {
      image = ImageUtil.load(name);
    } catch (IOException e) {
      return;
    }
    model = image;
    view.addToDisplayList(new ViewImageImpl(image));
  }

  @Override
  public void save(String newName) {
    try {
      ImageUtil.save(newName, model);
    } catch (Exception e) {
      throw new IllegalArgumentException("not a valid save path");
    }
  }

  // for testing purposes... only need to compare the images
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GUIControllerImpl)) {
      return false;
    }

    GUIControllerImpl other = (GUIControllerImpl) o;
    for (int row = 0; row < this.model.getRows(); row++) {
      for (int col = 0; col < this.model.getCols(); col++) {
        if (!this.model.getPixel(row, col).equals(other.model.getPixel(row, col))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.model.getRows(), this.model.getCols(),
            this.model.getPixel(0, 0).getRed());
  }
}
