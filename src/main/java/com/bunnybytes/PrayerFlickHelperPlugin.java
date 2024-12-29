package com.bunnybytes;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

@Slf4j
@PluginDescriptor(
		name = "Prayer Flick Helper"
)
public class PrayerFlickHelperPlugin extends Plugin
{
	private List<Integer> ticksSinceStart = new ArrayList<>();
	private List<Integer> prayerClicks = new ArrayList<>();

	@Inject
	private PrayerFlickOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Client client;

	@Inject
	private PrayerFlickHelperConfig config;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	/*
	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		Widget quickPrayerWidget = client.getWidget(WidgetInfo.MINIMAP_QUICK_PRAYER_ORB);

		Point mousePos = client.getMouseCanvasPosition();
		boolean clickedPrayerIcon = quickPrayerWidget.getBounds().contains(mousePos.getX(), mousePos.getY());

		if (clickedPrayerIcon) {
			prayerClicks.add(0);
		}
	}
	*/

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		String menuOption = event.getMenuOption();

		if (menuOption.equals("Activate") || menuOption.equals("Deactivate")) {
			prayerClicks.add(0);
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick) {
		ticksSinceStart.add(0);
	}

	@Provides
	PrayerFlickHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PrayerFlickHelperConfig.class);
	}

	public List<Integer> getTicksSinceStart() {
		return ticksSinceStart;
	}

	public void updateTickStep(int limit) {
		List<Integer> newList = new ArrayList<>();

		for (int tick : ticksSinceStart) {
			int updatedTick = tick + 1;
			if (updatedTick <= limit) {
				newList.add(tick + 1);
			}
		}

		ticksSinceStart = newList;
	}

	public List<Integer> getPrayerClicks() {
		return prayerClicks;
	}

	public void updatePrayerStep(int limit) {
		List<Integer> newList = new ArrayList<>();

		for (int tick : prayerClicks) {
			int updatedTick = tick + 1;
			if (updatedTick <= limit) {
				newList.add(tick + 1);
			}
		}

		prayerClicks = newList;
	}

	public void updateStep(List<Integer> list, int limit) {
		ListIterator<Integer> listIterator = list.listIterator();

		while (listIterator.hasNext()) {
			int tick = listIterator.next();
			int updatedTick = tick + 1;
			if (updatedTick <= limit) {
				listIterator.set(updatedTick);
			} else {
				listIterator.remove();
			}
		}
	}
}