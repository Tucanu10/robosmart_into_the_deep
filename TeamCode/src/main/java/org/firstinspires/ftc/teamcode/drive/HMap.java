package org.firstinspires.ftc.teamcode.drive;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.PIDBrat;

@Config
public class HMap {

    public static double bratPower = 3; // Intre 0 si 10 pentru adjustarea vitezei bratului
    public static int bratPos = 1000;
    public static double colectarePos = 0.4;
    public DcMotorEx brat = null;
    public Servo colect = null;

    public void init(HardwareMap hmap) {


        brat = hmap.get(DcMotorEx.class, "brat");


        colect = hmap.get(Servo.class, "colect");

        brat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brat.setDirection(DcMotorSimple.Direction.FORWARD);
        brat.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        colectare(colectarePos);
   }

    public void ridicaBrat() {
        // Instanțiere PIDBrat cu constantele dorite
        PIDBrat pid = new PIDBrat(0.01, 0.001, 0.1); // Ajustează valorile kP, kI, kD în funcție de necesitate
        pid.setTargetPosition(bratPos); // Setăm poziția țintă relativ la punctul de plecare (0)

        // Buclă de control PID
        while (Math.abs(brat.getCurrentPosition() - bratPos) > 5) { // Toleranță la poziție (5 ticks)
            double currentPos = brat.getCurrentPosition();
            double power = pid.update(currentPos);

            // Motorul trebuie să schimbe direcția dacă trece de poziția țintă
            if (currentPos > bratPos && power > 0) {
                power = -power; // Inversăm direcția dacă trece de poziție
            }

            // Aplicarea limitelor pentru putere (în caz de suprasaturație)
            power = Math.max(-1.0, Math.min(1.0, power));

            brat.setPower(power);

            try {
                sleep(10); // Pauză scurtă pentru stabilitate (10ms)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Oprirea motorului
        brat.setPower(0);
    }

    public void colectare(double pos) {
        colect.setPosition(pos);
    }
}
