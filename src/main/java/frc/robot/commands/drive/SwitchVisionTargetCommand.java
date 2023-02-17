package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.VisionSubsystem.VisionTargetType;

public class SwitchVisionTargetCommand extends CommandBase {

    private final VisionSubsystem visionSubsystem;

    /**
     * Set the current heading in the driveSubsystem
     *
     * @param pipeline value 0-7
     * @param visionSubsystem
     */
    public SwitchVisionTargetCommand(VisionSubsystem visionSubsystem) {

        this.visionSubsystem = visionSubsystem;
    }

    @Override
    public void execute() {
        System.out.println("SwitchVisionTargetCommand");
        VisionTargetType currentTargetType = visionSubsystem.getCurrentVisionTargetType();
        System.out.println("Current target type is " + currentTargetType);
        // please optimize me
        int idx = 0;
        for (int i = 0; i < VisionTargetType.values().length; i++) {
            if (currentTargetType == VisionTargetType.values()[i]) {
                System.out.println("Vision target found in array - index is " + i);
                idx = i;
                break;
            }
        }

        idx++;
        if (idx == VisionTargetType.values().length) {
            idx = 0;
        }

        VisionTargetType nextTargetType = VisionTargetType.values()[idx];
        System.out.println("Setting vision target type to " + nextTargetType);
        visionSubsystem.setVisionTargetType(nextTargetType);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
