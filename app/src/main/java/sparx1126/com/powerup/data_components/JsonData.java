package sparx1126.com.powerup.data_components;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JsonData {

    protected Map<String, String> stringValuesMap;
    protected Map<String, Integer> intValuesMap;
    protected Map<String, Float> floatValuesMap;
    protected Map<String, Boolean> booleanValuesMap;
    protected Map<String, JSONObject> jsonObjectsMap;
    protected Map<String, JSONArray> jsonArraysMap;

    public JsonData() {
        stringValuesMap = new HashMap<>(0);
        intValuesMap = new HashMap<>(0);
        floatValuesMap = new HashMap<>(0);
        booleanValuesMap = new HashMap<>(0);
        jsonObjectsMap = new HashMap<>(0);
        jsonArraysMap = new HashMap<>(0);
    }

    public Map<String, String> getStringValuesMap() { return stringValuesMap; }
    public Map<String, Integer> getIntValuesMap() { return intValuesMap; }
    public Map<String, Boolean> getBooleanValuesMap() { return booleanValuesMap; }
    public Map<String, Float> getFloatValuesMap() { return floatValuesMap; }
    public Map<String, JSONObject> getJsonObjectsMap() { return jsonObjectsMap; }
    public Map<String, JSONArray> getJsonArraysMap() { return jsonArraysMap; }

    public void restoreFromJsonString(String _jsonString) {
        try {
            JSONObject jsonObj = new JSONObject(_jsonString);
            restoreFromJsonObject(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void restoreFromJsonObject(JSONObject _jsonObj) {
        for (String key : stringValuesMap.keySet()) {
            stringValuesMap.put(key, getString(_jsonObj, key));
        }
        for (String key : intValuesMap.keySet()) {
            intValuesMap.put(key, getInt(_jsonObj, key));
        }
        for (String key : floatValuesMap.keySet()) {
            floatValuesMap.put(key, getFloat(_jsonObj, key));
        }
        for (String key : booleanValuesMap.keySet()) {
            booleanValuesMap.put(key, getBoolean(_jsonObj, key));
        }
        for (String key : jsonObjectsMap.keySet()) {
            jsonObjectsMap.put(key, getJsonObject(_jsonObj, key));
        }
        for (String key : jsonArraysMap.keySet()) {
            jsonArraysMap.put(key, getJsonArray(_jsonObj, key));
        }
    }

    protected int getInt(JSONObject _jsonObj, String _key) {
        int rtnData = 0;

        try {
            rtnData = _jsonObj.getInt(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    protected float getFloat(JSONObject _jsonObj, String _key) {
        float rtnData = (float)0.0;

        try {
            //https://ourcodeworld.com/articles/read/367/how-to-get-a-float-value-from-a-json-object-in-java
            rtnData = BigDecimal.valueOf(_jsonObj.getDouble(_key)).floatValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    protected boolean getBoolean(JSONObject _jsonObj, String _key) {
        boolean rtnData = false;

        try {
            rtnData = _jsonObj.getBoolean(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    protected String getString(JSONObject _jsonObj, String _key) {
        String rtnData = "";

        try {
            rtnData = _jsonObj.get(_key).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    protected JSONObject getJsonObject(JSONObject _jsonObj, String _key) {
        JSONObject rtnData = null;

        try {
            rtnData = _jsonObj.getJSONObject(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    protected JSONArray getJsonArray(JSONObject _jsonObj, String _key) {
        JSONArray rtnData = null;

        try {
            rtnData = _jsonObj.getJSONArray(_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnData;
    }

    public String getJsonString() {
        JSONObject jsonObj = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : stringValuesMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Integer> entry : intValuesMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Float> entry : floatValuesMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Boolean> entry : booleanValuesMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, JSONObject> entry : jsonObjectsMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, JSONArray> entry : jsonArraysMap.entrySet()) {
                jsonObj.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj.toString();
    }
}
