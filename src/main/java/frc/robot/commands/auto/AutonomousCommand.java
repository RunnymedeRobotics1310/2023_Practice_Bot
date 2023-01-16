package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class AutonomousCommand extends SequentialCommandGroup {

    public AutonomousCommand(DriveSubsystem driveSubsystem, SendableChooser<String> autoChooser) {

        String selectedAuto = autoChooser.getSelected();

        // The selector should always return one of the auto
        // patterns, if not, then do nothing
        if (selectedAuto == null) {
            System.out.println("*** ERROR - NULL auto selected ***");
            addCommands(new InstantCommand());
            return;
        }

        // Placeholder for auto commands
        switch (selectedAuto) {

        case AutoConstants.AUTO_PATTERN_DO_NOTHING:
            // Do nothing
            System.out.println("Do nothing auto selected");
            addCommands(new InstantCommand());
            break;

        case AutoConstants.AUTO_PATTERN_MOVE:
            // Move
            System.out.println("Move auto pattern selected");
            // TODO: Make a new move command and insert it here.
            addCommands(new InstantCommand());
            break;

        default:
            // How did we get here?
            System.out.println("Auto selection(" + selectedAuto + ") was not programmed!");
            addCommands(new InstantCommand());
        }
    }

}
