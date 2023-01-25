package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants.DriveMode;


public class DriveModeSelector {

    public SendableChooser<DriveMode> driveModeChooser;

    // FIXME: Do students understand initializer blocks?
    // Should we change this to a constructor?
    {
        driveModeChooser = new SendableChooser<DriveMode>();
        driveModeChooser.setDefaultOption("Arcade", DriveMode.ARCADE);
        driveModeChooser.addOption("Tank", DriveMode.TANK);
        driveModeChooser.addOption("Quentin", DriveMode.QUENTIN);

        SmartDashboard.putData("Drive Mode", driveModeChooser);
    }

    public DriveMode getDriveMode() {
        return driveModeChooser.getSelected();
    }
}
