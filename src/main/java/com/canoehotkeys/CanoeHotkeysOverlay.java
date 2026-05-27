package com.canoehotkeys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class CanoeHotkeysOverlay extends Overlay
{
	private static final Color BG = new Color(0, 0, 0, 200);
	private static final Color BORDER = new Color(255, 215, 0);
	private static final Color TEXT = Color.WHITE;
	private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

	private final Client client;
	private final CanoeHotkeysConfig config;

	@Inject
	CanoeHotkeysOverlay(Client client, CanoeHotkeysConfig config)
	{
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		graphics.setFont(FONT);
		FontMetrics fm = graphics.getFontMetrics();

		for (CanoeType type : CanoeType.values())
		{
			Widget widget = client.getWidget(type.widgetId);
			if (widget == null || widget.isHidden())
			{
				continue;
			}

			Rectangle bounds = widget.getBounds();
			if (bounds == null || bounds.width <= 0 || bounds.height <= 0)
			{
				continue;
			}

			String label = type.keybind.apply(config).toString();
			int textWidth = fm.stringWidth(label);
			int padX = 6;
			int padY = 3;
			int boxW = textWidth + padX * 2;
			int boxH = fm.getHeight() + padY;

			int x = bounds.x + bounds.width / 2 - boxW / 2;
			int y = bounds.y + 4;

			graphics.setColor(BG);
			graphics.fillRect(x, y, boxW, boxH);
			graphics.setColor(BORDER);
			graphics.drawRect(x, y, boxW, boxH);
			graphics.setColor(TEXT);
			graphics.drawString(label, x + padX, y + fm.getAscent() + padY / 2);
		}

		return null;
	}
}
