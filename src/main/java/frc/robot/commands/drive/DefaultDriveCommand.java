package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDriveCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final XboxController driverController;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DefaultDriveCommand(XboxController driverController, DriveSubsystem driveSubsystem) {

        this.driverController = driverController;
        this.driveSubsystem = driveSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double speed = - driverController.getLeftY();
        if (Math.abs(speed) < .2) {
            speed = 0;
        }
        else {
            // Scale the range of [0.2-1.0] to [0.0-1.0];
            speed = ((Math.abs(speed) - .2) / .8) * Math.signum(speed);
        }

        double turn = driverController.getRightX();
        if (Math.abs(turn) < .2) {
            turn = 0;
        }
        else {
            // Scale the range of [0.2-1.0] to [0.0-1.0];
            turn = ((Math.abs(turn) - .2) / .8) * Math.signum(turn);
        }

        SmartDashboard.putNumber("Speed", speed);
        SmartDashboard.putNumber("Turn", turn);


        double leftSpeed = 0, rightSpeed = 0;

        if(speed > 0){
            if(turn > 0){
                leftSpeed = speed + turn;
                rightSpeed = speed;
           }
           else if(turn < 0){
               leftSpeed = speed;
               rightSpeed = speed - turn;
           }
           else{
               leftSpeed = speed;
               rightSpeed = speed;
           }
        }

        else if(speed < 0){
            if(turn < 0){
                leftSpeed = speed;
                rightSpeed = speed + turn;
            }
            else if(turn > 0){
                leftSpeed = speed - turn;
                rightSpeed = speed;
            }
            else{
                leftSpeed = speed;
                rightSpeed = speed;
            }
        }
        else{
            leftSpeed = speed + turn;
            rightSpeed = speed - turn;
        }



        // doubles the speed of the robot
        boolean boost = false;
        if (driverController.getRightBumper()) {
            boost = true;
        }
        if (!boost) {
           driveSubsystem.setMotorSpeeds(leftSpeed / 2, rightSpeed / 2);
        }
         else {
            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);
        }

        // stops the robot
        boolean deadStop = false;
        if (driverController.getLeftBumper()) {
            deadStop = true;
        }

        if (deadStop) {
            driveSubsystem.setMotorSpeeds(0, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
