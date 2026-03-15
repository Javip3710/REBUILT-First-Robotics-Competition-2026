// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  TalonFX motor1, motor2;
  /** Creates a new Shooter. */
  ShuffleboardTab tab = Shuffleboard.getTab("Shooter");
  GenericEntry shooterPower = tab.add("Power: ", 0.21).getEntry();
  /** Creates a new Shooter. */
  Double speed = shooterPower.getDouble(0.21);

  public Shooter() {
    motor1 = new TalonFX(30, "CANivore");
    motor2 = new TalonFX(31, "CANivore");
    motor1.setNeutralMode(NeutralModeValue.Coast);
    motor2.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setPower(double power){
    motor1.set(power * speed);
    motor2.set(-power * speed);
  }

  // for sim
  double gamePieceDeltaX;
  double gamePieceDeltaY;
  double gamePieceDeltaZ;


  public void bringGamePieceToRobot(Pose2d pose, double initialSpeed, double radiansFromGround) {
    gamePiece = new Pose3d(pose.getX(), pose.getY(), 0, new Rotation3d());
    Rotation2d rotation = pose.getRotation();
    initialSpeed = initialSpeed * 0.004;

    gamePieceDeltaX = initialSpeed * Math.cos(radiansFromGround) * rotation.getCos();;

    gamePieceDeltaY = initialSpeed * Math.cos(radiansFromGround) * rotation.getSin();

    gamePieceDeltaZ = initialSpeed * Math.sin(radiansFromGround);

  }  

  public void hideGamePiece() {
    gamePiece = new Pose3d(-1, -1, 0, new Rotation3d());
  }

  public void moveGamePiece() {
    gamePiece = gamePiece.plus(new Transform3d(gamePieceDeltaX, gamePieceDeltaY, gamePieceDeltaZ, new Rotation3d()));
    gamePieceDeltaZ -= 0.0392; // 9.8 meters per sec or 0.0392 meters per 4 ms // maybe it's supposed to be per 20 ms
  }

  // adds gamePiece to sim
  Pose3d gamePiece = new Pose3d(-1, -1, 0, new Rotation3d());
  StructPublisher<Pose3d> publisher = NetworkTableInstance.getDefault().getStructTopic("Game Piece", Pose3d.struct).publish();

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    speed = shooterPower.getDouble(0);
    
    // adds gamePiece to sim
    publisher.set(gamePiece);
  }
}
