package com.pinky.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.core.io.Resource;


public class CounterAPIUtil {
	
	public static TreeMap<String, Integer> countTextOccurance(Resource resource) throws IOException {
		//String fileName = "H:/Workspace/Evaluation/OptusEvaluation/resources/Sample.txt";

        String inputLine = null;
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        BufferedReader bufferedReader = null;
        try {
        	InputStream is = resource.getInputStream();
        	bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((inputLine = bufferedReader.readLine()) != null) {
            	String[] words = inputLine.split("[ \n\t\r.,;:!?(){}]");
                for (int wordCounter = 0; wordCounter < words.length; wordCounter++) {
                    String key = words[wordCounter].toLowerCase();
                    if (key.length() > 0) {
                        if (map.get(key) == null) {
                            map.put(key, 1);
                        }
                        else {
                            int value = map.get(key).intValue();
                            value++;
                            map.put(key, value);
                         }
                    }
                }
            }
        } catch (IOException error) {
            System.out.println("Invalid File");  
        }
        finally {
        	bufferedReader.close();
        }
        return map;
	}
	
    public static List<Entry<String, Integer>> sortByValueInDecreasingOrder(Map<String, Integer> wordMap) { 
    	Set<Entry<String, Integer>> entries = wordMap.entrySet(); 
    	//System.out.println("List of entry map  " + entries);
    	List<Entry<String, Integer>> list = new ArrayList <Entry<String, Integer>>(entries); 
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    		public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
    			return (o2.getValue()).compareTo(o1.getValue());
    		}
    	});
    	return list; 
    }
    


}
