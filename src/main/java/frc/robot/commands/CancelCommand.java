package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to
 * cancel any running commands
 */
public class CancelCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    /**
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public CancelCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void initialize() {
        System.out.println("Cancel Command started.");

        driveSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        // This command is always finished after initializing.
        return true;
    }

}
