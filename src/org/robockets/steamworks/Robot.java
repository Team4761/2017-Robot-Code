package org.robockets.steamworks;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.autonomous.AutoTest;
import org.robockets.steamworks.autonomous.EasyAuto;
import org.robockets.steamworks.autonomous.MaxAuto;
import org.robockets.steamworks.autonomous.MidAuto;
import org.robockets.steamworks.ballintake.BallIntake;
import org.robockets.steamworks.ballintake.SpinBallIntakeRollers;
import org.robockets.steamworks.ballintake.IntakeBalls;
import org.robockets.steamworks.camera.Webcam;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.climber.Climber;
import org.robockets.steamworks.commands.Cylon;
import org.robockets.steamworks.commands.MakeExtraSpace;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.steamworks.commands.Shoot;
import org.robockets.steamworks.commands.ShootWithPID;
import org.robockets.steamworks.commands.SpinSpinners;
import org.robockets.steamworks.commands.TunePID;
import org.robockets.steamworks.drivetrain.DriveWithMP;
import org.robockets.steamworks.drivetrain.Turn;
import org.robockets.steamworks.drivetrain.Drivetrain;
import org.robockets.steamworks.drivetrain.Joyride;
import org.robockets.steamworks.drivetrain.ResetDriveEncoders;
import org.robockets.steamworks.drivetrain.ToggleDriveMode;
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
	public static IntakeFlap intakeFlap;
	public static LED ledSubsystem;

	public static Command autonomousCommand;
	public static Command autoTest;

	public static Command easyAuto1;
	public static Command easyAuto2;
	public static Command easyAuto3;
	public static Command midAuto1;
	public static Command midAuto2;
	public static Command midAuto3;
	public static Command maxAuto1;
	public static Command maxAuto2;
	public static Command maxAuto3;

	public static Command drive;
	public static Command climb;
	public static Command toggleDriveMode;
	public static Command cylonCommand;

	private SendableChooser<Command> autonomousChooser;
	
	private boolean smartDashboardDebug = true;

	public Robot() {
		System.out.println("i swear to freaking god");
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// SUBSYSTEMS //
		ballIntake = new BallIntake();
		elevator = new Elevator();
		conveyor = new Conveyor();
		climber = new Climber();
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		gearIntake = new GearIntake();
		intakeFlap = new IntakeFlap(1);
		ledSubsystem = new LED();
		cylonCommand = new Cylon();
		
		// COMMANDS //
		climb = new Climb(0.5);
		drive = new Joyride();
		toggleDriveMode = new ToggleDriveMode();	
		
		// SMARTDASHBOARD //
		initSmartDashboard();
		
		// INIT //
		Webcam.getInstance().startThread();
		//chooser.addObject("My Auto", new MyAutoCommand());
		//RobotMap.gyro.calibrate();
		
		// AUTO //
		autoTest = new AutoTest();
		easyAuto1 = new EasyAuto(1);
		easyAuto2 = new EasyAuto(2);
		easyAuto3 = new EasyAuto(3);
		midAuto1 = new MidAuto(1);
		midAuto2 = new MidAuto(2);
		midAuto3 = new MidAuto(3);
		maxAuto1 = new MaxAuto(1);
		maxAuto2 = new MaxAuto(2);
		maxAuto3 = new MaxAuto(3);
		
		autonomousChooser = new SendableChooser<Command>();
		autonomousChooser.addDefault("AutoTest", autoTest);
		autonomousChooser.addObject("EasyAutoStart1", easyAuto1);
		autonomousChooser.addObject("EasyAutoStart2", easyAuto2);
		autonomousChooser.addObject("EasyAutoStart3", easyAuto3);
		autonomousChooser.addObject("MidAutoStart1", midAuto1);
		autonomousChooser.addObject("MidAutoStart2", midAuto2);
		autonomousChooser.addObject("MidAutoStart3", midAuto3);
		autonomousChooser.addObject("MaxAutoStart1", maxAuto1);
		autonomousChooser.addObject("MaxAutoStart2", maxAuto2);
		autonomousChooser.addObject("MaxAutoStart3", maxAuto3);
		
		SmartDashboard.putData("Autonomous selector", autonomousChooser);
		
		RobotMap.rollerEncoderCounter.setUpSource(RobotMap.rollerEncoder);
		RobotMap.rollerEncoderCounter.setUpDownCounterMode();
		RobotMap.rollerEncoderCounter.setDistancePerPulse(1.0);
		
		RobotMap.shooterRollerSpeedController.setInverted(true);
		
		oi = new OI();
	}
	
	public void initSmartDashboard() {
		
		// CLIMBER //
		Robot.climber.initSmartDashboard(smartDashboardDebug);
		SmartDashboard.putData("Climb", new Climb(1));
		
		// DRIVE ENCODERS //
		SmartDashboard.putData(new ResetDriveEncoders());
		SmartDashboard.putData("Drive 60 at 10 per second with encoders", new DriveWithMP(60, 10));
		SmartDashboard.putData("Drive 100 at 30 per second with encoders", new DriveWithMP(100, 30));

		// GYRO //
		//SmartDashboard.putData("GyroTurn Absolute", new Turn(TurnType.ABSOLUTE, 90)); // Angle will be on SmartDashboard from the Turn command
		//SmartDashboard.putData("GyroTurn Relative", new Turn(TurnType.RELATIVE, 90));
		//SmartDashboard.putData("GyroPIDGo", new TunePID());

		// BALL INTAKE //
		SmartDashboard.putData("IntakeRollersForward", new SpinBallIntakeRollers(1));
		SmartDashboard.putData("IntakeRollersBackward", new SpinBallIntakeRollers(-1));
		SmartDashboard.putData("Intake Balls", new IntakeBalls());
		
		// GEAR INTAKE //
		SmartDashboard.putData("Toggle Intake Flap", new ToggleIntakeFlap());
		
		// CONVEYORS // The Conveyor, "Magic Carpet," is moved when `MoveElevator` is called
		//SmartDashboard.putData("MoveMagicCarpetForward", new MoveConveyor(RelativeDirection.YAxis.FORWARD));
		//SmartDashboard.putData("MoveMagicCarpetBackward", new MoveConveyor(RelativeDirection.YAxis.BACKWARD));
		SmartDashboard.putNumber("elevator Speed", 0.5);
		SmartDashboard.putData("MoveElevatorUp", new MoveElevator(RelativeDirection.ZAxis.UP, 1));
		SmartDashboard.putData("Fluff", new MoveElevator(RelativeDirection.ZAxis.DOWN,  SmartDashboard.getNumber("elevator Speed", 1)));
		SmartDashboard.putData("MakeExtraSpace", new MakeExtraSpace());	
		
		// SHOOTER //
		SmartDashboard.putData(new SpinSpinners());
		SmartDashboard.putData(new Shoot(false));
		SmartDashboard.putData(new ShootWithPID());
		
		
		SmartDashboard.putNumber("Edit Shooter PID setpoint", 0);
		SmartDashboard.putNumber("Edit Shooter PID P value", 0);
		SmartDashboard.putNumber("Edit Shooter PID I value", 0);
		SmartDashboard.putNumber("Edit Shooter PID D value", 0);
		SmartDashboard.putNumber("Edit Shooter PID F value", 0);
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("djyro", RobotMap.gyro.getAngle());
		Robot.climber.periodicSmartDashboard(smartDashboardDebug);
		gearIntake.periodicSmartDashboard();

		//SDDumper.dumpPidController("Gyro", Robot.drivetrain.gyroPID);
		//SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
		//drivetrain.gyroPID.setPID(SmartDashboard.getNumber("GyroP", 0),SmartDashboard.getNumber("GyroI", 0),SmartDashboard.getNumber("GyroD", 0));
		//drivetrain.gyroPID.setSetpoint(SmartDashboard.getNumber("GyroSetpoint", 0));

		SDDumper.dumpEncoder("Left encoder", RobotMap.leftEncoder);
		SDDumper.dumpEncoder("Right encoder", RobotMap.rightEncoder);

		SDDumper.dumpPidController("Left drivepod PID", drivetrain.leftPodPID);
		SDDumper.dumpPidController("Right drivepod PID", drivetrain.rightPodPID);

		// Use the SmartDashboard PID Values

		Robot.drivetrain.leftPodPID.setPID(SmartDashboard.getNumber("Left drivepod PID P value", 0),
				SmartDashboard.getNumber("Left drivepod PID I value", 0),
				SmartDashboard.getNumber("Left drivepod PID D value", 0));
		Robot.drivetrain.leftPodPID.setSetpoint(SmartDashboard.getNumber("Left drivepod PID setpoint", 0));


		// Possibly?
		Robot.drivetrain.rightPodPID.setPID(-SmartDashboard.getNumber("Left drivepod PID P value", 0),
				SmartDashboard.getNumber("Left drivepod PID I value", 0),
				SmartDashboard.getNumber("Left drivepod PID D value", 0));
		Robot.drivetrain.rightPodPID.setSetpoint(SmartDashboard.getNumber("Left drivepod PID setpoint", 0));

		SDDumper.dumpMisc();
		
		SmartDashboard.putNumber("Intake flap encoder1 position", RobotMap.leftIntakeFlapServo.get());
		
		SmartDashboard.putNumber("Touchless encoder count", RobotMap.rollerEncoderCounter.get());
		SmartDashboard.putNumber("Touchless encoder rate", RobotMap.rollerEncoderCounter.getRate());
		
		//System.out.println(RobotMap.rollerEncoderCounter.getRate());
		SDDumper.dumpPidController("Shooter PID", Robot.shooter.shooterPIDController);
		Robot.shooter.shooterPIDController.setSetpoint(SmartDashboard.getNumber("Edit Shooter PID setpoint", 0));
    	Robot.shooter.shooterPIDController.setPID(SmartDashboard.getNumber("Edit Shooter PID P value", 0.01), SmartDashboard.getNumber("Edit Shooter PID I value", 0.001), SmartDashboard.getNumber("Edit Shooter PID D value", 0.001), SmartDashboard.getNumber("Edit Shooter PID F value", 0.01));
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
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
