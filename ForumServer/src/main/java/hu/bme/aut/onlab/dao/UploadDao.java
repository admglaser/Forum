package hu.bme.aut.onlab.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

@LocalBean
@Stateless
public class UploadDao {

	private final String PROFILE_IMAGES_PATH = "/var/www/html/images/profiles/";	
	
	public String addImage(InputStream inputStream) throws Exception{
		try {
			BufferedImage image = ImageIO.read(inputStream);
			if (image.getWidth() > 500 || image.getHeight() > 500) {
				throw new Exception("Image width/height cant be higher than 500x500.");
			}
			String id = String.valueOf(System.currentTimeMillis());
			String fileName = id + ".png";
			File file = new File(PROFILE_IMAGES_PATH + fileName);
			ImageIO.write(image, "png", file);
			return id;
		} catch (Exception e) {
			throw new Exception("Image upload failed.");
		} 
	}

}
