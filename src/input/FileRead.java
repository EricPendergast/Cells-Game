package input;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class FileRead{
	public static BufferedReader getReader(String fileLoc){
		BufferedReader br = null;
		try {
			URL url = FileRead.class.getResource(fileLoc);
			br = new BufferedReader(new FileReader(url.getPath()));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		return br;
	}
	public static BufferedWriter getWriter(String fileLoc){
		BufferedWriter bw = null;
		try {
			URL url = FileRead.class.getResource(fileLoc);
			bw = new BufferedWriter(new FileWriter(url.getPath()));
		} catch (IOException e) {e.printStackTrace();}
		
		return bw;
	}
	public static BufferedImage getImage(String fileLoc){
		return toBufferedImage(new ImageIcon(FileRead.class.getResource(fileLoc)).getImage());
	}
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    return bimage;
	}
}
