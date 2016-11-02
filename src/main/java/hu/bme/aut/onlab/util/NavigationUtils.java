package hu.bme.aut.onlab.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class NavigationUtils {

	public static final int ELEMENTS_PER_PAGE = 10;
	public static final String FIRST_PAGE_TEXT = "«";
	public static final String LAST_PAGE_TEXT = "»";
	
	private static JSONObject getPageJsonObject(String text, String link, boolean active) {
		JSONObject pageJson = new JSONObject();
		pageJson.put("text", text);
		pageJson.put("link", link);
		pageJson.put("active", active);
		return pageJson;
	}
	
	public static JSONArray getPagesJsonArray(String link, int currentPage, int elementCount) {
		JSONArray pagesJsonArray = new JSONArray();
		
		int lastPage = elementCount / ELEMENTS_PER_PAGE;
		
		pagesJsonArray.put(getPageJsonObject(FIRST_PAGE_TEXT, link, false));
		
		for (int i=currentPage-3; i<currentPage; i++) {
			if (i > 0) {
				pagesJsonArray.put(getPageJsonObject(String.valueOf(i), link + "/" + i, false));
			}
		}
		
		pagesJsonArray.put(getPageJsonObject(String.valueOf(currentPage), link + "/" + currentPage, true));
		
		for (int i=currentPage+1; i<=currentPage+3; i++) {
			if (i <= lastPage) {
				pagesJsonArray.put(getPageJsonObject(String.valueOf(i), link + "/" + i, false));
			}
		}
		
		pagesJsonArray.put(getPageJsonObject(LAST_PAGE_TEXT, link + "/" + lastPage, false));
		
		return pagesJsonArray;
	}
	
}
