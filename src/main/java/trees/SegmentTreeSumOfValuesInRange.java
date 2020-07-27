package trees;

/**
 * Segment Tree to keep sum of a given range.
 */
public class SegmentTreeSumOfValuesInRange {

    // array to hold segment tree nodes
    int segmentTree[];

    public SegmentTreeSumOfValuesInRange(int arr[], int n) {

        // calculate height of the segment tree
        int height = (int) Math.ceil(Math.log(n)/Math.log(2));

        // maximum size of the segment tree
        int maxSize = 2 * (int) Math.pow(2, height) - 1;

        // memory allocation
        segmentTree = new int[maxSize];

        constructSegmentTree(arr, 0, n - 1, 0);
    }

    // Construct the segment tree recursively
    private int constructSegmentTree(int[] arr, int start, int end, int index) {

        // if there is only one element in the array, store it in current node
        if(start == end) {
            segmentTree[index] = arr[start];
//            return arr[start];
            return segmentTree[index];
        }

        // if there are more than one element, then recur left and right subtree and
        // store the sum in current index
        int midPoint = getMidPoint(start, end);
        segmentTree[index] = constructSegmentTree(arr, start, midPoint, 2 * index + 1) +
                             constructSegmentTree(arr, midPoint + 1, end, 2 * index + 2);
        return segmentTree[index];
    }

    private int getMidPoint(int start, int end) {
        return start + (end - start) / 2;
    }


    private int getSumInRange(int n, int startRange, int endRange) {

        // bound check
        if(startRange < 0 || endRange > n - 1 || startRange > endRange) {
            System.out.println("Invalid Input");
            return -1;
        }

        return getSumInRange(0, n - 1, startRange, endRange, 0);
    }

    private int getSumInRange(int start, int end, int startRange, int endRange, int index) {

        // if the segment of this node is a part of given range, then return the sum of the segment
        if(startRange <= start && endRange >= end) {
            return segmentTree[index];
        }

        // if segment is outside the given range
        if(end < startRange || start > endRange) {
            return 0;
        }

        //if a part of this segment overlaps with the given range
        int midPoint = getMidPoint(start, end);
        return getSumInRange(start, midPoint, startRange, endRange, 2 * index + 1) +
                getSumInRange(midPoint + 1, end, startRange, endRange, 2 * index + 2);
    }

    private void updateValue(int[] arr, int n, int index, int newValue) {

        // check for error input index
        if(index < 0 || index > n - 1) {
            System.out.println("Invalid Input");
            return;
        }

        // get difference
        int diff = newValue - arr[index];
        // assign the value in array
        arr[index] = newValue;

        updateValueInSegmentTree(0, n - 1, index, diff, 0);
    }

    // a recursive function to update the nodes with given index range
    private void updateValueInSegmentTree(int start, int end, int index, int diff, int currentIndex) {

        //base case if given index lies outside the range of the segment
        if(index < start || index > end) {
            return;
        }

        // if the index is in range of this node, then update the value of node and
        // its children
        segmentTree[currentIndex] = segmentTree[currentIndex] + diff;
        if(start != end) {
            int midPoint = getMidPoint(start, end);
            updateValueInSegmentTree(start, midPoint, index, diff, 2 * currentIndex + 1);
            updateValueInSegmentTree(midPoint + 1, end, index, diff, 2 * currentIndex + 2);
        }
    }

    public static void main(String args[]) {
        int arr[] = {1, 3, 5, 7, 9, 11};
        int n = arr.length;

        // build segment tree from given array
        SegmentTreeSumOfValuesInRange segmentTree = new SegmentTreeSumOfValuesInRange(arr, n);

        // Get sum of values in range 1 to 3
        System.out.println("Sum of values in given range = " + segmentTree.getSumInRange(n, 1, 3));

        // update set arr[1] = 10 and update corresponding segment tree nodes
        segmentTree.updateValue(arr, n, 1, 10);

        // Sum after updating arr and segement tree nodes
        System.out.println("Sum of values in given range = " + segmentTree.getSumInRange(n, 1, 3));
    }
}
