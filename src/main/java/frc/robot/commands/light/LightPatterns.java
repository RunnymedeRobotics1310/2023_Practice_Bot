package frc.robot.commands.light;

public class LightPatterns {
    static int[]   orange = { 127, 25, 0 };

    static int[]   blue   = { 0, 0, 127 };
    static int[]   red    = { 127, 0, 0 };
    static int[]   white  = { 127, 127, 127 };
    static int[]   black  = { 0, 0, 0 };

    static int[][] french = { blue, red, white };

    public static int[][] createSolidPattern(int length, int[] color) {
        int[][] result = new int[length][3];
        for (int i = 0; i < length; i++) {
            result[i] = color;
        }
        return result;
    }

}
