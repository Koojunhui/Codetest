package basicAlgorithms;

import java.util.*;

public class GreedyAlgorithms {
    // Activity Selection (회의실 배정)
    // 리턴: 선택된 활동 개수 (보통 정답으로 count만 필요함)
    public static int activitySelection(int[][] activities) {
        Arrays.sort(activities, Comparator.comparingInt(a -> a[1])); // 종료시간 기준 정렬
        int count = 0, lastEnd = -1;
        for (int[] act : activities) {
            if (act[0] >= lastEnd) {
                count++;
                lastEnd = act[1];
            }
        }
        return count;
    }

    // Minimum Coins (동전 거스름돈)
    // 리턴: 필요한 최소 동전 개수, 불가능하면 -1
    public static int minCoins(int[] coins, int amount) {
        Arrays.sort(coins);
        int count = 0;
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] <= amount) {
                count += amount / coins[i];
                amount %= coins[i];
            }
        }
        return (amount == 0) ? count : -1;
    }

    // Fractional Knapsack (부분 배낭 문제)
    // 리턴: 담을 수 있는 최대 가치 (double)
    public static double fractionalKnapsack(int W, int[] weights, int[] values) {
        int n = weights.length;
        List<int[]> items = new ArrayList<>();
        for (int i = 0; i < n; i++) items.add(new int[]{weights[i], values[i]});

        // 무게당 가치 내림차순
        items.sort((a, b) -> Double.compare((double)b[1]/b[0], (double)a[1]/a[0]));

        double totalValue = 0;
        for (int[] item : items) {
            if (W >= item[0]) {
                W -= item[0];
                totalValue += item[1];
            } else {
                totalValue += (double)item[1] * W / item[0];
                break;
            }
        }
        return totalValue;
    }

    // Huffman Coding
    // 리턴: 전체 최소 비용 (int)
    public static int huffmanCoding(int[] freq) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int f : freq) pq.offer(f);

        int cost = 0;
        while (pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            cost += a + b;
            pq.offer(a + b);
        }
        return cost;
    }
}
