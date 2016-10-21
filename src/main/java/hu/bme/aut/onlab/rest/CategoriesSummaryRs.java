package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.view.CategoriesSummaryBean;
import org.json.JSONArray;

import javax.ejb.EJB;
import javax.persistence.Tuple;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by N. Vilagos.
 */
@Path("/categories_summary")
public class CategoriesSummaryRs extends BaseRs {
    @EJB
    private CategoriesSummaryBean categoriesSummaryBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedCategory() {
        List<Tuple> data = categoriesSummaryBean.getView();

        List<List<Object>> dataAsFieldValues = generateListOfObjects(data);

        List<Integer> subcategories = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<List<Object>> correctedData = formatResultList(dataAsFieldValues, 0, subcategories, false);

        Map<Integer, List<String>> fields = new HashMap<>();
        fields.put(0, Arrays.asList(
                "id",
                "title",
                "subcategories"
                ));
        fields.put(1, Arrays.asList(
                "id",
                "title",
                "desc",
                "topicCount",
                "postCount",
                "lastTopicId",
                "lastTitle",
                "lastPostId",
                "lastMemberId",
                "lastPoster",
                "lastDate"));
        JSONArray result = generateJson(fields, correctedData);

        // Generate subcategory link //
        //   1. Read subcategory ids
        List<Object> subcategoryIds = getJSONValues(result, Arrays.asList("subcategories", "id"));
        //   2. Create links
        List<Object> subcategoryLinks = new ArrayList<>();
        for (Object id : subcategoryIds) {
            subcategoryLinks.add("#/subcategory/" + id);
        }
        //   3. Insert the values
        addJSONValues(result, Collections.singletonList("subcategories"), "subcategoryLink", subcategoryLinks);
        //   4. Delete the ids
        deleteJSONFields(result, Arrays.asList("subcategories", "id"));


        // Generate post link //
        //   1. Read topic and post ids
        List<Object> topicIds = getJSONValues(result, Arrays.asList("subcategories", "lastTopicId"));
        List<Object> postIds = getJSONValues(result, Arrays.asList("subcategories", "lastPostId"));
        //   2. Create links
        List<Object> postLinks = new ArrayList<>();
        for (int i=0; i < topicIds.size(); i++) {
            postLinks.add("#/topic/" + topicIds.get(i) + "/" + postIds.get(i));
        }
        //   3. Insert the values
        addJSONValues(result, Collections.singletonList("subcategories"), "postLink", postLinks, EnumJSONConditions.IS_FIELD_IN_JSON_OBJECT, "lastDate");
        //   4. Delete the ids
        deleteJSONFields(result, Arrays.asList("subcategories", "lastTopicId"));
        deleteJSONFields(result, Arrays.asList("subcategories", "lastPostId"));


/*

        // Generate user link //
        //   1. Read user ids
        List<Object> userIds = getJSONValues(result, Arrays.asList("subcategories", "lastMemberId"));
        //   2. Create links
        List<Object> userLinks = new ArrayList<>();
        for (Object id : userIds) {
            userLinks.add("#/user/" + id);
        }
        //   3. Insert the values
        addJSONValues(result, Collections.singletonList("subcategories"), "userLink", userLinks);
        //   4. Delete the ids
        deleteJSONFields(result, Arrays.asList("subcategories", "id"));
*/


        return result.toString();

    }

}