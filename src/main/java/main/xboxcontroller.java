package main;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtonsDelta;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputComponentsDelta;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputAxis;
import com.ivan.xinput.enums.XInputButton;

public class xboxcontroller {
	// ------------------------------------------------------------------------//
	// Threads
	// ------------------------------------------------------------------------//

	// Camera
	Thread cameraUp;
	Thread cameraDown;
	Thread cameraLeft;
	Thread cameraRight;
	// Movement
	Thread moveForward;
	Thread moveRight;
	Thread moveLeft;
	Thread moveBackwards;
	Thread strafeRight;
	Thread strafeLeft;

	// ------------------------------------------------------------------------//
	// Device Variables
	// ------------------------------------------------------------------------//

	private volatile XInputDevice14 device;
	private volatile XInputComponents components;
	private volatile XInputComponentsDelta delta;
	private volatile XInputButtonsDelta buttons;
	private volatile XInputAxes axes;

	private Robot r;

	// MousePos
	private volatile int MouseX;
	private volatile int MouseY;

	public xboxcontroller() {
		try {
			r = new Robot();
			device = XInputDevice14.getDeviceFor(0);

			components = device.getComponents();
			delta = device.getDelta();
			buttons = delta.getButtons();
			axes = components.getAxes();

			// Threads for CameraControl

			// Moves the Camera Up when Pressing Right stick to the top
			cameraUp = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							device.poll();
							// Move Camera to the top
							if (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) > 0.3) {
								r.mousePress(MouseEvent.BUTTON1_MASK);
								while (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) > 0.3) {
									r.mouseMove(MouseInfo.getPointerInfo().getLocation().x,
											MouseInfo.getPointerInfo().getLocation().y - 1);
									Thread.sleep(5);
									device.poll();
								}
								r.mouseRelease(MouseEvent.BUTTON1_MASK);
							}
						}
					} catch (Exception e) {
						System.err.println("ERROR in CameraUp");
						e.printStackTrace();
					}
				}
			});
			// Moves the Camera down when Pressing Right stick to the bottom
			cameraDown = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {
							device.poll();
							// Move Camera to the Bottom
							if (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) < -0.3) {
								r.mousePress(MouseEvent.BUTTON1_MASK);
								while (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) < -0.3) {
									r.mouseMove(MouseInfo.getPointerInfo().getLocation().x,
											MouseInfo.getPointerInfo().getLocation().y + 1);
									Thread.sleep(5);
									device.poll();
								}
								r.mouseRelease(MouseEvent.BUTTON1_MASK);
							}
						}
					} catch (Exception e) {
						System.err.println("ERROR in CameraDown");
						e.printStackTrace();
					}
				}
			});

			// Start the Threads
			cameraUp.start();
			cameraDown.start();

			while (true) {
				device.poll();

				// ONECLICK MACRO
				// ------------------------------------------------------------------------//
				if (buttons.isPressed(XInputButton.A)) {
					r.keyPress(KeyEvent.VK_4);
				} else if (buttons.isReleased(XInputButton.A)) {
					r.keyRelease(KeyEvent.VK_4);
				}
				// ------------------------------------------------------------------------//
				// BF
				// ------------------------------------------------------------------------//
				if (buttons.isPressed(XInputButton.RIGHT_SHOULDER)) {
					r.keyPress(KeyEvent.VK_SHIFT);
					r.keyPress(KeyEvent.VK_3);
				} else if (buttons.isReleased(XInputButton.RIGHT_SHOULDER)) {
					r.keyRelease(KeyEvent.VK_SHIFT);
					r.keyRelease(KeyEvent.VK_3);
				}
				// ------------------------------------------------------------------------//
				// AR
				// ------------------------------------------------------------------------//
				if (buttons.isPressed(XInputButton.LEFT_SHOULDER)) {
					r.keyPress(KeyEvent.VK_SHIFT);
					r.keyPress(KeyEvent.VK_2);
				} else if (buttons.isReleased(XInputButton.LEFT_SHOULDER)) {
					r.keyRelease(KeyEvent.VK_SHIFT);
					r.keyRelease(KeyEvent.VK_2);
				}
				// ------------------------------------------------------------------------//
				// Tab for a target
				// ------------------------------------------------------------------------//
				if (buttons.isPressed(XInputButton.B)) {
					r.keyPress(KeyEvent.VK_TAB);
				} else if (buttons.isReleased(XInputButton.B)) {
					r.keyRelease(KeyEvent.VK_TAB);
				}
				// ------------------------------------------------------------------------//
				// GET THE CAMERA GOING
				// LINKS = X -1 Y 0
				// RECHTS = X +1 Y 0
				// HOCH = X 0 Y 1
				// RUNTER = X 0 Y -1
				// ------------------------------------------------------------------------//

				float LTX = axes.get(XInputAxis.LEFT_THUMBSTICK_X);
				float LTY = axes.get(XInputAxis.LEFT_THUMBSTICK_Y);
				float RTX = axes.get(XInputAxis.RIGHT_THUMBSTICK_X);
				float RTY = axes.get(XInputAxis.RIGHT_THUMBSTICK_Y);
				// System.out.println("LTX: " + LTX + ", LTY: " + LTY);
				System.out.println("RTX: " + RTX + ", RTY: " + RTY);
				System.out.println("MausPos: " + MouseInfo.getPointerInfo().getLocation().x + ","
						+ MouseInfo.getPointerInfo().getLocation().y);

				// float brake = axes.lt;

				Thread.sleep(50);
				// System.out.println("ROUND");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
