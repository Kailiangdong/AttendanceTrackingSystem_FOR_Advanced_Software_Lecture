package com.example.guestbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import com.google.appengine.repackaged.com.google.common.base.Pair;
//import com.google.appengine.repackaged.org.apache.lucene.util.PriorityQueue;

public class Notary {
	// h: hash chain
	private ArrayList<Double> attendance_database;
	// K: sid, V: bid
	// How to get sid ??? Group Number + Week ???
	/**
	 * @Parameter: K: sid => uniqual session id of each group V: bid =>
	 */
	private Map<Integer, Integer> root_database;

	// Should be changed ...
	private ArrayList<Subscriber> subscribers;

	// temp queue
	// com.google.appengine.repackaged.org.apache.lucene.util.PriorityQueue;
	// private PriorityQueue q;
	// java.util.PriorityQueue;
	private PriorityQueue<Integer> queue;

	public Notary() {
		attendance_database = new ArrayList<Double>();
		subscribers = new ArrayList<Subscriber>();
		Random r = new Random();
		// Initial seed
		double h_0 = r.nextDouble();
		attendance_database.add(h_0);

		root_database = new HashMap<Integer, Integer>();
		queue = new PriorityQueue<Integer>();
	}

	// Each group will get a sid when the session starts
	// The bid and sid are sent from RasperryPi to Notary
	public void initialOfSession(Pair<Integer, Integer> tupel) {
		// Second element in the tupel is the sid, and the first element in
		// tupel is the bid
		// Key is the 'sid', value is the 'bid'.
		root_database.put(tupel.second, tupel.first);
		send_to_subscribers(tupel);
	}

	/**
	 * Process working with the incomming hash code 'e' from RaspberryPi, which
	 * will be stored in queue
	 * 
	 * @Param e: a hash value, which is the result of hashing using an
	 *        immatriculation id 'imat' and the session id 'sid' by raspberryPi
	 */
	public void input_hashcode(int e) {
		queue.add(e);
	}

	/**
	 * Send tupel to all subscriber
	 */
	public void send_to_subscribers(Pair<Integer, Integer> tupel) {
		for (Subscriber r : subscribers) {
			r.recive_tupel(tupel);
		}
	}
	
	/**
	 * Send block of e (the temp queue) to all subscriber
	 */
	public void send_to_subscribers(PriorityQueue<Integer> queue) {
		for (Subscriber r : subscribers) {
			r.recive_blcok(queue);
		}
	}
}
