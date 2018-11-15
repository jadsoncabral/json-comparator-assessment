package br.jad.comparator;

import br.jad.comparator.commons.ComparatorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComparatorHelperTest {

    @Test(expected = JSONException.class)
    public void compare_InvalidJson_ExceptionThrown() throws Exception {
        ComparatorHelper.compare("{ \"name\": test wrong\" }", "{ \"name\": \"test right\" }");
    }

    @Test
    public void compare_ValidJson_IsEqual() throws Exception {
        String result = ComparatorHelper.compare("{ \"name\": \"John Snow\" }", "{ \"name\": \"John Snow\" }");

        assertEquals(new JSONObject(result).get("result"), "IS EQUAL");
    }

    @Test
    public void compare_ValidJson_IsNotEqualSize() throws Exception {
        String result = ComparatorHelper.compare("{ \"name\": \"John Snow\", \"from\": \"GOT\" }", "{ \"name\": \"John Show\" }");

        assertEquals(new JSONObject(result).get("result"), "IS NOT EQUAL SIZE");
    }

    @Test
    public void compare_ValidJson_FieldMatchFailure() throws Exception {
        String result = ComparatorHelper.compare("{ \"name\": \"John Snow\", \"from\": \"GOT\" }", "{ \"name\": \"John Show\", \"from\": \"GOT\" }");

        JSONObject objectResult = new JSONObject(result);
        JSONArray resultArray = objectResult.getJSONArray("result");

        int idx = 0;
        for (int i=0; i<resultArray.length(); i++) {
            if (resultArray.getJSONObject(i).has("failedFieldsMatch")) {
                idx = i;
                break;
            }
        }

        assertEquals(resultArray.getJSONObject(idx).get("failedFieldsMatch"), new JSONArray(new Object[] { "name" }));
    }

    @Test
    public void compare_ValidJson_FieldMissingFailure() throws Exception {
        String result = ComparatorHelper.compare("{ \"name\": \"John Snow\", \"from\": \"GOT\" }", "{ \"name\": \"John Snow\", \"other\": \"FOO\" }");

        JSONObject objectResult = new JSONObject(result);
        JSONArray resultArray = objectResult.getJSONArray("result");

        int idx = 0;
        for (int i=0; i<resultArray.length(); i++) {
            if (resultArray.getJSONObject(i).has("missingFields")) {
                idx = i;
                break;
            }
        }

        assertEquals(resultArray.getJSONObject(idx).get("missingFields"), new JSONArray(new Object[] { "from" }));
    }

    @Test
    public void compare_ValidJson_FieldUnexpctedFailure() throws Exception {
        String result = ComparatorHelper.compare("{ \"name\": \"John Snow\", \"from\": \"GOT\" }", "{ \"name\": \"John Snow\", \"other\": \"FOO\" }");

        JSONObject objectResult = new JSONObject(result);
        JSONArray resultArray = objectResult.getJSONArray("result");

        int idx = 0;
        for (int i=0; i<resultArray.length(); i++) {
            if (resultArray.getJSONObject(i).has("unexpectedFields")) {
                idx = i;
                break;
            }
        }

        assertEquals(resultArray.getJSONObject(idx).get("unexpectedFields"), new JSONArray(new Object[] { "other" }));
    }
}
