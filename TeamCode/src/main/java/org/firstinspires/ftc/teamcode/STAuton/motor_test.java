package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;

@Autonomous(name = "motor test", group = "Autonomous")
//Naam van project
public class motor_test extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                            //Slaat op hoe lang de robot is geinitialiseerd


    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;

    void init(HardwareMap map) {
        leftFront = map.get(DcMotorEx.class, "left_front");
        rightFront = map.get(DcMotorEx.class, "right_front");
        leftBack = map.get(DcMotorEx.class, "left_back");
        rightBack = map.get(DcMotorEx.class, "right_back");
    }



    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            double ms = runtime.milliseconds();
            int offset = 0;
            int stage = (int) Math.round(ms / 3000) - 1 - offset;
            telemetry.addData("stage", stage);
            switch (stage) {
                case 0:
                    leftFront.setPower(1);
                    break;
                case 1:
                    leftFront.setPower(0);
                    rightFront.setPower(1);
                    break;
                case 2:
                    rightFront.setPower(0);
                    leftBack.setPower(1);
                    break;
                case 3:
                    leftBack.setPower(0);
                    rightBack.setPower(1);
                    break;
                default:
                    //offset=stage;
                    leftFront.setPower(0);
                    leftBack.setPower(0);
                    rightFront.setPower(0);
                    rightBack.setPower(0);
                    break;
            }
            telemetry.update();
        }
    }
}
