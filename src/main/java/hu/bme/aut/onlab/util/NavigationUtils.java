package hu.bme.aut.onlab.util;

import hu.bme.aut.onlab.dto.out.PagesDto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
		
		int lastPage = getPageOfElement(elementCount);
		
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

	public static List<PagesDto> getListOfPagesDto(String link, int currentPage, int elementCount) {
		List<PagesDto> pagesDtos = new ArrayList<>();

		int lastPage = getPageOfElement(elementCount);

		pagesDtos.add(new PagesDto(FIRST_PAGE_TEXT, link, false));

		for (int i = currentPage - 3; i < currentPage; i++) {
			if (i > 0) {
				pagesDtos.add(new PagesDto(String.valueOf(i), link + "/" + i, false));
			}
		}

		pagesDtos.add(new PagesDto(String.valueOf(currentPage), link + "/" + currentPage, true));

		for (int i = currentPage + 1; i <= currentPage + 3; i++) {
			if (i <= lastPage) {
				pagesDtos.add(new PagesDto(String.valueOf(i), link + "/" + i, false));
			}
		}

		pagesDtos.add(new PagesDto(LAST_PAGE_TEXT, link + "/" + lastPage, false));

		return pagesDtos;
	}

	public static int getPageOfElement(int elementCount) {
		return (int) Math.ceil(elementCount / ((double) ELEMENTS_PER_PAGE));
	}
	
}
