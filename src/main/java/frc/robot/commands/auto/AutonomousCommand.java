package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drive.DriveOnHeadingCommand;
import frc.robot.commands.drive.DriveToTargetCommand;
import frc.robot.subsystems.DriveSubsystem;

public class AutonomousCommand extends SequentialCommandGroup {

    private static final String ZONE_AT_GRID           = "At Grid";
    private static final String ZONE_IN_FIELD          = "At Center of Field";

    private String              startingPosition       = null;
    private String              currentOrientation     = null;
    private String              currentZone            = null;

    private Alliance            alliance               = null;

    private String              firstGamePieceScoring  = null;
    private String              exitZoneAction         = null;
    private String              secondGamePieceScoring = null;
    private String              balanceAction          = null;
    private DriveSubsystem      driveSubsystem         = null;


    public AutonomousCommand(DriveSubsystem driveSubsystem,
        SendableChooser<String> startingPositionChooser,
        SendableChooser<String> startingOrientationChooser,
        SendableChooser<String> firstGamePieceScoringChooser,
        SendableChooser<String> exitZoneActionChooser,
        SendableChooser<String> secondGamePieceScoringChooser,
        SendableChooser<String> balanceChooser) {

        this.driveSubsystem = driveSubsystem;

        // Default is to do nothing.
        // If more commands are added, the instant command will end and
        // the next command will be executed.
        addCommands(new InstantCommand());

        startingPosition       = startingPositionChooser.getSelected();
        currentOrientation     = startingOrientationChooser.getSelected();

        firstGamePieceScoring  = firstGamePieceScoringChooser.getSelected();
        exitZoneAction         = exitZoneActionChooser.getSelected();
        secondGamePieceScoring = secondGamePieceScoringChooser.getSelected();
        balanceAction          = balanceChooser.getSelected();

        alliance               = DriverStation.getAlliance();

        StringBuilder sb = new StringBuilder();
        sb.append("Auto Selections");
        sb.append("\n   Starting Postion          :").append(startingPosition);
        sb.append("\n   Starting Orientation      :").append(currentOrientation);
        sb.append("\n   First Game Piece Scoring  :").append(firstGamePieceScoring);
        sb.append("\n   Exit Zone Action          :").append(exitZoneAction);
        sb.append("\n   Second Game Piece Scoring :").append(secondGamePieceScoring);
        sb.append("\n   Balance Action            :").append(balanceAction);
        sb.append("\nAlliance                     :").append(alliance);

        System.out.println(sb.toString());

        // If any of these are null, then there was some kind of error.
        // FIXME: Is there anything we can do here?
        if (startingPosition == null
            || currentOrientation == null
            || firstGamePieceScoring == null
            || exitZoneAction == null
            || secondGamePieceScoring == null
            || balanceAction == null) {

            System.out.println("*** ERROR - null found in auto pattern builder ***");
            return;
        }

        // Print an error if the alliance is not set
        if (alliance == null) {
            System.out.println("*** ERROR **** null Alliance ");
            return;
        }
        else if (alliance == Alliance.Invalid) {
            System.out.println("*** ERROR *** Invalid alliance");
            return;
        }

        // The robot always starts next to the grid
        currentZone = ZONE_AT_GRID;

        /*
         * Set the gyro heading if required
         */
        if (currentOrientation.equals(AutoConstants.AUTO_ORIENTATION_FACE_GRID)) {

            // FIXME:
            // set the gyro heading to 180 degrees to match the robot field alignment
            // zero (North) is always pointing away from the driver station.

            // addCommands(new SetGyroHeadingCommand(180)); <- for example
        }

        /*
         * Compose the required auto commands for each of the steps in the auto
         * based on the selector values
         */
        addStep1Commands_ScoreFirstGamePiece();
        addStep2Commands_ExitZone();
        addStep3Commands_ScoreSecondGamePiece();
        addStep4Commands_Balance();
    }

    /**
     * Step 1 - Score first game piece
     */
    private void addStep1Commands_ScoreFirstGamePiece() {

        // The selector must be set to score low or score mid, otherwise
        // there is nothing to do

        if (!(firstGamePieceScoring.equals(AutoConstants.AUTO_SCORE_LOW)
            || firstGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CONE)
            || firstGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CUBE))) {
            return;
        }

        if (currentOrientation.equals(AutoConstants.AUTO_ORIENTATION_FACE_GRID)) {

            // FIXME:
            // Set the arm position (different positions required for cone or cube
            // Drive forward?
            // Drop the piece

        }
        else {
            // Currently facing the field

            // When facing the field, only a low delivery is allowed because a piece would be
            // balanced on the bumper
            if (firstGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CONE)
                || firstGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CUBE)) {
                System.out.println("Cannot score mid unless facing grid, overriding to score low");
            }

            // FIXME:
            // Drive Backward and forward
            // NOTE: deposit the piece placed on the bumper in the low scoring position using
            // gravity and inertia (or maybe a piston?)
        }
    }

    /**
     * Step 2 - Exit the zone, and optionally pick up a second game piece
     */
    private void addStep2Commands_ExitZone() {

        // An exit zone action must be selected, otherwise do nothing

        if (!(exitZoneAction.equals(AutoConstants.AUTO_LEAVE_ZONE)
            || exitZoneAction.equals(AutoConstants.AUTO_GRAB_PIECE))) {
            return;
        }

        // Start by moving to the center of the field
        if (currentOrientation.equals(AutoConstants.AUTO_ORIENTATION_FACE_GRID)) {

            // FIXME:
            // Back up out of zone
        }
        else {
            addCommands(new DriveOnHeadingCommand(0, 0.5, 400, 2.5, driveSubsystem));
            addCommands(new DriveToTargetCommand(0, 0.1, 400, 10, driveSubsystem));

        }

        currentZone = ZONE_IN_FIELD;

        /*
         * If a piece is not required, this portion is complete
         */
        if (!exitZoneAction.equals(AutoConstants.AUTO_GRAB_PIECE)) {
            return;
        }

        /*
         * Grab a piece
         */

        // FIXME:
        // Rotate to heading zero
        // Note: the robot may already be at heading zero if it started facing the field
        // VISION? Look for a piece
        // Rotate to the target
        // Turn on the arm intake (not sure how this works)
        // Drive forward until object is captured

        currentOrientation = AutoConstants.AUTO_ORIENTATION_FACE_FIELD;
    }

    /**
     * Step 3 - Deliver a second game piece if required
     */
    private void addStep3Commands_ScoreSecondGamePiece() {

        // If a scoring location is not set, there is nothing to do.

        if (!(secondGamePieceScoring.equals(AutoConstants.AUTO_SCORE_LOW)
            || secondGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CONE)
            || secondGamePieceScoring.equals(AutoConstants.AUTO_SCORE_MID_CUBE))) {
            return;
        }

        // Check that that grabbing a piece was scheduled

        if (!exitZoneAction.equals(AutoConstants.AUTO_GRAB_PIECE)) {
            System.out.println("*** ERROR *** Cannot deliver a second piece if it was not picked up");
            // Do nothing
            return;
        }

        // FIXME:
        // Rotate to heading 180 to face the grid
        // Drive over to the grid
        // VISION? Acquire the nearest vision target
        // Position the arm to the appropriate height
        // Drive towards target
        // Drop object

        currentOrientation = AutoConstants.AUTO_ORIENTATION_FACE_GRID;
        currentZone        = ZONE_AT_GRID;
    }

    /**
     * Step 4 - Balance if required
     */
    private void addStep4Commands_Balance() {

        // If the balance command was not selected, there is nothing to do

        if (!balanceAction.equals(AutoConstants.AUTO_BALANCE)) {
            return;
        }

        // FIXME:

        // Determine the path to the platform

        // Determine the path to the charging station based on the starting position, current
        // orientation and current zone.

        // NOTE: This is some complex logic, but essentially the charging station is
        // in one of 6 directions from the robot.
        // If N is toward the opposing alliance, the the charging station is NW, N, NE of
        // the robot when the robot is at the Grid, SW, S, or SE when the robot is in the field.

        // An Example:
        // If the robot is in the Grid area, and the robot is on the Blue Alliance, and
        // the robot has started in the low position, then the charging station is
        // North-West of the robot.

        // In the case of the Red alliance, the charging station will be to the North-East
        // in the same scenario.

        // NOTE: A small additional complexity is that the robot could be currently facing
        // either toward the field or toward the grid.

        // Drive to the platform

        // Balance on the platform
    }
}
