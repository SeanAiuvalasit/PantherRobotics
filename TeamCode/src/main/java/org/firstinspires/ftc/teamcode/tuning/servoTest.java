package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
public class servoTest extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx leftY = hardwareMap.get(DcMotorEx.class, "leftSlide");
     //   DcMotor rightY = hardwareMap.get(DcMotorEx.class, "rightSlide");
        leftY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftY.setDirection(DcMotor.Direction.FORWARD);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
   /**     rightY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightY.setDirection(DcMotor.Direction.FORWARD);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
*/
        //Servos
    /**    Servo leftX = hardwareMap.get(Servo.class, "leftX");
        Servo rightX = hardwareMap.get(Servo.class, "rightX");
        Servo leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
        Servo rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
        Servo clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");
*/
        // TODO: find servo positions
        final int SPECIMEN_HEIGHT = 1000;
        final int BUCKET_HEIGHT = 0;
        final int Y_HOME = 0;
        boolean atSpecimenPos = false;

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("leftY pos: ", leftY.getCurrentPosition());
            telemetry.update();

            if(gamepad1.x) //runs specimen path
            {
                atSpecimenPos = !atSpecimenPos;
                if(!atSpecimenPos)
                {
                    leftY.setTargetPosition(SPECIMEN_HEIGHT);
                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.3);
               //     rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

               //     rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               //     leftX.setPosition(SPECIMEN_OUT);
               //     rightX.setPosition(1 - SPECIMEN_OUT);
                }
                else
                {
                    leftY.setTargetPosition(Y_HOME);
                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.3);
                //    rightY.setTargetPosition(-1 * Y_HOME);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 //   rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 //   leftX.setPosition(X_HOME);
                 //   rightX.setPosition(1 - X_HOME);
                }
            }
        }
    }
}
