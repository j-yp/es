package elasticSearchDemo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Demo {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("e");
		set.add("f");
		set.add("b");
		set.add("a");
		set.add("i");
		set.add("h");
		set.add("g");
		set.add("c");
		set.add("d");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		System.out.println(year);
		System.out.println(set);
	}
}
