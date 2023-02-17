package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.VisionSubsystem.VisionTargetType;

public class SwitchVisionTargetCommand extends CommandBase {

    private final VisionSubsystem  visionSubsystem;
    private final VisionTargetType visionTargetType;

    /**
     * Switches the vision target to the next {@link VisionTargetType} enum value
     * 
     * @param visionSubsystem
     */
    public SwitchVisionTargetCommand(VisionSubsystem visionSubsystem) {

        this(null, visionSubsystem);
    }

    /**
     * Switch the vision target to the specified {@link VisionTargetType}.
     * 
     * @param visionTargetType requested vision target type
     * @param visionSubsystem
     */
    public SwitchVisionTargetCommand(VisionTargetType visionTargetType, VisionSubsystem visionSubsystem) {

        this.visionTargetType = visionTargetType;
        this.visionSubsystem  = visionSubsystem;
    }


    @Override
    public void initialize() {

        System.out.print("SwitchVisionTargetCommand");
        if (visionTargetType != null) {
            System.out.println(": Switch to vision target " + visionTargetType);
        }
        else {
            System.out.println(": Switch to next value");
        }

        // If the vision target was passed in, then use that value
        if (visionTargetType != null) {
            visionSubsystem.setVisionTargetType(visionTargetType);
            return;
        }

        // If the vision target was not passed in, then set the vision target to the
        // next value in the enum

        VisionTargetType currentTargetType = visionSubsystem.getCurrentVisionTargetType();
        System.out.println("Current target type is " + currentTargetType);

        // please optimize me
        // FIXME: @tony... how about...
        // int idx = currentTargetType.ordinal();
        // int nextIdx = (idx + 1) % VisionTargetType.values().length;
        // NOTE: Uses modulo, the if-statement may be clearer for students.
        int idx = 0;
        for (int i = 0; i < VisionTargetType.values().length; i++) {
            if (currentTargetType == VisionTargetType.values()[i]) {
                System.out.println("Vision target found in array - index is " + i);
                idx = i;
                break;
            }
        }

        int nextIdx = idx++;
        if (nextIdx == VisionTargetType.values().length) {
            nextIdx = 0;
        }

        VisionTargetType nextVisionTargetType = VisionTargetType.values()[nextIdx];
        System.out.println("Setting vision target type to " + nextVisionTargetType);

        visionSubsystem.setVisionTargetType(nextVisionTargetType);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
