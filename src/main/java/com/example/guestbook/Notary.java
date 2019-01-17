package com.example.guestbook;

import java.util.ArrayList;
import java.util.Random;

public class Notary {
	public ArrayList<Double> attendance_database;
	
	
	public Notary() {
		Random r = new Random();
		// Initial seed
		double h_0 = r.nextDouble();
		attendance_database.add(h_0);
		
		
	}
}
