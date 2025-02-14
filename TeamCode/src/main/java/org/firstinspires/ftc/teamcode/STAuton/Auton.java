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
    double pos_yoffset = 0;
    double pos_xoffset = 0;

    boolean autoEnabled = true;
    boolean manual = false;
    boolean hasInit = false;

    double vy = 0; //velocity x
    double vx = 0; //velocity y
    double va = 0; //angular velocity

    double servoRotation = 0.25;
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
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        imu.initialize(parameters);
        imu.initialize(parameters);
        imu.initialize(parameters);
        imu.initialize(parameters);
        imu.initialize(parameters);
        //All_Parts parts = null;
        All_Parts parts = new All_Parts();
        drivetrain.init(hardwareMap);
        parts.init(hardwareMap);
        telemetry.addData("armpos", parts.getArmPos());
        telemetry.addData("init done", true);
        telemetry.update();
        waitForStart();
        double startuptime = runtime.milliseconds();
        //double armPos=0;


        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        while (opModeIsActive()) {
            double armPos = parts.getArmPos();
            telemetry.addData("autonomous mode enabled", autoEnabled);
            telemetry.addData("has initialized", hasInit);
            pos_y = parts.posY() - pos_yoffset;
            pos_x = parts.posX() - pos_xoffset;

            if (autoEnabled) {
                double ms = runtime.milliseconds() - startuptime;
                //int stage = 0;
                //int stage = (int) Math.round(ms / 3000) - 1; //required for async execution. dont remove

                telemetry.addData("armpos", armPos);
                telemetry.addData("stage", stage);
                telemetry.addData("Pos_y", pos_y);
                telemetry.addData("Pos_x", pos_x);
                telemetry.addData("milliseconds,", ms);
                telemetry.addData("Slidespos", parts.getSlidesPos());
                telemetry.addData("arm pos displacement", parts.getArmPosDisplacement());
                armPos = parts.getArmPos();
                switch (stage) {

                    case "init complete":  //test
                        slidesPos = 8500;
                        vy = -1;
                        if (armPos <= -500){
                            arm = 0;
                        } else {
                            arm = -0.4;
                        }
                        if (-pos_y > 18000) {

                            stage = "at location";
                        }
                        break;

                    case "at location":
                        vy = 0;
                        if (armPos <= -500){
                            arm = 0;
                        } else {
                            arm = -0.4;
                        }
                        if (parts.getSlidesPos() > 7350) {
                            stage = "slides up";
                            startuptime = runtime.milliseconds();
                        }
                        break;

                    case "slides up":
                        sampleStorage = 0.3;
                        vy = 0;
                        if (ms > 1000) {
                            stage = "sample scored";
                        }
                        break;

                    case "sample scored":
                        vx = -1;

                        sampleStorage = 0.9;
                        /*if (pos_x <= -10000 && pos_y >= -5000) {
                            stage = "retreat complete";
                        }*/
                        if (pos_x <= -15000) {
                            stage = "retreat complete";
                            startuptime = runtime.milliseconds();
                        }


                        break;

                    case "retreat complete":
                        vx = 0;
                        vy = 0;
                        angle = 90;
                        if (Math.abs(angles.firstAngle-90) <= 1) {
                            stage = "aligned";
                            pos_yoffset = pos_y;
                            pos_xoffset = pos_x;
                        }
                        break;

                    case "aligned":
                        vy=1;
                        slidesPos = 0;
                        if(pos_y>=4500){
                            stage="arrived at sample 1 y";
                        }
                        break;
                    case "arrived at sample 1 y":
                        vy=0;
                        vx=1;
                        if(pos_x>=2000){
                            stage = "arrived at sample 1";
                        }
                        break;
                    case "arrived at sample 1":
                        vx=0;
                        vy=0;
                        arm= -0.6;
                        if (armPos<= -1000){
                            stage= "arm down";
                            startuptime= runtime.milliseconds();
                        }
                        break;
                    case "arm down":
                        arm = 0;
                        if (ms > 1000){
                            stage= "small break";
                            startuptime= runtime.milliseconds();
                        }
                        break;
                    case "small break":
                        intakeClaw = 1;
                        if (ms > 1000 && parts.getSlidesPos() < 200) {
                            stage = "sample picked up";
                        }
                        break;
                    case "sample picked up":
                        arm = 0.6;
                        intakeOrientation = 0.7;
                        servoRotation = 1;
                        if (armPos  >= -150) {
                            stage = "arm at tray";
                            startuptime= runtime.milliseconds();
                        }
                        break;
                    case "arm at tray":
                        arm = 0;
                        if (ms  > 500) {
                            stage = "no arm speed";
                        }
                        break;
                    case "no arm speed":
                        intakeClaw = 0;
                        if (ms  > 500) {
                            stage = "second sample dropped in tray";
                            startuptime=runtime.milliseconds();
                        }
                        break;

                    case "second sample dropped in tray":
                        vx=-1;
                        if (ms>2000){
                            stage = "posx aligned";
                        }

                        break;
                    case "posx aligned":
                        vx=0;
                        vy=-1;
                        if (pos_y<=3500){
                            stage = "at basket 2";
                        }
                        break;
                    case "at basket 2":
                        vy=0;
                        vx=0;
                        if (armPos <= -500){
                            arm = 0;
                        } else {
                            arm = -0.4;
                        }
                        intakeOrientation = 0;
                        slidesPos = 8500;
                        if (armPos  <= -500 && parts.getSlidesPos() > 7350) {
                            stage = "arm weg en slides omhoog tweede sample";
                        }
                        break;
                    case "arm weg en slides omhoog tweede sample":
                        sampleStorage = 0.3;
                        if (ms  > 1000) {
                            stage = "second sample dropped in basket";
                        }
                        break;
                    default:
                        vy = 0;
                        vx = 0;
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
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("direction", angles.firstAngle);
            va = (Math.abs(angles.firstAngle - angle + 20) - Math.abs(angles.firstAngle - angle - 20)) * -0.02225;
            telemetry.addData("va1", va);
            if (Math.abs(va) < 0.30) {
                if (Math.abs(angles.firstAngle - angle) < 1) {
                    va = 0;
                } else {
                    va = va * Math.abs(0.3 / va);
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
                parts.servoRotation(servoRotation);


                telemetry.addData("arm power", arm);
            }
            telemetry.addData("slidespower", parts.slidespower());
            telemetry.update();
        }
    }
}