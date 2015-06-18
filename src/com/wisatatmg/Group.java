package com.wisatatmg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Group {
	  public String string;
	  public final List<JSONObject> children = new ArrayList<JSONObject>();

	  public Group(String string) {
	    this.string = string;
	  }
}
