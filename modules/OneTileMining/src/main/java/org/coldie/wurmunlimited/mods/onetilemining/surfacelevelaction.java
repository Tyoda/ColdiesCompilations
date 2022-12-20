package org.coldie.wurmunlimited.mods.onetilemining;


import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Constants;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.*;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.server.skills.Skills;
import com.wurmonline.server.structures.BridgePart;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modsupport.actions.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class surfacelevelaction implements ModAction, BehaviourProvider, ActionPerformer {
	public final short actionId;
	public final ActionEntry actionEntry;
	public static final Logger logger = Logger.getLogger(surfacelevelaction.class.getName());
	public surfacelevelaction() {
		actionId = (short) ModActions.getNextActionId();
		actionEntry = new ActionEntryBuilder(actionId, "Lower rock to flat", "Lowering rock to flat", new int[0]).range(12).build();
		ModActions.registerAction(actionEntry);
	}
	
	@Override
	public List<ActionEntry> getBehavioursFor(Creature performer, Item source, int tilex, int tiley, boolean onSurface, int tile) {
		byte type = Tiles.decodeType(tile);
		if (type == 4 && source.isMiningtool())
				return Collections.singletonList(actionEntry);

		return null;
	}

	private boolean canLevel(Action act, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short action, float counter) {
		int encodedTile = Server.surfaceMesh.getTile(tilex, tiley);
		Village village = Zones.getVillage(tilex, tiley, performer.isOnSurface());
		if (village != null) {
			if (!village.isActionAllowed((short)144, performer, false, encodedTile, 0)) {
				if (!Zones.isOnPvPServer(tilex, tiley)) {
					performer.getCommunicator().sendNormalServerMessage("This action is not allowed here, because the tile is on a player owned deed that has disallowed it.", (byte)3);
					return true;
				}
				if (!village.isEnemy(performer)) {
					if (performer.isLegal()) {
						performer.getCommunicator().sendNormalServerMessage("That would be illegal here. You can check the settlement token for the local laws.", (byte)3);
						return true;
					}
				}
			}
		}


		if ((tilex < 1) || (tilex > (1 << Constants.meshSize) - 1) || (tiley < 1) || (tiley > (1 << Constants.meshSize) - 1)) {
			performer.getCommunicator().sendNormalServerMessage("The water is too deep to mine.", (byte)3);
			return true;
		}

		if (Zones.isTileProtected(tilex, tiley)) {
			performer.getCommunicator().sendNormalServerMessage("This tile is protected by the gods. You can not mine here.", (byte)3);
			return true;
		}

		int y;
		VolaTile vt;
		for (int x = 0; x >= -1; --x) {
			for (y = 0; y >= -1; --y) {
				vt = Zones.getTileOrNull(tilex + x, tiley + y, true);
				if ((vt != null) && (vt.getStructure() != null)) {
					if (vt.getStructure().isTypeHouse()) {
						if ((x == 0) && (y == 0)) {
							performer.getCommunicator().sendNormalServerMessage("You cannot mine in a building.", (byte)3);
						}
						else {
							performer.getCommunicator().sendNormalServerMessage("You cannot mine next to a building.", (byte)3);
						}
						return true;
					}


					for (BridgePart bp : vt.getBridgeParts()) {
						if (bp.getType().isSupportType()) {
							performer.getCommunicator().sendNormalServerMessage("The bridge support nearby prevents mining.");
							return true;
						}
						if (((x == -1) && (bp.hasEastExit())) || ((x == 0) &&
								(bp.hasWestExit())) || ((y == -1) &&
								(bp.hasSouthExit())) || ((y == 0) &&
								(bp.hasNorthExit()))) {
							performer.getCommunicator().sendNormalServerMessage("The end of the bridge nearby prevents mining.");
							return true;
						}
					}
				}
			}
		}

		if (performer.getStatus().getStamina() < 6000) {
			performer.getCommunicator().sendNormalServerMessage("You must rest.");
			return true;
		}

		vt = Zones.getTileOrNull(tilex, tiley, true);
		if ((vt != null) && (vt.getFencesForLevel(0).length > 0)) {
			performer.getCommunicator().sendNormalServerMessage("You cannot mine next to a fence.", (byte)3);
			return true;
		}
		vt = Zones.getTileOrNull(tilex, tiley - 1, true);
		if ((vt != null) && (vt.getFencesForLevel(0).length > 0)) {
			performer.getCommunicator().sendNormalServerMessage("You cannot mine next to a fence.", (byte)3);
			return true;
		}
		vt = Zones.getTileOrNull(tilex - 1, tiley, true);
		if ((vt != null) && (vt.getFencesForLevel(0).length > 0)) {
			performer.getCommunicator().sendNormalServerMessage("You cannot mine next to a fence.", (byte)3);
			return true;
		}

		VolaTile dropTile = Zones.getTileOrNull((int)performer.getPosX() >> 2, (int)performer.getPosY() >> 2, true);
		if (dropTile != null) {
			if (dropTile.getNumberOfItems(performer.getFloorLevel()) > 99) {
				performer.getCommunicator().sendNormalServerMessage("There is no space to mine here. Clear the area first.", (byte)3);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean action(Action act, Creature performer, Item source, int tilex, int tiley, boolean onSurface, int heightOffset, int tile, short action, float counter) {
		byte type = Tiles.decodeType(tile);
		if (type == 4 && source.isMiningtool()) {
			boolean done;
			//only done once at start.
			if (counter == 1.0F) {
				Skill mining;
				Skills skills = performer.getSkills();
				try {
					mining = skills.getSkill(1008);
				} catch (NoSuchSkillException e) {
					mining = skills.learn(1008, 1.0F);
				}

				if(canLevel(act, performer, source, tilex, tiley, onSurface, heightOffset, tile, action, counter)) return true;

				int minHeight = Short.MAX_VALUE;
				int totalHeight = 0;
				int currheight;
				int maxmines = 0;
				for (int x = tilex; x <= tilex+1; ++x) {
					for (int y1 = tiley; y1 <= tiley+1; ++y1) {
						int mytile = Server.surfaceMesh.getTile(x, y1);
						currheight = Tiles.decodeHeight(mytile);
						if(currheight <= -25) {
							performer.getCommunicator().sendNormalServerMessage("The water is too deep to mine.");
							return true;
						}

						totalHeight = totalHeight + currheight;
						minHeight = Math.min(currheight, minHeight);
						if(currheight>minHeight)
							maxmines = maxmines + (currheight - minHeight);

						if (TileRockBehaviour.cannotMineSlope(performer, mining, x, y1)) {
							return true;
						}

						int caveTile = Server.caveMesh.getTile(x, y1);
						short caveFloor = Tiles.decodeHeight(caveTile);
						int caveCeilingHeight = caveFloor + (short)(Tiles.decodeData(caveTile) & 0xFF);
						if (currheight - 1 <= caveCeilingHeight) {
							performer.getCommunicator().sendNormalServerMessage("The rock sounds hollow.", (byte)3);
							return true;
						}
					}
				}

				if(minHeight < 0)
					minHeight=0;

				maxmines = totalHeight - (minHeight * 4);
				int time = Actions.getStandardActionTime(performer, mining, source, 0.0D);
				act.setData(Math.round((double)time/10));
				time = time*maxmines;
				try {
					performer.getCurrentAction().setTimeLeft(time);
				}
				catch (NoSuchActionException nsa) {
					logger.log(Level.INFO, "This action does not exist?", nsa);
				}

				Server.getInstance().broadCastAction(performer.getName() + " starts mining.", performer, 5);
				performer.getCommunicator().sendNormalServerMessage("You start to mine.");
				performer.sendActionControl(act.getActionEntry().getVerbString().toLowerCase(), true, time);
				source.setDamage(source.getDamage() + 0.0015F * source.getDamageModifier());
				performer.getStatus().modifyStamina(-1000.0F);
			}

			int maxHeight = Short.MIN_VALUE;
			int minHeight = Short.MAX_VALUE;
			int minex = tilex;
			int miney = tiley;
			int currheight;
			int maxmines = 0;

			for (int x = tilex; x <= tilex+1; ++x) {
				for (int y1 = tiley; y1 <= tiley+1; ++y1) {
					int mytile = Server.surfaceMesh.getTile(x, y1);
					currheight = Tiles.decodeHeight(mytile);
					if(currheight <= -25) {
						performer.getCommunicator().sendNormalServerMessage("The water is too deep to mine.");
						return true;
					}
					minHeight = Math.min(currheight, minHeight);
					if(currheight>minHeight)
						maxmines = maxmines + (currheight - minHeight);

					if (currheight > maxHeight) {
						minex = x;
						miney = y1;
					}

					Skill mining;
					Skills skills = performer.getSkills();
					try {
						mining = skills.getSkill(1008);
					} catch (NoSuchSkillException e) {
						mining = skills.learn(1008, 1.0F);
					}

					maxHeight = Math.max(currheight, maxHeight);

					if (TileRockBehaviour.cannotMineSlope(performer, mining, x, y1)) {
						return true;
					}

					int caveTile = Server.caveMesh.getTile(x, y1);
					short caveFloor = Tiles.decodeHeight(caveTile);
					int caveCeilingHeight = caveFloor + (short)(Tiles.decodeData(caveTile) & 0xFF);
					short h = Tiles.decodeHeight(tile);
					if (h - 1 <= caveCeilingHeight) {
						performer.getCommunicator().sendNormalServerMessage("The rock sounds hollow.", (byte)3);
						return true;
					}
				}
			}

			if(maxHeight != minHeight) {
				//mining action
				if (act.currentSecond() % act.getData() == 0 || performer.getPower() > 3) {
					if(canLevel(act, performer, source, tilex, tiley, onSurface, heightOffset, tile, action, counter)) {
						return true;
					}

					if(performer.getPower() <= 3) {
						source.setDamage(source.getDamage() + 0.0015F * source.getDamageModifier());
						performer.getStatus().modifyStamina(-2000.0F);
					}

					TileRockBehaviour.mine(act, performer, source, tilex, tiley, action, 100000.0f, minex, miney);
					act.setRarity((byte)0);
				}
				done = false;
			}else {
				performer.getCommunicator().sendSafeServerMessage("Finished lowering.");
				done = true;
			}
			return done;
		}
		return false;
	}

	@Override
	public short getActionId() {
		return actionId;
	}
}