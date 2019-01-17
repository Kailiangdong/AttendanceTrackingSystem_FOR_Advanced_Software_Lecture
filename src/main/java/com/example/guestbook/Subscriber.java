package com.example.guestbook;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.google.appengine.repackaged.com.google.common.base.Pair;

public class Subscriber {
	// K: sid, V: bid
	// How to get sid ??? Group Number + Week ???
	/**
	 * @Parameter: K: sid => uniqual session id of each group V: bid =>
	 */
	private Map<Integer, Integer> root_database;

	// temp queue
	// com.google.appengine.repackaged.org.apache.lucene.util.PriorityQueue;
	// private PriorityQueue q;
	// java.util.PriorityQueue;
	private PriorityQueue<Integer> queue;

	public Subscriber() {
		root_database = new HashMap<Integer, Integer>();
	}

	/**
	 * Recive the pairs from sever and stor in the local database
	 */
	public void recive_tupel(Pair<Integer, Integer> tupel) {
		// Second element in the tupel is the sid, and the first element in
		// tupel is the bid
		// Key is the 'sid', value is the 'bid'.
		root_database.put(tupel.second, tupel.first);
	}
	
	/**
	 * Recive the queue of block e_s from sever and stor in the local database
	 */
	public void recive_blcok(PriorityQueue<Integer> e_s){
		for(Integer e: e_s){
			queue.add(e);
		}
	}
}
