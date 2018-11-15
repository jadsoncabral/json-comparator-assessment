package br.jad.comparator.commons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.stream.Collectors;

/**
 * Compare two decoded Base64 JSON as String
 */
public class ComparatorHelper {

    private ComparatorHelper() { }

    /**
     * This method is used to compare two JSON Strings.
     * @param left This is the first parameter to compare method
     * @param right This is the second parameter to compare method
     * @return String This returns the result of comparison.
     */
    public static String compare(String left, String right) throws JSONException {
        JSONObject leftNode = new JSONObject(left);
        JSONObject rightNode = new JSONObject(right);

        JSONObject result = new JSONObject();
        JSONCompareResult compareResult = JSONCompare.compareJSON(leftNode, rightNode, JSONCompareMode.STRICT);

        if (compareResult.passed()) {
            result.put("result", "IS EQUAL");
        } else if (leftNode.length() != rightNode.length()) {
            result.put("result", "IS NOT EQUAL SIZE");
        } else {
            JSONObject failed = new JSONObject();
            failed.put("failedFieldsMatch", new JSONArray(
                    compareResult.getFieldFailures().stream().map(FieldComparisonFailure::getField).collect(Collectors.toList())
            ));

            JSONObject mising = new JSONObject();
            failed.put("missingFields", new JSONArray(
                    compareResult.getFieldMissing().stream().map(FieldComparisonFailure::getExpected).collect(Collectors.toList())
            ));

            JSONObject unexpected = new JSONObject();
            failed.put("unexpectedFields", new JSONArray(
                    compareResult.getFieldUnexpected().stream().map(FieldComparisonFailure::getActual).collect(Collectors.toList())
            ));

            result.put("result", new JSONArray( new Object[] { failed, mising, unexpected }) );
        }

        return result.toString();
    }
}
