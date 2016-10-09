package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.view.CategoriesSummaryBean;
import org.json.JSONArray;

import javax.ejb.EJB;
import javax.persistence.Tuple;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by N. Vilagos.
 */
@Path("/categories_summary")
public class CategoriesSummaryRs extends BaseRs {
    @EJB
    CategoriesSummaryBean categoriesSummaryBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedCategory() {
        List<Tuple> data = categoriesSummaryBean.getView();

        List<List<Object>> dataAsFieldValues = generateListOfObjects(data);

        List<Integer> subcategories = Arrays.asList(2, 3, 4, 5, 6, 7, 8);
        List<List<Object>> correctedData = formatResultList(dataAsFieldValues, 0, subcategories, true);

        Map<Integer, List<String>> fields = new HashMap<>();
        fields.put(0, Arrays.asList(
                "title",
                "subcategories"
                ));
        fields.put(1, Arrays.asList(
                "title",
                "desc",
                "topicCount",
                "postCount",
                "lastTitle",
                "lastPoster",
                "lastDate"));
        JSONArray result = generateJson(fields, correctedData);

        return result.toString();

    }
}
