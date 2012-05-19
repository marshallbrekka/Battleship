package model;

import java.io.Serializable;

public class ShotLocation implements Serializable {
	public int x, y;
	public ShotLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ShotLocation(int[] coords) {
		this.x = coords[0];
		this.y = coords[1];
	}
}
