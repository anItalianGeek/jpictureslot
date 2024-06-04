import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Author: Ognjen Vasic
 * Email: ognjenvasic70@gmail.com
 * 
 * Purpose of the project: Add a feature to the JLabel swing component, 
 * which is to allow you to resize images within the component without having
 * to change the size of the component manually each time.
 *
 *
 * Note: The component will behave just like the C# PictureBox component does!
*/
public class JPictureSlot extends JLabel {
    /*
     * Two class attributes.
     * 
     * imgPath contains the path to the resource that the JLabel is going to show
     * sizeMode sets how the resource is going to "fit" inside the JLabel
    */
    private String imgPath;
    private String sizeMode;
    
    /*
     * One and only constructor, takes 2 parameters, the path of the resource and the display style
     *
     * The constructor will check if the display mode input is "correct" and will apply the normal style if necessary
     * However it won't check if the path of the resource actually exists, the verifyPath method should be used before invoking the constructor
    */
    public JPictureSlot(String imgPath) {
        // creating a standard JLabel and setting some eessential attributes. The super keyword is used so that the local methods can't be invoked
        super();
        super.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgPath)));
        super.setText("");
        super.setSize(100, 50); // assume this is the default size of the "JPictureSlot"
        
        this.imgPath = imgPath; // setting the path property
        this.sizeMode = "normal"; // setting the sizeMode property
        
        // creating the event listener
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                onResize(e);
            }
        });
    }
    
    /*
     * Constructor with no parameters: Simply calls the other constructor by setting the sizemode to normal
     * and setting the imgPath property to "nothing.png" (The image is in the same package of this java class).
     * 
     * Note: To display the component in the NetBeans IDE component palette a constructor with no parameters is needed.
    */
    public JPictureSlot() {
        this("nothing.png");
    }
    
    /*
     * setSizeMode is the "main" method of the class. It's purpose is to change the way that the JLabel uses to represent the resource.
     * Note that setSizeMode takes one parameter, a string, which represents the new displaying style.
     * Also remember that if the input (sizeMode parameter) is "completely wrong" the method will NOT modify the display style type.
     * 
     * Obviously, someone can easily call the other setters (for example setSizeModeAuto). The only difference really is that setSizeMode uses a
     * string to select the displaying style while the other methods can only do "their thing".
     *
     * The getter of the sizeMode property is at the bottom of the following block of methods, just before the next multi-line comment block.
    */
    public void setSizeMode(String sizeMode){
        switch (sizeMode.toLowerCase()){
            case "normal": this.setSizeModeNormal(); this.sizeMode = sizeMode; return;
            case "stretch": this.setSizeModeStretch(); this.sizeMode = sizeMode; return;
            case "autosize":
            case "auto": this.setSizeModeAuto(); this.sizeMode = sizeMode; return;
            case "centerimage": 
            case "center": this.setSizeModeCenter(); this.sizeMode = sizeMode; return;
            case "zoom": this.setSizeModeZoom(); this.sizeMode = sizeMode; return;
            default: throw new IllegalArgumentException("Unknown SizeMode Setting. No changes applied.");
        }
    }
    
    /*
     * setSizeModeNormal method.
     *
     * The image is positioned at the top left corner of the JPictureSlot component.
     * If the image is bigger than the component it gets cut.
    */
    public void setSizeModeNormal(){
        try {
            BufferedImage mainImg = ImageIO.read(new File(this.imgPath)); // create a buffered image
            
            // create a new "transparent" image with the same dimension of the component
            BufferedImage out = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // "draw" the new image using the original one
            Graphics2D painter = out.createGraphics();
            painter.drawImage(mainImg, AffineTransform.getScaleInstance(1, 1), null); // Scale 1:1, doesn't matter if the edges are cut off
            
            this.setIcon(new ImageIcon(out)); // set the "new" icon to the current JPictureSlot
            
            this.sizeMode = "normal";
        } catch (Exception ex) {
            throw new Error("Image creation or image resizing has failed.");
        }
    }
    
    /*
     * setSizeModeStretch method
     *
     * The image inside is resized so that it can fit inside the component.
    */
    public void setSizeModeStretch(){
        try {
            BufferedImage mainImg = ImageIO.read(new File(this.getImgPath()));
            int imgWidth = mainImg.getWidth();
            int imgHeight = mainImg.getHeight();
            
            int lblWidth = this.getWidth();
            int lblHeight = this.getHeight();
            
            BufferedImage out = new BufferedImage(lblWidth, lblHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D painter = out.createGraphics();
            
            // getting the scale neeeded to "draw" the new, resized, image.
            double scaleX = (double) lblWidth / imgWidth;
            double scaleY = (double) lblHeight / imgHeight;
            
            AffineTransform transformer = AffineTransform.getScaleInstance(scaleX, scaleY);
            
            painter.drawImage(mainImg, transformer, null);
            
            this.setIcon(new ImageIcon(out));

            this.sizeMode = "stretch";
        } catch (Exception ex) {
            throw new Error("Image creation or Image Stretching has failed.");
        }
    }
    
    /*
     * setSizeModeAuto method.
     *
     * After invoking the method, the JPictureSlot component will have the same size of the image.
    */
    public void setSizeModeAuto(){
        try {
            BufferedImage img = ImageIO.read(new File(this.getImgPath()));
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            
            // to avoid stack overflows the superclass setSize method must be invoked
            super.setSize(imgWidth, imgHeight);
            
            this.setIcon(new ImageIcon(img));

            this.sizeMode = "auto";
        } catch (Exception ex){
            throw new Error("Image creation or Component Resizing has failed.");
        }
    }
    
    /*
     * setSizeModeCenter method.
     *
     * The image is displayed in the center of the component.
     * If the image is bigger than the JPictureSlot it will be positioned at the center but the outer edges are cut off.
    */
    public void setSizeModeCenter(){
        try {
            BufferedImage img = ImageIO.read(new File(this.getImgPath()));
            this.setIcon(new ImageIcon(img));
            
            // setting the alignment of the component to the center
            this.setHorizontalAlignment(CENTER);
            this.setVerticalAlignment(CENTER);

            this.sizeMode = "center";
        } catch (Exception ex){
            throw new Error("Image creation or image centering has failed.");
        }
    }
    
    /*
     * setSizeModeZoom method.
     *
     * The image is resized to fit in the component but unlike the "stretch" mode, "zoom" keeps the original proportions
    */
    public void setSizeModeZoom(){
        try {
            BufferedImage mainImg = ImageIO.read(new File(this.getImgPath()));
            int imgWidth = mainImg.getWidth();
            int imgHeight = mainImg.getHeight();
            int lblWidth = this.getWidth();
            int lblHeight = this.getHeight();
            
            BufferedImage out = new BufferedImage(lblWidth, lblHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D painter = out.createGraphics();
            double scaleX = (double) lblWidth / imgWidth;
            double scaleY = (double) lblHeight / imgHeight;
            AffineTransform transformer = AffineTransform.getScaleInstance(scaleX, scaleY);
            painter.drawImage(mainImg, transformer, null);
            
            this.setHorizontalAlignment(CENTER);
            this.setHorizontalAlignment(CENTER);    
            this.setIcon(new ImageIcon(out));
            
            this.sizeMode = "zoom";
        } catch (Exception ex) {
            throw new Error("Image creation or image resizing has failed.");
        }
    }
    
    public String getSizeMode() {
        return sizeMode;
    }
    
    /*
     * setImgPath is a function that takes one parameter, a string, which is used to change the imgPath property.
     * 
     * the getter of the sizeMode property is the first one
    */
    public String getImgPath(){
        return imgPath;
    }
    
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
        File checker = new File(imgPath);
        if (checker.exists()){
            try {
                this.setIcon(new ImageIcon(ImageIO.read(checker)));
            } catch (IOException ioe){
                System.out.println("An error occured while loading the image.");
            }
            this.setSizeMode(this.getSizeMode());
        }
        else
            throw new IllegalArgumentException("Specified Path does not Exist.");
    }
    
    /*
     * If the user does not change the size of the component by using the class method setSize
     * and drags the corners of the component, the following method will be invoked.
     *
     * You may see the declared event listener in the constructor calling this.
     * Else, the event listener has been removed to prevent the label from changing its size in a never ending cycle. This problem was encountered while debugging this project.
     * Overriding the setSize method is the second option. You may uncomment the lines that create the event listener in the constructor if you want to try anything.
    */
    
    private void onResize(ComponentEvent evt) {                                         
        JPictureSlot source = (JPictureSlot) evt.getSource();
        source.setSizeMode(this.sizeMode);
    }     
    
    @Override
    public void setSize(int width, int height){
        super.setSize(width, height);
        this.setSizeMode(this.sizeMode);
    }
    
    /*
     * The verifyPath method is used to check if a particular path to an image exists.
     *
     * It is irrelevant to the actual purpose of the class, it should be used before invoking the constructor to check if the path of a resource actually exists
    */
    public static boolean VerifyPath(String path){
        if (path.contains(".png") || path.contains(".jpg") || path.contains(".jpeg")){
            File file = new File(path);
            return file.exists();
        }
        return false;
    }
}
