package com.grt192.controller;

import com.grt192.core.StepController;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SolenoidBase;

/**
 * 
 * @author anand
 */
public class DashBoardController extends StepController {

	public void act() {
		updateDefaultDashboard();
	}

	public void updateDefaultDashboard() {
		Dashboard lowDashData = DriverStation.getInstance()
				.getDashboardPackerLow();
		lowDashData.addCluster();
		{
			lowDashData.addCluster();
			{ // analog modules
				lowDashData.addCluster();
				{
					for (int i = 1; i <= 8; i++) {
						lowDashData.addFloat((float) AnalogModule
								.getInstance(1).getAverageVoltage(i));
					}
				}
				lowDashData.finalizeCluster();
				lowDashData.addCluster();
				{
					for (int i = 1; i <= 8; i++) {
						lowDashData.addFloat((float) AnalogModule
								.getInstance(2).getAverageVoltage(i));
					}
				}
				lowDashData.finalizeCluster();
			}
			lowDashData.finalizeCluster();

			lowDashData.addCluster();
			{ // digital modules
				lowDashData.addCluster();
				{
					lowDashData.addCluster();
					{
						int module = 4;
						lowDashData.addByte(DigitalModule.getInstance(module)
								.getRelayForward());
						lowDashData.addByte(DigitalModule.getInstance(module)
								.getRelayForward());
						lowDashData.addShort(DigitalModule.getInstance(module)
								.getAllDIO());
						lowDashData.addShort(DigitalModule.getInstance(module)
								.getDIODirection());
						lowDashData.addCluster();
						{
							for (int i = 1; i <= 10; i++) {
								lowDashData.addByte((byte) DigitalModule
										.getInstance(module).getPWM(i));
							}
						}
						lowDashData.finalizeCluster();
					}
					lowDashData.finalizeCluster();
				}
				lowDashData.finalizeCluster();

				lowDashData.addCluster();
				{
					lowDashData.addCluster();
					{
						int module = 6;
						lowDashData.addByte(DigitalModule.getInstance(module)
								.getRelayForward());
						lowDashData.addByte(DigitalModule.getInstance(module)
								.getRelayReverse());
						lowDashData.addShort(DigitalModule.getInstance(module)
								.getAllDIO());
						lowDashData.addShort(DigitalModule.getInstance(module)
								.getDIODirection());
						lowDashData.addCluster();
						{
							for (int i = 1; i <= 10; i++) {
								lowDashData.addByte((byte) DigitalModule
										.getInstance(module).getPWM(i));
							}
						}
						lowDashData.finalizeCluster();
					}
					lowDashData.finalizeCluster();
				}
				lowDashData.finalizeCluster();

			}
			lowDashData.finalizeCluster();
            
            // Solenoid data: currently not functional
            // API change -- need to instantiate a SolenoidBase before
            // Reading getAll()
			lowDashData.addByte((byte) 0);
		}
		lowDashData.finalizeCluster();
		lowDashData.commit();
	}
}
