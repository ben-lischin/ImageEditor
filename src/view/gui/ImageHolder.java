package view.gui;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Dimension;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import model.ViewImage;

/**
 * Represents the image being edited by the program to that will be displayed.
 */
public class ImageHolder extends JPanel {
  private final JLabel image;

  /**
   * Constructs the image holder.
   */
  public ImageHolder() {
    super();
    this.image = new JLabel("Displays image here");
    add(this.image);
  }

  void displayImage(ViewImage image) {
    this.image.setText("");
    BufferedImage imageToDisplay = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < image.getWidth(); row++) {
      for (int col = 0; col < image.getHeight(); col++) {
        Color pixColor = new Color(image.getPixel(row, col).getRed(),
                image.getPixel(row, col).getGreen(), image.getPixel(row,
                col).getBlue(), image.getPixel(row, col).getAlpha());
        int rgb = pixColor.getRGB();
        imageToDisplay.setRGB(row,col,rgb);
      }
    }

    this.image.setIcon(new ImageIcon(imageToDisplay));
    this.image.setBorder(BorderFactory.createMatteBorder(-1,-1,-1,-1,
            Color.black));
    this.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
  }

}
