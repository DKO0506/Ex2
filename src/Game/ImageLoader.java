package Game;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageLoader extends JPanel {

    private Image backgroundImage;

    private ImageLoader()throws IOException {


        backgroundImage = ImageIO.read(new File("Pics\\forest.jpg"));



    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
