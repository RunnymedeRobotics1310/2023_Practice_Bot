package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends CommandBase {

    private final double         heading, speed, distanceInches, timeoutSeconds;
    private final DriveSubsystem driveSubsystem;

    private long                 initializeTime = 0;

    /**
     * Drive on a specified compass heading (0-360 degrees) for the specified distance in inches.
     * <p>
     * This constructor uses the {@link Constants#DEFAULT_COMMAND_TIMEOUT_SECONDS} for the command
     * timeout
     *
     * @param heading 0-360 degrees
     * @param speed in the range 0-1.0 for forward travel, 0 - -1.0 for reverse travel
     * @param distanceInches for the robot to travel before this command ends.
     * Use a positive number even if traveling backwards
     * @param driveSubsystem
     */
    public DriveOnHeadingCommand(double heading, double speed, double distanceInches, DriveSubsystem driveSubsystem) {
        this(heading, speed, distanceInches, Constants.DEFAULT_COMMAND_TIMEOUT_SECONDS, driveSubsystem);
    }

    /**
     * Drive on a specified compass heading (0-360 degrees) for the specified distance in inches.
     *
     * @param heading 0-360 degrees
     * @param speed in the range 0-1.0 for forward travel, 0 - -1.0 for reverse travel
     * @param distanceInches for the robot to travel before this command ends.
     * Use a positive number even if traveling backwards
     * @param timeoutSeconds to stop this command if the distance has not been reached
     * @param driveSubsystem
     */
    public DriveOnHeadingCommand(double heading, double speed, double distanceInches, double timeoutSeconds,
        DriveSubsystem driveSubsystem) {

        this.heading        = heading;
        this.speed          = speed;
        this.distanceInches = distanceInches;
        this.timeoutSeconds = timeoutSeconds;
        this.driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {

        // Reset the distance to zero.
        // Note: this must be done in the initialize instead of in the constructor
        // because the command could get constructed long before it is run
        driveSubsystem.resetEncoders();

        initializeTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {

        // Track the gyro heading.
        // FIXME: how can we do this?

        double currentHeading = driveSubsystem.getHeading();

        // Determine the error between the current heading and
        // the desired heading
        // FIXME:
        double error          = 0;

        // Drive the motors appropriately to align to the heading
        // Q: going forward, which direction should the robot
        // turn based on the error?
        // FIXME:
        // Things to think about: we want the speed to be as close to
        // the set speed as possible.
        double leftSpeed      = 0, rightSpeed = 0;


        // In the end, set the speeds on the motors
        driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
    }

    @Override
    public boolean isFinished() {

        // This command can either reach the distance or time out

        // Check the distance
        // use the absolute value to account for driving backwards
        if (Math.abs(driveSubsystem.getEncoderDistanceInches()) > Math.abs(distanceInches)) {
            return true;
        }

        // Check the timeout
        if ((System.currentTimeMillis() - initializeTime) / 1000d > timeoutSeconds) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        // Stop the robot
        driveSubsystem.setMotorSpeeds(0, 0);
    }
}
