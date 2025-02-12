package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;
import org.firstinspires.ftc.teamcode.robotParts_new.Drivetrain_new;

import java.util.List;

@Autonomous(name = "Autonomous", group = "Autonomous")
//Naam van project
public class Auton extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    Drivetrain_new drivetrain = new Drivetrain_new();
    //Slaat op hoe lang de robot is geinitialiseerd

    double pos_y = 0;
    double pos_x = 0;

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
    double arm = 0;
    double sampleStorage = 0.95;
    double intakeClaw = 0;
    double intakeOrientation = 0;
    //double pos_x = parts.motorPos(hardwareMap)[1]; //x position
    //double pos_y = parts.motorPos(hardwareMap)[3]; // y position
    BNO055IMU imu;
    Orientation angles;
    double angle = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        //All_Parts parts = null;
        All_Parts parts = new All_Parts();
        drivetrain.init(hardwareMap);
        parts.init(hardwareMap);
        telemetry.addData("armpos", parts.getArmPos());
        telemetry.update();
        waitForStart();
        double startuptime = runtime.milliseconds();
        //double armPos=0;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        while (opModeIsActive()) {
            double armPos = parts.getArmPos();
            telemetry.addData("autonomous mode enabled", autoEnabled);
            telemetry.addData("has initialized", hasInit);
            pos_y = parts.posY();
            pos_x = parts.posX();

            if (autoEnabled) {
                double ms = runtime.milliseconds() - startuptime;
                //int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1; //required for async execution. dont remove
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("direction", angles.firstAngle);
                telemetry.addData("armpos", armPos);
                telemetry.addData("stage", stage);
                telemetry.addData("Pos_y", pos_y);
                telemetry.addData("milliseconds,", ms);
                telemetry.addData("Slidespos", parts.getSlidesPos());
                telemetry.addData("arm pos displacement", parts.getArmPosDisplacement());
                //armPos+=100*arm;
                switch (stage) {

                    case "init complete":  //test
                        slidesPos = 4200;
                        vy = -1;
                        arm = 0;
                        if (-pos_y > 8000) {
                            stage = "at location";
                        }
                        break;

                    case "at location":
                        vy = 0;
                        if (parts.getSlidesPos() > 3500) {
                            stage = "slides up";
                            startuptime = runtime.milliseconds();
                        }
                        break;

                    case "slides up":
                        sampleStorage = 0.5;
                        if (ms > 600) {
                            stage = "sample scored";
                        }
                        break;

                    case "sample scored":
                        vx = -1;
                        sampleStorage = 0.9;
                        if (pos_x <= -10000) {
                            stage = "retreat complete";
                            startuptime = runtime.milliseconds();
                        }
                        break;

                    case "retreat complete":
                        vx = 0;
                        slidesPos = 0;
                        angle = 90;
                        if (angles.firstAngle > 87) {
                            stage = "sample picked up";
                        }
                        break;


                    case "arm down":
                        arm = -1;
                        intakeClaw = 0;
                        if (ms > 500) {
                            stage = "sample picked up";
                        }
                        break;


                    default:
                        vy = 0;
                        vx = 0;
                        va = 0;
                        arm = 0;
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
            va = (Math.abs(angles.firstAngle - angle + 20) - Math.abs(angles.firstAngle - angle - 20)) * -0.02225;
            telemetry.addData("va1", va);
            if (Math.abs(va) < 0.30) {
                if (Math.abs(angles.firstAngle - angle) > 3) {
                    va = va * Math.abs(0.3 / va);
                } else {
                    va = 0;
                }
            }
            telemetry.addData("va2", va);

            //handle movement
            if (!manual) {
                drivetrain.drive(vy, vx, va, 3);
                //parts.setSlidesPower(slides);
                parts.setSlidePosition(slidesPos);
                parts.setArmPower(arm);
                parts.sampleBakje(sampleStorage);
                parts.intakeClaw(intakeClaw);
                parts.setIntakeTurn(intakeOrientation);


                telemetry.addData("arm power", arm);
            }
            telemetry.addData("slidespower", parts.slidespower());
            telemetry.update();
        }
    }
}