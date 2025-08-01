package com.imagebankai;

import javax.management.Descriptor;
import java.awt.*;

public enum ResizeMode
{
	Bilinear("Bilinear"),
	Bicubic("Bicubic"),
	NearestNeighbour("Nearest Neighbour");

	private final String description;

	ResizeMode(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return this.description;
	}
}
