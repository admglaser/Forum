package hu.bme.aut.onlab.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

/**
 * Created by N. Vilagos.
 */
public class BaseRs {

    private static JSONArray generateJson(Map<Integer, List<String>> fields, Integer key, List<Tuple> data) {
        JSONArray result = new JSONArray();

        List<String> currentFields = fields.get(key);
        int numberOfFields = currentFields.size();
        int sizeOfData = data.size();

        // Iterate through the data
        for (Tuple currentObject : data) {
            // Create the JSON object contain
            JSONObject json = new JSONObject();

            //Iterate through the fields of the current data
            for (int j = 0; j < numberOfFields; j++) {
                if (currentObject.get(j) instanceof List) {
                    // If the current data is a list (of Tuple), then do the generation recursively.
                    //   Thus, safe to cast.
                    json.put(currentFields.get(j), generateJson(fields, key + 1, (List<Tuple>) currentObject.get(j)));
                } else {
                    json.put(currentFields.get(j), currentObject.get(j));
                }
            }

            // Put the data to the result
            result.put(json);
        }

        return result;
    }

    protected static JSONArray generateJson(Map<Integer, List<String>> fields, List<Tuple> data){
        return generateJson(fields, 0, data);
    }

    protected static JSONArray generateSimpleJson(Map<Integer, List<String>> fields, List<Tuple> data) {
        JSONArray result = new JSONArray();

        List<String> currentFields = fields.get(0);
        int numberOfFields = currentFields.size();

        // Iterate through the data
        for (Tuple currentObject : data) {
            // Create the JSON object contain
            JSONObject json = new JSONObject();

            //Iterate through the fields of the current data
            for (int j = 0; j < numberOfFields; j++) {
                json.put(currentFields.get(j), currentObject.get(j));
            }

            // Put the data to the result
            result.put(json);
        }

        return result;
    }

}
