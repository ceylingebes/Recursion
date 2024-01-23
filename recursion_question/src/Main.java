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
        printWaterFilledColumns(heights);
    }

    private static int findMaxIndex(int[] heights, int leftIndex, int rightIndex) {
        int maxIndex = leftIndex;
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            if (heights[i] > heights[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int fill(int[] heights, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return 0; // base case
        }
        // Find the tallest column index between leftIndex and rightIndex
        int maxIndex = findMaxIndex(heights, leftIndex, rightIndex);
        int waterAmount = 0;
        int leftMaxHeight = 0;
        int rightMaxHeight = 0;

        for (int i = leftIndex; i < maxIndex; i++) {
            leftMaxHeight = Math.max(leftMaxHeight, heights[i]);
            waterAmount += Math.max(0, leftMaxHeight - heights[i]);
        }

        for (int i = rightIndex; i > maxIndex; i--) {
            rightMaxHeight = Math.max(rightMaxHeight, heights[i]);
            waterAmount += Math.max(0, rightMaxHeight - heights[i]);
        }

        // Recurse on left and right segments
        waterAmount += fill(heights, leftIndex, maxIndex - 1);
        waterAmount += fill(heights, maxIndex + 1, rightIndex);

        // System.out.println("Max index: " + maxIndex);
        // System.out.println("Water amount: " + waterAmount + " leftIndex: " + leftIndex + " rightIndex: " + rightIndex);

        return waterAmount;
    }


    public static void printWaterFilledColumns(int[] heights) {
        int maxHeight = getMaxHeight(heights);
        int[][] waterMatrix = new int[maxHeight][heights.length];

        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[i]; j++) {
                waterMatrix[maxHeight - j - 1][i] = 1;
            }
        }

        fillWaterMatrix(waterMatrix);

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < heights.length; j++) {
                System.out.print(waterMatrix[i][j] == 1 ? "|" : waterMatrix[i][j] == 2 ? "w" : " ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(heights.length));
    }

    private static int getMaxHeight(int[] heights) {
        int max = 0;
        for (int height : heights) {
            if (height > max) {
                max = height;
            }
        }
        return max;
    }

    private static void fillWaterMatrix(int[][] waterMatrix) {
        for (int i = 0; i < waterMatrix.length; i++) {
            int start = -1, end = -1;
            for (int j = 0; j < waterMatrix[i].length; j++) {
                if (waterMatrix[i][j] == 1) {
                    if (start == -1) {
                        start = j;
                    } else {
                        end = j;
                        for (int k = start + 1; k < end; k++) {
                            waterMatrix[i][k] = 2; // Fill with water
                        }
                        start = j;
                    }
                }
            }
        }
    }
}
