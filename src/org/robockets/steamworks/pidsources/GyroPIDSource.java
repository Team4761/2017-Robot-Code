package org.robockets.steamworks.pidsources;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import org.robockets.steamworks.RobotMap;

public class GyroPIDSource implements PIDSource {

    @Override
    public void setPIDSourceType(PIDSourceType pidSourceType) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return RobotMap.gyro.getAngle();
    }

}
