package controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;


import model.functions.BlurSharpen;
import model.functions.Brighten;
import model.functions.ColorTransformation;
import model.functions.FlipHorizontal;
import model.functions.FlipVertical;
import model.functions.GreyScale;
import model.functions.IFunctions;
import model.IModel;
import model.kernel.BlurKernel;
import model.kernel.SharpenKernel;
import view.IView;

/**
 * Implementation of the controller that handles Images.
 */
public class ControllerImpl implements IController {
  private IModel model;
  private final IView view;
  private final Readable in;
  private final Map<String, Function<Scanner, IFunctions>> commands;


  /**
   * Constructs the controller.
   * Note: does not require a model input; this is assigned through loading a file.
   *
   * @param view the view to transmit to
   * @param in   receives the user input
   * @throws IllegalArgumentException when any of the inputs are null.
   */
  public ControllerImpl(IView view, Readable in) throws IllegalArgumentException {
    if (view == null || in == null) {
      throw new IllegalArgumentException("must have non-null view and input");
    }
    this.view = view;
    this.in = in;


    this.commands = new HashMap<String, Function<Scanner, IFunctions>>();

    // build the map of commands
    this.commands.put("red-component", sc -> new GreyScale(p -> p.getRed()));
    this.commands.put("green-component", sc -> new GreyScale(p -> p.getGreen()));
    this.commands.put("blue-component", sc -> new GreyScale(p -> p.getBlue()));
    this.commands.put("value", sc -> new GreyScale(p -> Math.max(p.getRed(), Math.max(p.getGreen(),
            p.getBlue()))));
    // intensity rounded down
    this.commands.put("intensity", sc -> new GreyScale(p -> (p.getRed() + p.getGreen()
            + p.getBlue()) / 3));
    // luma rounded to the nearest int (normal math rounding)
    this.commands.put("luma", sc -> new GreyScale(p -> (int) Math.round(
            (.2126 * p.getRed()) + (.7152 * p.getGreen()) + (.0722 * p.getBlue()))));
    this.commands.put("horizontal-flip", sc -> new FlipHorizontal());
    this.commands.put("vertical-flip", sc -> new FlipVertical());
    this.commands.put("brighten", sc -> new Brighten(sc.nextInt()));
    this.commands.put("blur", sc -> new BlurSharpen(new BlurKernel(sc.nextInt())));
    this.commands.put("sharpen", sc -> new BlurSharpen(new SharpenKernel()));
    // luma redone with ColorTransformation
    double[][] lumaMatrix = new double[3][3];
    lumaMatrix[0][0] = .2126;
    lumaMatrix[0][1] = .7152;
    lumaMatrix[0][2] = .0722;
    lumaMatrix[1][0] = .2126;
    lumaMatrix[1][1] = .7152;
    lumaMatrix[1][2] = .0722;
    lumaMatrix[2][0] = .2126;
    lumaMatrix[2][1] = .7152;
    lumaMatrix[2][2] = .0722;
    this.commands.put("greyscale", sc -> new ColorTransformation(lumaMatrix));
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
    this.commands.put("sepia", sc -> new ColorTransformation(sepiaMatrix));
  }

  /**
   * constructor to allow the controller to take in a given model directly.
   *
   * @param view  the view to transmit to
   * @param in    receives the user input
   * @param model the requested model
   * @param name  the name to store the model
   * @throws IllegalArgumentException when any of the inputs are null.
   */
  public ControllerImpl(IView view, Readable in, IModel model, String name) {
    if (view == null || in == null || model == null || name == null) {
      throw new IllegalArgumentException("must have non-null view and input");
    }
    this.view = view;
    this.in = in;

    this.commands = new HashMap<String, Function<Scanner, IFunctions>>();
    this.model = model;
    this.model.addImage(name, model);


    // build the map of commands
    this.commands.put("red-component", sc -> new GreyScale(p -> p.getRed()));
    this.commands.put("green-component", sc -> new GreyScale(p -> p.getGreen()));
    this.commands.put("blue-component", sc -> new GreyScale(p -> p.getBlue()));
    this.commands.put("value", sc -> new GreyScale(p -> Math.max(p.getRed(), Math.max(p.getGreen(),
            p.getBlue()))));
    // intensity rounded down
    this.commands.put("intensity", sc -> new GreyScale(p -> (p.getRed() + p.getGreen()
            + p.getBlue()) / 3));
    // luma rounded to the nearest int (normal math rounding)
    this.commands.put("luma", sc -> new GreyScale(p -> (int) Math.round(
            (.2126 * p.getRed()) + (.7152 * p.getGreen()) + (.0722 * p.getBlue()))));
    this.commands.put("horizontal-flip", sc -> new FlipHorizontal());
    this.commands.put("vertical-flip", sc -> new FlipVertical());
    this.commands.put("brighten", sc -> new Brighten(sc.nextInt()));
    this.commands.put("blur", sc -> new BlurSharpen(new BlurKernel(sc.nextInt())));
    this.commands.put("sharpen", sc -> new BlurSharpen(new SharpenKernel()));
    // luma redone with ColorTransformation
    double[][] lumaMatrix = new double[3][3];
    lumaMatrix[0][0] = .2126;
    lumaMatrix[0][1] = .7152;
    lumaMatrix[0][2] = .0722;
    lumaMatrix[1][0] = .2126;
    lumaMatrix[1][1] = .7152;
    lumaMatrix[1][2] = .0722;
    lumaMatrix[2][0] = .2126;
    lumaMatrix[2][1] = .7152;
    lumaMatrix[2][2] = .0722;
    this.commands.put("greyscale", sc -> new ColorTransformation(lumaMatrix));
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
    this.commands.put("sepia", sc -> new ColorTransformation(sepiaMatrix));

  }

  /**
   * Performs the appropriate method based off of the input command
   * (load, save, and the various edits).
   */
  @Override
  public void edit() {
    Scanner sc = new Scanner(this.in);
    String cmd = "";
    String filename = "";
    String name = "";
    String newName = "";
    while (!cmd.equals("save") && sc.hasNext()) {
      IFunctions c;
      cmd = sc.next();
      switch (cmd) {
        case "load":
          filename = sc.next();
          name = sc.next();
          try {
            this.model = ImageUtil.load(filename);
          } catch (IOException e) {
            throw new IllegalArgumentException("invalid file");
          }
          this.model.addImage(name, this.model);
          break;
        case "save":
          filename = sc.next();
          name = sc.next();
          IModel getModel = this.model.getImage(name);
          if (getModel == null) {
            throw new IllegalArgumentException("A model with that name does not exist.");
          }
          this.model = getModel;
          ImageUtil.save(filename, this.model);
          break;
        default:
          name = sc.next();
          newName = sc.next();
          Function<Scanner, IFunctions> func = commands.getOrDefault(cmd, null);
          if (func == null) {
            throw new IllegalArgumentException("Command does not exist");
          }
          c = func.apply(sc);
          this.model = this.model.getImage(name).apply(c);
          this.model.addImage(newName, this.model);
      }
    }
  }




}
