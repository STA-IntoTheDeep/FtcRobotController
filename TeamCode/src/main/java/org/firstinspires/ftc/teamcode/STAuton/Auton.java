package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.robotParts_new.arm_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;
import org.firstinspires.ftc.teamcode.robotParts_new.Onderdelen_new;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.Math;

@Autonomous(name = "Autonomous", group = "Autonomous")
//Naam van project
public class Auton extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                            //Slaat op hoe lang de robot is geinitialiseerd


    arm_new arm = new arm_new();
    Drivetrain_new train = new Drivetrain_new();
    Onderdelen_new parts = new Onderdelen_new();                               //Roept de onderdelen aan uit de geïmporteerde map

    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;

    void init(HardwareMap map) {
        leftFront = map.get(DcMotorEx.class, "left_front");
        rightFront = map.get(DcMotorEx.class, "right_front");
        leftBack = map.get(DcMotorEx.class, "left_back");
        rightBack = map.get(DcMotorEx.class, "right_back");
        hasInit = true;
    }

    boolean autoEnabled = true;
    boolean manual = false;
    boolean hasInit = false;

    double vx = 0; //velocity x
    double vy = 0; //velocity y
    double va = 0; //angular velocity

    double fl = 0; //front left motor
    double fr = 0; //front right motor
    double bl = 0; //back left motor
    double br = 0; //back right motor

    @Override
    public void runOpMode() throws InterruptedException {
        train.init(hardwareMap);
        init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("autonomous mode enabled", autoEnabled);
            telemetry.addData("has initialized", hasInit);
            if (autoEnabled) {
                double ms = runtime.milliseconds();
                int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1;
                telemetry.addData("stage", stage);
                switch (stage) {
                    case 0:
                        vx = 5;
                        stage = 1;
                        break;
                    case 1:
                        vx = 0;
                        va = 5;
                        stage = 2;
                        break;
                    case 2:
                        va = 0;
                        vy = 5;
                        stage = 3;
                        break;
                    default:
                        vx = 0;
                        vy = 0;
                        va = 0;
                        stage = 0;
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
                train.drive(vx, vy, va, 5);
            } else if (hasInit) {
                leftFront.setPower(fl / 1000);
                rightFront.setPower(fr / 1000);
                rightBack.setPower(br / 1000);
                leftBack.setPower(bl / 1000);
            }
            telemetry.update();
        }
    }
}
