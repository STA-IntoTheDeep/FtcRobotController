package org.firstinspires.ftc.teamcode.STAuton;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;

//import org.firstinspires.ftc.teamcode.robotParts_new.All_Parts;

@Autonomous(name = "imu test", group = "Autonomous")
//Naam van project
public class imu_test extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();                            //Slaat op hoe lang de robot is geinitialiseerd

    Orientation angles;
    BNO055IMU imu;

    void init(HardwareMap map) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }



    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("direction first", angles.firstAngle);
            telemetry.addData("direction second", angles.secondAngle);
            telemetry.addData("direction third", angles.thirdAngle);
            telemetry.update();
        }
    }
}
