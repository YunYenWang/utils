package com.cht.util;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileBlockingQueueTest {
	FileBlockingQueue queue;
	File path = new File("/tmp/queue");
	String prefix = "messages";
	
	@Before
	public void before() throws Exception {
		queue = new FileBlockingQueue(path, prefix);
	}
	
	@After
	public void after() throws Exception {
		queue.close();
	}	
	
	@Test
	public void testSave() throws Exception {
		long last = System.currentTimeMillis();		

		byte[] bytes = new byte[100];
		
		int count = 100000;		
		for (int i = 0;i < count;i++) {		
			queue.put(bytes);
		}
		
		System.out.printf("speed: %,d / s\n", count * 1000L / (System.currentTimeMillis() - last));
		
		Thread.sleep(2000L);
	}
	
	@Test
	public void testGetOne() throws Exception {
		long last = System.currentTimeMillis();
		
		queue.take();
		
		System.out.printf("elapse: %,d ms\n", System.currentTimeMillis() - last);
		
		Thread.sleep(2000L);
	}
	
	@Test
	public void testDeleteAll() throws Exception {
		long last = System.currentTimeMillis();
		
		int count = 0;
		for (;;) {
			byte[] bytes = queue.peek();
			if (bytes == null) {
				break;
			}
			
			queue.remove();			
			
			count += 1;
		}
		
		System.out.printf("speed: %,d / s\n", count * 1000L / (System.currentTimeMillis() - last));
		
		Thread.sleep(2000L);
	}
}
