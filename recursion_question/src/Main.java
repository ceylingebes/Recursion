public class Main {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Give proper arguments");
            return;
        }
        int numOfCols = Integer.parseInt(args[0]);
        int[] heights = new int[numOfCols];
        for (int i = 0; i < numOfCols; i++) {
            heights[i] = Integer.parseInt(args[i + 1]);
        }

        int waterAmount = fill(heights, 0, heights.length - 1);
        System.out.println("The amount of water is: " + waterAmount);
    }

    public static int fill(int[] heights, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return 0; // base case
        }

        int waterAmount = 0;
        int smallerHeight = Math.min(heights[leftIndex], heights[rightIndex]);
        int biggerHeightIndex = (heights[leftIndex] > heights[rightIndex]) ? leftIndex : rightIndex;

        // iterate over inner columns
        for (int i = leftIndex + 1; i < rightIndex; i++) {
            waterAmount += Math.max(0, smallerHeight - heights[i]); // if smaller height is longer, fill; 0 otherwise
            if (heights[i] > heights[biggerHeightIndex]) {
                biggerHeightIndex = i; // Update biggerHeightIndex within the loop
            }
        }

        // recursion with updated boundaries
        if (heights[leftIndex] < heights[rightIndex]) {
            // Move left boundary towards the center
            waterAmount += fill(heights, leftIndex, biggerHeightIndex);
        } else {
            // Move right boundary towards the center
            waterAmount += fill(heights, biggerHeightIndex, rightIndex);
        }

        return waterAmount;
    }

}
