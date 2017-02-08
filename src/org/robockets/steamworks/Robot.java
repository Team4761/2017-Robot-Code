package org.robockets.steamworks;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.commands.*;
import org.robockets.steamworks.subsystems.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	public static BallIntake ballIntake;
  	public static Climber climber;
	public static Conveyor conveyor;
	public static Drivetrain drivetrain;
	public static Shooter shooter;
	public static Elevator elevator;

	public static GearIntake gearIntake;


	private Command autonomousCommand;
	private Command drive;
	private Command climb;
	private SendableChooser chooser = new SendableChooser();

	private CameraServer cameraServer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		ballIntake = new BallIntake();
		elevator = new Elevator();
		conveyor = new Conveyor();
    	climber = new Climber();
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		gearIntake = new GearIntake();


		// chooser.addObject("My Auto", new MyAutoCommand());
		climb = new Climb(0.5);
		drive = new Joyride(0.5);
    
		SmartDashboard.putData("Auto mode", chooser);

		SmartDashboard.putData(new SpinSpinners());
		SmartDashboard.putData(new Shoot());

		SmartDashboard.putData(new Climb(0.5));

		SmartDashboard.putData("MoveConveyorForward", new MoveConveyor(RelativeDirection.YAxis.FORWARD));
		SmartDashboard.putData("MoveConveyorBackward", new MoveConveyor(RelativeDirection.YAxis.BACKWARD));

		RobotMap.gyro.calibrate();

		cameraServer = CameraServer.getInstance();

		cameraServer.startAutomaticCapture();

	}

	@Override
	public void robotPeriodic() {
		gearIntake.periodicSmartDashboard();
		SmartDashboard.putNumber("RawShooterEncoderValue", RobotMap.shooterEncoder.get());

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("GyroData", RobotMap.gyro.getAngle());
		//System.out.println(RobotMap.gyro.getAngle());
		SmartDashboard.putNumber("LeftEncoder", RobotMap.leftEncoder.get());
		SmartDashboard.putNumber("RightEncoder", RobotMap.rightEncoder.get());
		System.out.println(RobotMap.leftEncoder.get());
		System.out.println(RobotMap.rightEncoder.get());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected(); // This is not working

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// Schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		drive.start();

		SmartDashboard.putNumber("GyroP", drivetrain.gyroPID.getP());
		SmartDashboard.putNumber("GyroI", drivetrain.gyroPID.getI());
		SmartDashboard.putNumber("GyroD", drivetrain.gyroPID.getD());
		SmartDashboard.putNumber("GyroSetpoint", drivetrain.gyroPID.getSetpoint());

		SmartDashboard.putData(new TunePID());
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		drivetrain.gyroPID.setPID(SmartDashboard.getNumber("GyroP", 0),SmartDashboard.getNumber("GyroI", 0),SmartDashboard.getNumber("GyroD", 0));
		drivetrain.gyroPID.setSetpoint(SmartDashboard.getNumber("GyroSetpoint", 0));
		System.out.println(RobotMap.gyro.getAngle());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
