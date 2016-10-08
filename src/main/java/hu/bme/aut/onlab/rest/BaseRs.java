package hu.bme.aut.onlab.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Tuple;
import java.util.*;

/**
 * Created by N. Vilagos.
 */
public class BaseRs {

    /**
     * Generates a {@link JSONArray} object from the given data.
     *   Recursive upon multiple lists.
     * @param fields    The names of the separate fields. The different keys describes the different lists of data.
     * @param key       The key to the fields to decide which list of string will be used.
     * @param data      The data to put into JSON.
     * @return          The generated {@link JSONArray} object.
     */
    private static JSONArray generateJson(Map<Integer, List<String>> fields, Integer key, List<List<Object>> data) {
        JSONArray result = new JSONArray();

        List<String> currentFields = fields.get(key);
        int numberOfFields = currentFields.size();
        int sizeOfData = data.size();

        // Iterate through the data
        for (List<Object> currentObject : data) {
            // Create the JSON object contain
            JSONObject json = new JSONObject();

            //Iterate through the fields of the current data
            for (int j = 0; j < numberOfFields; j++) {
                if (currentObject.get(j) instanceof List) {
                    // If the current data is a list (of Tuple), then do the generation recursively.
                    //   Thus, safe to cast.
                    JSONArray array = generateJson(fields, key + 1, (List<List<Object>>) currentObject.get(j));
                    json.put(currentFields.get(j), array);
                } else {
                    json.put(currentFields.get(j), currentObject.get(j));
                }
            }

            // Put the data to the result
            result.put(json);
        }

        return result;
    }

    /**
     * Add sub list to a list.
     * @param list                      The root list to extend.
     * @param tuple                     The tuple of data to put in the sub list.
     * @param collectionFieldIndexes    The field
     */
    private static void addSublist(List<Object> list, Tuple tuple, List<Integer> collectionFieldIndexes) {
        List<Object> subList = new ArrayList<Object>();
        list.add(list.size(), subList);
        for (Integer inputIndex : collectionFieldIndexes) {
            subList.add(subList.size(), tuple.get(inputIndex));
        }
    }

    /**
     * Searches for the given object's value in the given list.
     * @param searchList    The list to search in.
     * @param indexOfField  The index of the field of each list to compare with the given object.
     * @param comparable    The object to compare.
     * @return              The index of the given list where the given object's value has been found. Returns -1 if the given object's value is not found in the given list.
     */
    private static int findFistEqualityIn2Depth(List<List<Object>> searchList, Integer indexOfField, Object comparable) {
        int idIndex = -1;
        for (List<Object> storedData : searchList) {
            if (storedData.get(indexOfField).equals(comparable)) {
                idIndex = searchList.indexOf(storedData);
            }
        }
        return idIndex;
    }

    /**
     * Creates a list of ascending number sequence with the given start and quantity, removing the given exclusions.
     * @param startingNumber    The first (and smallest) number of the sequence.
     * @param quantity          The number of the elements of the initial sequence (before removing exclusions).
     * @param valuesToRemove    The numbers to remove from the initial sequence.
     * @return                  The list of ascending number sequence with the given start and quantity, removing the given exclusions.
     */
    private static List<Integer> createNumberSequenceWithExclusions(int startingNumber, int quantity, List<Integer> valuesToRemove) {
        List<Integer> notListFields = new ArrayList<>();
        for (int i=0; i < quantity; i++) {
            notListFields.add(i, startingNumber + i);
        }
        notListFields.removeAll(valuesToRemove);
        return notListFields;
    }

    /**
     * Formats a list of {@link Tuple} to the list of list of {@link Object}s, with the required changes.
     *   If collection is included in the result list then the separate results will be merged and the changed data will be put in a collection.
     *   ID of the root entity in the list of {@link Tuple} is required to process.
     * @param data                      The data to work on.
     * @param idFieldIndex              The index of the ID of the root entity in the individual {@link Tuple}s.
     * @param collectionFieldIndexes    The field indexes that are in a collection in the individual {@link Tuple}s.
     * @param isIdDeletable             Whether delete the ID of the root entity from the result or not.
     * @return                          The reformatted data.
     */
    protected static List<List<Object>> formatResultList(List<Tuple> data, Integer idFieldIndex, List<Integer> collectionFieldIndexes, boolean isIdDeletable) {
        List<List<Object>> result = new ArrayList<>();

        int idResultIndex = 0;
        if (Collections.min(collectionFieldIndexes) < idFieldIndex) {
            for (Integer integer : collectionFieldIndexes) {
                if (integer <= idFieldIndex) {
                    idResultIndex--;
                }
            }
            // +1: The list is an element too.
            idResultIndex = idResultIndex + idFieldIndex + 1;
        }

        for (Tuple tuple : data) {
            // Initialize components //
            List<Object> instance = new ArrayList<>();
            int outputIndex = 0;

            // Create the list of the not-list type fields' number
            List<Integer> notListFields = createNumberSequenceWithExclusions(0, tuple.getElements().size(), collectionFieldIndexes);

            // Check for containment //
            int foundIdListIndex = findFistEqualityIn2Depth(result, idResultIndex, tuple.get(idFieldIndex));

            int indexOfList = Collections.min(collectionFieldIndexes);

            if (foundIdListIndex < 0) {
                // The whole data must be stored //

                // Manage non-list type fields //
                for (Integer inputIndex : notListFields) {
                    instance.add(outputIndex++, tuple.get(inputIndex));
                }

                // Manage list fields //
                // Create list(s)
                List<Object> listOfLists = new ArrayList<Object>();
                instance.add(indexOfList, listOfLists);

                // Add the fields to the new list
                addSublist(listOfLists, tuple, collectionFieldIndexes);

                // Add the whole data to the result
                result.add(instance);
            } else {
                // Only the list elements must be stored //

                // Manage list fields
                List<Object> list = ((List<Object>) result.get(foundIdListIndex).get(indexOfList));
                addSublist(list, tuple, collectionFieldIndexes);
            }
        }

        if (isIdDeletable) {
            for (List<Object> objects : result) {
                objects.remove(idResultIndex);
            }
        }

        return result;
    }

    /**
     * Generates a {@link JSONArray} object from the given data.
     * @param fields    The names of the separate fields. The different keys describes the different lists of data.
     * @param data      The data to put into JSON.
     * @return          The generated {@link JSONArray} object.
     */
    protected static JSONArray generateJson(Map<Integer, List<String>> fields, List<List<Object>> data){
        return generateJson(fields, 0, data);
    }

    /**
     * Generates a simple {@link JSONArray} object from the given data.
     *   Multiple lists are not supported.
     * @param fields    The names of the separate fields. The different keys describes the different lists of data.
     * @param data      The data to put into JSON.
     * @return          The generated {@link JSONArray} object.
     */
    @Deprecated
    protected static JSONArray generateSimpleJson(Map<Integer, List<String>> fields, List<List<Object>> data) {
        JSONArray result = new JSONArray();

        List<String> currentFields = fields.get(0);
        int numberOfFields = currentFields.size();

        // Iterate through the data
        for (List<Object> currentObject : data) {
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
