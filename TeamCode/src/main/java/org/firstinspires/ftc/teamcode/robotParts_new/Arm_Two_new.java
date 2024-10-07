package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm_Two_new {
    private DcMotorEx arm_2;
    double armPos_2;
    public void initArm2(HardwareMap map) {
        arm_2 = map.get(DcMotorEx.class, "arm");
        arm_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void rotate2 (double power){
        armPos_2 = arm_2.getCurrentPosition();
        arm_2.setPower(power);
    }

    public double getCurrentPos (){
        return arm_2.getCurrentPosition();
    }
}
