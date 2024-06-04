# JPictureSlot: A Java Swing Component for Image Display

## Overview

Welcome to **JPictureSlot**, a custom Java Swing component designed to provide flexible and dynamic image display capabilities, similar to the PictureBox component in C#. This project aims to simplify image handling in Java Swing applications by allowing easy resizing and positioning of images within a JLabel-based component.

## Features

- **Multiple Display Modes**: Easily switch between different modes such as Normal, Stretch, Auto Size, Center Image, and Zoom.
- **Event-Driven Resizing**: Automatically adjusts the image display based on component resizing events.
- **Simple API**: Intuitive methods for setting image paths and display modes.
- **Seamless Integration**: Compatible with existing Java Swing applications and tools like NetBeans IDE.

## Usage

### Importing JPictureSlot

```
import your.package.name.JPictureSlot;
```

### Creating an Instance

You can create an instance of JPictureSlot with a specified image path:
```
JPictureSlot pictureSlot = new JPictureSlot("path/to/your/image.png");
```
Or create an istance with the default image with path "./nothing.png" (NOT INCLUDED IN THE REPO)
```
JPictureSlot pictureSlot = new JPictureSlot();
```

### Setting Display Modes

Change the display mode to fit your needs:

```
pictureSlot.setSizeMode("stretch");
```

Available display modes:

- `normal`: Display the image at its original size, aligned to the top-left corner.
- `stretch`: Stretch the image to fit the component's dimensions.
- `auto`: Adjust the component size to match the image's original size.
- `center`: Center the image within the component.
- `zoom`: Resize the image to fit the component while maintaining the original aspect ratio.

### Changing Image Path

Update the image displayed by JPictureSlot:

```
pictureSlot.setImgPath("new/path/to/your/image.png");
```
### Checking Image Path Validity

Before setting an image path, you can verify its existence:

```
boolean isValid = JPictureSlot.VerifyPath("path/to/your/image.png");
```

## Author

- **Ognjen Vasic**
  - Email: ognjenvasic70@gmail.com

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request or open an Issue for any improvements or suggestions.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
