package soar.gui.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import soar.Soar;
import soar.management.mod.Mod;
import soar.management.mod.ModCategory;
import soar.utils.GlUtils;
import soar.utils.RenderUtils;
import soar.utils.mouse.MouseUtils;

public class GuiClientSettings extends GuiScreen{

	private FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	private ModCategory selectedCategory;
	
	public GuiClientSettings() {
		selectedCategory = ModCategory.HUD;
	}
	
	@Override
	public void initGui() {
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		ScaledResolution sr = new ScaledResolution(mc);
		
		int addX = 190;
		int addY = 110;
		
		int x = (sr.getScaledWidth() / 2) - addX;
		int y = (sr.getScaledHeight() / 2) - addY;
		int width = addX * 2;
		int height = addY * 2;
		
		int categoryOffsetY = 42;
		int modOffsetY = 15;
		
		//Draw background
		RenderUtils.drawRect(x, y, width, height, Integer.MIN_VALUE);
		RenderUtils.drawRect(x, y, 85, height, Integer.MIN_VALUE);

		RenderUtils.drawRect(x, y + 29, 85, 1, Integer.MIN_VALUE);
		
		//Draw Soar lite Text
		GlUtils.startScale(x, y, fr.getStringWidth("Soar Lite"), fr.FONT_HEIGHT, 1.5F);
		fr.drawString("Soar Lite", x + 13, y + 10, -1);
		GlUtils.stopScale();
		
		//Draw categories
		for(ModCategory c : ModCategory.values()) {
			
			String formattedName = c.name().replace("PERFORMANCE", "Performance").replace("RENDER", "Render").replace("PLAYER", "Player").replace("OTHER", "Other");

			GlUtils.startScale(x, y, fr.getStringWidth(formattedName), categoryOffsetY + fr.FONT_HEIGHT, 1.1F);
			fr.drawString(formattedName, x + 10, y + categoryOffsetY, -1);
			GlUtils.stopScale();
			
			categoryOffsetY +=26;
		}
		
		//Draw mods list
		for(Mod m : Soar.instance.modManager.getModByCategory(selectedCategory)) {
			
			if(!m.isHide()) {
				RenderUtils.drawRect(x + 100, y + modOffsetY, width - 115, 28, Integer.MIN_VALUE);
				fr.drawString(m.getName(), x + 110, y + modOffsetY + 10, -1);
				
				modOffsetY+=35;
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		
		ScaledResolution sr = new ScaledResolution(mc);
		
		int addX = 190;
		int addY = 110;
		
		int x = (sr.getScaledWidth() / 2) - addX;
		int y = (sr.getScaledHeight() / 2) - addY;
		int width = addX * 2;
		int height = addY * 2;
		
		int categoryOffsetY = 48;
		int modOffsetY = 15;
		
		//Select Category
		for(ModCategory c : ModCategory.values()) {
			
			if(MouseUtils.isInside(mouseX, mouseY, x, (y - 10) + categoryOffsetY, 85, 22) && mouseButton == 0) {
				selectedCategory = c;
			}
			
			categoryOffsetY +=26;
		}
		
		for(Mod m : Soar.instance.modManager.getModByCategory(selectedCategory)) {
			
			if(!m.isHide()) {
				
				if(MouseUtils.isInside(mouseX, mouseY, x + 100, y + modOffsetY, width - 115, 28) && mouseButton == 0) {
					m.toggle();
				}
				
				modOffsetY+=35;
			}
		}
	}
	
	@Override
    public void onGuiClosed() {
		Soar.instance.configManager.save();
    }
}
