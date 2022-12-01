package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.RunnymedeGameController;
import frc.robot.RunnymedeGameController.Stick;
import frc.robot.subsystems.DriveSubsystem;

public class FieldOrientedDriveCommand extends CommandBase {

    private static final double SPIN_IN_PLACE_HEADING_ERROR_DEG = 60;

    private static final double SPIN_SPEED_PER_DEGREE =
            Constants.DriveConstants.MAX_SPIN_SPEED / SPIN_IN_PLACE_HEADING_ERROR_DEG;

    private final DriveSubsystem driveSubsystem;
    private final RunnymedeGameController driverController;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public FieldOrientedDriveCommand(RunnymedeGameController driverController, DriveSubsystem driveSubsystem) {

        this.driverController = driverController;
        this.driveSubsystem = driveSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.setTestMode(false);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double joystickHeading = driverController.getStickAngle(Stick.RIGHT);

        SmartDashboard.putNumber("Joystick Heading", joystickHeading);

        double speed = driverController.getLeftY();

        // If the heading is -1, then then no rotation is required, so
        // use only the speed
        if (joystickHeading < 0) {
            driveSubsystem.setMotorSpeeds(speed, speed);
            return;
        }

        double robotHeading = driveSubsystem.getHeading();

        // Determine the heading error
        // The heading error must be between +/- 180 degrees
        double headingError = (joystickHeading - robotHeading) % 360.0d;

        if (headingError < -180) {
            headingError += 360;
        }

        if (headingError > 180) {
            headingError -= 360;
        }

        SmartDashboard.putNumber("Heading Error", headingError);

        // If the speed is zero, or the heading error is > max error for spinning in place
        // then the robot is just spinning.
        boolean spin = (speed == 0 || headingError > SPIN_IN_PLACE_HEADING_ERROR_DEG);

        if (spin) {

            // No spin will be required when within 5 degrees of the target when spinning in place
            double spinSpeed = 0;

            if (Math.abs(headingError) > SPIN_IN_PLACE_HEADING_ERROR_DEG) {

                spinSpeed = Constants.DriveConstants.MAX_SPIN_SPEED * Math.signum(headingError);

            }

            else if (Math.abs(headingError) > 5) {

                spinSpeed = SPIN_SPEED_PER_DEGREE * headingError;

            }

            // Positive spin speeds will be clockwise
            driveSubsystem.setMotorSpeeds(spinSpeed, -spinSpeed);
        }

        else {

            // If not spinning, then drive in the direction of the joystick pointer
            double spinSpeed = SPIN_SPEED_PER_DEGREE * headingError / 2;

            // If the spin causes the speed to go over 1, then scale the
            // speed back to allow the full turn ratio between the
            // right and the left sides
            if (Math.abs(speed) + Math.abs(spinSpeed) > 1) {

                speed = (1.0 - Math.abs(spinSpeed)) * Math.signum(speed);
            }

            driveSubsystem.setMotorSpeeds(speed + spinSpeed, speed - spinSpeed);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setMotorSpeeds(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        // This command ends on a Start, A button press combination
        return driverController.getStartButton() && driverController.getAButton();
    }
}
