package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Arm_new;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name = "Autonomous", group = "Autonomous")
//Naam van project
public class Auton extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    All_Parts parts = new All_Parts();
    Arm_new arm = new Arm_new();

    //Slaat op hoe lang de robot is geinitialiseerd

    double pos_y = 0;
    //double armpos_small = 0;

    boolean autoEnabled = true;
    boolean manual = false;
    boolean hasInit = false;

    double vy = 0; //velocity x
    double vx = 0; //velocity y
    double va = 0; //angular velocity

    double fl = 0; //front left motor
    double fr = 0; //front right motor
    double bl = 0; //back left motor
    double br = 0; //back right motor
    int stage = 0;
    //double pos_x = parts.motorPos(hardwareMap)[1]; //x position
    //double pos_y = parts.motorPos(hardwareMap)[3]; // y position


    @Override
    public void runOpMode() throws InterruptedException {
        //All_Parts parts = null;
        parts.init(hardwareMap);
        arm.initArm(hardwareMap);
        double armPos1 = 0;
        double startpos = -parts.posY();

        waitForStart();
        while (opModeIsActive()) {
            pos_y = -parts.posY() - startpos;
            armPos1 = parts.armPos()[1];
            telemetry.addData("autonomous mode enabled", autoEnabled);
            telemetry.addData("has initialized", hasInit);
            if (autoEnabled) {
                double ms = runtime.milliseconds();
                //int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1; //required for async execution. dont remove
                telemetry.addData("stage", stage);
                telemetry.addData("Pos_y", pos_y);
                telemetry.addData("armPos1", armPos1);
                switch (stage) {
                    case 0:
                        vy = 1;
                        vx = 0;
                        va = 0;
                        if (pos_y > 41000){
                            stage = 1;
                        }
                        break;
                    case 1:
                        vy = 0;
                        break;


                    default:
                        vy = 0;
                        vx = 0;
                        va = 0;
                        parts.servo0(0,0);
                        break;
                }

            } else {
                double x = gamepad1.left_stick_x;
                double y = gamepad1.left_stick_y;

                telemetry.addData("gx", x);
                telemetry.addData("gy", y);
            }

            //if (gamepad1.a) autoEnabled = true;
            //if (gamepad1.b) autoEnabled = false;


            //handle movement
            if (!manual) {
                parts.drive0(vy, vx, va, 3);
            }/* else if (hasInit) {
                leftFront.setPower(fl / 1000);
                rightFront.setPower(fr / 1000);
                rightBack.setPower(br / 1000);
                leftBack.setPower(bl / 1000);
            }*/
            telemetry.update();
        }
    }
}
