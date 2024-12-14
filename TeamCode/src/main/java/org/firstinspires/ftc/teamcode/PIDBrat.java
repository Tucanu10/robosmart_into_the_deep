package org.firstinspires.ftc.teamcode;

public class PIDBrat {
    private final double kP;  // Proportional constant
    private final double kI;  // Integral constant
    private final double kD;  // Derivative constant

    private double targetPosition;
    private double previousError;
    private double integral;

    public PIDBrat(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
    }

    public double update(double currentPosition) {
        double error = targetPosition - currentPosition;
        integral += error;
        double derivative = error - previousError;

        // PID control equation
        double output = kP * error + kI * integral + kD * derivative;

        // Update previous error for the next iteration
        previousError = error;

        return output;
    }
}
