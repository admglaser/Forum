package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.CategoryBean;
import hu.bme.aut.onlab.beans.SubcategoryBean;
import hu.bme.aut.onlab.model.Category;
import hu.bme.aut.onlab.model.Category_;
import hu.bme.aut.onlab.model.Subcategory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.persistence.Tuple;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by N. Vilagos.
 */
@Path("/test")
public class TestRs extends BaseRs {


    @EJB
    CategoryBean categoryBean;

    @EJB
    SubcategoryBean subcategoryBean;

    @GET
    public String testString() {
        return "It lives!";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> listLocalCategories() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setTitle("Egyes");
        Category category2 = new Category();
        category2.setId(2);
        category2.setTitle("Kettes");

        List<Category> list = new ArrayList<>();
        list.add(category1);
        list.add(category2);
        return list;
    }

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> listRemoteCategories() {
        return categoryBean.findAllEntity();
    }

    @GET
    @Path("/category-{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getRemoteCategory(@PathParam("id") Integer id) {
        return categoryBean.findEntityById(Category_.id, id);
    }

    @GET
    @Path("/subcategory_combined")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedSubCategory() {
        List<Tuple> data = subcategoryBean.testJoinedFind();
        JSONArray result = new JSONArray();
        for (Tuple object : data){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", object.get(0));
            jsonObject.put("desc", object.get(1));
            jsonObject.put("category_title", object.get(2));
            jsonObject.put("topics", object.get(3));
            result.put(jsonObject);
        }

        return result.toString();
    }

    @GET
    @Path("/category_combined")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedCategory() {
        List<Tuple> data = categoryBean.testJoinedFind();
        List<List<Object>> dataAsFieldValues = generateListOfObjects(data);

        List<Integer> subcategories = Arrays.asList(2, 3);
        List<List<Object>> correctedData = formatResultList(dataAsFieldValues, 0, subcategories, true);

        Map<Integer, List<String>> fields = new HashMap<>();
        fields.put(0, Arrays.asList("title", "subcategories"));
        fields.put(1, Arrays.asList("subcat_title", "subcat_desc"));
        JSONArray result = generateJson(fields, correctedData);

        return result.toString();
    }

    @GET
    @Path("/modified_category_combined")
    @Produces(MediaType.APPLICATION_JSON)
    public String listModifiedRemoteCombinedCategory() {
        List<Tuple> data = categoryBean.testJoinedFind();
        List<List<Object>> dataAsFieldValues = generateListOfObjects(data);

        List<Integer> subcategories = Arrays.asList(2, 3);
        List<List<Object>> correctedData = formatResultList(dataAsFieldValues, 0, subcategories, true);

        Map<Integer, List<String>> fields = new HashMap<>();
        fields.put(0, Arrays.asList("title", "subcategories"));
        fields.put(1, Arrays.asList("subcat_title", "subcat_desc"));
        JSONArray result = generateJson(fields, correctedData);

        // Get the titles of the subcategories
        List<Object> subcategoryTitles = getJSONValues(result, Arrays.asList("subcategories", "subcat_title"));

        // Add the given titles with new key ("cloned_title" in "subcategories")
        addJSONValues(result, Arrays.asList("subcategories"), "cloned_title", subcategoryTitles);

        // Delete the original key + value pairs (key: "title" in "subcategories")
        deleteJSONFields(result, Arrays.asList("subcategories", "subcat_title"));

        // Count the subcategories in the JSON file
        Integer countSubcategories = countJSONField(result, Arrays.asList("subcategories", "cloned_title"));
        //   And add the result to the JSON
        result.put(
                ( new JSONObject() ).put("number_of_subcategories", countSubcategories)
        );

        return result.toString();
    }

    @GET
    @Path("/full_category")
    @Produces(MediaType.APPLICATION_JSON)
    public String listFullCategory() {
        JSONArray result = new JSONArray();
        List<Category> categories = categoryBean.findAllEntity();
        for (Category category : categories) {
            JSONObject element = new JSONObject();
            element.put("title", category.getTitle());

            JSONArray subcategories = new JSONArray();
            for (Subcategory subcategory : category.getSubcategoriesById()) {
                JSONObject subcatElement = new JSONObject();
                subcatElement.put("subcat_title", subcategory.getTitle());
                subcategories.put(subcatElement);
            }

            element.put("subcategories", subcategories);

            result.put(element);
        }
        return result.toString();
    }
}
