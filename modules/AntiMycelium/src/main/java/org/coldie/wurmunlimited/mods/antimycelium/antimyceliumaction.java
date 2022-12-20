package org.coldie.wurmunlimited.mods.antimycelium;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Players;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;

public class antimyceliumaction implements ModAction, BehaviourProvider, ActionPerformer {
	public static short actionId;
	static ActionEntry actionEntry;

	public antimyceliumaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = ActionEntry.createEntry(actionId, "Cleanse Mycelium", "Cleansing Mycelium", new int[0]);
		ModActions.registerAction(actionEntry);
	}

	@Override
	public BehaviourProvider getBehaviourProvider() {
		return this;
	}

	@Override
	public ActionPerformer getActionPerformer() {
		return this;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
		if (performer instanceof Player && performer.getPower() >= 5 && source.getTemplateId() == 176)
			return Collections.singletonList(actionEntry);

		return null;
	}
	
	@Override
	public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
		if (performer instanceof Player && performer.getPower() >= 5 && source.getTemplateId() == 176) {
			
        	int tilex = (int) (performer.getPosX()/4);
        	int tiley = (int) (performer.getPosY()/4);			
        	performer.getCommunicator().sendSafeServerMessage("TileX: "+tilex);
		    int sx = Zones.safeTileX(tilex - 20 );
		    int sy = Zones.safeTileY(tiley - 20 );
		    int ex = Zones.safeTileX(tilex + 20 );
		    int ey = Zones.safeTileY(tiley + 20 );
		    for (int x = sx; x <= ex; x++)
		    {
		      for (int y = sy; y <= ey; y++)
		      {
		              int tile = Server.surfaceMesh.getTile(x, y);
		              byte type = Tiles.decodeType(tile);
		              Tiles.Tile theTile = Tiles.getTile(type);
		              byte data = Tiles.decodeData(tile);
		              if ((type == (byte) 5) || (type == (byte) 40) || (type == (byte) 10) || 
		                (theTile.isMyceliumTree()) || 
		                (theTile.isMyceliumBush()))
		              {
		                if (theTile.isMyceliumTree())
		                {
		                  Server.setSurfaceTile(x, y, 
		                    Tiles.decodeHeight(tile), theTile.getTreeType(data).asNormalTree(), data);
		                }
		                else if (theTile.isMyceliumBush())
		                {
		                  Server.setSurfaceTile(x, y, 
		                    Tiles.decodeHeight(tile), theTile.getBushType(data).asNormalBush(), data);
		                }
		                else if (type == (byte) 40)
		                {
		                  Server.setSurfaceTile(x, y, 
		                    Tiles.decodeHeight(tile), (byte) 38, (byte)0);
		                }
		                else if (type == (byte) 10)
		                {
		                  Server.setSurfaceTile(x, y, 
		                    Tiles.decodeHeight(tile), (byte) 2, (byte)0);
		                }
		                Players.getInstance().sendChangedTile(x, y, true, false);
		              }
		          }
		      }
			return true;
		}
		return false;
	}

}