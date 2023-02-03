package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {
    private static final double     DRIVE_FILTER_VALUE = 0.2;
    private final DriveSubsystem    driveSubsystem;
    private final XboxController    driverController;
    private final DriveModeSelector driveModeSelector;


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
            setMotorSpeedsQuentin();
            break;
        }

        // handle dead stop
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

        // Filter out low input values to reduce drivetrain drift
        double leftY      = getScaledValue(driverController.getLeftY());
        double leftX      = getScaledValue(driverController.getLeftX());
        double leftSpeed  = leftY * -1 + leftX;
        double rightSpeed = leftY * -1 - leftX;

        // Boost
        if (driverController.getRightBumper()) {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }
        else {
            driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
    }

    private void setMotorSpeedsTank() {

        double leftSpeed  = getScaledValue(-driverController.getLeftY());
        double rightSpeed = getScaledValue(-driverController.getRightY());

        // Boost
        if (driverController.getRightBumper()) {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }
        else {
            driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
    }

    private void setMotorSpeedsQuentin() {

        double speed = getScaledValue(-driverController.getLeftY());
        double turn  = getScaledValue(driverController.getRightX());

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

        // Boost
        if (driverController.getRightBumper()) {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }
        else {
            driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
    }

    private static double getScaledValue(double value) {

        if (Math.abs(value) < DRIVE_FILTER_VALUE) {
            value = 0;
        }
        else {
            // Scale the range of [0.2-1.0] to [0.0-1.0];
            value = ((Math.abs(value) - DRIVE_FILTER_VALUE) / (1 - DRIVE_FILTER_VALUE)) * Math.signum(value);
        }
        return value;
    }
}