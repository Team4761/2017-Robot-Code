package org.robockets.steamworks;

import org.robockets.steamworks.commands.ToggleDriveMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(PortNumbers.JOYSTICK_PORT_NUMBER);
    
    Button yButton = new JoystickButton(joystick, 4);
    
    public OI() {
    	yButton.whenPressed(Robot.toggleDriveMode);
    }
}
