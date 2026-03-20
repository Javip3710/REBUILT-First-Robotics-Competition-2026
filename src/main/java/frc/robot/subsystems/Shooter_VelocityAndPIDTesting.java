// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Shooter_VelocityAndPIDTesting extends SubsystemBase {

  TalonFX motor1, motor2;

  public ShuffleboardTab tab = Shuffleboard.getTab("ShooterPIDVelocity");
  GenericEntry shooterPower = tab.add("Target Velocity: ", 0).getEntry();

  double Targetspeed = shooterPower.getDouble(0);

  final MotionMagicVelocityVoltage request2 = new MotionMagicVelocityVoltage(0).withEnableFOC(true);


  final NeutralOut requestN = new NeutralOut();



  /** Creates a new Shooter_VelocityAndPIDTesting. */
  public Shooter_VelocityAndPIDTesting() {

  // motor1 = new TalonFX(30);
  motor1 = new TalonFX(30, "CANivore");
  motor2 = new TalonFX(31, "CANivore");

  var TalonFXConfigs = new TalonFXConfiguration();
  var Slot0Configs = TalonFXConfigs.Slot0;

  Slot0Configs.kV = 0.0075;
  Slot0Configs.kP = 0.01;
  Slot0Configs.kI = 0;
  Slot0Configs.kD = 0;



  var motionMagicConfigs = TalonFXConfigs.MotionMagic;
    motionMagicConfigs.MotionMagicAcceleration = 400; // Target acceleration of 400 rps/s (0.25 seconds to max)
    motionMagicConfigs.MotionMagicJerk = 4000; // Target jerk of 4000 rps/s/s (0.1 seconds)


  motor1.getConfigurator().apply(TalonFXConfigs);
  motor2.getConfigurator().apply(TalonFXConfigs);
  motor1.setNeutralMode(NeutralModeValue.Coast);
  motor2.setNeutralMode(NeutralModeValue.Coast);
  }

  public void Start(){ //in RPS(revolutions per second)
    // motor1.setControl(request1.withVelocity(Targetspeed));
    // motor2.setControl(request1.withVelocity(-Targetspeed));
    motor1.setControl(request2.withVelocity(Targetspeed));
    motor2.setControl(request2.withVelocity(-Targetspeed));
  }

  public void setNeutral(){ 
    motor1.setControl(requestN);
    motor2.setControl(requestN);
  }

  @Override
  public void periodic() {
    Targetspeed = shooterPower.getDouble(0);
    // This method will be called once per scheduler run
  }
}
