package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class RunnymedeGameController extends XboxController {

    private static double DEFAULT_AXIS_DEADBAND = .2;

    private double        axisDeadband          = DEFAULT_AXIS_DEADBAND;

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
            value      = value / (1.0 - axisDeadband);

            // multiply by 1.0 or -1.0 in order to put the sign back
            // on the end result based on the original axis value.
            value     *= Math.signum(axisValue);

            axisValue  = value;
        }

        // The Y axis values should be inverted in order to make North (away
        // from the driver) positive.
        if (axis == XboxController.Axis.kLeftY.value || axis == XboxController.Axis.kRightY.value) {
            axisValue *= -1.0;
        }

        return axisValue;
    }

    /**
     * Set the axis deadband on the stick and trigger axes of this gameController
     * <p>
     * The value set applies to the x and y axes of the left and right sticks, as
     * well as the trigger's axes values.
     * <p>
     * The deadband must be set larger than the highest expected value returned from
     * the stick axis when they are released. A released controller axis will not
     * always return to zero.
     * <p>
     * Use the method {@link #getRawHardwareAxisValue} to get the raw hardware value
     * coming off the axis.
     *
     * @param axisDeadband
     */
    public void setAxisDeadband(double axisDeadband) {
        this.axisDeadband = axisDeadband;
    }

    /**
     * Get the raw hardware axis value (unmodified by the deadband)
     *
     * @param axis see {@link XboxController.Axis} for list of axis constants
     */
    public double getRawHardwareAxisValue(int axis) {
        return super.getRawAxis(axis);
    }
}
