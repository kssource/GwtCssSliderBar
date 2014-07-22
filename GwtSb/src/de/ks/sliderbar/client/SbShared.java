package de.ks.sliderbar.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.Random;

public class SbShared {

	private static final String[] loremPixelRubrics = {
			"abstract", "animals", "business", "cats", "city", "food", "nightlife", 
			"fashion", "people", "nature", "sports", "technics", "transport"
	};
	
	
	// return 10(random ordered)  http://lorempixel.com urls from random rubric
	public static List<String> getRandomImageUrl(int imgWidth, int imgHeigth){
		ArrayList<String> out = new ArrayList<>();
		// get random rubric
		int randomRubricIndex = Random.nextInt(loremPixelRubrics.length);
		String rubric = loremPixelRubrics[randomRubricIndex];
		
		// create List 1 to 10
		ArrayList<Integer> numList = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			numList.add((i+1));
		}
		
		// shuffle numList
		for(int index = 0; index < numList.size(); index += 1) {  
		    Collections.swap(numList, index, index + Random.nextInt(numList.size() - index));  
		}
		
		// create urls
		for (Integer integer : numList) {
			StringBuilder builder = new StringBuilder("http://lorempixel.com/");
			builder.append(imgWidth).append("/").append(imgHeigth);
			builder.append("/").append(rubric).append("/").append(integer.intValue());
			out.add(builder.toString());
		}
		
		return out;
	}
	
	
}
