package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveModeSelector {

    public SendableChooser<DriveMode> driveModeChooser;
    {
        driveModeChooser = new SendableChooser<DriveMode>();
        driveModeChooser.addOption("Arcade", DriveMode.ARCADE);
        driveModeChooser.addOption("Tank", DriveMode.TANK);
        driveModeChooser.setDefaultOption("Quentin", DriveMode.QUENTIN);

        SmartDashboard.putData("Drive Mode", driveModeChooser);

    }

    public DriveMode getDriveMode() {
        return driveModeChooser.getSelected();
    }
}
