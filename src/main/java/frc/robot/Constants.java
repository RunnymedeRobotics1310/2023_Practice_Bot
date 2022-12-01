// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {

        public static final int LEFT_MOTOR_PORT = 10;
        public static final int RIGHT_MOTOR_PORT = 20;
        public static final boolean LEFT_MOTOR_REVERSED = true;
        public static final boolean RIGHT_MOTOR_REVERSED = false;

        public static final boolean LEFT_ENCODER_REVERSED = false;
        public static final boolean RIGHT_ENCODER_REVERSED = true;

        public static final int ENCODER_COUNTS_PER_REVOLUTION = 1024;
        public static final double ROBOT_WHEEL_DIAMETER_INCHES = 6;

        public static final double INCHES_PER_ENCODER_COUNT =
                // Assumes the encoders are directly mounted on the wheel shafts
                (ROBOT_WHEEL_DIAMETER_INCHES * Math.PI) / ENCODER_COUNTS_PER_REVOLUTION;

        public static final boolean GYRO_REVERSED = false;
    }

    public static final class AutoConstants {
        public static final String AUTO_PATTERN_DO_NOTHING = "Do nothing";
        public static final String AUTO_PATTERN_MOVE = "Move";
    }

    public static final class OiConstants {

        public static final int DRIVER_CONTROLLER_PORT = 0;
    }

}
