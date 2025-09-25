package basicAlgorithms;

import java.util.*;

public class DataStructures {
    // 1. Heap (Priority Queue)
    // 최소 힙
    public static PriorityQueue<Integer> minHeap() {
        return new PriorityQueue<>();
    }
    // 최대 힙
    public static PriorityQueue<Integer> maxHeap() {
        return new PriorityQueue<>(Collections.reverseOrder());
    }

    // 2. HashMap / HashSet
    public static Map<String, Integer> hashMap() {
        return new HashMap<>();
    }
    public static Set<Integer> hashSet() {
        return new HashSet<>();
    }

    // 3. TreeMap / TreeSet (정렬 기반)
    public static TreeMap<Integer, String> treeMap() {
        return new TreeMap<>();
    }
    public static TreeSet<Integer> treeSet() {
        return new TreeSet<>();
    }

    // 4. Trie (Prefix Tree)
    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;
    }
    class Trie {
        private final TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
            }
            node.isEndOfWord = true;
        }

        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (!node.children.containsKey(c)) return false;
                node = node.children.get(c);
            }
            return node.isEndOfWord;
        }

        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (!node.children.containsKey(c)) return false;
                node = node.children.get(c);
            }
            return true;
        }
    }

    // 5. Segment Tree (구간합 예시)
    class SegmentTree {
        int[] tree;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 1, 0, n - 1);
        }

        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(arr, node * 2, start, mid);
                build(arr, node * 2 + 1, mid + 1, end);
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }

        public int query(int l, int r) {
            return query(1, 0, n - 1, l, r);
        }

        private int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) return 0; // 구간 밖
            if (l <= start && end <= r) return tree[node]; // 구간 안
            int mid = (start + end) / 2;
            return query(node * 2, start, mid, l, r)
                    + query(node * 2 + 1, mid + 1, end, l, r);
        }

        public void update(int idx, int val) {
            update(1, 0, n - 1, idx, val);
        }

        private void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                if (idx <= mid) update(node * 2, start, mid, idx, val);
                else update(node * 2 + 1, mid + 1, end, idx, val);
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }
    }
}
