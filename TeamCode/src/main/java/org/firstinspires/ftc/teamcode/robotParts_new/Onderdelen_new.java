package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;


public class Onderdelen_new {
    public CRServo servo0;
    public void init(HardwareMap map) {
        servo0 = map.get( CRServo.class, "servo0");
    }
    public void servo0(double power){
        servo0.setPower(power);
    }


}
