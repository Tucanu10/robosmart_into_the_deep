package org.firstinspires.ftc.teamcode.auto.red;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.HMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Rosu Close Parcare")
public class RedClosePark extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        HMap robot = new HMap();

        robot.init(hardwareMap);

        telemetry.setMsTransmissionInterval(50);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.update();

        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            drive.setPoseEstimate(new Pose2d(-26, 60, Math.toRadians(-90)));

            TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d(-26, -60, Math.toRadians(90)))
                    .strafeLeft(35)
                    .build();

            robot.colectare(HMap.colectarePos);
            drive.followTrajectorySequence(trajectory);
            robot.ridicaBrat();
        }
    }
}