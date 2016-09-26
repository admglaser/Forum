package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.model.Category;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@Path("test")
@RequestScoped
public class TestRs {


    @GET
    public String testString() {
        return "It lives!";
    }

    @GET
    @Path("json")
    @Produces("application/json")
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
}
