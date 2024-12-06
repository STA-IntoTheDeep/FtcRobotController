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
    double slidesPos = 0;
    double arm;
    double sampleStorage = 1;
    double intakeClaw = 0;
    double intakeOrientation = 1;
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
            pos_y = parts.posY();
            if (autoEnabled) {
                double ms = runtime.milliseconds() - startuptime;
                //int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1; //required for async execution. dont remove
                telemetry.addData("stage", stage);
                telemetry.addData("Pos_y", pos_y);
                telemetry.addData("milliseconds,",ms);
                telemetry.addData("Slidespos", parts.getSlidesPos());
                switch (stage) {
                    case "init complete":
                        vy = -1;
                        vx = 0;
                        va = 0;
                        if (pos_y<=-13000) {
                            stage = "bij basket";

                        }
                        break;
                    case "bij basket"://-14722
                        vy = 0;
                        slides = 0.9;
                        if (parts.getSlidesPos() > 3500) {
                            stage = "slides omhoog";
                            startuptime = ms;
                            ms = startuptime - ms;
                        }
                        break;
                    case "slides omhoog":
                        slides = 0.3;
                        sampleStorage = 0;
                        if (ms > 1000) {
                            stage = "first sample scored";
                        }
                        break;
                    case "first sample scored":
                        va = 1;
                        sampleStorage = 0.95;
                        slides = 0;
                        if (pos_y > -12000) {
                            stage = "draai naar samples";
                        }
                        break;
                    case "draai naar samples":
                        va = 0;
                        vy = 1;
                        intakeOrientation = 0;
                        intakeClaw = 0;
                        if (pos_y > 5000) {
                            stage = "bij first sample";
                            startuptime = ms;
                            ms = startuptime - ms;
                        }
                        break;
                    case "bij first sample":
                        arm = 1;
                        if (ms > 1200) {
                            stage = "arm naar buiten";
                        }
                        break;
                    case "arm naar buiten":
                        arm = 0;
                        intakeClaw = 1;
                        slides = -0.9;
                        if (parts.getSlidesPos() < 300) {
                            stage = "slides naar beneden";
                            startuptime = ms;
                            ms = startuptime - ms;
                        }
                        break;
                    case "slides naar beneden":
                        arm = -1;
                        intakeOrientation = 1;
                        slides = 0;
                        if (ms > 1300) {
                            stage = "klaar voor sample drop";
                        }
                        break;
                    case "klaar voor sample drop":
                        intakeClaw = 0;
                        if (ms > 2000) {
                            stage = "sample dropped";
                        }
                        break;
                    case "sample dropped":
                        vx = 1;
                        if (ms > 2500) {
                            stage = "naar zijkant";
                        }
                        break;
                    case "naar zijkant":
                        if (ms < 3800){
                            arm = -1;
                        }
                        else {
                            arm = 0;
                        }
                        slides = 1;
                        if ((parts.getSlidesPos() > 3500) && (ms > 3800)) {
                        stage = "slides omhoog met tweede sample";
                        startuptime = ms;
                        ms = startuptime - ms;
                        }
                        break;
                    case "slides omhoog met tweede sample":
                        slides = 0.3;
                        sampleStorage = 0;
                        if (ms > 1000) {
                            stage = "second sample scored";
                        }
                        break;
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
                parts.setSlidePosition(slidesPos);
                parts.setSlidesPower(slides);
                parts.setArmPower(arm);
                parts.sampleBakje(sampleStorage);
                parts.intakeClaw(intakeClaw);
                parts.setIntakeOrientation(intakeOrientation);
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
