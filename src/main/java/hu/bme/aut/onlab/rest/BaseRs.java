package hu.bme.aut.onlab.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Tuple;
import java.lang.reflect.Field;
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
     * @param listOfFieldValues         The listOfFieldValues of data to put in the sub list.
     * @param collectionFieldIndexes    The field
     */
    private static void addSublist(List<Object> list, List<Object> listOfFieldValues, List<Integer> collectionFieldIndexes) {
        List<Object> subList = new ArrayList<Object>();
        list.add(list.size(), subList);
        for (Integer inputIndex : collectionFieldIndexes) {
            subList.add(subList.size(), listOfFieldValues.get(inputIndex));
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

    private static <T> boolean isGivenValueContained(T[] searchableIterable, T valueToSearch) {
        return isGivenValueContained(Arrays.asList(searchableIterable), valueToSearch);
    }

    private static <T> boolean isGivenValueContained(Iterable<T> searchableIterable, T valueToSearch) {
        for (T fieldToCompare : searchableIterable) {
            if (fieldToCompare.equals(valueToSearch)) {
                return true;
            }
        }
        return false;
    }

    private static JSONArray addJSONValues(JSONArray data, List<String> toId, String key, List<Object> values, List<Boolean> writtenValues, EnumJSONConditions condition, Object conditionValue) {
        if (toId != null) {
            // Only search if value is given and search is necessary
            if (writtenValues != null && values.size() == writtenValues.size()) {
                for (Object subData : data) {
                    if (subData instanceof JSONObject) {
                        JSONObject castedSubData = (JSONObject) subData;
                        if (toId.size() > 0) {
                            // Search for the given field
                            String fieldToSearch = toId.get(0);
                            if ( isGivenValueContained(JSONObject.getNames(castedSubData), fieldToSearch) ) {
                                Object fieldValue = castedSubData.get(fieldToSearch);
                                // Search for further fields recursively
                                if (fieldValue instanceof JSONArray) {
                                    JSONArray castedArrayCandidate = (JSONArray) fieldValue;
                                    List<String> newToId = new ArrayList<>(toId);
                                    newToId.remove(0);
                                    addJSONValues(castedArrayCandidate, newToId, key, values, writtenValues, condition, conditionValue);
                                }
                            }
                        } else {
                            if ( (condition != EnumJSONConditions.IS_FIELD_IN_JSON_OBJECT)
                                  ||  isGivenValueContained(JSONObject.getNames(castedSubData), conditionValue)) {
                                // Actual add will be performed
                                int valueIndex = 0;
                                boolean isFound = false;
                                while ((!isFound) && valueIndex < writtenValues.size()) {
                                    // Searches for the first false value in the writtenValues list.
                                    //   Thus this means that that value with the same index has not been added to the given JSONArray yet.
                                    Object valueToPut = values.get(valueIndex);
                                    Boolean hasWritten = writtenValues.get(valueIndex);
                                    if (hasWritten.equals(Boolean.FALSE)) {
                                        // If the false value has been found:
                                        //    1. Change the value from false to true in the writtenValues list.
                                        writtenValues.set(valueIndex, Boolean.TRUE);
                                        //    2. Put the values to the appropriate JSONObject.
                                        castedSubData.put(key, valueToPut);
                                        //    3. Finish the while loop.
                                        isFound = true;
                                    }
                                    valueIndex++;
                                }
                                if (!isFound) {
                                    // Not enough values to put has been given.
                                    throw new IndexOutOfBoundsException("Not enough values has been given to put. Size: " + values.size());
                                }
                            }
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("The following argument is illegal: " + "writtenValues" + ". It must not be null and the size of it must be the same as size of the following List: " + "values" + ".");
            }
        } else {
            throw new IllegalArgumentException("The given ID list must not be null. Use empty List to add value to the root.");
        }

        return data;
    }

    private static List<Object> operationOnJSONFields(JSONArray data, List<String> fromId, EnumJSONFieldOperationType operationType) throws OperationNotSupportedException {
        // Declarations
        //   Only used upon EnumJSONFieldOperationType.GET
        List<Object> result = new ArrayList<>();

        if (operationType == EnumJSONFieldOperationType.COUNT) {
            result.add(0);
        }

        if (fromId != null && fromId.size() > 0) {
            String fieldToSearch = fromId.get(0);
            // Only search if value is given and search is necessary
            for (Object subData : data) {
                if (subData instanceof JSONObject) {
                    JSONObject castedSubData = (JSONObject) subData;
                    // Search for the given field
                    for (String fieldName : JSONObject.getNames(castedSubData)) {
                        if (fieldName.equals(fieldToSearch)) {
                            Object fieldValue = castedSubData.get(fieldName);
                            if (fromId.size() > 1) {
                                // Search for further fields recursively
                                if (fieldValue instanceof JSONArray) {
                                    JSONArray castedArrayCandidate = (JSONArray) fieldValue;
                                    List<String> newToId = new ArrayList<>(fromId);
                                    newToId.remove(0);
                                    for (Object searchedValues : operationOnJSONFields(castedArrayCandidate, newToId, operationType)) {
                                        result.add(searchedValues);
                                    }
                                }
                            } else {
                                if (operationType == EnumJSONFieldOperationType.GET) {
                                    // Actual add will be performed
                                    result.add(fieldValue);
                                } else if (operationType == EnumJSONFieldOperationType.COUNT) {
                                    // Actual counting will be performed
                                    result.set(0, ((Integer) result.get(0)) + 1);
                                } else if (operationType == EnumJSONFieldOperationType.DELETE) {
                                    // Actual delete will be performed
                                    castedSubData.remove(fieldToSearch);
                                } else {
                                    // No other operation is implemented
                                    throw new OperationNotSupportedException("No such operation is supported: " + operationType);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("The given ID list must contain element(s).");
        }

        return result;
    }

    protected static JSONArray addJSONValues(JSONArray data, List<String> toId, String key, List<Object> values) {
        return addJSONValues(data, toId, key, values, EnumJSONConditions.NONE, null);
    }

    protected static JSONArray addJSONValues(JSONArray data, List<String> toId, String key, List<Object> values, EnumJSONConditions condition, Object conditionValue) {
        // Boolean: is written yet?
        List<Boolean> writtenValues = new ArrayList<>();
        for (Object object : values)
        {
            writtenValues.add(Boolean.FALSE);
        }
        return addJSONValues(data, toId, key, values, writtenValues, condition, conditionValue);
    }

    protected static List<Object> getJSONValues(JSONArray data, List<String> fromId) {
        try {
            return operationOnJSONFields(data, fromId, EnumJSONFieldOperationType.GET);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException("Could not execute the operation.", e);
        }
    }

    protected static Integer countJSONField(JSONArray data, List<String> id) {
        try {
            List<Object> result = operationOnJSONFields(data, id, EnumJSONFieldOperationType.COUNT);
            Integer count = 0;
            for (Object level_count : result) {
                count += (Integer) level_count;
            }
            return count;
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException("Could not execute the operation.", e);
        }
    }

    protected static void deleteJSONFields(JSONArray data, List<String> id) {
        try {
            operationOnJSONFields(data, id, EnumJSONFieldOperationType.DELETE);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException("Could not execute the operation.", e);
        }
    }

    /**
     * Formats a list of Object, with the required changes.
     *   If collection is included in the result list then the separate results will be merged and the changed data will be put in a collection.
     *   ID of the root entity in the list of the Object is required to process.
     * @param data                      The data to work on.
     * @param idFieldIndex              The index of the ID of the root entity in the individual Objects.
     * @param collectionFieldIndexes    The field indexes that are in a collection in the individual Objects.
     * @param isIdDeletable             Whether delete the ID of the root entity from the result or not.
     * @return                          The reformatted data.
     */
    protected static List<List<Object>> formatResultList(List<List<Object>> data, Integer idFieldIndex, List<Integer> collectionFieldIndexes, boolean isIdDeletable) {
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

        for (List<Object> fieldList : data) {
            // Initialize components //
            List<Object> instance = new ArrayList<>();
            int outputIndex = 0;

            // Create the list of the not-list type fields' number
            List<Integer> notListFields = createNumberSequenceWithExclusions(0, fieldList.size(), collectionFieldIndexes);

            // Check for containment //
            int foundIdListIndex = findFistEqualityIn2Depth(result, idResultIndex, fieldList.get(idFieldIndex));

            int indexOfList = Collections.min(collectionFieldIndexes);

            if (foundIdListIndex < 0) {
                // The whole data must be stored //

                // Manage non-list type fields //
                for (Integer inputIndex : notListFields) {
                    instance.add(outputIndex++, fieldList.get(inputIndex));
                }

                // Manage list fields //
                // Create list(s)
                List<Object> listOfLists = new ArrayList<Object>();
                instance.add(indexOfList, listOfLists);

                // Add the fields to the new list
                addSublist(listOfLists, fieldList, collectionFieldIndexes);

                // Add the whole data to the result
                result.add(instance);
            } else {
                // Only the list elements must be stored //

                // Manage list fields
                List<Object> list = ((List<Object>) result.get(foundIdListIndex).get(indexOfList));
                addSublist(list, fieldList, collectionFieldIndexes);
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

    protected static List<List<Object>> generateListOfObjects(List<Tuple> tupleList) {
        List<List<Object>> result = new ArrayList<>();
        int index = 0;
        for (Tuple tuple : tupleList) {
            List<Object> listOfFields = new ArrayList<>();

            int fieldIndex = 0;
            final int numberOfFields = tuple.getElements().size();
            for (int i=0; i < numberOfFields; i++) {
                Object fieldValue = tuple.get(i);
                listOfFields.add(fieldIndex++, fieldValue);
            }

            result.add(index++, listOfFields);
        }
        return result;
    }

    protected static List<List<Object>> generateListOfObjectsFromEntities(List<? extends Object> objectList) throws IllegalAccessException {
        List<List<Object>> result = new ArrayList<>();
        int index = 0;
        for (Object currentObject : objectList) {
            List<Object> listOfFields = new ArrayList<>();

            int fieldIndex = 0;
            for (Field field : currentObject.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(currentObject);
                listOfFields.add(fieldIndex++, fieldValue);
            }
            result.add(index++, listOfFields);
        }
        return result;
    }


    private enum EnumJSONFieldOperationType {
        GET,
        COUNT,
        DELETE;
    }

    public enum EnumJSONConditions {
        NONE,
        IS_FIELD_IN_JSON_OBJECT
    }
}
