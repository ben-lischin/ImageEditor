package view.gui;

import model.Histogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;

/**
 * Represents the panel containing the histogram.
 */
public class HistogramGraph extends JPanel {
  private final Gui main;
  private static Histogram hist;

  /**
   * Constructs the histogram panel.
   *
   * @param hist  the histogram to be represented
   * @param main  the GUI to be updated
   */
  public HistogramGraph(Histogram hist, Gui main) {
    if (hist == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }
    this.hist = hist;
    this.main = main;
    repaint();
    paintThis();
  }

  public void paintThis() {
    main.paint();
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int width = 255;
    int redHeight = (int) (getHeight() / 6.2) + 100;
    int greenHeight = (int) (getHeight() / 3.2) + 100;
    int blueHeight = (int) (getHeight() / 2.2) + 100;
    int intensityHeight = (int) (getHeight() / 1.65) + 100;
    double percentage = .2;


    Graphics2D graphics = (Graphics2D) g;

    int[] counter = new int[this.hist.getSize()];
    for (int i = 0; i < counter.length; i++) {
      counter[i] = i;
    }
    int newWidth = 1;
    //System.out.println(newWidth);
    graphics.setColor(Color.black);
    graphics.drawString("Histogram values", 100, 30);
    for (int i = 0; i < 256; i++) {
      graphics.setColor(Color.red);
      graphics.drawRect(i + 20, redHeight - (int) Math.round(hist.getValueAt(i,
              "red") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "red") * percentage));
      graphics.fillRect(i + 20, redHeight - (int) Math.round(hist.getValueAt(i,
              "red") * percentage), 1, (int) Math.round(hist.getValueAt(i,

              "red") * percentage));
      graphics.setColor(Color.green);
      graphics.drawRect(i + 20, greenHeight - (int) Math.round(hist.getValueAt(i,
              "green") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "green") * percentage));
      graphics.fillRect(i + 20, greenHeight - (int) Math.round(hist.getValueAt(i,
              "green") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "green") * percentage));
      graphics.setColor(Color.blue);
      graphics.drawRect(i + 20, blueHeight - (int) Math.round(hist.getValueAt(i,
              "blue") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "blue") * percentage));
      graphics.fillRect(i + 20, blueHeight - (int) Math.round(hist.getValueAt(i,
              "blue") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "blue") * percentage));
      graphics.setColor(Color.black);
      graphics.drawRect(i + 20, intensityHeight - (int) Math.round(hist.getValueAt(i,
              "intensity") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "intensity") * percentage));
      graphics.fillRect(i + 20, intensityHeight - (int) Math.round(hist.getValueAt(i,
              "intensity") * percentage), 1, (int) Math.round(hist.getValueAt(i,
              "intensity") * percentage));

    }
    repaint();
    paintThis();

  }
}
