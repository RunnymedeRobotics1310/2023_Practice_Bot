// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Global constants
    public static final double DEFAULT_COMMAND_TIMEOUT_SECONDS = 5;

    public static final class AutoConstants {

        public static final String AUTO_START_TOP              = "Top";
        public static final String AUTO_START_MID              = "Mid";
        public static final String AUTO_START_BOTTOM           = "Bottom";

        public static final String AUTO_ORIENTATION_FACE_GRID  = "Face grid";
        public static final String AUTO_ORIENTATION_FACE_FIELD = "Face field";

        public static final String AUTO_DO_NOTHING             = "Do nothing";

        public static final String AUTO_SCORE_LOW              = "Score low";
        public static final String AUTO_SCORE_MID_CONE         = "Score mid cone";
        public static final String AUTO_SCORE_MID_CUBE         = "Score mid cube";

        public static final String AUTO_LEAVE_ZONE             = "Leave zone";
        public static final String AUTO_GRAB_PIECE             = "Grab piece";

        public static final String AUTO_NO_SECOND_PIECE        = "No second piece";

        public static final String AUTO_BALANCE                = "Balance";
    }

    public static final class DriveConstants {

        public static enum DriveMode {
            TANK, ARCADE, QUENTIN;
        }

        public static final int     LEFT_MOTOR_PORT               = 10;
        public static final int     RIGHT_MOTOR_PORT              = 20;

        public static final boolean LEFT_MOTOR_REVERSED           = false;
        public static final boolean RIGHT_MOTOR_REVERSED          = true;

        public static final boolean LEFT_ENCODER_REVERSED         = false;
        public static final boolean RIGHT_ENCODER_REVERSED        = true;

        public static final int     ENCODER_COUNTS_PER_REVOLUTION = 1024;
        public static final double  ROBOT_WHEEL_DIAMETER_INCHES   = 6;

        public static final double  INCHES_PER_ENCODER_COUNT      =
            // Assumes the encoders are directly mounted on the wheel shafts
            (ROBOT_WHEEL_DIAMETER_INCHES * Math.PI) / ENCODER_COUNTS_PER_REVOLUTION;

        public static final boolean GYRO_REVERSED                 = false;
    }

    public static final class OiConstants {

        public static final int DRIVER_CONTROLLER_PORT = 0;
    }

    public static final class VisionConstants {

        /** Time to switch pipelines and acquire a new vision target */
        public static final double TARGET_SWITCH_TIME_SEC = .5;
    }
}
