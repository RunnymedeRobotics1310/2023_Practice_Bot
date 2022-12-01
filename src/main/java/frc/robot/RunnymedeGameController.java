package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class RunnymedeGameController extends XboxController {

    public static enum Stick {LEFT, RIGHT};

    private static double DEFAULT_AXIS_DEADBAND = .2;

    private double axisDeadband = DEFAULT_AXIS_DEADBAND;

    public RunnymedeGameController(int port) {
        super(port);
    }

    @Override
    public double getRawAxis(int axis) {

        double axisValue = super.getRawAxis(axis);

        if (Math.abs(axisValue) < axisDeadband) {
            axisValue = 0;
        }
        else {
            // Subtract the deadband (take the absolute value in order to remove
            // the deadband amount whether it is positive or negative.
            double value = Math.abs(axisValue) - axisDeadband;

            // Scale the value to the full range of 0-1.0 after the deadband amount
            // is removed
            value = value / (1.0 - axisDeadband);

            // multiply by 1.0 or -1.0 in order to put the sign back
            // on the end result based on the original axis value.
            value *= Math.signum(axisValue);

            axisValue = value;
        }

        // The Y axis values should be inverted in order to make North (away
        // from the driver) positive.
        if (       axis == XboxController.Axis.kLeftY.value
                || axis == XboxController.Axis.kRightY.value) {
            axisValue *= -1.0;
        }

        return axisValue;
    }

    /**
     * Set the axis deadband on the stick and trigger axes of this gameController
     * <p>
     * The value set applies to the x and y axes of the left and right sticks,
     * as well as the trigger's axes values.
     * <p>
     * The deadband must be set larger than the highest expected value returned
     * from the stick axis when they are released.  A released controller axis will
     * not always return to zero.
     * <p>
     * Use the method {@link #getRawHardwareAxisValue} to get the raw hardware value coming
     * off the axis.
     * @param axisDeadband
     */
    public void setAxisDeadband(double axisDeadband) {
        this.axisDeadband = axisDeadband;
    }

    /**
     * Get the raw hardware axis value (unmodified by the deadband)
     * @param axis see {@link XboxController.Axis} for list of axis constants
     */
    public double getRawHardwareAxisValue(int axis) {
        return super.getRawAxis(axis);
    }

    /**
     * Determine the stick angle in the range of 0-360 degrees, where zero is pointing straight up
     * <p>
     * If the stick is released (axes are within the deadband) then the stick angle will be -1.0d
     * @param stick whose angle is to be retrieved.
     * @return angle in the range 0-360, or -1.0 if the stick is not being moved
     */
    public double getStickAngle(Stick stick) {

        double x = 0;
        double y = 0;

        switch (stick) {

        case LEFT:
            x = getLeftX();
            y = getLeftY();
            break;

        case RIGHT:
            x = getRightX();
            y = getRightY();
            break;
        }

        // If the stick is not being moved (significantly), return -1.
        if (Math.abs(x) < .3 && Math.abs(y) < .3) {
            return -1;
        }

        // NOTE: when taking the arcTan of the coordinates, positive
        //       is counter-clockwise away from East, with positive
        //       to the north, and negative to the south.
        double radiansFromEast = Math.atan2(y, x);

        // The desired output is relative to N, rather than relative to E.
        double radiansFromNorth = radiansFromEast - (Math.PI/2.0d);

        // Degrees are measured in the clockwise direction from 0 - 359 degrees.
        double degreesFromNorth = 360.0d - Math.toDegrees(radiansFromNorth);

        degreesFromNorth %= 360.0d;  // Modulo 360.

        if (degreesFromNorth < 0) {
            degreesFromNorth += 360;
        }

        return degreesFromNorth;
    }
}
