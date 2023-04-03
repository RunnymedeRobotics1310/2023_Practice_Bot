package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {

    private static final int LIGHT_STRIP_LENGTH = 150;
    AddressableLED           ledStrip           = new AddressableLED(0);
    List<int[]>              lights             = new ArrayList<>();

    public LightSubsystem() {
        System.out.println("Initializing LightSubsystem with ledStripBuffer of length " + LIGHT_STRIP_LENGTH);

        // Length is expensive to set, so only set it once, then just update data
        ledStrip.setLength(LIGHT_STRIP_LENGTH);

        // Set the data
        ledStrip.setData(new AddressableLEDBuffer(LIGHT_STRIP_LENGTH));
        ledStrip.start();
    }

    private static final int[] OFF = { 0, 0, 0 };

    public void off() {
        for (int i = 0; i < LIGHT_STRIP_LENGTH; i++) {
            lights.set(i, OFF);
        }
        setLights();
    }

    public void setPattern(int sectionStart, int[][] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            int[] light = pattern[i];
            lights.set(i, light);
        }
        setLights();
    }

    private void setLights() {
        AddressableLEDBuffer buffer = new AddressableLEDBuffer(LIGHT_STRIP_LENGTH);
        for (int i = 0; i < lights.size(); i++) {
            int[] light = lights.get(i);
            buffer.setRGB(i, light[0], light[1], light[2]);
        }
        ledStrip.setData(buffer);
    }


}

