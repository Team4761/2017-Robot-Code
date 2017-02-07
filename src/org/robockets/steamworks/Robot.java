package org.robockets.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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
import org.robockets.steamworks.drivetrain.*;
import org.robockets.steamworks.subsystems.*;

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
		
		autonomousCommand = new AutoTest();
		
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putData(climb);

		RobotMap.gyro.calibrate();

		SmartDashboard.putData("GyroPIDGo", new TunePID());
		
		// SmartDashboard
		Robot.climber.initSmartDashboard(smartDashboardDebug);

		SmartDashboard.putData(new ResetDriveEncoders());
		
		Webcam.getInstance().startThread();

		SmartDashboard.putData(autonomousCommand);

	}

	@Override
	public void robotPeriodic() {
		/*drivetrain.gyroPID.setPID(SmartDashboard.getNumber("GyroP", 0), SmartDashboard.getNumber("GyroI", 0),SmartDashboard.getNumber("GyroD", 0));
		drivetrain.gyroPID.setSetpoint(SmartDashboard.getNumber("GyroSetpoint", 0));

		drivetrain.leftPodPID.setPID(SmartDashboard.getNumber("EncoderP", 0), SmartDashboard.getNumber("EncoderI", 0),SmartDashboard.getNumber("EncoderD", 0));
		drivetrain.leftPodPID.setSetpoint(SmartDashboard.getNumber("EncoderSetpoint", 0));
		drivetrain.rightPodPID.setPID(SmartDashboard.getNumber("EncoderP", 0), SmartDashboard.getNumber("EncoderI", 0),SmartDashboard.getNumber("EncoderD", 0));
		drivetrain.rightPodPID.setSetpoint(SmartDashboard.getNumber("EncoderSetpoint", 0));
		*/
		
		Robot.climber.periodicSmartDashboard(smartDashboardDebug);
		gearIntake.periodicSmartDashboard();

		SmartDashboard.putNumber("LeftEncoder", RobotMap.leftEncoder.get());
		SmartDashboard.putNumber("RightEncoder", RobotMap.rightEncoder.get());

		SmartDashboard.putNumber("LeftEncoderDistance", RobotMap.leftEncoder.getDistance());
		
		SmartDashboard.putNumber("Uptime", Timer.getFPGATimestamp());
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
		
		SmartDashboard.putNumber("GyroP", drivetrain.gyroPID.getP());
		SmartDashboard.putNumber("GyroI", drivetrain.gyroPID.getI());
		SmartDashboard.putNumber("GyroD", drivetrain.gyroPID.getD());
		SmartDashboard.putNumber("GyroSetpoint", drivetrain.gyroPID.getSetpoint());

		SmartDashboard.putNumber("EncoderP", drivetrain.leftPodPID.getP());
		SmartDashboard.putNumber("EncoderI", drivetrain.leftPodPID.getI());
		SmartDashboard.putNumber("EncoderD", drivetrain.leftPodPID.getD());
		SmartDashboard.putNumber("EncoderSetpoint", 0);


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
