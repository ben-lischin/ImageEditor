package view.gui;


import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;



import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;


import controller.IGUIController;
import model.Histogram;
import model.ViewImage;

/**
 * Represents a GUI implementation that the user can interact with.
 */
public class GuiImpl extends JFrame implements Gui {
  private IGUIController controller;
  private final EditsPanel editPanel;
  private final Header header;
  private final ImageHolder imageHolder;
  private HistogramGraph histogram;

  /**
   * Constructs the GUI.
   * @param model the model to be represented
   */
  public GuiImpl(ViewImage model) {
    super();
    Objects.requireNonNull(model);
    editPanel = new EditsPanel();

    updateHistogram(model);
    header = new Header();
    imageHolder = new ImageHolder();
    imageHolder.setPreferredSize(new Dimension(800,800));
    JPanel main = new JPanel();
    main.setLayout(new BorderLayout());
    this.setMinimumSize(new Dimension(600,600));
    main.add(header, "North");
    main.add(editPanel, "West");

    main.add(new JScrollPane(imageHolder),"Center");
    histogram.setPreferredSize(new Dimension(300,300));
    histogram.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
    main.add(histogram,"East");

    add(main);
    setTitle("Image Processor");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width, screenSize.height);
  }


  public void addToDisplayList(ViewImage image) {
    this.showImage(image);
    this.updateHistogram(image);
  }

  private void showImage(ViewImage image) {
    this.imageHolder.displayImage(image);
  }

  private void updateHistogram(ViewImage image) {
    Histogram histogram2 = new Histogram(image);
    histogram = new HistogramGraph(histogram2,this);
    paint();
  }

  public void addController(IGUIController controller) {
    this.header.addController(controller);
    this.editPanel.addController(controller);
  }

  public void paint() {
    this.repaint();
  }
}
