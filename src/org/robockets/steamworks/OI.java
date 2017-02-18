package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(0); // XBox Controller
    public static Joystick attack3Left = new Joystick(1);
    public static Joystick attack3Right = new Joystick(2);

    public static Joystick launchpad = new Joystick(3);
    
    Button yButton = new JoystickButton(joystick, 4);

    /////////////////////
    /// Miscellaneous ///
    /////////////////////

    Button misc1 = new JoystickButton(launchpad, 2);
    Button misc2 = new JoystickButton(launchpad, 3);
    Button misc3 = new JoystickButton(launchpad, 4);
    Button misc4 = new JoystickButton(launchpad, 5);

    ///////////////
    /// Climber ///
    ///////////////

    Button climber1 = new JoystickButton(launchpad, 7);
    Button climber2 = new JoystickButton(launchpad, 6);

    ///////////////
    /// Lifter? ///
    ///////////////

    Button lifter1 = new JoystickButton(launchpad, 8);
    Button lifterMan1 = new JoystickButton(launchpad, 20);
    Button lifterMan2 = new JoystickButton(launchpad, 1); // Blame Dominik

    /////////////
    /// Shoot ///
    /////////////

    Button shooter1 = new JoystickButton(launchpad, 10);
    Button shooter2 = new JoystickButton(launchpad, 9);
    Button shooterMan1 = new JoystickButton(launchpad, 19);

    ///////////////////
    /// Gear Intake ///
    ///////////////////

    Button gearIntake1 = new JoystickButton(launchpad, 12);
    Button gearIntake2 = new JoystickButton(launchpad, 11);
    Button gearIntakeMan1 = new JoystickButton(launchpad, 17);
    Button gearIntakeMan2 = new JoystickButton(launchpad, 18);

    ///////////////////
    /// Ball Intake ///
    ///////////////////

    Button ballIntake1 = new JoystickButton(launchpad, 14);
    Button ballIntake2 = new JoystickButton(launchpad, 13);
    Button ballIntakeMan1 = new JoystickButton(launchpad, 16);
    Button ballIntakeMan2 = new JoystickButton(launchpad, 15);

    public OI() {
    	//yButton.whenPressed(Robot.toggleDriveMode);
        bindButtons();
    }

    private void bindButtons() {

    }
}
