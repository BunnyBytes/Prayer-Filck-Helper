package com.bunnybytes;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("prayerFlick")
public interface PrayerFlickHelperConfig extends Config
{
	@ConfigItem(
			keyName = "backgroundColour",
			name = "Background Colour",
			description = "The background colour of the graph"
	)
	default Color backgroundColour()
	{
		return Color.BLACK;
	}

	@ConfigItem(
			keyName = "tickColour",
			name = "Tick Colour",
			description = "The colour that the game ticks show on the graph"
	)
	default Color tickColour()
	{
		return Color.RED;
	}

	@ConfigItem(
			keyName = "clickColour",
			name = "Click Colour",
			description = "The colour that the prayer clicks show on the graph"
	)
	default Color clickColour()
	{
		return Color.GREEN;
	}

	@ConfigItem(
			keyName = "deactivateColour",
			name = "Deactivate Colour",
			description = "The colour that the deactivate prayer clicks show on the graph"
	)
	default Color deactivateColour()
	{
		return Color.RED;
	}
}
