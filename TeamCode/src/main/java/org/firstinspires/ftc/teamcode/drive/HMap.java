package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;
@Config
public class HMap {



    public DcMotorEx colectare = null,
                     glisiere = null;

    public Servo avion = null;

    public static double avion_armat = 0.2,
                    avion_tras = 0;
   public void init(HardwareMap hmap){

      colectare = hmap.get(DcMotorEx.class, "colectare");
      glisiere = hmap.get(DcMotorEx.class, "glisiere");

      glisiere.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      avion = hmap.get(Servo.class, "avion");
      avion.setPosition(avion_armat);
   }
}
