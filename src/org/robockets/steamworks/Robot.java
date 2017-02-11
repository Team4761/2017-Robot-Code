package org.robockets.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.steamworks.autonomous.AutoTest;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.climber.Climber;
import org.robockets.steamworks.camera.Webcam;
import org.robockets.steamworks.commands.TunePID;
import org.robockets.steamworks.drivetrain.Drivetrain;
import org.robockets.steamworks.drivetrain.Joyride;
import org.robockets.steamworks.drivetrain.ResetDriveEncoders;
import org.robockets.steamworks.subsystems.BallIntake;
import org.robockets.steamworks.subsystems.Conveyor;
import org.robockets.steamworks.subsystems.GearIntake;
import org.robockets.steamworks.subsystems.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final double MAX_SPEED = 240; // in/s
	
	public static OI oi;

	public static BallIntake ballIntake;
	public static Climber climber;
	public static Conveyor conveyor;
	public static Drivetrain drivetrain;
	public static Shooter shooter;
	public static GearIntake gearIntake;

	private Command autonomousCommand;
	private Command drive;
	private Command climb;
	private SendableChooser chooser = new SendableChooser();
	
	private boolean smartDashboardDebug = true;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();

		ballIntake = new BallIntake();
		conveyor = new Conveyor();
		climber = new Climber();
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		gearIntake = new GearIntake();

		// chooser.addObject("My Auto", new MyAutoCommand());
		climb = new Climb(0.5);
		drive = new Joyride(0.5);
		
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putData(climb);

		RobotMap.gyro.calibrate();

		SmartDashboard.putData("GyroPIDGo", new TunePID());
		
		// SmartDashboard
		Robot.climber.initSmartDashboard(smartDashboardDebug);

		SmartDashboard.putData(new ResetDriveEncoders());
		
		Webcam.getInstance().startThread();

		autonomousCommand = new AutoTest();

		SmartDashboard.putData(autonomousCommand);

	}

	@Override
	public void robotPeriodic() {
		
		Robot.climber.periodicSmartDashboard(smartDashboardDebug);
		gearIntake.periodicSmartDashboard();

		SDDumper.dumpEncoder("Left encoder", RobotMap.leftEncoder);
		SDDumper.dumpEncoder("Right encoder", RobotMap.rightEncoder);
		
		SDDumper.dumpPidController("Left drivepod PID", drivetrain.leftPodPID);
		SDDumper.dumpPidController("Right drivepod PID", drivetrain.rightPodPID);
		
		SDDumper.dumpMisc();
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
		autonomousCommand = new AutoTest();
		if(autonomousCommand != null) {
			autonomousCommand.start();
		}
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
		
		drivetrain.leftPodPID.enable();
		drivetrain.rightPodPID.enable();
		drive.start();

		SmartDashboard.putData(new TunePID());

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		final boolean encoderPIDStatus = Robot.drivetrain.isEncoderPIDEnabled();
		if(!encoderPIDStatus) {
			System.out.println("move to manual control");
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
