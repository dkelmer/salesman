import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

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
	static boolean DEBUG = false;
	Edge[] edges, tree;
	int[] sets;
	int n, m;
	ArrayList<City> tsmPath = new ArrayList<City>();

	private double MST() {
		double w = 0;
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
	
	double processRoute(Route r) {
		ArrayList<City> cities = r.cities;
		int N = cities.size();
		n = N;
		m = N*N;
		edges = new Edge[m];
		sets = new int[n];
		for (int i = 0; i < n; i++) {
			sets[i] = i;
		}
		tree = new Edge[n-1];
		int edgeCount = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				City a = cities.get(i);
				City b = cities.get(j);
				int u = a.id-1; //0 based.
				int v = b.id-1; //0 based.
				double w = a.getDistance(b);
				edges[edgeCount++] = new Edge(u, v, w);
			}
		}
		Arrays.sort(edges);
		double mstWeight = MST();
		if (DEBUG) {
			System.out.println("MST length: " + mstWeight);
			System.out.println("TSM upper bound: " + mstWeight*2);
		}

		//uncommment to print out MST path.
		/*
		for (int i = 0; i < n - 1; i++)
			System.out.println((tree[i].u + 1) + "-" + (tree[i].v + 1) + " " + tree[i].w);
		*/
		return 2*mstWeight;
	}
	
	ArrayList<Integer> path() {
		boolean[] visited = new boolean[n];
		ArrayList<ArrayList<Edge>> adjList = new ArrayList<ArrayList<Edge>>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) adjList.add(new ArrayList<Edge>());
		
		for (int i = 0; i < n - 1; i++) {
			int u = tree[i].u;
			int v = tree[i].v;
			adjList.get(u).add(tree[i]);
			adjList.get(v).add(tree[i]);
		}		
		dfs(visited, adjList, path, 0);
		return path;
	}
	
	//gets preorder path.
	void dfs(boolean[] visited, ArrayList<ArrayList<Edge>> adjList, ArrayList<Integer> path, int curr) {
		visited[curr] = true;
		path.add(curr);
		ArrayList<Edge> neighbors = adjList.get(curr);
		for (Edge e : neighbors) {
			int dest = (e.u == curr)? e.v : e.u; //get the other end of the edge.
			if (!visited[dest])
				dfs(visited, adjList, path, dest);
		}
	}
}

class Edge implements Comparable<Edge> {
	public int u, v;
	public double w;

	public Edge(int u, int v, double w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}

	public int compareTo(Edge e2) {
		return (int)(w - e2.w);
	}
}