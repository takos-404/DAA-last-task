import java.util.*;

public class Marker {

    // Mission 3a
    public static int[] prefixFunction(String p) {
        int m = p.length();
        int[] pi = new int[m];
        for (int i = 1, k = 0; i < m; i++) {
            while (k > 0 && p.charAt(i) != p.charAt(k)) {
                k = pi[k - 1];
            }
            if (p.charAt(i) == p.charAt(k)) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }

    // Mission 3b
    public static int[] findAll(String text, String p) {
        int n = text.length(), m = p.length();
        if (m == 0) return new int[0];

        int[] pi = prefixFunction(p);
        List<Integer> hits = new ArrayList<>();

        for (int i = 0, k = 0; i < n; i++) {
            while (k > 0 && text.charAt(i) != p.charAt(k)) {
                k = pi[k - 1];
            }
            if (text.charAt(i) == p.charAt(k)) {
                k++;
            }
            if (k == m) {
                hits.add(i - m + 1);
                k = pi[k - 1];
            }
        }

        int[] res = new int[hits.size()];
        for (int i = 0; i < res.length; i++) res[i] = hits.get(i);
        return res;
    }

    // Mission 4
    public static int sharedWords(String a, String b, int k) {
        if (a.length() < k || b.length() < k) return 0;

        long mod = 1000000007;
        long base = 131;
        long top = 1;
        for (int i = 1; i < k; i++) top = (top * base) % mod;

        Map<Long, List<Integer>> bMap = new HashMap<>();
        long hb = 0;
        for (int i = 0; i < k; i++) hb = (hb * base + b.charAt(i)) % mod;
        bMap.computeIfAbsent(hb, x -> new ArrayList<>()).add(0);

        for (int i = 1; i + k <= b.length(); i++) {
            hb = (hb - b.charAt(i - 1) * top % mod + mod) % mod;
            hb = (hb * base + b.charAt(i + k - 1)) % mod;
            bMap.computeIfAbsent(hb, x -> new ArrayList<>()).add(i);
        }

        int count = 0;
        long ha = 0;
        for (int i = 0; i < k; i++) ha = (ha * base + a.charAt(i)) % mod;
        count += checkMatch(a, 0, b, k, ha, bMap) ? 1 : 0;

        for (int i = 1; i + k <= a.length(); i++) {
            ha = (ha - a.charAt(i - 1) * top % mod + mod) % mod;
            ha = (ha * base + a.charAt(i + k - 1)) % mod;
            count += checkMatch(a, i, b, k, ha, bMap) ? 1 : 0;
        }

        return count;
    }

    private static boolean checkMatch(String a, int i, String b, int k, long ha, Map<Long, List<Integer>> bMap) {
        if (!bMap.containsKey(ha)) return false;
        for (int pos : bMap.get(ha)) {
            boolean match = true;
            for (int j = 0; j < k; j++) {
                if (a.charAt(i + j) != b.charAt(pos + j)) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        return false;
    }
}