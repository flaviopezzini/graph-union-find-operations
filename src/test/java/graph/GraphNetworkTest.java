package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphNetworkTest {
	
	@Test
	public void testSizeValidation() {
		try {
			// negative size network
			new GraphNetwork(-1);
			fail("It should have thrown an exception");
		} catch (IllegalArgumentException iae) {
			// do nothing, this is expected
		}
		try {
			// empty network
			new GraphNetwork(0);
			// valid size network
			new GraphNetwork(1);
		} catch (Throwable e) {
			fail("It should not have thrown an exception");
		}
	}
	
	@Test
	public void testConnect() {
		GraphNetwork network = new GraphNetwork(0);
		try {
			network.union(1, 2);
			fail("It should have thrown an exception that the capacity has reached the limit");
		} catch (GraphNetwork.StackOverflow re) {
			// do nothing, this is expected
		}

		GraphNetwork network2 = new GraphNetwork(2);
		try {
			network2.union(1, 2);
			network2.union(1, 3);
			fail("It should have thrown an exception that the capacity has reached the limit");
		} catch (GraphNetwork.StackOverflow re) {
			// do nothing, this is expected
		}
		GraphNetwork network3 = new GraphNetwork(3);
		try {
			network3.union(1, 2);
			network3.union(1, 3);
		} catch (GraphNetwork.StackOverflow re) {
			fail("It should not have thrown an exception, the size is 3 and there are 3 elements");
		}
	}
	
	@Test
	public void testAreConnected() {
		GraphNetwork network = new GraphNetwork(8);
		network.union(1, 2);
		network.union(6, 2);
		network.union(2, 4);
		network.union(5, 8);
		network.union(4, 8);
		assertTrue(network.areConnected(1, 4));
		assertTrue(network.areConnected(1, 6));
		assertTrue(network.areConnected(1, 2));
		assertTrue(network.areConnected(5, 8));
		assertFalse(network.areConnected(3, 5));
		assertTrue(network.areConnected(4, 5));
		assertTrue(network.areConnected(1, 5));
	}

	@Test
	public void testAreConnectedWeightedTrees() {
		GraphNetwork network = new GraphNetwork(10);
		network.union(4, 3);
		network.union(3, 8);

		assertTrue(network.areConnected(3, 8));
	}
}