package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.operator.RunnymedeGameController;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {

    private final DriveSubsystem    driveSubsystem;
    private final XboxController    driverController;
    private final DriveModeSelector driveModeSelector;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(RunnymedeGameController driverController, DriveSubsystem driveSubsystem,
        DriveModeSelector driveModeSelector) {

        this.driverController  = driverController;
        this.driveSubsystem    = driveSubsystem;
        this.driveModeSelector = driveModeSelector;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("DefaultDriveCommand started.");
    }

    private static final int AXIS_LEFT_Y  = 1;
    private static final int AXIS_RIGHT_X = 4;

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // forwards/backwards speed
        double       speed   = driverController.getRawAxis(AXIS_LEFT_Y);
        // turn speed
        final double rawTurn = driverController.getRawAxis(AXIS_RIGHT_X);

        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Turn", rawTurn);

        double  turn      = rawTurn / 2;
        boolean boost     = driverController.getLeftBumper();
        boolean slow      = driverController.getRightBumper();

        double  leftSpeed = 0, rightSpeed = 0;

        if (slow) {
            speed = speed / 5;
            turn  = turn / 2.5; // Turn was already divided by 2 above
        }

        else if (!boost) {
            speed = speed / 2;
        }

        else {
            speed = Math.signum(speed);
        }

        if (speed < 0) {
            turn = -turn;
        }

        if (speed == 0) {
            leftSpeed  = turn;
            rightSpeed = -turn;
        }
        else if (boost) {

            // Turning left
            if (rawTurn < 0) {

                // If boosted and at full speed, and the
                // turn is limited to 0.5, then the max turn
                // that can be achieved is 1.0 vs 0.5.
                leftSpeed  = speed + turn;
                rightSpeed = speed;
            }
            // Turning right
            else if (rawTurn > 0) {
                leftSpeed  = speed;
                rightSpeed = speed - turn;
            }
            else {
                leftSpeed  = speed;
                rightSpeed = speed;
            }
        }
        else {
            if (rawTurn < 0) {
                leftSpeed  = speed;
                rightSpeed = speed - turn;
            }
            else if (rawTurn > 0) {
                leftSpeed  = speed + turn;
                rightSpeed = speed;
            }
            else {
                rightSpeed = speed;
                leftSpeed  = speed;
            }
        }
        driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("DefaultDriveCommand interrupted.");
        }
        else {
            System.out.println("DefaultDriveCommand ended.");
        }
    }
}