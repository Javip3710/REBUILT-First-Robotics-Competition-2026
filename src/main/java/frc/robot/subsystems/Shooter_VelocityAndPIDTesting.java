// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter_VelocityAndPIDTesting extends SubsystemBase {

  TalonFX motor1, motor2;


  ShuffleboardTab tab = Shuffleboard.getTab("ShooterPIDVelocity");
  GenericEntry shooterPower = tab.add("Target Velocity: ", 0).getEntry();
  GenericEntry vpid = tab.add("kV: ", 0).getEntry();
  GenericEntry ppid = tab.add("kP: ", 0).getEntry();
  GenericEntry ipid = tab.add("kI: ", 0).getEntry();
  GenericEntry dpid = tab.add("kD: ", 0).getEntry();



  double Targetspeed = shooterPower.getDouble(0);

  final VoltageOut request1 = new VoltageOut(0);


  /** Creates a new Shooter_VelocityAndPIDTesting. */
  public Shooter_VelocityAndPIDTesting() {

  motor1 = new TalonFX(0);
  motor2 = new TalonFX(0);
  


  var TalonFXConfigs = new TalonFXConfiguration();
  var Slot0Configs = TalonFXConfigs.Slot0;

  Slot0Configs.kV = vpid.getDouble(0);
  Slot0Configs.kP = ppid.getDouble(0);
  Slot0Configs.kI = ipid.getDouble(0);
  Slot0Configs.kD = dpid.getDouble(0);

  motor1.getConfigurator().apply(Slot0Configs);
  motor2.getConfigurator().apply(Slot0Configs);
  motor1.setNeutralMode(NeutralModeValue.Coast);
  motor2.setNeutralMode(NeutralModeValue.Coast);
  }

  public void Start(){
    motor1.setControl(request1.withOutput(Targetspeed));
    motor2.setControl(request1.withOutput(-Targetspeed));
  }

  public void Shoot(double speed){ //in RPS
    motor1.setControl(request1.withOutput(speed));
    motor2.setControl(request1.withOutput(-speed));
  }

  public void Stop(){
    motor1.setControl(new VelocityVoltage(0));
    motor2.setControl(new VelocityVoltage(0));
  }

  @Override
  public void periodic() {
    Targetspeed = shooterPower.getDouble(0);
    // This method will be called once per scheduler run
  }
}
