package hd.elysium.render;

import hd.elysium.Elysium;
import hd.elysium.tile.TileEntityPortal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

public class RenderPortal extends TileEntitySpecialRenderer
{
	public int displayList;
	public ResourceLocation beamTexture;
	
	public RenderPortal(int poligons)
	{
		this.displayList = glGenLists(1);
		glNewList(this.displayList, GL_COMPILE);
			glPushAttrib(GL_ENABLE_BIT);
				glDisable(GL_LIGHTING);
				glDisable(GL_CULL_FACE);
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glBegin(GL_QUADS);
					for(int i = 0; i < poligons; i++)
					{
						glTexCoord2f(16F*i/poligons, 100F);
						glVertex3d(Math.cos(Math.PI*2*i/poligons), 100D, -Math.sin(Math.PI*2*i/poligons));
						glTexCoord2f(16F*i/poligons, 0F);
						glVertex3d(Math.cos(Math.PI*2*i/poligons), 0D, -Math.sin(Math.PI*2*i/poligons));
						glTexCoord2f(16F*(i+1)/poligons, 0F);
						glVertex3d(Math.cos(Math.PI*2*(i+1)/poligons), 0D, -Math.sin(Math.PI*2*(i+1)/poligons));
						glTexCoord2f(16F*(i+1)/poligons, 100F);
						glVertex3d(Math.cos(Math.PI*2*(i+1)/poligons), 100D, -Math.sin(Math.PI*2*(i+1)/poligons));
					}
				glEnd();
			glPopAttrib();
		glEndList();
		this.beamTexture = new ResourceLocation(Elysium.ID+":textures/misc/beam.png");
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick)
	{
		TileEntityPortal portal = (TileEntityPortal) tile;
		glPushMatrix();
			glTranslated(x+0.5D, y-1F+(portal.tex+portal.mtex*partialTick)%1F, z+0.5D);
			glRotatef(portal.deg+portal.mdeg*partialTick, 0F, 1F, 0F);
			glScalef(portal.rad+portal.mrad*partialTick, 1F, portal.rad+portal.mrad*partialTick);
			bindTexture(this.beamTexture);
			glCallList(this.displayList);
		glPopMatrix();
	}
}
