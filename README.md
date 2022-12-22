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
Visual representation of the red, green, blue, and intensity components of the current version of the (edited) image.

## Input guidelines (for script or System.in)
### Loading an image
#### load: "load (filename to load from, containing filetype) (name to store as)\n"
ex: load res/image.png image
### Edit commands
#### red-component: "red-component (stored name of image to edit) (name to store edited image as)\n"
ex: red-component image imagered
#### green-component: "green-component (stored name of image to edit) (name to store edited image as)\n"
ex: green-component image imagegreen
#### blue-component: "blue-component (stored name of image to edit) (name to store edited image as)\n"
ex: blue-component image imageblue
#### value: "value (stored name of image to edit) (name to store edited image as)\n"
ex: value image imagevalue
#### intensity: "intensity (stored name of image to edit) (name to store edited image as)\n"
ex: intensity image imageintensity
#### luma: "luma (stored name of image to edit) (name to store edited image as)\n"
ex: luma image imageluma
#### horizontal-flip: "horizontal-flip (stored name of image to edit) (name to store edited image as)\n"
ex: horizontal-flip image imagehflip
#### vertical-flip: "vertical-flip (stored name of image to edit) (name to store edited image as)\n"
ex: vertical-flip image imagevflip
#### brighten: "brighten (stored name of image to edit) (name to store edited image as) (amount to brighten by, must be positive)\n"
ex: brighten image imagebright 50
#### darken: "brighten (stored name of image to edit) (name to store edited image as) (amount to brighten by, must be negative)\n"
ex: brighten image imagedark -50
#### blur: "blur (stored name of image to edit) (name to store edited image as) (size of blur kernel)\n"
ex: blur image imageblur 3
#### sharpen: "sharpen (stored name of image to edit) (name to store edited image as)\n"
ex: sharpen image imagesharp
#### greyscale (luma): "greyscale (stored name of image to edit) (name to store edited image as)\n"
ex: greyscale image imagegreyscale
#### sepia: "sepia (stored name of image to edit) (name to store edited image as)\n"
ex: sepia image imagesepia
### Saving an image
#### save: "save (filename to save to, containing filetype) (stored name of image to save)\n"
ex: save res/imagesepia.jpg imagesepia
