package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PIDBrat;
import org.firstinspires.ftc.teamcode.drive.HMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp(name = "TeleOP", group = "TeleOP")
@Config

public class TeleOP extends LinearOpMode {

    HMap robot = new HMap();
    // PID pentru braț
    public static double Kp = 0.005; // Factor proporțional
    public static double Ki = 0.00001; // Factor integrativ
    public static double Kd = 0.00001; // Factor derivativ
    // Parametrii robotului
    public static double speed = 85;    // Viteza de mișcare a robotului (reglabilă)
    SampleMecanumDrive drive;
    PIDBrat pidBrat;
    private final double bratTarget = 800;      // Poziția dorită a brațului (control PID)

    @Override
    public void runOpMode() throws InterruptedException {
        // Inițializări hardware și PID
        robot.init(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        pidBrat = new PIDBrat(Kp, Ki, Kd);

        // Configurare Telemetry (Driver Station + Dashboard)
        FtcDashboard dashboard = FtcDashboard.getInstance();
        MultipleTelemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            // Actualizare poziție robot
            drive.update();

            // Mișcare mecanum drive controlată de joystick-ul gamepad1
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * speed / 100,
                            -gamepad1.left_stick_x * speed / 100,
                            -gamepad1.right_stick_x * speed / 100
                    )
            );


            double bratPower = -gamepad2.left_stick_y * HMap.bratPower / 10;
            robot.brat.setPower(bratPower);

            // Reglaj viteză mecanum drive
            if (gamepad1.left_bumper) speed = 50; // Viteză redusă
            if (gamepad1.right_bumper) speed = 85; // Viteză normală

            if (gamepad2.x) {
                robot.colectare(0.6);
            }
            if (gamepad2.a) {
                robot.colectare(HMap.colectarePos);
            }

            // Telemetry
            telemetry.addData("Viteză Robot", speed);
            telemetry.addData("Poziție Robot (X)", drive.getPoseEstimate().getX());
            telemetry.addData("Poziție Robot (Y)", drive.getPoseEstimate().getY());
            telemetry.addData("Heading Robot", Math.toDegrees(drive.getPoseEstimate().getHeading()));

            telemetry.addData("Poziție Braț (Curentă)", robot.brat.getCurrentPosition());
            telemetry.addData("Poziție Braț (Țintă)", bratTarget);
            telemetry.addData("PID Output Braț", robot.brat.getPower());

            telemetry.addData("Poziție Servo Colectare", robot.colect.getPosition());

            telemetry.update();

            telemetry.update();
        }
    }
}



