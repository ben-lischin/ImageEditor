package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import controller.IGUIController;

/**
 * Represents the panel containing all the image edit buttons.
 */
public class EditsPanel extends JPanel implements ActionListener {
  IGUIController controller;
  private final Map<String, JPanel> comToPMap;

  private JTextField brightenVal;
  private JTextField darkenVal;

  /**
   * Constructs the edit panel.
   */
  public EditsPanel() {
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Hashmap of all of the possible edits and their custom panels
    this.comToPMap = new HashMap<String, JPanel>();

    // string form of all the possible edits
    String[] edits = {"Flip-Vertical", "Flip-Horizontal", "Blur",
        "Sharpen", "Sepia", "Intensity", "Luma", "Value", "Red-component", "Green-component",
        "Blue-component"};

    // create a panel for every possible edit
    createPanel("Brighten");
    createPanel("Darken");

    // set default panel to null so it doesnt display
    JPanel brightenPanel = comToPMap.get("Brighten");
    JPanel darkenPanel = comToPMap.get("Darken");

    // creates the button for the types of edits
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(7, 2));
    for (String edit : edits) {
      JButton button = new JButton(edit);
      button.setSize(new Dimension(100, 30));
      button.setActionCommand(edit);
      button.addActionListener(this);
      buttonPanel.add(button);
    }

    JPanel title = new JPanel();
    title.setMaximumSize(new Dimension(600, 25));
    title.add(new JLabel("Image Edits", JLabel.LEFT));

    buttonPanel.setMaximumSize(new Dimension(600, 300));

    add(title);
    add(buttonPanel);
    add(brightenPanel);
    add(darkenPanel);

  }

  private void createPanel(String edit) {
    if (edit.equals("Darken")) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBorder(BorderFactory.createTitledBorder(edit));
      panel.setMaximumSize(new Dimension(300, 100));
      JPanel panelSPecific = new JPanel();
      panelSPecific.setLayout(new BoxLayout(panelSPecific, BoxLayout.X_AXIS));
      panelSPecific.setMaximumSize(new Dimension(180, 50));
      JLabel input = new JLabel("Value to " + edit + " by:   ");
      this.brightenVal = new JTextField("10");
      brightenVal.setSize(new Dimension(50, 50));
      panelSPecific.add(input);
      panelSPecific.add(brightenVal);
      JButton applyDarken = new JButton("Apply");
      applyDarken.setActionCommand("Darken");
      applyDarken.addActionListener(this);
      panel.add(panelSPecific);
      panel.add(applyDarken);
      comToPMap.put(edit, panel);
    } else if (edit.equals("Brighten")) {
      JPanel bPanel = new JPanel();
      bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
      bPanel.setBorder(BorderFactory.createTitledBorder(edit));
      bPanel.setMaximumSize(new Dimension(300, 100));
      JPanel bPanelSpecific = new JPanel();
      bPanelSpecific.setLayout(new BoxLayout(bPanelSpecific, BoxLayout.X_AXIS));
      bPanelSpecific.setMaximumSize(new Dimension(180, 50));
      JLabel bInput = new JLabel("Value to " + edit + " by:   ");
      darkenVal = new JTextField("10");
      darkenVal.setSize(new Dimension(50, 50));
      bPanelSpecific.add(bInput);
      bPanelSpecific.add(darkenVal);
      JButton applyBrighten = new JButton("Apply");
      applyBrighten.setActionCommand("Brighten");
      applyBrighten.addActionListener(this);
      bPanel.add(bPanelSpecific);
      bPanel.add(applyBrighten);
      comToPMap.put(edit, bPanel);
    }
  }

  public void addController(IGUIController controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
    switch (e.getActionCommand()) {
      case "Brighten":
        int brighten = Integer.parseInt(brightenVal.getText());
        this.controller.brighten(brighten);
        break;
      case "Darken":
        int darken = -Integer.parseInt(darkenVal.getText());
        this.controller.brighten(darken);
        break;
      case "Flip-Vertical":
        this.controller.flip("vertical");
        break;
      case "Flip-Horizontal":
        this.controller.flip("horizontal");
        break;
      case "Blur":
        this.controller.blur();
        break;
      case "Sharpen":
        this.controller.sharpen();
        break;
      case "Red-component":
        this.controller.greyscale("Red-component");
        break;
      case "Green-component":
        this.controller.greyscale("Green-component");
        break;
      case "Blue-component":
        this.controller.greyscale("Blue-component");
        break;
      case "Sepia":
        this.controller.greyscale("Sepia");
        break;
      case "Value":
        this.controller.greyscale("Value");
        break;
      case "Luma":
        this.controller.greyscale("Luma");
        break;
      case "Intensity":
        this.controller.greyscale("Intensity");
        break;
      default:
        throw new IllegalArgumentException("action does not exist");
    }
  }
}
