package moteurJeu;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;

import affichage.*;
import ia.*;
import map.*;

public class Maintest_v1 {

	public static void main(String[] args) {
		
		
		MoteurJeu j = new MoteurJeu();
		j.jeu();
		
		SortedSet<Map.Entry<String, Double>> sortedset = new TreeSet<Map.Entry<String, Double>>(
	            new Comparator<Map.Entry<String, Double>>() {
	                @Override
	                public int compare(Map.Entry<String, Double> e1,
	                        Map.Entry<String, Double> e2) {
	                    return e1.getValue().compareTo(e2.getValue());
	                }
	            });

	  //sortedset.addAll(myMap.entrySet());


	    SortedMap<String, Double> myMap = new TreeMap<String, Double>();
	    myMap.put("a", 10.0);
	    myMap.put("b", 9.0);
	    myMap.put("c", 11.0);
	    myMap.put("d", 2.0);
	    sortedset.addAll(myMap.entrySet());
	    System.out.println(sortedset);
	    
	    // Output: [d=2.0, b=9.0, a=10.0, c=11.0]
		

	}
	

}
