package com.pinky.test.facade;

import java.io.IOException;
import java.util.ArrayList;

import com.pinky.test.domain.SearchTextResponse;

public interface CounterAPIFacade {
	
	public SearchTextResponse searchWordOccurance(String[] searchText);
	
	public ArrayList<String> listTopOccurngWords(int limit) throws IOException;

}
