package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Arm_new {
    private DcMotorEx arm;

    public void initArm(HardwareMap map) {
        arm = map.get(DcMotorEx.class,"arm");
    }

    public void rotate (double power){
        arm.setPower(power);
    }
}
