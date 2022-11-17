package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final XboxController driverController;

    private final double DRIVE_FILTER_VALUE = 0.075f;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(XboxController driverController, DriveSubsystem driveSubsystem) {

        this.driverController = driverController;
        this.driveSubsystem = driveSubsystem;

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

        // What else to put here?

        // Filter out low input values to reduce drivetrain drift
       // double leftY = (Math.abs(driverController.getRawAxis(1)) < DRIVE_FILTER_VALUE) ? 0.0f
       //         : driverController.getRawAxis(1);
       // double leftX = (Math.abs(driverController.getRawAxis(0)) < DRIVE_FILTER_VALUE) ? 0.0f
       //         : driverController.getRawAxis(0);
       // double leftSpeed = leftY * -1 + leftX;
       // double rightSpeed = leftY * -1 - leftX;

        // Tank drive:
        double leftY = -driverController.getLeftY();
        double rightY = -driverController.getRightY();
        double leftT = driverController.getLeftTriggerAxis();
        double rightT = driverController.getRightTriggerAxis();
        boolean boost = false;

        if (driverController.getRightBumper()) {
            boost = true;
        }
        // Also tank drive:
         if (leftT >0) {
         driveSubsystem.setMotorSpeeds(-leftT, leftT);
         }
         else if (rightT >0) {
         driveSubsystem.setMotorSpeeds(rightT, -rightT);
         }

        // if (!boost) {
        //     // Not sure if this is a good speed!
        //     driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        // } else {
        //     driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        // }

        // stops the robot
        boolean deadStop = false;
        if (driverController.getLeftBumper()) {
            deadStop = true;
        }

        if (deadStop) {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
        {
            driveSubsystem.setMotorSpeeds(leftY,rightY);
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
}
