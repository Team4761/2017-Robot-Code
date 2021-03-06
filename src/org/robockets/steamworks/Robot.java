package org.robockets.steamworks;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.autonomous.AutoTest;
import org.robockets.steamworks.autonomous.BaselineAuto;
import org.robockets.steamworks.autonomous.DeliverGearPlusPlus;
import org.robockets.steamworks.autonomous.DumbAuto;
import org.robockets.steamworks.autonomous.EasyAuto;
import org.robockets.steamworks.autonomous.SecretWeaponAuto;
import org.robockets.steamworks.camera.CVConstants;
import org.robockets.steamworks.camera.SetVisionEnabled;
import org.robockets.steamworks.camera.VisionManager;
import org.robockets.steamworks.climber.Climb;
import org.robockets.steamworks.climber.Climber;
import org.robockets.steamworks.elevator.MoveElevator;
import org.robockets.steamworks.drivetrain.Drivetrain;
import org.robockets.steamworks.elevator.ElevatorDPadListener;
import org.robockets.steamworks.drivetrain.Joyride;
import org.robockets.steamworks.drivetrain.ResetDriveEncoders;
import org.robockets.steamworks.gearintake.GearIntake;
import org.robockets.steamworks.gearintake.GearIntakeJoystickListener;
import org.robockets.steamworks.shooter.Shoot;
import org.robockets.steamworks.shooter.ShootWithPID;
import org.robockets.steamworks.shooter.Shooter;
import org.robockets.steamworks.shooter.ShooterListener;
import org.robockets.steamworks.shooter.SpinSpinners;
import org.robockets.steamworks.elevator.Elevator;
import org.robockets.steamworks.lights.LED;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the
 * name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static OI oi;

	public static Climber climber;
	public static Drivetrain drivetrain;
	public static Shooter shooter;
	public static Elevator elevator;
	public static GearIntake gearIntake;
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
	public static Command baselineAuto;
	public static Command secretWeaponAuto;
	public static Command dumbAuto;
	public static Command deliverGearPlusPlus;

	public static Command drive;
	public static Command climb;
	public static Command elevatorListener;
	public static Command shooterListener;
	public static Command gearIntakeListener;

	public static VisionManager visionManager;

	private SendableChooser<Command> autonomousChooser;

	public Robot() {
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		System.out.println("Robot initializing...");

		NetworkTable.globalDeleteAll();
		
		////////////////
		// SUBSYSTEMS //
		////////////////

		initSubsystems();

		//////////////
		// COMMANDS //
		//////////////

		climb = new Climb(0.5);
		drive = new Joyride();
		gearIntakeListener = new GearIntakeJoystickListener();

		////////////////////
		// SMARTDASHBOARD //
		////////////////////

		initSmartDashboard();

		//////////
		// AUTO //
		//////////
		initAutoThings();

		//////////
		// INIT //
		//////////

		CameraServer.getInstance().startAutomaticCapture("DRIVING CAMERA", 0);
		visionManager = VisionManager.getInstance();
		visionManager.startProcessing();

		RobotMap.rollerEncoderCounter.setUpSource(RobotMap.rollerEncoder);
		RobotMap.rollerEncoderCounter.setUpDownCounterMode();
		RobotMap.rollerEncoderCounter.setDistancePerPulse(1.0);

		RobotMap.shooterRollerSpeedController.setInverted(true);
		RobotMap.climberSpeedController.setInverted(true);
		RobotMap.climberSpeedController2.setInverted(true);
		RobotMap.gearIntakeArm.setInverted(true);
		RobotMap.gearIntakeWheels.setInverted(true);

		RobotMap.leftEncoder.setDistancePerPulse(4 * Math.PI / 360);
		RobotMap.rightEncoder.setDistancePerPulse(4 * Math.PI / 360);

		SmartDashboard.putNumber("Shooter PID P value", shooter.shooterPIDController.getP());
		SmartDashboard.putNumber("Shooter PID I value", shooter.shooterPIDController.getI());
		SmartDashboard.putNumber("Shooter PID D value", shooter.shooterPIDController.getD());
		SmartDashboard.putNumber("Shooter PID F value", shooter.shooterPIDController.getF());

		Robot.ledSubsystem.cylon(56);

		oi = new OI();

		System.out.println("Robot done initializing");
	}
	
	private void initSmartDashboard() {

		/////////////
		// CLIMBER //
		/////////////

		Robot.climber.initSmartDashboard();
		SmartDashboard.putData("Climb", new Climb(-1));

		////////////////
		// DRIVETRAIN //
		////////////////

		SmartDashboard.putData(new ResetDriveEncoders());

		SmartDashboard.putNumber("Left drivepod PID P value", Robot.drivetrain.leftPodPID.getP());
		SmartDashboard.putNumber("Left drivepod PID I value", Robot.drivetrain.leftPodPID.getI());
		SmartDashboard.putNumber("Left drivepod PID D value", Robot.drivetrain.leftPodPID.getD());
		SmartDashboard.putNumber("Left drivepod PID F value", Robot.drivetrain.leftPodPID.getF());

		SmartDashboard.putNumber("Right drivepod PID P value", Robot.drivetrain.rightPodPID.getP());
		SmartDashboard.putNumber("Right drivepod PID I value", Robot.drivetrain.rightPodPID.getI());
		SmartDashboard.putNumber("Right drivepod PID D value", Robot.drivetrain.rightPodPID.getD());
		SmartDashboard.putNumber("Right drivepod PID F value", Robot.drivetrain.rightPodPID.getF());


		//////////
		// GYRO //
		//////////

		/*SmartDashboard.putData("GyroTurn Absolute", new Turn(TurnType.ABSOLUTE, 90)); // Angle will be on SmartDashboard from the Turn command
		SmartDashboard.putData("GyroTurn Relative", new Turn(TurnType.RELATIVE, 90));
		SmartDashboard.putNumber("GyroP", drivetrain.gyroPID.getP());
		SmartDashboard.putNumber("GyroI", drivetrain.gyroPID.getI());
		SmartDashboard.putNumber("GyroD", drivetrain.gyroPID.getD());
		SmartDashboard.putNumber("GyroSetpoint", drivetrain.gyroPID.getSetpoint());*/

		///////////////
		// ELEVATOR ///
		///////////////
		SmartDashboard.putData("MoveElevatorUp", new MoveElevator(RelativeDirection.ZAxis.UP, 1));

		/////////////
		// SHOOTER //
		/////////////
		SmartDashboard.putData(new SpinSpinners());
		SmartDashboard.putData(new Shoot(false));
		SmartDashboard.putData(new ShootWithPID());

		////////////
		// VISION //
		////////////
		SmartDashboard.putData("Enable vision", new SetVisionEnabled(true));
		SmartDashboard.putData("Disable vision", new SetVisionEnabled(false));

		OI.initTestMode();
	}

	@Override
	public void robotPeriodic() {

		/////////////
		// CLIMBER //
		/////////////

		Robot.climber.periodicSmartDashboard();

		//////////
		// GYRO //
		//////////

		//SDDumper.dumpPidController("Gyro", Robot.drivetrain.gyroPID);
		//SmartDashboard.putNumber("Gyro Angle", RobotMap.gyro.getAngle());
		//drivetrain.gyroPID.setPID(SmartDashboard.getNumber("GyroP", 0),SmartDashboard.getNumber("GyroI", 0),SmartDashboard.getNumber("GyroD", 0));
		//drivetrain.gyroPID.setSetpoint(SmartDashboard.getNumber("GyroSetpoint", 0));


		////////////////
		// DRIVETRAIN //
		////////////////

		SDDumper.dumpEncoder("Left encoder", RobotMap.leftEncoder);
		SDDumper.dumpEncoder("Right encoder", RobotMap.rightEncoder);

		// Use the SmartDashboard PID Values
		Robot.drivetrain.leftPodPID.setPID(SmartDashboard.getNumber("Left drivepod PID P value", 0),
				SmartDashboard.getNumber("Left drivepod PID I value", 0),
				SmartDashboard.getNumber("Left drivepod PID D value", 0));

		// Possibly?
		Robot.drivetrain.rightPodPID.setPID(SmartDashboard.getNumber("Right drivepod PID P value", 0),
				SmartDashboard.getNumber("Right drivepod PID I value", 0),
				SmartDashboard.getNumber("Right drivepod PID D value", 0));

		SmartDashboard.putNumber("Left drivepod PID Setpoint", drivetrain.leftPodPID.getSetpoint());
		SmartDashboard.putNumber("Right drivepod PID Setpoint", drivetrain.rightPodPID.getSetpoint());

		SDDumper.dumpMisc();

		/////////////////
		// GEAR INTAKE //
		/////////////////

		SmartDashboard.putNumber("Gear Intake Current", Robot.gearIntake.readCurrent());
		SmartDashboard.putBoolean("Is Gear Intake Stalling?", Robot.gearIntake.isStalling());
		SmartDashboard.putNumber("Intake flap encoder1 position", RobotMap.leftIntakeFlapServo.get());
		gearIntake.periodicSmartDashboard();

		///////////////////////
		// TOUCHLESS ENCODER //
		///////////////////////

		SmartDashboard.putNumber("Touchless encoder count", RobotMap.rollerEncoderCounter.get());
		SmartDashboard.putNumber("Touchless encoder rate", RobotMap.rollerEncoderCounter.getRate());

		/////////////
		// SHOOTER //
		/////////////

    	Robot.shooter.shooterPIDController.setPID(
    			SmartDashboard.getNumber("Shooter PID P value", 0),
				SmartDashboard.getNumber("Shooter PID I value", 0),
				SmartDashboard.getNumber("Shooter PID D value", 0),
				SmartDashboard.getNumber("Shooter PID F value", 0));

    	////////////
		// VISION //
		////////////
		SmartDashboard.putBoolean("Is vision enabled?", CVConstants.SHOULD_RUN_VISION);


		SmartDashboard.putNumber("Drivetrain average current", drivetrain.getAverageCurrent());
    	
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

		drivetrain.leftPodPID.disable();
		drivetrain.rightPodPID.disable();

		drive.start();
		elevatorListener.start();
		shooterListener.start();
		//gearIntakeListener.start();

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		if (RobotMap.gearInputBreakbeamSensor.get()) {
			Robot.ledSubsystem.cylon(56);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void initSubsystems() {
		elevator = new Elevator();
		climber = new Climber();
		drivetrain = new Drivetrain();
		shooter = new Shooter();
		gearIntake = new GearIntake();
		ledSubsystem = new LED();
		elevatorListener = new ElevatorDPadListener();
		shooterListener = new ShooterListener();
	}

	private void initAutoThings() {
		autoTest = new AutoTest();
		dumbAuto = new DumbAuto();
		baselineAuto = new BaselineAuto();
		easyAuto1 = new EasyAuto(1);
		easyAuto2 = new EasyAuto(2);
		easyAuto3 = new EasyAuto(3);
		secretWeaponAuto = new SecretWeaponAuto();
		deliverGearPlusPlus = new DeliverGearPlusPlus(36);

		/*midAuto1 = new MidAuto(1);
		midAuto2 = new MidAuto(2);
		midAuto3 = new MidAuto(3);

		maxAuto1 = new MaxAuto(1);
		maxAuto2 = new MaxAuto(2);
		maxAuto3 = new MaxAuto(3);*/

		System.out.println("Autonomous Chooser Initializing...");

		autonomousChooser = new SendableChooser<>();

		autonomousChooser.addObject("EasyAutoTurnRight", easyAuto1);
		autonomousChooser.addObject("EasyAutoTurnLeft", easyAuto3);
		autonomousChooser.addDefault("EasyAutoStraight", easyAuto2);
		autonomousChooser.addObject("Secret Weapon Auto", secretWeaponAuto);
		autonomousChooser.addObject("Baseline Auto", baselineAuto);
		autonomousChooser.addObject("Dumb Auto", dumbAuto);
		autonomousChooser.addObject("Center gear with current monitoring", deliverGearPlusPlus);

		/*autonomousChooser.addObject("MidAutoStart1", midAuto1);
		autonomousChooser.addObject("MidAutoStart2", midAuto2);
		autonomousChooser.addObject("MidAutoStart3", midAuto3);
		autonomousChooser.addObject("MaxAutoStart1", maxAuto1);
		autonomousChooser.addObject("MaxAutoStart2", maxAuto2);
		autonomousChooser.addObject("MaxAutoStart3", maxAuto3);
		*/

		SmartDashboard.putData("Autonomous selector", autonomousChooser);

		System.out.println("Autonomous Choosing Finished Initializing");
	}
}

