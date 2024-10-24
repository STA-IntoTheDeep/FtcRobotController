package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm_new {
    private DcMotorEx arm;
    double armPos;
    double armPosOffset;
    public void initArm(HardwareMap map) {
        arm = map.get(DcMotorEx.class, "arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armPosOffset = arm.getCurrentPosition();
    }

    public void rotate (double power){
        armPos = arm.getCurrentPosition();
        arm.setPower(power);
    }

    public double getCurrentPos (){
        return arm.getCurrentPosition()-armPosOffset;
    }
}
