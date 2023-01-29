// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.OiConstants;
import frc.robot.commands.CancelCommand;
import frc.robot.commands.auto.AutonomousCommand;
import frc.robot.commands.drive.DefaultDriveCommand;
import frc.robot.commands.drive.DriveModeSelector;
import frc.robot.commands.drive.DriveOnHeadingCommand;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem    driveSubsystem                = new DriveSubsystem();

    // A set of choosers for autonomous patterns
    SendableChooser<String>         startingPositionChooser       = new SendableChooser<>();
    SendableChooser<String>         startingOrientationChooser    = new SendableChooser<>();
    SendableChooser<String>         firstGamePieceScoringChooser  = new SendableChooser<>();
    SendableChooser<String>         exitZoneActionChooser         = new SendableChooser<>();
    SendableChooser<String>         secondGamePieceScoringChooser = new SendableChooser<>();
    SendableChooser<String>         balanceChooser                = new SendableChooser<>();

    // A chooser for the drive mode
    private final DriveModeSelector driveModeSelector             = new DriveModeSelector();

    // The driver's controller
    private final XboxController    driverController              = new XboxController(OiConstants.DRIVER_CONTROLLER_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Initialize all Subsystem default commands.
        driveSubsystem.setDefaultCommand(new DefaultDriveCommand(driverController, driveSubsystem, driveModeSelector));

        // Initialize the autonomous choosers
        initAutoSelectors();

        // Configure the button bindings
        configureButtonBindings();
    }

    private void initAutoSelectors() {

        startingPositionChooser.setDefaultOption(AutoConstants.AUTO_START_TOP, AutoConstants.AUTO_START_TOP);
        SmartDashboard.putData(startingPositionChooser);
        startingPositionChooser.addOption(AutoConstants.AUTO_START_MID, AutoConstants.AUTO_START_MID);
        startingPositionChooser.addOption(AutoConstants.AUTO_START_BOTTOM, AutoConstants.AUTO_START_BOTTOM);

        startingOrientationChooser.setDefaultOption(AutoConstants.AUTO_ORIENTATION_FACE_GRID,
            AutoConstants.AUTO_ORIENTATION_FACE_GRID);
        SmartDashboard.putData(startingOrientationChooser);
        startingOrientationChooser.addOption(AutoConstants.AUTO_ORIENTATION_FACE_FIELD,
            AutoConstants.AUTO_ORIENTATION_FACE_FIELD);

        firstGamePieceScoringChooser.setDefaultOption(AutoConstants.AUTO_SCORE_LOW, AutoConstants.AUTO_SCORE_LOW);
        SmartDashboard.putData(firstGamePieceScoringChooser);
        firstGamePieceScoringChooser.addOption(AutoConstants.AUTO_SCORE_MID_CONE, AutoConstants.AUTO_SCORE_MID_CONE);
        firstGamePieceScoringChooser.addOption(AutoConstants.AUTO_SCORE_MID_CUBE, AutoConstants.AUTO_SCORE_MID_CUBE);
        firstGamePieceScoringChooser.addOption(AutoConstants.AUTO_DO_NOTHING, AutoConstants.AUTO_DO_NOTHING);

        exitZoneActionChooser.setDefaultOption(AutoConstants.AUTO_LEAVE_ZONE, AutoConstants.AUTO_LEAVE_ZONE);
        SmartDashboard.putData(exitZoneActionChooser);
        exitZoneActionChooser.addOption(AutoConstants.AUTO_GRAB_PIECE, AutoConstants.AUTO_GRAB_PIECE);
        exitZoneActionChooser.addOption(AutoConstants.AUTO_DO_NOTHING, AutoConstants.AUTO_DO_NOTHING);

        secondGamePieceScoringChooser.setDefaultOption(AutoConstants.AUTO_DO_NOTHING, AutoConstants.AUTO_DO_NOTHING);
        SmartDashboard.putData(secondGamePieceScoringChooser);
        secondGamePieceScoringChooser.addOption(AutoConstants.AUTO_SCORE_LOW, AutoConstants.AUTO_SCORE_LOW);
        secondGamePieceScoringChooser.addOption(AutoConstants.AUTO_SCORE_MID_CONE, AutoConstants.AUTO_SCORE_MID_CONE);
        secondGamePieceScoringChooser.addOption(AutoConstants.AUTO_SCORE_MID_CUBE, AutoConstants.AUTO_SCORE_MID_CUBE);

        balanceChooser.setDefaultOption(AutoConstants.AUTO_BALANCE, AutoConstants.AUTO_BALANCE);
        SmartDashboard.putData(balanceChooser);
        balanceChooser.addOption(AutoConstants.AUTO_DO_NOTHING, AutoConstants.AUTO_DO_NOTHING);

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        // Cancel all commands on the XBox controller three lines (aka. start) button
        new Trigger(() -> driverController.getStartButton())
            .onTrue(new CancelCommand(driveSubsystem));

        // Example using the POV to Drive on Heading at .5 speed for 50cm.
        new Trigger(() -> (driverController.getPOV() == 0))
            .onTrue(new DriveOnHeadingCommand(0, .5, 50, driveSubsystem));

        new Trigger(() -> (driverController.getPOV() == 90))
            .onTrue(new DriveOnHeadingCommand(90, .5, 50, driveSubsystem));

        // ... etc for 180, 270.
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        return new AutonomousCommand(driveSubsystem, startingPositionChooser, startingOrientationChooser,
            firstGamePieceScoringChooser, exitZoneActionChooser, secondGamePieceScoringChooser, balanceChooser);

    }
}
