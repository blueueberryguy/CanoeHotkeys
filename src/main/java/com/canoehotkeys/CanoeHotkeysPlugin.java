package com.canoehotkeys;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Canoe Hotkeys"
)
public class CanoeHotkeysPlugin extends Plugin implements KeyListener
{
	@Inject
	private Client client;

	@Inject
	private CanoeHotkeysConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ClientThread clientThread;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CanoeHotkeysOverlay overlay;

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
		overlayManager.remove(overlay);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		CanoeType match = matchCanoe(e);
		if (match == null)
		{
			return;
		}

		int widgetId = match.widgetId;
		if (client.getWidget(widgetId) == null)
		{
			return;
		}

		e.consume();

		String option = "Make<col=ff9040> " + match.optionLabel;
		clientThread.invoke(() -> client.menuAction(
			0, widgetId, MenuAction.CC_OP, 1, -1, option, ""));
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	private CanoeType matchCanoe(KeyEvent e)
	{
		for (CanoeType type : CanoeType.values())
		{
			if (type.keybind.apply(config).matches(e))
			{
				return type;
			}
		}
		return null;
	}

	@Provides
	CanoeHotkeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CanoeHotkeysConfig.class);
	}
}
