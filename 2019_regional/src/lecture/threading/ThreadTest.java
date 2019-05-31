package lecture.threading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ThreadTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		Vector<Integer> array = new Vector<>();
		// VLookup
		// Lookup
		// Key -> Value
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("A", 100);
		map.put("B", 70);
		map.put("C", 50);
		
		System.out.println(map.get("A"));
		
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				array.add(i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}).start(); 
		
		for (int i = 0; i < 10; i++) {
			array.add(i);
			Thread.sleep(100);
		}
		
		System.in.read();
		
		System.out.println(array.size());
		
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
		}
	}

}
