package org.robockets.steamworks.commands;



import org.robockets.steamworks.LightsColors;
import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ButtonPress extends Command {

	LightsColors type;
	
    public ButtonPress(LightsColors type) {
    	requires(Robot.ledSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.type = type;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (type == LightsColors.PURPLE) {
    		Robot.ledSubsystem.cylon(1);
    	} else if (type == LightsColors.YELLOW) {
    		Robot.ledSubsystem.cylon(2);
    	} else if(type == LightsColors.BLUE){
    		Robot.ledSubsystem.cylon(3);
    	} else if(type == LightsColors.GREEN){
    		Robot.ledSubsystem.cylon(4);
    	} else if(type == LightsColors.BROWN){
    		Robot.ledSubsystem.cylon(5);
    	} else if(type == LightsColors.WHITE){
    		Robot.ledSubsystem.cylon(6);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ledSubsystem.cylon(56);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}