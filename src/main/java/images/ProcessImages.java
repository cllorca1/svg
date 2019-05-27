package images;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.*;

public class ProcessImages {


    public static void main(String[] args) throws IOException {

        File imageFile = new File(args[0]);
        BufferedImage image = ImageIO.read(imageFile);

        System.out.println(image.getData().getPixel(10,10, (int[]) null)[1]);
        System.out.println(image.getData().getPixel(10,10, (int[]) null));
        System.out.println(image.getData().getPixel(1,1, (int[]) null));
        System.out.println(image.getData().getPixel(10,10, (int[]) null));
        System.out.println(image.getData().getPixel(10,20, (int[]) null));
        System.out.println(image.getData().getPixel(10,30, (int[]) null));
        System.out.println(image.getData().getPixel(10,10, (int[]) null));

    }

}
