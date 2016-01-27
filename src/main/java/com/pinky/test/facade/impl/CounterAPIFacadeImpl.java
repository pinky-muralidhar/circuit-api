package com.pinky.test.facade.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.pinky.test.domain.SearchTextResponse;
import com.pinky.test.facade.CounterAPIFacade;
import com.pinky.test.util.CounterAPIUtil;

@Service
public class CounterAPIFacadeImpl  implements CounterAPIFacade{
	

	@Autowired
	private ResourceLoader resourceLoader;
	
	private static final Logger logger = Logger.getLogger(CounterAPIFacadeImpl.class);

	public SearchTextResponse searchWordOccurance(String[] searchText) {
		if(logger.isDebugEnabled()){
			logger.debug("CounterAPIFacadeImpl.searchWordOccurance() - STARTS. ");
		}
		// Find Key words
		TreeMap<String, Integer> textOccInFileMap;
		SearchTextResponse searchTextResponse = new SearchTextResponse();
		Map<String, Integer> finalSearchMap = new HashMap<String, Integer>();
		try {
			Resource resource = resourceLoader.getResource("classpath:Sample.txt");
			textOccInFileMap = CounterAPIUtil.countTextOccurance(resource);
	        System.out.println("Search the array of values " + searchText.toString());
	        List<Map<String, Integer>> finalSearchWordlist = new ArrayList <Map<String, Integer>>();
	        for (String searchWord: searchText) {
	        	System.out.println(searchWord + " --> " + (textOccInFileMap.get(searchWord.toLowerCase()) == null? 0: textOccInFileMap.get(searchWord.toLowerCase())));
	        	finalSearchMap = new HashMap<String, Integer>();
	        	finalSearchMap.put(searchWord, textOccInFileMap.get(searchWord.toLowerCase()) == null? 0: textOccInFileMap.get(searchWord.toLowerCase()));
	        	finalSearchWordlist.add(finalSearchMap);
	        }
	        searchTextResponse.setCounts(finalSearchWordlist);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("CounterAPIFacadeImpl.searchWordOccurance() - ENDS. ");
		}
		return searchTextResponse;
	}

	public ArrayList<String> listTopOccurngWords(int limit) {
        System.out.println("Top " + limit + " values ");
       
        TreeMap<String, Integer> textOccInFileMap;
        ArrayList<String> rows = new ArrayList<String>();
		try {
			Resource resource = resourceLoader.getResource("classpath:Sample.txt");
			textOccInFileMap = CounterAPIUtil.countTextOccurance(resource);
	        List<Entry<String, Integer>> sortedTextByValue = CounterAPIUtil.sortByValueInDecreasingOrder(textOccInFileMap);
	        for (int i=0; i < limit; i++) {
	        	Map.Entry<String, Integer> entry = sortedTextByValue.get(i);
	        	rows.add(entry.getKey()+"|"+entry.getValue());
	        	rows.add("\n");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug(String.format("List of top %s is %s", limit, rows.toString()));
		}
		return rows;
	}

}
