package salesman;

import java.util.Arrays;

/*
 * Minimum spanning tree
 * @author Darko Aleksic
 */
class MinimumSpanningTree {
	/**
	 * Thanks goes to Gilbert Lee. Page 26 of 47 Compiled for the 2012 GNYR NYU
	 * ICPC Team Codebook
	 *
	 * Minimum Spanning Tree - Kruskal’s O(mlogm) (sorting edges)
	 *
	 * NOTE: Needs class Edge below
	 */
	Edge[] edges, tree;
	int[] sets;
	int n, m;

	private int MST() {
		int w = 0;
		int cnt = 0;
		for (int i = 0; i < m; i++) {
			int s1 = find(edges[i].u);
			int s2 = find(edges[i].v);
			if (s1 != s2) {
				union(s1, s2);
				w += edges[i].w;
				tree[cnt] = edges[i];
				cnt++;
			}
			if (cnt == n - 1)
				break;
		}
		if (cnt < n - 1)
			return 0; // or something meaningful (no tree)
		return w;
	}

	private void union(int s1, int s2) {
		// not sure if this max/min thingy is needed, I needed it somewhere
		sets[Math.min(s1, s2)] = Math.max(s1, s2);
	}

	private int find(int index) {
		if (sets[index] == index)
			return index;
		return sets[index] = find(sets[index]);
	}

	/* Minimum Spanning Tree example - UVa LA 2515 */
	void mstExample() {
		n = 3; // number of nodes
		m = 7; // number of edges
		int[][] input = { { 1, 2, 19 }, { 2, 3, 11 }, { 3, 1, 7 }, { 1, 3, 5 },
				{ 2, 3, 89 }, { 3, 1, 91 }, { 1, 2, 32 } };
		sets = new int[n];
		for (int i = 0; i < n; i++) {
			sets[i] = i;
		}
		edges = new Edge[m];

		for (int i = 0; i < m; i++) {
			int u = input[i][0] - 1; // 0-based!
			int v = input[i][1] - 1; // 0-based!
			int w = input[i][2];
			edges[i] = new Edge(u, v, w);
		}
		Arrays.sort(edges, 0, m);
		tree = new Edge[n - 1];
		System.out.println("MST length: " + MST());
		for (int i = 0; i < n - 1; i++)
			System.out.println((tree[i].u + 1) + "-" + (tree[i].v + 1) + " "
					+ tree[i].w);
	}

	public static void main(String args[]) {
		MinimumSpanningTree mst = new MinimumSpanningTree();
		mst.mstExample();
	}
}

class Edge implements Comparable<Edge> {
	public int u, v, w;

	public Edge(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	public int compareTo(Edge e2) {
		return w - e2.w;
	}
}