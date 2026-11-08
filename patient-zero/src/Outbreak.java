import java.util.*;

public class Outbreak {

    // Mission 5a
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        int[] take = new int[n];
        int[] skip = new int[n];

        dfsDp(0, -1, nb, cost, take, skip);

        boolean[] tested = new boolean[n];
        rebuild(0, -1, true, nb, take, skip, tested);

        return tested;
    }

    private static void dfsDp(int u, int p, int[][] nb, int[] cost, int[] take, int[] skip) {
        take[u] = cost[u];
        skip[u] = 0;
        for (int v : nb[u]) {
            if (v == p) continue;
            dfsDp(v, u, nb, cost, take, skip);
            take[u] += Math.min(take[v], skip[v]);
            skip[u] += take[v];
        }
    }

    private static void rebuild(int u, int p, boolean pTested, int[][] nb, int[] take, int[] skip, boolean[] tested) {
        if (!pTested) {
            tested[u] = true;
        } else {
            tested[u] = take[u] <= skip[u];
        }
        for (int v : nb[u]) {
            if (v == p) continue;
            rebuild(v, u, tested[u], nb, take, skip, tested);
        }
    }

    // Mission 5b
    public static int longestChain(int n, int[][] nb) {
        int[] maxChain = new int[1];
        dfsChain(0, -1, nb, maxChain);
        return maxChain[0];
    }

    private static int dfsChain(int u, int p, int[][] nb, int[] maxChain) {
        int max1 = 0, max2 = 0;
        for (int v : nb[u]) {
            if (v == p) continue;
            int h = dfsChain(v, u, nb, maxChain) + 1;
            if (h > max1) {
                max2 = max1;
                max1 = h;
            } else if (h > max2) {
                max2 = h;
            }
        }
        maxChain[0] = Math.max(maxChain[0], max1 + max2);
        return max1;
    }
}