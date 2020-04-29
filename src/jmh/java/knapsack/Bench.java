package knapsack;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import knapsack.impl.BruteForce;
import knapsack.impl.BestFirstSearch;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = { "-Xms2G", "-Xmx2G" })
@Warmup(iterations = 3)
@Measurement(iterations = 8)
public class Bench {

	@Param({ "10" })
	private int NE;

	private ArrayList<Item> items;
	private BestFirstSearch bfs;
	private BruteForce backtrack;

	@Setup
	public void setup() {
		int capacity = 1000;
		items = new ArrayList<Item>();
		for (int i = 0; i < 10; i++) {
			items.add(Util.item(100, 100));
		}
		bfs = new BestFirstSearch(items, capacity);
		backtrack = new BruteForce(items, capacity);
	}

	@Benchmark
	public void bfs(Blackhole bh) {
		for (int i = 0; i < NE; i++) {
			bfs.solve();
		}
	}

	@Benchmark
	public void backtrack(Blackhole bh) {
		for (int i = 0; i < NE; i++) {
			backtrack.solve();
		}
	}

}
