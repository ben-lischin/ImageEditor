# ImageEditor

## How to run the program:
 - If "-text" is interpreted as a command-line argument, the user may enter load/edit/save commands through the System.in
 - If a script location is provided following "-file" in the command-line arguments, this program will execute that script to edit an image
     - Example of a script you can use to run the program: script.txt (located within this project folder). To use this, enter the command-line argument: "-file script.txt"
 - Otherwise, if nothing is specified, a GUI application will open for the user to interact with and edit images
 
 Note: the first command in any case should be load. This assigns the  model which all the the other edit and save commands will use

The save command will end the program if it is run through System.in or a script (i.e. those methods can only edit one image). To end the program used through the GUI application, you must close the application window (you can edit multiple images without restarting the program)

Application UI
<img width="1440" alt="Screen Shot 2022-12-22 at 2 49 06 PM" src="https://user-images.githubusercontent.com/94412143/209214956-81f5feac-ae8d-4cf6-a599-79bec6bc91a4.png">

### Histogram

### Input guidelines (for script or System.in)
 
