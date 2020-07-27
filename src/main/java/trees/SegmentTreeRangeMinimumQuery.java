package trees;

public class SegmentTreeRangeMinimumQuery {

    int segmentTree[];

    public SegmentTreeRangeMinimumQuery(int arr[], int n) {

        // find the height of the tree
        int height = (int) Math.ceil(Math.log(n)/ Math.log(2));

        // find the maximum space required
        int maxSize = 2 * (int) Math.pow(2, height) - 1;

        // allocate space to segment tree
        segmentTree = new int[maxSize];

        constructSegmentTree(arr, 0, n - 1, 0);
    }

    private int constructSegmentTree(int[] arr, int start, int end, int index) {

        // if we only have one element in the segement tree then assign the value and return
        if(start == end) {
            segmentTree[index] = arr[start];
            return segmentTree[index];
        }

        // split and assign the minimum value from left and right node to current segment
        int midPoint = getMidPoint(start, end);
        segmentTree[index] = Math.min(
                                    constructSegmentTree(arr, start, midPoint, 2 * index + 1),
                                    constructSegmentTree(arr, midPoint + 1, end, 2 * index + 2)
                                    );
        return segmentTree[index];
    }

    private int getMidPoint(int start, int end) {
        return start + (end - start) / 2;
    }

    private int getMinimumInRange( int n, int rangeStart, int rangeEnd) {

        // bound checks
        if(rangeStart < 0 || rangeEnd > n - 1 || rangeStart > rangeEnd) {
            System.out.println("Invalid input range");
            return -1;
        }

        return getMinimumInRange(0, n - 1, rangeStart, rangeEnd, 0);
    }

    private int getMinimumInRange(int start, int end, int rangeStart, int rangeEnd, int index) {
        // if the segment of this node is part of given range, then return the min of the segment
        if(rangeStart <= start && rangeEnd >= end) {
            return segmentTree[index];
        }

        // if segement is outside the given range
        if(end < rangeStart || start > rangeEnd) {
            return Integer.MAX_VALUE;
        }

        // if part of the segment overlaps with the given range
        int midPoint = getMidPoint(start, end);
        return Math.min(
                getMinimumInRange(start, midPoint, rangeStart, rangeEnd, 2 * index + 1),
                getMinimumInRange(midPoint + 1, end, rangeStart, rangeEnd, 2 * index + 2)
                );
    }

    public static void main(String args[]) {
        int arr[] = {1, 3, 2, 7, 9, 11};
        int n = arr.length;

        SegmentTreeRangeMinimumQuery segmentTree = new SegmentTreeRangeMinimumQuery(arr, n);

        int rangeStart = 1;
        int rangeEnd = 5;
        System.out.println("Minimum of values in range [" +rangeStart+ "," + rangeEnd + "] is : " + segmentTree.getMinimumInRange(n, rangeStart, rangeEnd));

    }
}
