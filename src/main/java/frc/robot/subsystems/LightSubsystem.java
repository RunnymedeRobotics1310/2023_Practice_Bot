package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightSubsystem extends SubsystemBase {

    AddressableLED       ledStrip       = new AddressableLED(0);
    AddressableLEDBuffer ledStripBuffer = new AddressableLEDBuffer(100);

    public LightSubsystem() {
        System.out.println("Initializing LightSubsystem with ledStripBuffer of length " + ledStripBuffer.getLength());

        // Length is expensive to set, so only set it once, then just update data
        ledStrip.setLength(ledStripBuffer.getLength());

        // Set the data
        ledStrip.setData(ledStripBuffer);
        ledStrip.start();
    }

    public void off() {
        setAllToColor(0, 0, 0);
    }

    private void setAllToColor(int R, int G, int B) {

        for (var i = 0; i < ledStripBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            ledStripBuffer.setRGB(i, R, G, B);
        }
        ledStrip.setData(ledStripBuffer);



    }

    public void setPattern(int sectionStart, int[][] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            int[] light = pattern[i];
            ledStripBuffer.setRGB(sectionStart + i, light[0], light[1], light[2]);
        }
        ledStrip.setData(ledStripBuffer);
    }


}

