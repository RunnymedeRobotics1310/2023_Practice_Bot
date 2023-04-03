package frc.robot.commands.light;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightSubsystem;

public class SetFrenchPatternCommand extends CommandBase {

    private final LightSubsystem lightSubsystem;


    public SetFrenchPatternCommand(LightSubsystem lightSubsystem) {

        this.lightSubsystem = lightSubsystem;

        addRequirements(lightSubsystem);
    }

    @Override
    public void initialize() {

        for (int light = 0; light < 50; light += LightPatterns.french.length) {
            lightSubsystem.setPattern(light, LightPatterns.french);
        }
    }

    @Override
    public void end(boolean interrupted) {
        lightSubsystem.off();
    }

}
