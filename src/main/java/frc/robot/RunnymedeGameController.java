package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class RunnymedeGameController extends XboxController {

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

        return axisValue;
    }

    /**
     * Set the axis deadband on the stick axes of this gameController
     * <p>
     * The value set applies to the x and y axis of the left and right stick,
     * and the trigger axis values.
     *
     * @param axisDeadband
     */
    public void setAxisDeadband(double axisDeadband) {
        this.axisDeadband = axisDeadband;
    }

    /**
     * Get the raw hardware axis value (unmodified by the deadband)
     * @param axis see {@link XboxController.Axis} for list of axis constants
     */
    public double getHardwareAxisValue(int axis) {
        return super.getRawAxis(axis);
    }
}
