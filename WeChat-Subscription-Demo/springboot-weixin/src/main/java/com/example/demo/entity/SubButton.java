package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级菜单
 * @author 27270
 *
 */
public class SubButton extends AbstractButton{

	private List<AbstractButton> sub_button = new ArrayList<AbstractButton>();

	public List<AbstractButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbstractButton> sub_button) {
		this.sub_button = sub_button;
	}

	public SubButton(String name) {
		super(name);
	}
	
}
