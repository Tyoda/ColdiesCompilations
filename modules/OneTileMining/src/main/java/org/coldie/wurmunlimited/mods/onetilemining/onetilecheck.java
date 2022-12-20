package org.coldie.wurmunlimited.mods.onetilemining;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.Skill;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("unused")
public class onetilecheck {
	// public static final Logger logger = Logger.getLogger(onetilemining.class.getName());

	@SuppressWarnings("unused")
	public static int canmine(int digTilex, int digTiley){
		for (int x = -1; x <= 0; ++x) {
			for (int y2 = -1; y2 <= 0; ++y2) {
				byte decType = Tiles.decodeType(Server.surfaceMesh.getTile(digTilex + x, digTiley + y2));
				if (decType == Tiles.Tile.TILE_ROCK.id || decType == Tiles.Tile.TILE_CLIFF.id){
					return Server.surfaceMesh.getTile(digTilex + x, digTiley + y2);
				}
			}
		}
		return 1;
	}

	@SuppressWarnings("unused")
	public static int canraise(int digTilex, int digTiley){
		for (int x = -1; x <= 0; ++x) {
			for (int y2 = -1; y2 <= 0; ++y2) {
				byte decType = Tiles.decodeType(Server.surfaceMesh.getTile(digTilex + x, digTiley + y2));
				if (decType == Tiles.Tile.TILE_ROCK.id || decType == Tiles.Tile.TILE_CLIFF.id){
					return Tiles.Tile.TILE_ROCK.id;
				}
			}
		}
		return 1;
	}

	@SuppressWarnings("unused")
	public static int gettile(int digTilex, int digTiley, short newHeight){
		return Tiles.encode(newHeight,
							Tiles.decodeType(Server.surfaceMesh.getTile(digTilex, digTiley)),
							Tiles.decodeData(Server.surfaceMesh.getTile(digTilex, digTiley))
		);
	}

	@SuppressWarnings("unused")
	public static int getactiontime(Creature performer, Skill skill, @Nullable Item source){
		int tickMining = Actions.getStandardActionTime(performer, skill, source, 0.0);
		tickMining = (int) (tickMining*onetilemining.levelfactor);
		return tickMining;
	}
}