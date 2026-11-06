# JPictureSlot: A Java Swing Component for Image Display

> Written in the first semester of my fourth year, straight after the object-oriented
> programming course. Java Swing has no equivalent of C#'s PictureBox: no standard
> component handles image scaling and positioning with configurable display modes. So I
> built one.
>
> I went on to reuse it in later projects whenever I needed image handling in a GUI, which
> makes it the most practically useful thing I've written here.

## Overview

**JPictureSlot** is a custom Java Swing component that extends `JLabel` to provide image display and scaling modes similar to the Windows Forms `PictureBox` control in C#. It groups image loading, buffered graphics transformations, and resize handling into a single component.

## Features

- **Five Display Modes**:
  - `normal`: Renders the image at 1:1 scale aligned to the top-left corner, clipping content that exceeds the component boundaries.
  - `stretch`: Scales the image independently in width and height to fill the entire component area.
  - `auto` / `autosize`: Automatically adjusts the component dimensions (`super.setSize`) to match the image dimensions.
  - `center` / `centerimage`: Centers the image at original scale within the component, clipping outer edges if larger than the bounds.
  - `zoom`: Scales the image proportionally to fit within component dimensions while preserving original aspect ratio.
- **Event-Driven Resizing**: Recomputes the image transformation when the component receives a resize event.
- **Simple API**: Easy methods to switch display modes, update source file paths, and verify file existence before loading.
- **NetBeans GUI Builder Ready**: Includes a parameterless constructor allowing the component to be added to the NetBeans component palette.

## Project Structure & Integration

JPictureSlot is distributed as a single standalone source file ([`JPictureSlot.java`](JPictureSlot.java)), not as an external Maven or Gradle package.

To integrate it into a Java Swing application:

1. Copy [`JPictureSlot.java`](JPictureSlot.java) into your project's source directory (e.g. `src/main/java/com/example/ui/` or `src/`).
2. **Package declaration**: In the repository, `JPictureSlot.java` resides in the default (unnamed) package without a `package` header. If your project uses named packages, add your package header at line 1:
   ```java
   package com.example.ui;
   ```
   Other classes in your project can then import it normally:
   ```java
   import com.example.ui.JPictureSlot;
   ```
   *(If your application code also resides in the default package, no import statement is required).*

## Usage & API

### 1. Creating an Instance

- **With an explicit image path (recommended for code)**:
  ```java
  JPictureSlot slot = new JPictureSlot("assets/sample.png");
  ```

- **With the parameterless constructor**:
  ```java
  JPictureSlot slot = new JPictureSlot();
  ```
  > **Note on default image**: The parameterless constructor sets `imgPath` to `"nothing.png"`. This constructor was designed for visual palette integration in IDEs like NetBeans. Because `nothing.png` is not included in the repository, creating an empty instance will not fail immediately, but triggering resize operations or calling `setSizeMode(...)` without an existing file will cause `ImageIO.read` to fail and throw an `Error`. When using the default constructor, call `setImgPath(...)` with a valid file before displaying.

### 2. Setting Display Modes

Modes can be changed dynamically by passing the mode name (case-insensitive):
```java
slot.setSizeMode("zoom");
```
Supported string identifiers are `"normal"`, `"stretch"`, `"auto"` (or `"autosize"`), `"center"` (or `"centerimage"`), and `"zoom"`. Direct helper methods are also available (`slot.setSizeModeZoom()`, `slot.setSizeModeStretch()`, etc.).

### 3. Changing the Image Path

```java
slot.setImgPath("assets/new_image.png");
```
`setImgPath` verifies file existence; if the file is missing, an `IllegalArgumentException` is thrown.

### 4. Checking Image Path Validity

Before setting a path, you can check that the file exists and has a supported extension (`.png`, `.jpg`, `.jpeg`):
```java
boolean exists = JPictureSlot.VerifyPath("assets/sample.png");
```

## Minimal Code Example

```java
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DemoApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JPictureSlot Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            // Create slot with an existing image
            JPictureSlot slot = new JPictureSlot("sample.png");
            slot.setSizeMode("zoom");

            frame.getContentPane().add(slot);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

- **Ognjen Vasic** — ognjenvasic70@gmail.com
