package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class FieldOrientedDriveCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final XboxController driverController;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public FieldOrientedDriveCommand(XboxController driverController, DriveSubsystem driveSubsystem) {

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

        double leftSpeed = - driverController.getLeftY();

        double rightSpeed = - driverController.getRightY();

        if (Math.abs(leftSpeed) < .1) {
            leftSpeed = 0;
        }

        if (Math.abs(rightSpeed) < .1) {
            rightSpeed = 0;
        }

        driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
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
