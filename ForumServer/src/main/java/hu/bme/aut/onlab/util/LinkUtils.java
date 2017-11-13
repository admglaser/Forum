package hu.bme.aut.onlab.util;

public class LinkUtils {

	private static final String PROFILE_IMAGES_PATH = "images/profiles/";
	private static final String PROFILE_IMAGE_EXT = "png";
	private static final String PROFILE_IMAGE_DEFAULT_ID = "default";

	public static String getProfilePictureLink(String id) {
		return PROFILE_IMAGES_PATH + (id == null ? PROFILE_IMAGE_DEFAULT_ID : id) + "." + PROFILE_IMAGE_EXT;
	}
	
}
