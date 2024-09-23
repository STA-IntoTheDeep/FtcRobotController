package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Arm_new {

    public DcMotorEx arm;
    double armPos;
    public void initArm(HardwareMap map) {
        arm = map.get(DcMotorEx.class,"arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void rotate (double power){
        armPos = arm.getCurrentPosition();
        arm.setPower(power);
        /*if (800 > armPos && armPos < 2738) {+
            arm.setPower(power);
        }
        else if (armPos <= 2739 && power >= 0){
            arm.setPower(power);
        }
        else if (armPos >= 7000 && power <= 0){
            arm.setPower(power);
        }*/
    }
}
