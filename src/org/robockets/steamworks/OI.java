package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.ballintake.IntakeBalls;
import org.robockets.steamworks.ballintake.SpinBallIntakeRollers;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.commands.KillEverything;
import org.robockets.steamworks.commands.MakeExtraSpace;
import org.robockets.steamworks.commands.MaxFillElevator;
import org.robockets.steamworks.commands.MoveConveyor;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.steamworks.commands.Shoot;
import org.robockets.steamworks.commands.ShootWithPID;
import org.robockets.steamworks.commands.SpinSpinners;
import org.robockets.steamworks.intakeflap.IntakeFlap;
import org.robockets.steamworks.intakeflap.IntakeToPos;
import org.robockets.steamworks.intakeflap.MoveIntakeFlap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(0); // XBox Controller
    public static Joystick attack3Left = new Joystick(1); // Left attack joystick
    public static Joystick attack3Right = new Joystick(2); // Right attack joystick

    public static Joystick launchpad = new Joystick(3); // The launchpad for the button board
    
    Button yButton = new JoystickButton(joystick, 4);

    /////////////////////
    /// Miscellaneous ///
    /////////////////////

    Button misc1 = new JoystickButton(launchpad, 2);
    Button misc2 = new JoystickButton(launchpad, 6);
    Button misc3 = new JoystickButton(launchpad, 4);
    Button misc4 = new JoystickButton(launchpad, 5);

    ///////////////
    /// Climber ///
    ///////////////

    Button climber1 = new JoystickButton(launchpad, 7);
    Button climber2 = new JoystickButton(launchpad, 3);

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


        /////////////////
        // Gear Intake //
        /////////////////
    	gearIntake1.whenPressed(new IntakeToPos(IntakeFlap.IntakeState.FUEL));
        gearIntake2.whenPressed(new IntakeToPos(IntakeFlap.IntakeState.GEARS));
        //gearIntakeMan1.whileHeld(new MoveIntakeFlap(RelativeDirection.YAxis.FORWARD));
        //gearIntakeMan2.whileHeld(new MoveIntakeFlap(RelativeDirection.YAxis.BACKWARD));

        /////////////
        // Shooter //
        /////////////
        shooterMan1.toggleWhenPressed(new SpinSpinners());
        shooter1.whileHeld(new Shoot(true));
        shooter2.whileHeld(new Shoot(false));

        /////////////////
        // Ball Intake //
        /////////////////
        /*ballIntake1.toggleWhenPressed(new SpinBallIntakeRollers(-1));
        ballIntake2.toggleWhenPressed(new IntakeBalls());*/
        
        SpinBallIntakeRollers spinBallIntakeRollers = new SpinBallIntakeRollers(-1);
        
        ballIntake1.cancelWhenPressed(spinBallIntakeRollers);
        ballIntake2.whenPressed(spinBallIntakeRollers);
        ballIntakeMan1.whileHeld(new SpinBallIntakeRollers(1));
        ballIntakeMan2.whileHeld(new SpinBallIntakeRollers(-1)); // FIXME: Make this a relative direction thing

        //////////////////
        // Magic Carpet //
        //////////////////
        // The horizontal conveyor, "Magic Carpet," is moved when `MoveElevator` is called
        lifter1.whileHeld(new MakeExtraSpace());
        //lifterMan1.whileHeld(new MoveElevator(RelativeDirection.ZAxis.UP, 0.75, true));
        lifterMan1.whileHeld(new MoveElevator(RelativeDirection.ZAxis.DOWN, 0.75));

        /////////////
        // Climber //
        /////////////
        climber1.whileHeld(new Climb(1));
        climber2.whileHeld(new Climb(1));  
        /*
        shooterMan1.whenPressed(new KillEverything()); */
        //misc2.whenPressed(new Climb(1));*/
    }
}
