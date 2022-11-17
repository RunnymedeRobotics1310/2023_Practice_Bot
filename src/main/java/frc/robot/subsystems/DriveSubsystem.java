package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

    // The motors on the left side of the drive.
    private final TalonSRX leftPrimaryMotor  = new TalonSRX(DriveConstants.LEFT_MOTOR_PORT);
    private final TalonSRX leftFollowerMotor = new TalonSRX(DriveConstants.LEFT_MOTOR_PORT + 1);

    // The motors on the right side of the drive.
    private final TalonSRX rightPrimaryMotor  = new TalonSRX(DriveConstants.RIGHT_MOTOR_PORT);
    private final TalonSRX rightFollowerMotor = new TalonSRX(DriveConstants.RIGHT_MOTOR_PORT + 1);

    private double leftSpeed = 0;
    private double rightSpeed = 0;

    ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    
    /** Creates a new DriveSubsystem. */
    public DriveSubsystem() {

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        leftPrimaryMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);
        leftFollowerMotor.setInverted(DriveConstants.LEFT_MOTOR_REVERSED);

        leftPrimaryMotor.setNeutralMode(NeutralMode.Brake);
        leftFollowerMotor.setNeutralMode(NeutralMode.Brake);

        leftFollowerMotor.follow(leftPrimaryMotor);


        rightPrimaryMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);
        rightFollowerMotor.setInverted(DriveConstants.RIGHT_MOTOR_REVERSED);

        rightPrimaryMotor.setNeutralMode(NeutralMode.Brake);
        rightFollowerMotor.setNeutralMode(NeutralMode.Brake);

        rightFollowerMotor.follow(rightPrimaryMotor);

        // Setting both encoders to 0
        resetEncoders();
    }

    /**
     * Gets the heading of the robot.
     *
     * @return heading in the range of 0 - 360 degrees
     */
    public double getHeading() {
    	
    	double gyroAngle = gyro.getAngle();
    	
    	// The angle can be positive or negative and extends beyond 360 degrees.
    	double heading = gyroAngle % 360.0;
    	
    	if (heading < 0) {
    		heading += 360;
    	}

    	return heading;
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
        return 0; //leftEncoder.getPosition();
    }

    /**
     * Gets the right drive encoder.
     *
     * @return the right drive encoder
     */
    public double getRightEncoder() {
        return 0; // rightEncoder.getPosition();
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        //        rightEncoder.setPosition(0);
        //        leftEncoder.setPosition(0);
    }

    /**
     * Set the left and right speed of the primary and follower motors
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void setMotorSpeeds(double leftSpeed, double rightSpeed) {

        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;

        leftPrimaryMotor.set(ControlMode.PercentOutput, leftSpeed);
        rightPrimaryMotor.set(ControlMode.PercentOutput, rightSpeed);

        // NOTE: The follower motors are set to follow the primary
        // motors
    }

    @Override
    public void periodic() {

        SmartDashboard.putNumber("Right Motor", rightSpeed);
        SmartDashboard.putNumber("Left  Motor", leftSpeed);

        SmartDashboard.putNumber("Right Encoder", getRightEncoder());
        SmartDashboard.putNumber("Left Encoder", getLeftEncoder());

        SmartDashboard.putNumber("Distance (inches)", getDistanceInches());
        
        SmartDashboard.putData("Gyro", gyro);
        
        SmartDashboard.putNumber("Heading", getHeading());
    }
}
