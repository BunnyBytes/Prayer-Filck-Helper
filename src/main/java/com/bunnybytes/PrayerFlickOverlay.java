package com.bunnybytes;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class PrayerFlickOverlay extends Overlay {
    private final Client client;
    private final PrayerFlickHelperPlugin plugin;

    @Inject
    private PrayerFlickHelperConfig config;

    @Inject
    public PrayerFlickOverlay(Client client, PrayerFlickHelperPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        List<Integer> ticksSinceStart = plugin.getTicksSinceStart();

        if (ticksSinceStart.isEmpty()) {
            return null;
        }

        int width = 200; // Width of the graph
        int height = 50; // Height of the graph

        // Draw graph background
        graphics.setColor(config.backgroundColour());
        graphics.fillRect(0, 0, width, height);

        // Draw game ticks on graph
        graphics.setColor(config.tickColour());

        plugin.updateStep(plugin.getTicksSinceStart(), width);

        for (int tick : ticksSinceStart) {
            int x = width - tick;
            int y = height / 2;
            graphics.fillRect(x, 0, 2, height);
        }

        List<Integer> prayerClicksSinceStart = plugin.getPrayerClicks();

        // Draw prayer clicks as points on the graph
        graphics.setColor(config.clickColour());

        plugin.updateStep(plugin.getPrayerClicks(), width);

        for (int tick : prayerClicksSinceStart) {
            int x = width - tick;
            int y = height / 2;
            graphics.fillOval(x, y, 5, 5);
        }

        return new Dimension(width, height);
    }
}
