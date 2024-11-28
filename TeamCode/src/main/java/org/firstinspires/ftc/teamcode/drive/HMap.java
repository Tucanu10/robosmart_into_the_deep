package org.firstinspires.ftc.teamcode.drive;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;
@Config
public class HMap {

    public static double bratPower = 0.1;
    public static int bratPos = 1000;
    public static double bratSwivel = 0.0;
    public static double putereColectare = 0.5;
    public DcMotorEx brat = null;
    public Servo colect = null,
            servoBrat = null;

    public void init(HardwareMap hmap) {


        brat = hmap.get(DcMotorEx.class, "brat");


        colect = hmap.get(Servo.class, "colect");
        servoBrat = hmap.get(Servo.class, "servoBrat");

        brat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brat.setDirection(DcMotorSimple.Direction.FORWARD);
        brat.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ridicaBrat();
        rotireBrat();
        colectare();

   }

    public void ridicaBrat() {
        brat.setTargetPosition(bratPos);
        brat.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brat.setPower(bratPower);
        brat.setPower(0);
        brat.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void rotireBrat() {
        servoBrat.setPosition(bratSwivel);
    }

    public void colectare() {
        colect.setPosition(putereColectare);
    }
}
