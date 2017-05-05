package main;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.ivan.xinput.XInputAxes;
import com.ivan.xinput.XInputButtonsDelta;
import com.ivan.xinput.XInputComponents;
import com.ivan.xinput.XInputComponentsDelta;
import com.ivan.xinput.XInputDevice14;
import com.ivan.xinput.enums.XInputAxis;
import com.ivan.xinput.enums.XInputButton;

public class xboxcontroller {
	public xboxcontroller() {
		try {
			Robot r = new Robot();
			XInputDevice14 device = XInputDevice14.getDeviceFor(0);

			XInputComponents components = device.getComponents();
			XInputComponentsDelta delta = device.getDelta();
			XInputButtonsDelta buttons = delta.getButtons();
			XInputAxes axes = components.getAxes();

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
				// LINKS = X -1 Y 0
				// RECHTS = X +1 Y 0
				// HOCH = X 0 Y 1
				// RUNTER = X 0 Y -1
				// ------------------------------------------------------------------------//
				if ((axes.get(XInputAxis.LEFT_THUMBSTICK_X) < -0.5) && (axes.get(XInputAxis.LEFT_THUMBSTICK_Y) > 0.1)) {
					r.keyPress(KeyEvent.VK_W);
					r.mousePress(InputEvent.BUTTON2_MASK);
					r.mouseMove(1, 0);
					// r.mouseRelease(InputEvent.BUTTON2_MASK);
					// TODO GETMOUSEPOSITION
				}

				float LTX = axes.get(XInputAxis.LEFT_THUMBSTICK_X);
				float LTY = axes.get(XInputAxis.LEFT_THUMBSTICK_Y);
				System.out.println("LTX: " + LTX + ", LTY: " + LTY);
				// float brake = axes.lt;

				Thread.sleep(50);
				// System.out.println("ROUND");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
