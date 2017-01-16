package org.robockets.steamworks.subsystems;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 * Drivetrain subsystem
 */
public class Drivetrain extends Subsystem {

    private StringBuilder sb;

    private JsonParser jparser;

    public Drivetrain() {
        sb = new StringBuilder();
        jparser = new JsonParser();
    }

    public void initDefaultCommand() {

    }
   
    /**
     * Basic method to control the movement of the robot 'arcade' style
     * @param translate Motor speed to move forward: -1 to 1
     * @param rotate Rotation speed: -1 to 1
     */
    public void driveArcade(double translate, double rotate) {
        updateGyro();
        //RobotMap.robotDrive.arcadeDrive(translate, rotate);
    }

    /**
     * Basic method to control the movement of the robot 'tank' style
     * @param leftValue Motor speed for the left side of the robot: -1 to 1
     * @param rightValue Motor speed for the right side of the robot: -1 to 1
     */
    public void driveTank(double leftValue, double rightValue) {
        RobotMap.robotDrive.tankDrive(leftValue, rightValue);
    }

    public void updateGyro() {
        String buffer = RobotMap.serial.readString();

        //System.out.print(buffer);

        //sb.append(buffer);
        //System.out.println(sb.toString());

        if (buffer.contains("\n")) {
            sb.append(buffer);
            System.out.println(sb.toString() + "#####");
            // Parse
            /*try {
                JsonElement jElement = jparser.parse(sb.toString());
                System.out.println(jElement.getAsJsonObject().get("gyro").getAsJsonObject().get("x").getAsString());
            } catch (JsonSyntaxException e) {
                System.out.println("Invalid JSON!");
            }*/

            sb = new StringBuilder();
        } else {
            sb.append(buffer);
        }
    }
    
    /**
     * A method to stop the drivetrain.
     */
    public void stop() {
        driveArcade(0,0);
    }

}

