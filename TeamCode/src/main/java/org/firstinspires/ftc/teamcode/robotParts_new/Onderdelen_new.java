package org.firstinspires.ftc.teamcode.robotParts_new;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.Servo;

public class Onderdelen_new {
    public Servo servo0;
    public void init(HardwareMap map) {
        servo0 = map.get( Servo.class, "servo0");
    }
    public void servo0(double pos){
        servo0.setPosition(pos);
    }


}
