

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class BalanceCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    /**
     * Drive on a specified compass heading (0-360 degrees) for the specified distance in cm.
     *
     * @param driveSubsystem
     */
    public BalanceCommand(DriveSubsystem driveSubsystem) {

        this.driveSubsystem = driveSubsystem;

        addRequirements(driveSubsystem);

    }

    @Override
    public void initialize() {

        System.out.println("BalanceCommand started at " + driveSubsystem.getPitch());

        // Track the gyro pitch.
        double pitch = driveSubsystem.getPitch();
        double speed = 0;

        if (pitch > 0) {
            speed = .1;
        }
        else if (pitch < 0) {
            speed = -.1;
        }
        driveSubsystem.setMotorSpeeds(speed, speed);

    }

    @Override
    public boolean isFinished() {

        double pitch = driveSubsystem.getPitch();
        if (pitch < 1 && pitch > -1) {
            return true;
        }
        return false;

    }

    @Override
    public void end(boolean interrupted) {

        if (interrupted) {
            System.out.print("BalanceCommand interrupted");
        }
        else {
            System.out.print("BalanceCommand ended at " + driveSubsystem.getPitch());
        }

        // Stop the robot
        driveSubsystem.setMotorSpeeds(0, 0);
    }
}
