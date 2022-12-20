package net.coldie.wurmunlimited.mods.bountycoldiestyle;

import com.friya.wurmonline.server.loot.BeforeDropListener;
import com.friya.wurmonline.server.loot.LootResult;
import com.friya.wurmonline.server.loot.LootSystem;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.economy.Economy;

import java.io.IOException;



public class currencyadding implements BeforeDropListener {
    private static currencyadding instance;

    currencyadding() {
		instance = this;
    }

    public boolean onBeforeDrop(LootResult lootResult) {
        Creature[] arrattackers = lootResult.getKillers();
        Creature thisCreature = lootResult.getCreature();
        thisCreature.getStatus().getModType();
        
        
            
        if (thisCreature.isPlayer()) return false;
        float bounty = getConditionFactor(thisCreature) * Math.round(bountymod.bountyMultiplier * (bountymod.DRFactor - thisCreature.getArmourMod()) * thisCreature.getBaseCombatRating());
		bounty = bounty / thisCreature.getStatus().getBattleRatingTypeModifier();
        int numPlayers = 0;
		for (Creature c : arrattackers){
			if (c.isPlayer()) numPlayers = numPlayers+1;
		}
		if (bounty <= 1) bounty = 1;
		if (numPlayers > 1){
			bounty = Math.round((bounty*1.2)/numPlayers);
		}		
		if (bountymod.isgroup1(thisCreature)) bounty = bounty * bountymod.group1factor;
		if (bountymod.isgroup2(thisCreature)) bounty = bounty * bountymod.group2factor;
		if (bountymod.isgroup3(thisCreature)) bounty = bounty * bountymod.group3factor;
		if (bountymod.isgroup4(thisCreature)) bounty = bounty * bountymod.group4factor;
		if (bountymod.isgroup5(thisCreature)) bounty = bounty * bountymod.group5factor;
		if (bountymod.isgroup6(thisCreature)) bounty = bounty * bountymod.group6factor;
		if (bountymod.isgroup7(thisCreature)) bounty = bounty * bountymod.group7factor;
		if (bountymod.isgroup8(thisCreature)) bounty = bounty * bountymod.group8factor;
		if (bountymod.isgroup9(thisCreature)) bounty = bounty * bountymod.group9factor;

		
        int n = arrattackers.length;
        int n2 = 0;
        // because 1 is 1i and we want 1c.
        bounty = bounty * 100;
        while (n2 < n) {
        	Creature i = arrattackers[n2];
        	
        	if (i.isPlayer()){
	        		try {
						i.addMoney((long) bounty);
						String bountyMessage = Economy.getEconomy().getChangeFor((long) bounty).getChangeString();
						i.getCommunicator().sendSafeServerMessage("you got "+bountyMessage+" deposited into your bank.");
						if (numPlayers > 1){
							i.getCommunicator().sendSafeServerMessage("There were "+numPlayers+" player attackers.");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
        	}
            ++n2;
        }
        return true;
    }

    public static void onServerStarted() {
        LootSystem.getInstance().listen(instance);
    }

	public static float getConditionFactor(Creature C){
	      if (C.getStatus().getModType() > 0) {
	            switch (C.getStatus().getModType()) {
	                case 1: {
	                    return bountymod.fierce;
	                }
	                case 2: {
	                    return bountymod.angry;
	                }
	                case 3: {
	                    return bountymod.raging;
	                }
	                case 4: {
	                    return bountymod.slow;
	                }
	                case 5: {
	                    return bountymod.alert;
	                }
	                case 6: {
	                    return bountymod.greenish;
	                }
	                case 7: {
	                    return bountymod.lurking;
	                }
	                case 8: {
	                    return bountymod.sly;
	                }
	                case 9: {
	                    return bountymod.hardened;
	                }
	                case 10: {
	                    return bountymod.scared;
	                }
	                case 11: {
	                    return bountymod.diseased;
	                }
	                case 99: {
	                    return bountymod.champion;
	                }
	            }
	        }		
		return 1.0f;
	}

	public static currencyadding getInstance() {
		return instance;
	}
}

