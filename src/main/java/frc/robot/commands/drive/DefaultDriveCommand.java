package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {

    private final DriveSubsystem    driveSubsystem;
    private final XboxController    driverController;
    private final DriveModeSelector driveModeSelector;

    private final double            DRIVE_FILTER_VALUE = 0.075f;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(XboxController driverController, DriveSubsystem driveSubsystem,
        DriveModeSelector driveModeSelector) {

        this.driverController  = driverController;
        this.driveSubsystem    = driveSubsystem;
        this.driveModeSelector = driveModeSelector;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        DriveMode driveMode = driveModeSelector.getDriveMode();

        switch (driveMode) {
        case ARCADE:
            setMotorSpeedsArcade();
            break;
        case TANK:
            setMotorSpeedsTank();
            break;
        case QUENTIN:
            setMotorSpeedsQuentin();
            break;
        default:
            setMotorSpeedsArcade();
            break;
        }

        //
        // handle dead stop
        //
        // FIXME: The placement of this code may not result in the expected action
        //
        // The execute loop is called 50 times per second.
        //
        // The code above sets the motor speeds to a value, and then dead stop code
        // sets the motor speeds to a different value.
        //
        // The "dead stop" function may not actually stop the robot, it may
        // cause a jerky motor operation where the motors are constantly being
        // turned on and off again.
        //
        // Possible Solution: The dead stop code needs to take priority
        // over all other setting of the motor speeds. If there is a dead stop,
        // no other setting of the motor speeds should be possible.
        //
        // NOTE: the bumper dead stop feature overrides the joysticks temporarily
        // while held. When the bumper is released, the joysticks will take over again.
        // Hopefully that is the intended design.
        //
        if (driverController.getLeftBumper()) {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    private void setMotorSpeedsArcade() {

        // TODO: Filtering of joystick values to handle the deadband should be done in a
        // joystick
        // class
        /** see {@link RunnymedeGameController}. */

        // Filter out low input values to reduce drivetrain drift
        double leftY      = (Math.abs(driverController.getLeftY()) < DRIVE_FILTER_VALUE) ? 0.0f
            : driverController.getLeftY();
        double leftX      = (Math.abs(driverController.getLeftX()) < DRIVE_FILTER_VALUE) ? 0.0f
            : driverController.getLeftX();
        double leftSpeed  = leftY * -1 + leftX;
        double rightSpeed = leftY * -1 - leftX;


        // handle boost
        if (driverController.getRightBumper()) {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }
        else {
            // Not sure if this is a good speed!
            driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
    }

    private void setMotorSpeedsTank() {

        // TODO: THIS IS NOT CORRECT
        // FIXME: Please elaborate any comments like the above
        // How do we know what is not correct?

        // Filter out low input values to reduce drivetrain drift
        double  leftY      = (Math.abs(driverController.getLeftY()) < DRIVE_FILTER_VALUE) ? 0.0f
            : driverController.getLeftY();
        double  leftX      = (Math.abs(driverController.getLeftX()) < DRIVE_FILTER_VALUE) ? 0.0f
            : driverController.getLeftX();
        double  leftSpeed  = leftY * -1 + leftX;
        double  rightSpeed = leftY * -1 - leftX;

        // Tank drive:
        // double leftY = -driverController.getRawAxis(1);
        // double rightY = -driverController.getRawAxis(5);
        // double leftT = driverController.getRawAxis(2);
        // double rightT = driverController.getRawAxis(3);
        boolean boost      = false;

        if (driverController.getRightBumper()) {
            boost = true;
        }
        // Also tank drive:
        // if (leftT >0) {
        // driveSubsystem.setMotorSpeeds(-leftT, leftT)
        // }
        // else if (rightT >0) {
        // driveSubsystem.setMotorSpeeds(rightT, -rightT);
        // }
    }

    private void setMotorSpeedsQuentin() {

        double speed = -driverController.getLeftY();
        if (Math.abs(speed) < .2) {
            speed = 0;
        }
        else {
            // Scale the range of [0.2-1.0] to [0.0-1.0];
            speed = ((Math.abs(speed) - .2) / .8) * Math.signum(speed);
        }

        double turn = driverController.getRightX();
        if (Math.abs(turn) < .2) {
            turn = 0;
        }
        else {
            // Scale the range of [0.2-1.0] to [0.0-1.0];
            turn = ((Math.abs(turn) - .2) / .8) * Math.signum(turn);
        }

        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Turn", turn);

        double leftSpeed = 0, rightSpeed = 0;

        if (speed > 0) {
            if (turn > 0) {
                leftSpeed  = speed + turn;
                rightSpeed = speed;
            }
            else if (turn < 0) {
                leftSpeed  = speed;
                rightSpeed = speed - turn;
            }
            else {
                leftSpeed  = speed;
                rightSpeed = speed;
            }
        }

        else if (speed < 0) {
            if (turn < 0) {
                leftSpeed  = speed;
                rightSpeed = speed + turn;
            }
            else if (turn > 0) {
                leftSpeed  = speed - turn;
                rightSpeed = speed;
            }
            else {
                leftSpeed  = speed;
                rightSpeed = speed;
            }
        }
        else {
            leftSpeed  = speed + turn;
            rightSpeed = speed - turn;
        }

        // doubles the speed of the robot
        if (driverController.getRightBumper()) {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }
        else {
            driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
    }
}
