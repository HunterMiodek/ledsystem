package frc.robot.subsystems.led;

import java.util.Random;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class LedSubsystem extends SubsystemBase{ // led test

private final AddressableLED led;
private final AddressableLEDBuffer ledBuffer;

//
public LedSubsystem () {
  led = new AddressableLED(Constants.LED_PORT);
  ledBuffer = new AddressableLEDBuffer(Constants.LED_COUNT);  //LED Count is found through getlength()
  led.setLength(ledBuffer.getLength());

  led.setData(ledBuffer);
  led.start();

}

public void setBlue () {
  int r = 0;
  int g = 0;
  int b = 255;

  for (int i = 0; i < ledBuffer.getLength(); i++) { //iterate ever led in strip and aplly the rbg
      ledBuffer.setRGB(i, r, g, b);
  }

  led.setData(ledBuffer);
}

public void setRed () {
  int r = 0;
  int g = 0;
  int b = 255;

  for (int i = 0; i < ledBuffer.getLength(); i++) { //iterate ever led in strip and aplly the rbg
      ledBuffer.setRGB(i, r, g, b);
  }

  led.setData(ledBuffer);
}
public void setWhite () {
int r = 245;
int g = 240;
int b = 240;

for (int i = 0; i < ledBuffer.getLength(); i++) { //iterate ever led in strip and aplly the rbg
    ledBuffer.setRGB(i, r, g, b);
}

led.setData(ledBuffer);
}



public void setRainbow() {
  for (int i = 0; i < ledBuffer.getLength(); i++) {
      int hue = (Constants.rainbowFirstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
      ledBuffer.setHSV(i, hue, 255, 128);
  }

  led.setData(ledBuffer); 
  }

  public void setAutonomousScrolling() {
    String text = "AUTONOMOUS";
    scrollText(text);
}

public void setTeleOpScrolling() {
    String text = "TELEOP";
    scrollText(text);
}

private void scrollText(String text) {
    int bufferLength = ledBuffer.getLength();
    int textLength = text.length();
    int[] textPattern = new int[textLength * 5]; // Assuming each character is 5 LEDs wide

    // Create a pattern for the text
    for (int i = 0; i < textLength; i++) {
        char c = text.charAt(i);
        int charIndex = (int) c - 65; // Assuming 'A' starts at 0
        for (int j = 0; j < 5; j++) {
            textPattern[i * 5 + j] = (charIndex >> j) & 1;
        }
    }

    // Scroll the text across the LEDs
    for (int offset = 0; offset < bufferLength + textLength * 5; offset++) {
        for (int i = 0; i < bufferLength; i++) {
            int patternIndex = (i + offset) % textPattern.length;
            if (textPattern[patternIndex] == 1) {
                ledBuffer.setRGB(i, 255, 255, 255); // White color for text
            } else {
                ledBuffer.setRGB(i, 0, 0, 0); // Off for background
            }
        }
        led.setData(ledBuffer);
        try {
            Thread.sleep(100); // Adjust the delay for scrolling speed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public void setLedAutonomous() {
boolean autonomous = DriverStation.isAutonomous();
if (autonomous) {
if (DriverStation.isAutonomousEnabled()) {
setAutonomousScrolling();
} else {
setTeleOpScrolling();
}
}
}
public void setLedAlliance() {
  var alliance = DriverStation.getAlliance();
  
  if (alliance.isPresent()) { 
      if (alliance.get() == DriverStation.Alliance.Red) {
          setRed();
      } else if (alliance.get() == DriverStation.Alliance.Blue) {
          setBlue();
      }
  } else {
      setRainbow();
  }
}
}