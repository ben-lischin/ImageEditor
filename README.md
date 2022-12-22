# ImageEditor

## How to run the program:
 - If "-text" is interpreted as a command-line argument, the user may enter load/edit/save commands through the System.in
 - If a script location is provided following "-file" in the command-line arguments, this program will execute that script to edit an image
     - Example of a script you can use to run the program: script.txt (located within this project folder). To use this, enter the command-line argument: "-file script.txt"
 - Otherwise, if nothing is specified, a GUI application will open for the user to interact with and edit images
 
 Note: the first command in any case should be load. This assigns the  model which all the the other edit and save commands will use

The save command will end the program if it is run through System.in or a script (i.e. those methods can only edit one image). To end the program used through the GUI application, you must close the application window (you can edit multiple images without restarting the program)

Application UI

### Histogram

### Input guidelines (for script or System.in)
 
