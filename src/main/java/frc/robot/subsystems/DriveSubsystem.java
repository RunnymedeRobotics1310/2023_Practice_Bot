package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final CANSparkMax leftPrimaryMotor  = new CANSparkMax(DriveConstants.LEFT_MOTOR_PORT, MotorType.kBrushless);
    private final CANSparkMax leftFollowerMotor = new CANSparkMax(DriveConstants.LEFT_MOTOR_PORT + 1, MotorType.kBrushless);

    // The motors on the right side of the drive.
    private final CANSparkMax rightPrimaryMotor  = new CANSparkMax(DriveConstants.RIGHT_MOTOR_PORT, MotorType.kBrushless);
    private final CANSparkMax rightFollowerMotor = new CANSparkMax(DriveConstants.RIGHT_MOTOR_PORT + 1, MotorType.kBrushless);

    // The encoders for left + right motor
    private final RelativeEncoder rightEncoder = rightPrimaryMotor.getEncoder();
    private final RelativeEncoder leftEncoder  = leftPrimaryMotor.getEncoder();

    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        leftFollowerMotor.follow(leftPrimaryMotor);

        leftPrimaryMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);
        leftPrimaryMotor.setIdleMode(IdleMode.kBrake);

        rightFollowerMotor.follow(rightPrimaryMotor);

        rightPrimaryMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);
        rightPrimaryMotor.setIdleMode(IdleMode.kBrake);

        // Setting both encoders to 0
        resetEncoders();
    }

    public double getDistanceInches() {
        return getAverageEncoderDistance() * DriveConstants.INCHES_PER_ENCODER_COUNT;
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance() {
        return (getLeftEncoder() + getRightEncoder()) / 2;
    }

    /**
     * Gets the left drive encoder.
     *
     * @return the left drive encoder
     */
    public double getLeftEncoder() {
        return leftEncoder.getPosition();
    }

    /**
     * Gets the right drive encoder.
     *
     * @return the right drive encoder
     */
    public double getRightEncoder() {
        return rightEncoder.getPosition();
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);
    }

    /**
     * Set the left and right speed of the primary and follower motors
     * 
     * @param leftSpeed
     * @param rightSpeed
     */
    public void setMotorSpeeds(double leftSpeed, double rightSpeed) {

        leftPrimaryMotor.set(leftSpeed);
        rightPrimaryMotor.set(rightSpeed);

        // NOTE: The follower motors are set to follow the primary
        // motors
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Right Motor", rightPrimaryMotor.get());
        SmartDashboard.putNumber("Left  Motor", leftPrimaryMotor.get());

        SmartDashboard.putNumber("Right Encoder", getRightEncoder());
        SmartDashboard.putNumber("Left Encoder", getLeftEncoder());

        SmartDashboard.putNumber("Distance (inches)", getDistanceInches());
    }
}
