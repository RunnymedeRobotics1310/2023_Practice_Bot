package frc.robot.commands.light;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.LightPattern;

import static edu.wpi.first.wpilibj.util.Color.*;

public class Patterns {
    public static final LightPattern FRENCH = new LightPattern(kBlue, kRed, kWhite);
    public static final LightPattern RUNNYMEDE = new LightPattern(kRed, kBlack);

    public static LightPattern createSolidPattern(int length, Color color) {

        LightPattern pattern = new LightPattern();
        for (int i = 0; i < length; i++) {
            pattern.append(color);
        }
        return pattern;
    }

}
