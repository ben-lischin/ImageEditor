package view.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;


import javax.swing.BorderFactory;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.IGUIController;

/**
 * Represents the header panel with load and save buttons.
 */
public class Header extends JPanel implements ActionListener {
  private IGUIController controller;
  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private JFileChooser fileChooser;

  /**
   * Constructs the header.
   */
  public Header() {
    super();

    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new GridLayout(1, 2));
    mainPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
    add(mainPanel);
    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    mainPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Load");
    fileOpenButton.setActionCommand("Load");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    mainPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save");
    fileSaveButton.setActionCommand("Save");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
  }


  public void addController(IGUIController controller) {
    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
    if (e.getActionCommand().equals("Load")) {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG & PNG Images", "jpg", "gif", "png", "jpeg");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        controller.load(f.toString());
      }
    } else if (e.getActionCommand().equals("Save")) {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        System.out.println(f.getAbsolutePath());
        controller.save(f.getAbsolutePath());
      }
    } else {
      throw new IllegalArgumentException("action does not exist");
    }
  }


}
