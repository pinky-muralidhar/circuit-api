package com.pinky.test.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pinky.test.constants.CounterAPIConstants;
import com.pinky.test.domain.SearchTextRequest;
import com.pinky.test.domain.SearchTextResponse;
import com.pinky.test.facade.CounterAPIFacade;

@RestController
@EnableWebMvc
public class CounterAPIController {
	
	@Autowired
	CounterAPIFacade counterApiFacade;
	
	private static final Logger logger = Logger.getLogger(CounterAPIController.class);

	@RequestMapping(value=CounterAPIConstants.SEARCH, method = RequestMethod.POST)
	public @ResponseBody SearchTextResponse searchTextOccurances(@RequestBody SearchTextRequest searchTextReq) {
		
		SearchTextResponse searchTextResponse = null;
		if(logger.isDebugEnabled()){
			logger.debug(" Rest Operation to searchTextOccurances - STARTS. ");
		}
		try {
			String[] strarray = (String[]) searchTextReq.getSearchText().toArray(new String[searchTextReq.getSearchText().size()]);
			searchTextResponse = counterApiFacade.searchWordOccurance(strarray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("Rest Operation to searchTextOccurances completed its transaction - ENDS. ");
		}
		return searchTextResponse;

	}
	
	@RequestMapping(value=CounterAPIConstants.TOP+"/{limit}", method = RequestMethod.GET,
			headers = "Accept=application/json", produces = "text/csv")
	public void listTopWords(@PathVariable int limit, HttpServletResponse response) {
		if(logger.isDebugEnabled()){
			logger.debug("Rest Operation to listTopWords - STARTS. ");
		}
		try {
			ArrayList<String> rows = counterApiFacade.listTopOccurngWords(limit);
			response.setContentType("text/csv");
			String reportName = "TextOccurances.csv";
			response.setHeader("Content-disposition", "attachment;filename="+reportName);
			Iterator<String> iter = rows.iterator();
			while (iter.hasNext()) {
				String outputString = (String) iter.next();
				response.getOutputStream().print(outputString);
			}
	 
			response.getOutputStream().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("Rest Operation to listTopWords completed its transaction - ENDS. ");
		}
		
	}
}
