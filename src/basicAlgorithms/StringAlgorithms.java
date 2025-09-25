package basicAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class StringAlgorithms {
    // 1. KMP (Knuth-Morris-Pratt)
    // 패턴이 등장하는 모든 시작 인덱스 반환
    public static List<Integer> KMP(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length(), m = pattern.length();

        int[] lps = new int[m];
        computeLPS(pattern, lps);

        int i = 0, j = 0; // i = text index, j = pattern index
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == m) {
                    result.add(i - j); // 매칭 시작 위치
                    j = lps[j - 1];
                }
            } else {
                if (j > 0) j = lps[j - 1];
                else i++;
            }
        }
        return result;
    }
    private static void computeLPS(String pattern, int[] lps) {
        int len = 0, i = 1;
        lps[0] = 0;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else {
                if (len > 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
    }

    // 2. Rabin-Karp
    // 첫 번째 매칭 위치 (없으면 -1)
    public static int rabinKarp(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        if (m > n) return -1;

        int base = 256;
        int mod = 101; // 소수
        int h = 1;

        for (int i = 0; i < m - 1; i++) h = (h * base) % mod;

        int pHash = 0, tHash = 0;
        for (int i = 0; i < m; i++) {
            pHash = (base * pHash + pattern.charAt(i)) % mod;
            tHash = (base * tHash + text.charAt(i)) % mod;
        }

        for (int i = 0; i <= n - m; i++) {
            if (pHash == tHash) {
                if (text.substring(i, i + m).equals(pattern)) return i;
            }
            if (i < n - m) {
                tHash = (base * (tHash - text.charAt(i) * h) + text.charAt(i + 1)) % mod;
                if (tHash < 0) tHash += mod;
            }
        }
        return -1;
    }

    // 3. Z-Algorithm
    // 문자열 내에서 패턴이 등장하는 모든 위치 반환
    public static List<Integer> ZAlgorithm(String text, String pattern) {
        String s = pattern + "$" + text;
        int n = s.length();
        int[] Z = new int[n];
        computeZ(s, Z);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (Z[i] == pattern.length()) {
                result.add(i - pattern.length() - 1); // 매칭 시작 위치
            }
        }
        return result;
    }
    private static void computeZ(String s, int[] Z) {
        int n = s.length();
        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i <= R) Z[i] = Math.min(R - i + 1, Z[i - L]);
            while (i + Z[i] < n && s.charAt(Z[i]) == s.charAt(i + Z[i])) {
                Z[i]++;
            }
            if (i + Z[i] - 1 > R) {
                L = i; R = i + Z[i] - 1;
            }
        }
    }
}
