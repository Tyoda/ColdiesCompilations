package net.coldie.wurmunlimited.mods.decorations;

import java.io.IOException;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.skills.SkillList;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

public class decorationitems {

	public decorationitems() {
		
		decorations decor = decorations.getInstance();
		//cotton
			//teddy bear
			int teddyBearId = IdFactory.getIdFor("teddy bear", IdType.ITEMTEMPLATE);
			try {
	
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(teddyBearId, "teddy bear", "teddy bears", "superb", "good", "ok", "poor",
						"Something to cuddle and make you feel good.",
						new short[] { ItemTypes.ITEM_TYPE_CLOTH, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION },
						(short)306, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.teddybear.", 30.0F, 1, (byte)17, 10000, true).setContainerSize(50, 50, 50);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry teddy = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, teddyBearId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        teddy.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	        teddy.addRequirement(new CreationRequirement(2, ItemList.cotton, 5, true));
	        teddy.addRequirement(new CreationRequirement(3, ItemList.charcoal, 3, true));

			//bunny
			int bunnyId = IdFactory.getIdFor("bunny", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(bunnyId, "bunny", "bunnies", "superb", "good", "ok", "poor",
						"Something to cuddle and make you feel good.",
						new short[] { ItemTypes.ITEM_TYPE_CLOTH, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION},
						(short)306, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.bunny.", 30.0F, 1, (byte)17, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry bunny = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, bunnyId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        bunny.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	        bunny.addRequirement(new CreationRequirement(2, ItemList.cotton, 5, true));
	        bunny.addRequirement(new CreationRequirement(3, ItemList.charcoal, 3, true));

			//koala
			int koalaId = IdFactory.getIdFor("koala", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(koalaId, "koala", "koalas", "superb", "good", "ok", "poor",
						"Something to cuddle and make you feel good.",
						new short[] { ItemTypes.ITEM_TYPE_CLOTH, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION },
						(short)306, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.koala.", 30.0F, 1, (byte)17, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry koala = CreationEntryCreator.createAdvancedEntry(SkillList.CLOTHTAILORING, ItemList.needleIron, ItemList.clothString, koalaId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        koala.addRequirement(new CreationRequirement(1, ItemList.clothYard, 5, true));
	        koala.addRequirement(new CreationRequirement(2, ItemList.cotton, 5, true));
	        koala.addRequirement(new CreationRequirement(3, ItemList.charcoal, 3, true));

        //end cotton

        //wood
			//rocking horse
			int rockingHorseId = IdFactory.getIdFor("rocking horse", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(rockingHorseId, "rocking horse", "rocking horses", "superb", "good", "ok", "poor",
						"A wooden horse for a child to ride.",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE, ItemTypes.ITEM_TYPE_SUPPORTS_SECONDARY_COLOR, ItemTypes.ITEM_TYPE_HOLLOW, ItemTypes.ITEM_TYPE_VIEWABLE_SUBITEMS },
						(short)0, (short)1, 0, 9072000L, 150, 150, 150, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.rockinghorse.", 30.0F, 500, (byte)14, 10000, true).setSecondryItem("Seat and feet").setContainerSize(50, 50, 50);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry horse = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.hammerWood, ItemList.plank, rockingHorseId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        horse.addRequirement(new CreationRequirement(1, ItemList.shaft, 5, true));
	        horse.addRequirement(new CreationRequirement(2, ItemList.nailsIronSmall, 10, true));

	        //bowling
			int bowlingSetId = IdFactory.getIdFor("bowling set", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(bowlingSetId, "bowling set", "bowling sets", "superb", "good", "ok", "poor",
						"A game played by children.",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.bowling.", 30.0F, 1, (byte)14, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

	        @SuppressWarnings("unused")
			AdvancedCreationEntry bowling = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.log, bowlingSetId, false, true, 0.0f, false, false, CreationCategories.TOYS);

	        //dragon box
			int dragonBoxId = IdFactory.getIdFor("block set with dragon image", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(dragonBoxId, "block set with dragon image", "block sets with dragon image", "superb", "good", "ok", "poor",
						"A puzzle game",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION},
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.dragonbox.", 30.0F, 1, (byte)14, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry dragonbox = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, dragonBoxId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        dragonbox.addRequirement(new CreationRequirement(1, ItemList.shaft, 3, true));
	        dragonbox.addRequirement(new CreationRequirement(2, ItemList.dye, 1, true));


	        //lute
			int luteId = IdFactory.getIdFor("lute", IdType.ITEMTEMPLATE);
			try {

				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(luteId, "lute", "lutes", "superb", "good", "ok", "poor",
						"A puzzle game",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.lute.", 30.0F, 1, (byte)14, 1000, true);
			}catch(IOException e){ throw new HookException(e); }

	        AdvancedCreationEntry lute = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, luteId, false, true, 0.0f, false, false, CreationCategories.TOYS);
	        lute.addRequirement(new CreationRequirement(1, ItemList.plank, 2, true));
	        lute.addRequirement(new CreationRequirement(2, ItemList.metalWires, 6, true));


	        //bilboquet
			int bilboquetId = IdFactory.getIdFor("bilboquet", IdType.ITEMTEMPLATE);
			try {

				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(bilboquetId, "bilboquet", "bilboquets", "superb", "good", "ok", "poor",
						"A game played by children.",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.bilboquet.", 30.0F, 1, (byte)14, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			AdvancedCreationEntry bilboquet = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.log, bilboquetId, false, true, 0.0f, false, false, CreationCategories.TOYS);
			bilboquet.addRequirement(new CreationRequirement(1, ItemList.clothString, 1, true));

        //end wood

		//metal
			//leaning axe
			int leaningAxeId = IdFactory.getIdFor("leaning axe", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(leaningAxeId, "leaning axe", "leaning axes", "superb", "good", "ok", "poor",
						"An axe that leans.",
						new short[] { ItemTypes.ITEM_TYPE_METAL, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.leaningaxe.", 30.0F, 1, (byte)14, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry leaningaxe = CreationEntryCreator.createAdvancedEntry(SkillList.GROUP_SMITHING_WEAPONSMITHING, ItemList.shaft, ItemList.axeHeadMedium, leaningAxeId, true, true, 0.0f, false, false, CreationCategories.TOYS);


		//end metal

		//clay
			//ducky
			int duckId = IdFactory.getIdFor("duck", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(duckId, "duck", "ducks", "superb", "good", "ok", "poor",
						"A duck decoration.",
						new short[] { ItemTypes.ITEM_TYPE_POTTERY, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.duck.", 30.0F, 1, (byte)19, 10000, true).setContainerSize(1, 1, 1);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry duck = CreationEntryCreator.createAdvancedEntry(SkillList.POTTERY, ItemList.bodyHand, ItemList.clay, duckId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//marbles
			int marblesId = IdFactory.getIdFor("marbles", IdType.ITEMTEMPLATE);
			try {

				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(marblesId, "marbles", "marbles", "superb", "good", "ok", "poor",
						"Bag of marbles.",
						new short[] { ItemTypes.ITEM_TYPE_POTTERY, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.marbles.", 30.0F, 1, (byte)19, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry marbles = CreationEntryCreator.createAdvancedEntry(SkillList.POTTERY, ItemList.bodyHand, ItemList.clay, marblesId, false, true, 0.0f, false, false, CreationCategories.TOYS);

		//end clay

			//chess table
			int chessTableId = IdFactory.getIdFor("chess table", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessTableId, "chess table", "chess tables", "superb", "good", "ok", "poor",
						"A table designed for playing chess.",
						new short[] { ItemTypes.ITEM_TYPE_WOOD, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE, ItemTypes.ITEM_TYPE_HOLLOW, ItemTypes.ITEM_TYPE_VIEWABLE_SUBITEMS },
						(short)60, (short)1, 0, 9072000L, 400, 400, 400, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.chesstable.", 30.0F, 5000, (byte)14, 10000, true).setContainerSize(400, 400, 400);
			}catch(IOException e){ throw new HookException(e); }

			AdvancedCreationEntry ChessTable = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, chessTableId, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
	        ChessTable.addRequirement(new CreationRequirement(1, ItemList.shaft, 4, true));
	        ChessTable.addRequirement(new CreationRequirement(2, ItemList.plank, 4, true));

			//chess chair
			decor.setChessChairId(IdFactory.getIdFor("chess chair", IdType.ITEMTEMPLATE));
			int chessChairId = decor.getChessChairId();
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessChairId, "chess chair", "chess chairs", "superb", "good", "ok", "poor",
						"A decorative chair designed to match the chess table.",
						new short[] { 108, 21, 135, 86, 51, 52, 44, 117, 197, 199, 48 },
						(short)274, (short)41, 0, 9072000L, 150, 150, 150, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.furniture.chair.chesschair.", 30.0F, 500, (byte)14, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			AdvancedCreationEntry ChessChair = CreationEntryCreator.createAdvancedEntry(SkillList.CARPENTRY_FINE, ItemList.knifeCarving, ItemList.plank, chessChairId, false, true, 0.0f, false, false, CreationCategories.FURNITURE);
			ChessChair.addRequirement(new CreationRequirement(1, ItemList.shaft, 4, true));
			ChessChair.addRequirement(new CreationRequirement(2, ItemList.plank, 4, true));



			//chess white pawn
			int chessWhitePawnId = IdFactory.getIdFor("chess white pawn", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhitePawnId, "chess white pawn", "chess white pawns", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhitepawn.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhitepawn = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhitePawnId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess white horse
			int chessWhiteHorseId = IdFactory.getIdFor("chess white horse", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhiteHorseId, "chess white horse", "chess white horses", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhitehorse.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhitehorse = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhiteHorseId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess white castle
			int chessWhiteCastleId = IdFactory.getIdFor("chess white castle", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhiteCastleId, "chess white castle", "chess white castles", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhitecastle.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhitecastle = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhiteCastleId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess white bishop
			int chessWhiteBishopId = IdFactory.getIdFor("chess white bishop", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhiteBishopId, "chess white bishop", "chess white bishops", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhitebishop.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhitebishop = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhiteBishopId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess white king
			int chessWhiteKingId = IdFactory.getIdFor("chess white king", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhiteKingId, "chess white king", "chess white kings", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhiteking.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhiteking = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhiteKingId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess white queen
			int chessWhiteQueenId = IdFactory.getIdFor("chess white queen", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessWhiteQueenId, "chess white queen", "chess white queens", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chesswhitequeen.", 30.0F, 1, (byte)62, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chesswhitequeen = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.marbleShard, chessWhiteQueenId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black pawn
			int chessBlackPawnId = IdFactory.getIdFor("chess black pawn", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackPawnId, "chess black pawn", "chess black pawns", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackpawn.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackpawn = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackPawnId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black pawn
			int chessBlackHorseId = IdFactory.getIdFor("chess black horse", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackHorseId, "chess black horse", "chess black horses", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackhorse.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackhorse = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackHorseId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black castle
			int chessBlackCastleId = IdFactory.getIdFor("chess black castle", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackCastleId, "chess black castle", "chess black castles", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackcastle.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackcastle = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackCastleId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black bishop
			int chessBlackBishopId = IdFactory.getIdFor("chess black bishop", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackBishopId, "chess black bishop", "chess black bishops", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackbishop.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackbishop = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackBishopId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black king
			int chessBlackKingId = IdFactory.getIdFor("chess black king", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackKingId, "chess black king", "chess black kings", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackking.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackking = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackKingId, false, true, 0.0f, false, false, CreationCategories.TOYS);

			//chess black queen
			int chessBlackQueenId = IdFactory.getIdFor("chess black queen", IdType.ITEMTEMPLATE);
			try {
				com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(chessBlackQueenId, "chess black queen", "chess black queens", "superb", "good", "ok", "poor",
						"A chess peice used in playing a strategy game.",
						new short[] { ItemTypes.ITEM_TYPE_STONE, ItemTypes.ITEM_TYPE_REPAIRABLE, ItemTypes.ITEM_TYPE_NOT_MISSION, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_COLORABLE },
						(short)0, (short)1, 0, 9072000L, 1, 1, 1, -10,
						MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.toy.chessblackqueen.", 30.0F, 1, (byte)61, 10000, true);
			}catch(IOException e){ throw new HookException(e); }

			@SuppressWarnings("unused")
			AdvancedCreationEntry Chessblackqueen = CreationEntryCreator.createAdvancedEntry(SkillList.STONECUTTING, ItemList.stoneChisel, ItemList.slateShard, chessBlackQueenId, false, true, 0.0f, false, false, CreationCategories.TOYS);


	}
}