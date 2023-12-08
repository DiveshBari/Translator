package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static HashMap getAsHashMap(java.lang.Object aJsonObject) {
		HashMap aHashMapTo;
		Iterator aIterator;
		JsonArray aJsonArray;
		JsonElement aJsonElement;
		java.lang.Object aObject;
		
		HashMap aHashMapFrom = new HashMap();
		Gson aGson = new Gson();
		
		if (aJsonObject.getClass().toString().contains("JsonObject")) {
			aHashMapFrom = (HashMap) aGson.fromJson((JsonElement) aJsonObject, new HashMap().getClass());
		} else if(aJsonObject.getClass().toString().contains("String")) {
			//aHashMapFrom!=cast(HashMap,aGson!.fromJson(cast(java.lang.String,aJsonObject!), new HashMap().getClass(),err=*next)) ; break
			aHashMapFrom = (HashMap) aGson.fromJson((String) aJsonObject, new HashMap().getClass());
		}
		
		aHashMapTo = new HashMap();
		aIterator = aHashMapFrom.keySet().iterator();
		while(aIterator.hasNext()) {
			String aKey = (String) aIterator.next(); 
			aObject = aHashMapFrom.get(aKey);
			aJsonElement = aGson.toJsonTree(aObject);
			
			if (aJsonElement.isJsonObject()) {
				aHashMapTo.put(aKey, getAsHashMap(aJsonElement.getAsJsonObject()));
			} else if(aJsonElement.isJsonArray() && aKey=="method_getAsArrayList_HashMap"){
				aHashMapTo.put(aKey,(java.util.ArrayList)aObject);
			} else if(aJsonElement.isJsonArray()){
				ArrayList arrTemp = getAsArrayList(aJsonElement.getAsJsonArray());
				aHashMapTo.put(aKey, arrTemp);
			} else if(aJsonElement.isJsonNull()){
				aHashMapTo.put(aKey,aObject);
			} else if(aObject.getClass().toString().contains("java.util.ArrayList")){
				aHashMapTo.put(aKey, (java.util.ArrayList)aObject);
			} else if(aJsonElement.isJsonPrimitive()){
				aHashMapTo.put(aKey,aObject);
			} else {
				aHashMapTo.put(aKey,aObject);
			}
		}
		return aHashMapTo;	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static ArrayList getAsArrayList(java.lang.Object aJsonArray) {
		Gson aGson = new Gson();
		HashMap aHashMapFrom = null;
		HashMap aHashMapTo;
		Object aObject;
		JsonElement aJsonElement;
		
		if (aJsonArray.getClass().toString().contains("JsonArray")) {
			JsonObject aJsonObject = new JsonObject();
			aJsonObject.add("method_getAsArrayList_HashMap",(JsonArray)aJsonArray);
			aHashMapFrom = getAsHashMap(aJsonObject);
			
		} else if(aJsonArray.getClass().toString().contains("String")) {
			String strVoid = "{\"method_getAsArrayList_HashMap\""+":"+aJsonArray.toString()+"}";
			aHashMapFrom = getAsHashMap(strVoid);
		}
		ArrayList aArrayListFrom = (ArrayList)aHashMapFrom.get("method_getAsArrayList_HashMap");
		ArrayList aArrayListTo = new ArrayList();
		Iterator aIterator = aArrayListFrom.iterator();
		while(aIterator.hasNext()) {
			aObject = aIterator.next();
			aJsonElement = aGson.toJsonTree(aObject);
			
			if (aJsonElement.isJsonObject()) {
				aArrayListTo.add(getAsHashMap(aJsonElement.getAsJsonObject()));
			} 
			else if(aJsonElement.isJsonArray()){
				aArrayListTo.add(getAsArrayList(aJsonElement.getAsJsonArray()));
			} 
			else if(aJsonElement.isJsonNull()){
				aArrayListTo.add(aObject);
			} 
			else if(aObject.getClass().toString().trim().contains("java.util.ArrayList")){
				aArrayListTo.add((ArrayList)aObject);
			} 
			else if(aJsonElement.isJsonPrimitive()){
				aArrayListTo.add(aObject);
			} 
			else {
				aArrayListTo.add(aObject);
			}
		}
		
		return aArrayListTo;
	}	
}
