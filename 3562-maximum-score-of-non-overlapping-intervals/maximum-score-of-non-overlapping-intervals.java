import java.util.*;

class Solution {
    // av
    static class Interval {
        int l, r, weight, id;
        Interval(int l, int r, int weight, int id) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.id = id;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            arr[i] = new Interval(interval.get(0), interval.get(1), interval.get(2), i);
        }

        // Sort intervals by right endpoint; if tied, by left endpoint
        Arrays.sort(arr, (a, b) -> {
            if (a.r != b.r) return Integer.compare(a.r, b.r);
            return Integer.compare(a.l, b.l);
        });

        // Binary search predecessor array: prev[i] is index of last non-overlapping interval before i
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            int low = 0, high = i - 1, best = -1;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                if (arr[mid].r < arr[i].l) {
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            prev[i] = best;
        }

        // dp[k][i]: Best solution choosing up to k intervals using prefix [0...i]
        // State representation: long weight, List<Integer> indices
        long[][][] dpWeight = new long[5][n + 1][];
        // We will store best choice indices at each step:
        List<Integer>[][] dpIndices = new List[5][n + 1];

        for (int k = 0; k <= 4; k++) {
            dpIndices[k][0] = new ArrayList<>();
        }

        for (int k = 1; k <= 4; k++) {
            for (int i = 1; i <= n; i++) {
                Interval curr = arr[i - 1];
                int p = prev[i - 1];

                // Option 1: Skip current interval
                long weightSkip = dpWeight[k][i - 1] != null ? dpWeight[k][i - 1][0] : 0;
                List<Integer> indicesSkip = dpIndices[k][i - 1];

                // Option 2: Take current interval
                long weightTake = curr.weight + (p != -1 && dpWeight[k - 1][p + 1] != null ? dpWeight[k - 1][p + 1][0] : 0);
                List<Integer> indicesTake = new ArrayList<>(p != -1 && dpIndices[k - 1][p + 1] != null ? dpIndices[k - 1][p + 1] : Collections.emptyList());
                indicesTake.add(curr.id);
                Collections.sort(indicesTake);

                // Compare Options
                if (weightTake > weightSkip) {
                    dpWeight[k][i] = new long[]{weightTake};
                    dpIndices[k][i] = indicesTake;
                } else if (weightSkip > weightTake) {
                    dpWeight[k][i] = new long[]{weightSkip};
                    dpIndices[k][i] = indicesSkip;
                } else {
                    // Tie-breaker: Lexicographically smaller indices array
                    if (isLexicographicallySmaller(indicesTake, indicesSkip)) {
                        dpWeight[k][i] = new long[]{weightTake};
                        dpIndices[k][i] = indicesTake;
                    } else {
                        dpWeight[k][i] = new long[]{weightSkip};
                        dpIndices[k][i] = indicesSkip;
                    }
                }
            }
        }

        List<Integer> bestRes = dpIndices[4][n];
        int[] ans = new int[bestRes.size()];
        for (int i = 0; i < bestRes.size(); i++) {
            ans[i] = bestRes.get(i);
        }
        return ans;
    }

    private boolean isLexicographicallySmaller(List<Integer> a, List<Integer> b) {
        if (a == null) return false;
        if (b == null) return true;
        int len = Math.min(a.size(), b.size());
        for (int i = 0; i < len; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }
        return a.size() < b.size();
    }
}