package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;

@Autonomous(name = "Autonomous", group = "Autonomous")
//Naam van project
public class Auton extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //Slaat op hoe lang de robot is geinitialiseerd

    double pos_y = 0;

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
    String stage = "init complete";
    double slides;
    double arm;
    double sampleStorage = 1;
    double intakeClaw = 0;
    //double pos_x = parts.motorPos(hardwareMap)[1]; //x position
    //double pos_y = parts.motorPos(hardwareMap)[3]; // y position


    @Override
    public void runOpMode() throws InterruptedException {
        //All_Parts parts = null;
        All_Parts parts = new All_Parts();
        parts.init(hardwareMap);
        waitForStart();
        double startuptime = runtime.milliseconds();
        while (opModeIsActive()) {
            telemetry.addData("autonomous mode enabled", autoEnabled);
            telemetry.addData("has initialized", hasInit);
            if (autoEnabled) {
                double ms = runtime.milliseconds() - startuptime;
                //int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1; //required for async execution. dont remove
                telemetry.addData("stage", stage);
                telemetry.addData("Pos_y", pos_y);
                switch (stage) {
                    case "init complete":

                        vy = 1;
                        vx = 0;
                        va = 0;
                        slides = 0;
                        if (ms > 1000){
                            stage = "vydone";
                        }
                        break;
                    case "vydone":

                        vy = 0;
                        vx = 1;
                        va = 0;
                        slides = 0;
                        if (ms > 2000){
                            stage = "vxdone";
                        }
                        break;
                    case "vxdone":

                        vy = 0;
                        vx = 0;
                        va = 1;
                        slides = 0;
                        if (ms > 3000){
                            stage = "done";
                        }
                        break;


                        /*if (parts.getSlidesPos() > 3750) {
                            stage = "slides omhoog";
                            startuptime = ms;
                        }
                        break;*/

                    /*case "slides omhoog":
                        slides = 0;
                        vy = 1;
                        vx = 0;
                        if (ms > 500) {
                            stage = "bij basket";
                        }
                        break;
                    case "bij basket":
                        vy = 0;
                        sampleStorage = 0;
                        if (ms > 1200) {
                            stage = "first sample scored";
                        }
                        break;
                    case "first sample scored":
                        va = 1;
                        sampleStorage = 1;
                        if (ms > 3000) {
                            stage = "draai naar samples";
                        }
                        break;
                    case "draai naar samples":
                        va = 0;
                        arm = 1;
                        if (ms > 4500) {
                            stage = "arm naar buiten";
                        }
                        break;
                    case "arm naar buiten":
                        arm = 0;
                        intakeClaw = 1;
                        if (ms > 5000) {
                            stage = "done";
                        }
                        break;*/
                    case "done":
                        vx = 0;
                        vy = 0;
                        va = 0;
                        slides = 0;
                        break;
                    default:
                        vy = 0;
                        vx = 0;
                        va = 0;
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
                parts.setSlidesPower(slides);
                parts.setArmPower(arm);
                parts.sampleBakje(sampleStorage);
                parts.intakeClaw(intakeClaw);
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
