package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.gearintake.MoveGearIntakeArm;
import org.robockets.steamworks.gearintake.SpinGearIntake;
import org.robockets.steamworks.gearintake.SpitItOut;
import org.robockets.steamworks.lights.SendLight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joystick = new Joystick(0); // XBox Controller

    public static Joystick operatorJoystick = new Joystick(5);

    private Button driverRightBumper = new JoystickButton(joystick, 6);

    private Button aButton = new JoystickButton(operatorJoystick, 1);
    private Button bButton = new JoystickButton(operatorJoystick, 2);
    private Button xButton = new JoystickButton(operatorJoystick, 3);
    private Button yButton = new JoystickButton(operatorJoystick, 4);
    private Button leftBumperButton = new JoystickButton(operatorJoystick, 5);
    private Button rightBumperButton = new JoystickButton(operatorJoystick, 6);
    private Button selectButton = new JoystickButton(operatorJoystick, 7);
    private Button startButton = new JoystickButton(operatorJoystick, 8);
    //private Button leftStickDown = new JoystickButton(operatorJoystick, 9); // Don't use me!!!
    private Button rightStickDown = new JoystickButton(operatorJoystick, 10);

    public OI() {
        bindButtons();
    }

    private void bindButtons() {

        //xButton.whileHeld(new ShootWithPID(63));
        //yButton.whileHeld(new ShootWithPID(80));

		/*Button.whileHeld(new SpinGearIntake(RelativeDirection.Malone.IN, 0.2));
		yButton.whileHeld(new SpinGearIntake(RelativeDirection.Malone.OUT, 0.2));

		bButton.whileHeld(new SpinGearIntake(RelativeDirection.Malone.OUT, 0.60));
		aButton.whileHeld(new SpinGearIntake(RelativeDirection.Malone.IN, 0.60));

        leftBumperButton.whileHeld(new MoveGearIntakeArm(RelativeDirection.ZAxis.UP, 0.12, true));

        rightBumperButton.whenPressed(new SpitItOut());*/

		aButton.whenPressed(new SendLight(4));
		xButton.whenPressed(new SendLight(3));
		yButton.whenPressed(new SendLight(2));
		startButton.whenPressed(new SendLight(56));
		rightBumperButton.whenPressed(new SendLight(1));
		leftBumperButton.whenPressed(new SendLight(6));

        driverRightBumper.whileHeld(new Climb(1));

    }
    
    protected static void initTestMode() {
    	final String LEFT_DRIVEPOD_SUBSYSTEM_NAME = "Left Drivepod";
		LiveWindow.addSensor(LEFT_DRIVEPOD_SUBSYSTEM_NAME, "Encoder", RobotMap.leftEncoder);
		LiveWindow.addActuator(LEFT_DRIVEPOD_SUBSYSTEM_NAME, "Speed controller", RobotMap.leftDrivepodSpeedController);
		
		final String RIGHT_DRIVEPOD_SUBSYSTEM_NAME = "Right Drivepod";
		LiveWindow.addSensor(RIGHT_DRIVEPOD_SUBSYSTEM_NAME, "Encoder", RobotMap.rightEncoder);
		LiveWindow.addActuator(RIGHT_DRIVEPOD_SUBSYSTEM_NAME, "Speed controller", RobotMap.rightDrivepodSpeedController);
		
		final String GEAR_INTAKE_SUBSYSTEM_NAME = "Gear intake";
		LiveWindow.addActuator(GEAR_INTAKE_SUBSYSTEM_NAME, "Left servo", RobotMap.leftIntakeFlapServo);
		LiveWindow.addActuator(GEAR_INTAKE_SUBSYSTEM_NAME, "Right servo", RobotMap.rightIntakeFlapServo);
		LiveWindow.addSensor(GEAR_INTAKE_SUBSYSTEM_NAME, "Breakbeam sensor", RobotMap.gearInputBreakbeamSensor);
		
		final String ELEVATOR_SUBSYSTEM_NAME = "Elevator";
		LiveWindow.addActuator(ELEVATOR_SUBSYSTEM_NAME, "Speed controller", RobotMap.elevatorSpeedController);
		LiveWindow.addSensor(ELEVATOR_SUBSYSTEM_NAME, "Breakbeam sensor", RobotMap.elevatorBreakbeamSensor);
		
		final String SHOOTER_SUBSYSTEM_NAME = "Shooter";
		LiveWindow.addActuator(SHOOTER_SUBSYSTEM_NAME, "Roller speed controller", RobotMap.shooterRollerSpeedController);
		LiveWindow.addSensor(SHOOTER_SUBSYSTEM_NAME, "Touchless encoder", RobotMap.rollerEncoderCounter);
		
		final String GYRO_SUBSYSTEM_NAME = "Gyro";
		LiveWindow.addSensor(GYRO_SUBSYSTEM_NAME, "Gyro", RobotMap.gyro);
		
		final String FANCY_GEAR_INTAKE_SUBSYSTEM_NAME = "Fancy gear intake";
		LiveWindow.addActuator(FANCY_GEAR_INTAKE_SUBSYSTEM_NAME, "Wheels", RobotMap.gearIntakeWheels);
		LiveWindow.addActuator(FANCY_GEAR_INTAKE_SUBSYSTEM_NAME, "Arm", RobotMap.gearIntakeArm);
		LiveWindow.addSensor(FANCY_GEAR_INTAKE_SUBSYSTEM_NAME, "Lower limit switch", RobotMap.gearIntakeLowerLimitSwitch);
		LiveWindow.addSensor(FANCY_GEAR_INTAKE_SUBSYSTEM_NAME, "Upper limit switch", RobotMap.gearIntakeUpperLimitSwitch);
		LiveWindow.addSensor(FANCY_GEAR_INTAKE_SUBSYSTEM_NAME, "Breakbeam sensor", RobotMap.gearInputBreakbeamSensor);
    }
}
