package com.mycompany.a3.View.Components;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

public class SidePanelButton extends Button {

	public SidePanelButton(String text) {
		super(text);
		Style buttonStyle = getStyle();
		buttonStyle.setBgColor(ColorUtil.rgb(102, 102, 153));
		buttonStyle.setBgTransparency(175);
		buttonStyle.setFgColor(ColorUtil.rgb(255, 0, 0));
		buttonStyle.setBorder(Border.createBevelLowered());
	}

}
