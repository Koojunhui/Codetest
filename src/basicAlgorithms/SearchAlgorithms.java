package basicAlgorithms;

import java.util.*;

public class SearchAlgorithms {
    // BFS (Grid Version) → 최단 거리 배열 리턴
    public static int[][] BFS(int n, int m, int sx, int sy, int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        int[][] dist = new int[n][m]; // 최단거리 저장
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        q.offer(new int[]{sx, sy});
        visited[sx][sy] = true;
        dist[sx][sy] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] d : dirs) {
                int nx = cur[0] + d[0], ny = cur[1] + d[1];
                if (0 <= nx && nx < n && 0 <= ny && ny < m && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    dist[nx][ny] = dist[cur[0]][cur[1]] + 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return dist; // 각 좌표까지의 최소 거리
    }

    // BFS (Graph Node Version) → 탐색 순서 리턴
    public static List<Integer> BFS_Node(int start, List<List<Integer>> graph, boolean[] visited) {
        Queue<Integer> q = new LinkedList<>();
        List<Integer> order = new ArrayList<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            order.add(cur);
            for (int nxt : graph.get(cur)) {
                if (!visited[nxt]) {
                    visited[nxt] = true;
                    q.offer(nxt);
                }
            }
        }
        return order;
    }

    // DFS (Recursive) → 탐색 순서 리턴
    public static List<Integer> DFS(int node, boolean[] visited, List<List<Integer>> graph) {
        List<Integer> order = new ArrayList<>();
        dfsHelper(node, visited, graph, order);
        return order;
    }
    public static void dfsHelper(int node, boolean[] visited, List<List<Integer>> graph, List<Integer> order) {
        visited[node] = true;
        order.add(node);
        for (int nxt : graph.get(node)) {
            if (!visited[nxt]) dfsHelper(nxt, visited, graph, order);
        }
    }

    // DFS (Stack Iterative) → 탐색 순서 리턴
    public static List<Integer> DFS_Stack(int start, List<List<Integer>> graph, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> order = new ArrayList<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            if (!visited[cur]) {
                visited[cur] = true;
                order.add(cur);
                for (int nxt : graph.get(cur)) {
                    if (!visited[nxt]) stack.push(nxt);
                }
            }
        }
        return order;
    }

    // Dijkstra
    public static int[] dijkstra(int n, int start, List<List<int[]>> graph) {
        // graph.get(u) = { {v, cost}, ... }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], d = cur[1];
            if (d > dist[u]) continue;
            for (int[] edge : graph.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[v] > d + w) {
                    dist[v] = d + w;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }
        return dist;
    }

    // Union-Find
    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        void union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else { parent[rb] = ra; rank[ra]++; }
        }
    }

    // Topological Sort (Kahn’s Algorithm)
    public static List<Integer> topologicalSort(int n, List<List<Integer>> graph) {
        int[] indegree = new int[n];
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) indegree[v]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) q.offer(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : graph.get(u)) {
                if (--indegree[v] == 0) q.offer(v);
            }
        }
        return order;
    }
}
