package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.ballintake.SpinBallIntakeRollers;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.climber.ClimberListener;
import org.robockets.steamworks.commands.MakeExtraSpace;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.steamworks.intakeflap.IntakeFlap;
import org.robockets.steamworks.intakeflap.IntakeToPos;
import org.robockets.steamworks.lights.ButtonPress;
import org.robockets.steamworks.lights.KillLights;
import org.robockets.steamworks.lights.LightsColors;
import org.robockets.steamworks.shooter.Shoot;
import org.robockets.steamworks.shooter.SpinSpinners;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(0); // XBox Controller
    public static Joystick attack3Left = new Joystick(1); // Left attack joystick
    public static Joystick attack3Right = new Joystick(2); // Right attack joystick

    public static Joystick launchpad1 = new Joystick(3); // The left launchpad for the button board

    public static Joystick launchpad2 = new Joystick(4); // The right launchpad for the button board

    public static Joystick backupJoystick = new Joystick(5);

    Button driverRightBumper = new JoystickButton(joystick, 6);

    Button aButton = new JoystickButton(backupJoystick, 1);
    Button bButton = new JoystickButton(backupJoystick, 2);
    Button xButton = new JoystickButton(backupJoystick, 3);
    Button yButton = new JoystickButton(backupJoystick, 4);
    Button leftBumperButton = new JoystickButton(backupJoystick, 5);
    Button rightBumperButton = new JoystickButton(backupJoystick, 6);
    Button selectButton = new JoystickButton(backupJoystick, 7);
    Button startButton = new JoystickButton(backupJoystick, 8);

    /////////////////////
    /// Miscellaneous ///
    /////////////////////

    Button misc1 = new JoystickButton(launchpad2, 7); // Done
    Button misc2 = new JoystickButton(launchpad2, 14); // Done
    Button misc3 = new JoystickButton(launchpad2, 9); // Done
    Button misc4 = new JoystickButton(launchpad2, 5); // Does not work
    Button misc5 = new JoystickButton(launchpad2, 15); // Done

    ///////////////
    /// Climber ///
    ///////////////

    Button climber1 = new JoystickButton(launchpad2, 10); // Done
    Button climber2 = new JoystickButton(launchpad2, 16); // Done

    ///////////////
    /// Lifter? ///
    ///////////////

    Button lifter1 = new JoystickButton(launchpad1, 4); // Done
    Button lifterMan1 = new JoystickButton(launchpad1, 5); // Done
    Button lifterMan2 = new JoystickButton(launchpad1, 6); // Done

    /////////////
    /// Shoot ///
    /////////////

    Button shooter1 = new JoystickButton(launchpad1, 2); // Done
    Button shooter2 = new JoystickButton(launchpad1, 3); // Done
    Button shooterMan1 = new JoystickButton(launchpad1, 8); // Done

    ///////////////////
    /// Gear Intake ///
    ///////////////////

    Button gearIntake1 = new JoystickButton(launchpad2, 15); // Done
    Button gearIntake2 = new JoystickButton(launchpad1, 11); // Done
    Button gearIntakeMan1 = new JoystickButton(launchpad1, 14); // No
    Button gearIntakeMan2 = new JoystickButton(launchpad2, 6); // No

    ///////////////////
    /// Ball Intake ///
    ///////////////////

    Button ballIntake1 = new JoystickButton(launchpad1, 12); // Done
    Button ballIntake2 = new JoystickButton(launchpad1, 14); // Done
    Button ballIntakeMan1 = new JoystickButton(launchpad1, 10);
    Button ballIntakeMan2 = new JoystickButton(launchpad1, 10); // Does not work

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
        ballIntakeMan1.whileHeld(new SpinBallIntakeRollers(-1));
        ballIntakeMan2.whileHeld(new SpinBallIntakeRollers(1)); // FIXME: Make this a relative direction thing

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
        climber1.whileHeld(new Climb(0.5));
        climber2.whileHeld(new Climb(1));  
        /*
        shooterMan1.whenPressed(new KillEverything()); */
        //misc2.whenPressed(new Climb(1));*/


        ////////////
        // Backup //
        ////////////

        yButton.whenPressed(new IntakeToPos(IntakeFlap.IntakeState.GEARS));
        yButton.whenReleased(new KillLights());
        aButton.whenPressed(new IntakeToPos(IntakeFlap.IntakeState.FUEL));
        aButton.whenReleased(new KillLights());

        rightBumperButton.whileHeld(new SpinBallIntakeRollers(-1));
        leftBumperButton.whileHeld(new SpinBallIntakeRollers(1));

        driverRightBumper.whileHeld(new Climb(1));

        //xButton.whileHeld(new ButtonPress(LightsColors.BLUE));
        //bButton.whileHeld(new ButtonPress(LightsColors.WHITE));
    }
    
    protected static void initTestMode() {
    	final String LEFT_DRIVEPOD_SUBSYSTEM_NAME = "Left Drivepod";
		LiveWindow.addSensor(LEFT_DRIVEPOD_SUBSYSTEM_NAME, "Encoder", RobotMap.leftEncoder);
		LiveWindow.addActuator(LEFT_DRIVEPOD_SUBSYSTEM_NAME, "Speed controller", RobotMap.leftDrivepodSpeedController);
		
		final String RIGHT_DRIVEPOD_SUBSYSTEM_NAME = "Right Drivepod";
		LiveWindow.addSensor(RIGHT_DRIVEPOD_SUBSYSTEM_NAME, "Encoder", RobotMap.rightEncoder);
		LiveWindow.addActuator(RIGHT_DRIVEPOD_SUBSYSTEM_NAME, "Speed controller", RobotMap.rightDrivepodSpeedController);
		
		final String BALL_INTAKE_SUBSYSTEM_NAME = "Ball Intake";
		LiveWindow.addActuator(BALL_INTAKE_SUBSYSTEM_NAME, "Speed controller", RobotMap.ballIntakeRollerSpeedController);
		
		final String GEAR_INTAKE_SUBSYSTEM_NAME = "Gear intake";
		LiveWindow.addActuator(GEAR_INTAKE_SUBSYSTEM_NAME, "Left servo", RobotMap.leftIntakeFlapServo);
		LiveWindow.addActuator(GEAR_INTAKE_SUBSYSTEM_NAME, "Right servo", RobotMap.rightIntakeFlapServo);
		LiveWindow.addSensor(GEAR_INTAKE_SUBSYSTEM_NAME, "Breakbeam sensor", RobotMap.gearInputBreakbeamSensor);
		
		final String CONVEYOR_SUBSYSTEM_NAME = "Conveyor";
		LiveWindow.addActuator(CONVEYOR_SUBSYSTEM_NAME, "Speed controller", RobotMap.conveyorSpeedController);
		
		final String ELEVATOR_SUBSYSTEM_NAME = "Elevator";
		LiveWindow.addActuator(ELEVATOR_SUBSYSTEM_NAME, "Speed controller", RobotMap.elevatorSpeedController);
		LiveWindow.addSensor(ELEVATOR_SUBSYSTEM_NAME, "Breakbeam sensor", RobotMap.elevatorBreakbeamSensor);
		
		final String SHOOTER_SUBSYSTEM_NAME = "Shooter";
		LiveWindow.addActuator(SHOOTER_SUBSYSTEM_NAME, "Roller speed controller", RobotMap.shooterRollerSpeedController);
		LiveWindow.addSensor(SHOOTER_SUBSYSTEM_NAME, "Touchless encoder", RobotMap.rollerEncoderCounter);
		
		final String GYRO_SUBSYSTEM_NAME = "Gyro";
		LiveWindow.addSensor(GYRO_SUBSYSTEM_NAME, "Gyro", RobotMap.gyro);
    }
}
