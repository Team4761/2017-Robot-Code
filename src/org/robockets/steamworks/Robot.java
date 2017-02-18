package org.robockets.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.autonomous.AutoTest;
import org.robockets.steamworks.ballintake.BallIntake;
import org.robockets.steamworks.ballintake.SpinBallIntakeRollers;
import org.robockets.steamworks.camera.Webcam;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.climber.Climber;
import org.robockets.steamworks.commands.Cylon;
import org.robockets.steamworks.commands.MaxFillElevator;
import org.robockets.steamworks.commands.MoveConveyor;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.steamworks.commands.Shoot;
import org.robockets.steamworks.commands.SpinSpinners;
import org.robockets.steamworks.commands.TunePID;
import org.robockets.steamworks.drivetrain.Drivetrain;
import org.robockets.steamworks.drivetrain.Joyride;
import org.robockets.steamworks.drivetrain.ResetDriveEncoders;
import org.robockets.steamworks.drivetrain.ToggleDriveMode;
import org.robockets.steamworks.drivetrain.Turn;
import org.robockets.steamworks.subsystems.Conveyor;
import org.robockets.steamworks.subsystems.Elevator;
import org.robockets.steamworks.subsystems.GearIntake;
import org.robockets.steamworks.subsystems.LED;
import org.robockets.steamworks.subsystems.Shooter;
import org.robockets.steamworks.intakeflap.IntakeFlap;
import org.robockets.steamworks.intakeflap.ToggleIntakeFlap;

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
	public static LED ledSubsystem = new LED();
	public static Command autonomousCommand;
	public static Command autoTest;
	public static Command drive;
	public static Command climb;
	public static Command toggleDriveMode;
	public static Command cylonCommand = new Cylon();
	public static IntakeFlap intakeFlap;

	private SendableChooser<Command> autonomousChooser;
	
	private boolean smartDashboardDebug = true;

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
		intakeFlap = new IntakeFlap(1);

		// chooser.addObject("My Auto", new MyAutoCommand());
		climb = new Climb(0.5);
		drive = new Joyride(false);

		SmartDashboard.putData(new SpinSpinners());
		SmartDashboard.putData(new Shoot());

		SmartDashboard.putData(new Climb(0.5));

		toggleDriveMode = new ToggleDriveMode();
		climb = new Climb(0.5);
		drive = new Joyride(false);
		
		SmartDashboard.putData(climb);

		SmartDashboard.putNumber("New Gyro Angle(AbsoluteOrRelative)", 0);

		SmartDashboard.putData("GyroTurn Absolute", new Turn(TurnType.ABSOLUTE, 90)); // Angle will be on SmartDashboard from the Turn command
		SmartDashboard.putData("GyroTurn Relative", new Turn(TurnType.RELATIVE, 90));

		SmartDashboard.putData("IntakeRollersForward", new SpinBallIntakeRollers(1));
		SmartDashboard.putData("IntakeRollersBackward", new SpinBallIntakeRollers(-1));

		SmartDashboard.putData("MoveMagicCarpetForward", new MoveConveyor(RelativeDirection.YAxis.FORWARD));
		SmartDashboard.putData("MoveMagicCarpetBackward", new MoveConveyor(RelativeDirection.YAxis.BACKWARD));

		SmartDashboard.putData("MoveElevatorUp", new MoveElevator(RelativeDirection.ZAxis.UP, 1));
		SmartDashboard.putData("MoveElevatorDown", new MoveElevator(RelativeDirection.ZAxis.DOWN, 1));

		SmartDashboard.putData(new MaxFillElevator());

		RobotMap.gyro.calibrate();

		SmartDashboard.putNumber("GyroP", drivetrain.gyroPID.getP());
		SmartDashboard.putNumber("GyroI", drivetrain.gyroPID.getI());
		SmartDashboard.putNumber("GyroD", drivetrain.gyroPID.getD());
		SmartDashboard.putNumber("GyroSetpoint", drivetrain.gyroPID.getSetpoint());

		SmartDashboard.putData("GyroPIDGo", new TunePID());
		
		// SmartDashboard
		Robot.climber.initSmartDashboard(smartDashboardDebug);

		SmartDashboard.putData(new ResetDriveEncoders());
		
		Webcam.getInstance().startThread();

		autoTest = new AutoTest();
		autonomousChooser = new SendableChooser<>();
		autonomousChooser.addDefault("AutoTest", autoTest);
		//autonomousChooser.addObject("Another auto", myAuto);

		SmartDashboard.putData("Autonomous selector", autonomousChooser);
		SmartDashboard.putData(new ToggleIntakeFlap());

	}

	@Override
	public void robotPeriodic() {

		Robot.climber.periodicSmartDashboard(smartDashboardDebug);
		gearIntake.periodicSmartDashboard();

		SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());

		drivetrain.gyroPID.setPID(SmartDashboard.getNumber("GyroP", 0),SmartDashboard.getNumber("GyroI", 0),SmartDashboard.getNumber("GyroD", 0));
		drivetrain.gyroPID.setSetpoint(SmartDashboard.getNumber("GyroSetpoint", 0));
		//System.out.println(RobotMap.gyro.getAngle());


		SDDumper.dumpEncoder("Left encoder", RobotMap.leftEncoder);
		SDDumper.dumpEncoder("Right encoder", RobotMap.rightEncoder);

		SDDumper.dumpPidController("Left drivepod PID", drivetrain.leftPodPID);
		SDDumper.dumpPidController("Right drivepod PID", drivetrain.rightPodPID);

		SDDumper.dumpEncoder("Roller Encoder", RobotMap.rollerEncoder);

		SDDumper.dumpMisc();
		
		SmartDashboard.putNumber("Intake flap encoder position", RobotMap.intakeFlapServo.get());
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
		autonomousCommand = autonomousChooser.getSelected();
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
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}

		drive.start();

		cylonCommand.start();
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
