package frc.robot.commands.test;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RunnymedeGameController;
import frc.robot.subsystems.DriveSubsystem;

public class SystemTestCommand extends CommandBase {

    private enum TestMotor { NONE, LEFT_DRIVE, RIGHT_DRIVE }

    private final DriveSubsystem driveSubsystem;
    private final XboxController driverController;

    private boolean previousBumperPressed = false;
    private TestMotor previousTestMotor = TestMotor.NONE;

    private double motorSpeed = 0;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public SystemTestCommand(RunnymedeGameController driverController, DriveSubsystem driveSubsystem) {

        // Set test mode to disable all other commands
        RobotContainer.setTestMode(true);

        this.driverController = driverController;
        this.driveSubsystem = driveSubsystem;

        // Add all subsystems in order to cancel currently running commands
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        driveSubsystem.setMotorSpeeds(0,  0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        TestMotor testMotor = getTestMotor();

        if (testMotor != previousTestMotor) {
            motorSpeed = 0;
            setMotor(previousTestMotor, 0);
            previousTestMotor = testMotor;
        }

        if (driverController.getXButton()) {
            motorSpeed = 0;
        }
        else if (driverController.getAButton()) {
            motorSpeed -= .002;
        }
        else if (driverController.getYButton()) {
            motorSpeed += .002;
        }

        setMotor(testMotor, motorSpeed);
    }

    private void setMotor(TestMotor testMotor, double motorSpeed) {

        switch (testMotor) {

        case NONE:
            return;

        case LEFT_DRIVE:
            driveSubsystem.setMotorSpeeds(motorSpeed, 0);
            return;

        case RIGHT_DRIVE:
            driveSubsystem.setMotorSpeeds(0, motorSpeed);
            return;
        }
    }

    private TestMotor getTestMotor() {

        // Use the bumpers to get the current test motor
        // The bumpers must both be released in order to
        // advance the test motor state
        if (previousBumperPressed) {
            return previousTestMotor;
        }

        if (driverController.getRightBumper()) {

            previousBumperPressed = true;

            // advance to the next motor
            int ordinal = previousTestMotor.ordinal();
            ordinal++;
            if (ordinal > TestMotor.values().length) {
                ordinal = 0;
            }
            return TestMotor.values()[ordinal];
        }

        if (driverController.getLeftBumper()) {

            previousBumperPressed = true;

            // advance to the next motor
            int ordinal = previousTestMotor.ordinal();
            ordinal--;
            if (ordinal < 0) {
                ordinal = TestMotor.values().length-1;
            }
            return TestMotor.values()[ordinal];
        }

        previousBumperPressed = false;

        return previousTestMotor;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.setTestMode(false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        // The test command ends when the start button is pressed in
        // combination with either of the stick buttons.
        return driverController.getStartButton()
                &&      (  driverController.getYButton()
                        || driverController.getAButton());
    }
}
