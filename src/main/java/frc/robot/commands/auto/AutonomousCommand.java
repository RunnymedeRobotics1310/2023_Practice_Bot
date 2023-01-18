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

        case AutoConstants.AUTO_PATTERN_SCORE_LOW:
            // Bottom score mid depart
            System.out.println("Score Low auto pattern selected");
            // TODO:
            /*
             * move arm low
             * Open pads
             * Close pads
             */

        case AutoConstants.AUTO_PATTERN_SCORE_MID:
            // Bottom score mid depart
            System.out.println("Score mid auto pattern selected");
            // TODO:
            /*
             * move arm mid
             * Open pads
             * Close pads
             */
            addCommands(new InstantCommand());
            break;

        case AutoConstants.AUTO_PATTERN_REVERSE:
            // Bottom score mid depart
            System.out.println("Score mid auto pattern selected");
            // TODO:
            /*
             * Drive backwards
             */
            addCommands(new InstantCommand());
            break;

        case AutoConstants.AUTO_PATTERN_GRAB_PIECE:
            // Bottom score mid depart
            System.out.println("Score mid auto pattern selected");
            // TODO:
            /*
             * Open pads
             * Close pads
             */
            addCommands(new InstantCommand());
            break;

        case AutoConstants.AUTO_PATTERN_BALANCE:
            // Balance on charge station
            System.out.println("Balance auto pattern selected");
            /*
             * TODO
             * balance
             */
            addCommands(new InstantCommand());
            break;

        default:
            // How did we get here?
            System.out.println("Auto selection(" + selectedAuto + ") was not programmed!");
            addCommands(new InstantCommand());
        }
    }

}
