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
import frc.robot.commands.auto.AutonomousCommand;
import frc.robot.commands.drive.DefaultDriveCommand;
import frc.robot.commands.drive.FieldOrientedDriveCommand;
import frc.robot.commands.test.SystemTestCommand;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();

    private static boolean testMode = false;

    // A chooser for autonomous commands
    SendableChooser<String> autoChooser = new SendableChooser<>();

    // The driver's controller
    private final RunnymedeGameController driverController = new RunnymedeGameController(OiConstants.DRIVER_CONTROLLER_PORT);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Initialize all Subsystem default commands.
        driveSubsystem.setDefaultCommand(new DefaultDriveCommand(driverController, driveSubsystem));

        // Initialize the autonomous chooser
        autoChooser.setDefaultOption(AutoConstants.AUTO_PATTERN_DO_NOTHING, AutoConstants.AUTO_PATTERN_DO_NOTHING);
        SmartDashboard.putData(autoChooser);
        autoChooser.addOption(AutoConstants.AUTO_PATTERN_MOVE, AutoConstants.AUTO_PATTERN_MOVE);

        // Configure the button bindings
        setTestMode(false);
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        // Test Mode
        new Trigger(() -> !testMode)
        .and(new Trigger(() -> driverController.getStartButton()))
        .and(new Trigger(() -> driverController.getBackButton()))
        .whenActive(
                new SystemTestCommand(driverController, driveSubsystem));

        // Field Oriented Drive
        // NOTE: To end Field Oriented Drive use the Start and A button combination
        new Trigger(() -> !testMode)
        .and(new Trigger(() -> driverController.getStartButton()))
        .and(new Trigger(() -> driverController.getYButton()))
        .whenActive(
                new FieldOrientedDriveCommand(driverController, driveSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        return new AutonomousCommand(
                driveSubsystem,
                autoChooser);

    }

    public static void setTestMode(boolean isTestMode) {

        testMode = isTestMode;

        SmartDashboard.putBoolean("Test Mode", testMode);
        SmartDashboard.putBoolean("Driver Control", !testMode);
    }
}
