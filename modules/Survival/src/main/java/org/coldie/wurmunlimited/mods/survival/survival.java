package org.coldie.wurmunlimited.mods.survival;

import com.wurmonline.math.TilePos;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.*;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.Crops;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.behaviours.Vehicles;
import com.wurmonline.server.bodys.Body;
import com.wurmonline.server.bodys.BodyTemplate;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.NoArmourException;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.NoSpaceException;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.structures.Floor;
import com.wurmonline.server.zones.NoSuchZoneException;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class survival implements WurmServerMod, Configurable, ServerStartedListener, Initable, PlayerMessageListener, Versioned  {

    private static final Logger logger = Logger.getLogger(survival.class.getName());

    public static final String version = "ty1.0";

    // Configuration default values
    static private boolean enableTemperatureSurvival = true;
    static private boolean enableWaterDisease = true;
    static private boolean newPlayerProtection = false;
    static private boolean gmProtection = true;
    static private boolean verboseLogging = false;
    static private boolean hardMode = false;
    static private boolean enableCropSeasons = true;
    static private boolean northSouthMode = true;
    static private float difficultySetting = 0;
    static private short combattemperature = 2000;
    static private int maxWoundSize = 2000;
    static private int tempsourcefactor = 500;
    static private float armorfactor = 1.0f;
    static private int spamfilter = 0;
    static private boolean combatheat = true;
    static private int furstartid = 0;
    static private int furendid = 1;

    static private float clothaffect = 1.0F;
    static private float plateaffect = 1.0F;
    static private float leatheraffect = 1.0F;
    static private float woolaffect = 1.0F;

    // List of body parts
    private byte[] bodyParts =  new byte[] { BodyTemplate.head, BodyTemplate.torso, BodyTemplate.leftArm,
                            BodyTemplate.leftHand, BodyTemplate.rightArm, BodyTemplate.rightHand, BodyTemplate.legs,
                            BodyTemplate.leftFoot, BodyTemplate.rightFoot  };

    @Override
    public void onServerStarted() {
        ModActions.registerAction(new survivalreloadaction());
    }

    @Override
    public void configure(Properties properties) {
        doconfig(properties);
    }

    public static void doconfig(Properties properties){
        enableTemperatureSurvival = Boolean.parseBoolean(properties.getProperty("enableTemperatureSurvival", Boolean.toString(enableTemperatureSurvival)));
        enableWaterDisease = Boolean.parseBoolean(properties.getProperty("enableWaterDisease", Boolean.toString(enableWaterDisease)));
        newPlayerProtection = Boolean.parseBoolean(properties.getProperty("newPlayerProtection", Boolean.toString(newPlayerProtection)));
        verboseLogging = Boolean.parseBoolean(properties.getProperty("verboseLogging", Boolean.toString(verboseLogging)));
        gmProtection = Boolean.parseBoolean(properties.getProperty("gmProtection", Boolean.toString(gmProtection)));
        difficultySetting = Float.parseFloat(properties.getProperty("difficultySetting", Float.toString(difficultySetting)));
        maxWoundSize = Integer.parseInt(properties.getProperty("maxWoundSize", Integer.toString(maxWoundSize)));
        enableCropSeasons = Boolean.parseBoolean(properties.getProperty("enableCropSeasons", Boolean.toString(enableCropSeasons)));
        northSouthMode = Boolean.parseBoolean(properties.getProperty("northSouthMode", Boolean.toString(northSouthMode)));
        tempsourcefactor = Integer.parseInt(properties.getProperty("tempsourcefactor", Integer.toString(tempsourcefactor)));
        armorfactor = Float.parseFloat(properties.getProperty("armorfactor", Float.toString(armorfactor)));
        combattemperature = (short) Integer.parseInt(properties.getProperty("combattemperature", Integer.toString(combattemperature)));
        combatheat = Boolean.parseBoolean(properties.getProperty("combatheat", Boolean.toString(combatheat)));
        furstartid = Integer.parseInt(properties.getProperty("furstartid", Integer.toString(furstartid)));
        furendid = Integer.parseInt(properties.getProperty("furendid", Integer.toString(furendid)));

        clothaffect = Float.parseFloat(properties.getProperty("clothaffect", Float.toString(clothaffect)));
        plateaffect = Float.parseFloat(properties.getProperty("plateaffect", Float.toString(plateaffect)));
        leatheraffect = Float.parseFloat(properties.getProperty("leatheraffect", Float.toString(leatheraffect)));
        woolaffect = Float.parseFloat(properties.getProperty("woolaffect", Float.toString(woolaffect)));
    }

    @Override
    public boolean onPlayerMessage(Communicator communicator, String msg) {
        if (msg.startsWith("/mytemp")) {
            //String commandMessage = communicator.getCommandMessage();
            Player player = communicator.player;
            if (enableTemperatureSurvival) {
                TempEffects tempEffects = getTemperatureEffects(player);
                // Find average temperature and temperature delta, but do not apply wounds or generate warning messages
                tempEffects = pollBodyPartTemperature(player, false, false, tempEffects);
                StringBuilder message = new StringBuilder();

                // Produce a user-friendly summary of temperature and temperature delta

                if (tempEffects.averageTemperature > 200) {
                    message.append("You are hot.");
                } else {
                    if (tempEffects.averageTemperature == 0) {
                        message.append("You are freezing cold,");
                    } else if (tempEffects.averageTemperature < 70) {
                        message.append("You are very cold,");
                    } else if (tempEffects.averageTemperature < 130) {
                        message.append("You are cold,");
                    } else if (tempEffects.averageTemperature < 180) {
                        message.append("You are warm,");
                    } else {
                        message.append("You are very warm,");
                    }

                    if (tempEffects.averageModifiedTemperatureDelta == 0
                            || (tempEffects.averageTemperature < 130 && tempEffects.averageModifiedTemperatureDelta < 0)
                            || (tempEffects.averageTemperature >= 130 && tempEffects.averageModifiedTemperatureDelta > 0)) {
                        message.append(" and ");
                    } else {
                        message.append(" but ");
                    }

                    if (tempEffects.averageModifiedTemperatureDelta < -3) {
                        message.append("you are rapidly getting colder.");
                    } else if (tempEffects.averageModifiedTemperatureDelta < 0) {
                        message.append("you are getting colder.");
                    } else if (tempEffects.averageModifiedTemperatureDelta == 0) {
                        message.append("this is unlikely to change.");
                    } else if (tempEffects.averageModifiedTemperatureDelta <= 3) {
                        message.append("you are getting warmer.");
                    } else {
                        message.append("you are rapidly getting warmer.");
                    }
                }
                player.getCommunicator().sendNormalServerMessage("avg temp:"+tempEffects.averageTemperature+" "+message);
            }else {
                player.getCommunicator().sendNormalServerMessage("Temperature is currently turned off");
            }
            return true;
        }

        return false;
    }

    @Override
    public void init() {
        HookManager hookManager = HookManager.getInstance();
        hookManager.registerHook("com.wurmonline.server.players.Player", "poll", "()Z", new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler() {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
                        Player player = (Player) object;

                        if (enableTemperatureSurvival && !(player.hasSpellEffect((byte) 75) && newPlayerProtection)
                                && !(player.getPower() >= 2 && gmProtection) && !player.isDead() && player.secondsPlayed % 15.0F == 0.0F) {
                            // Fetches temperature effects for the polled player
                            TempEffects temperatureEffects = getTemperatureEffects(player);

                            // Cycles through body parts for the polled player and applies cooling/warming and frost wounds where appropriate
                            pollBodyPartTemperature(player, true, true, temperatureEffects);
                        }

                        return method.invoke(object, args);
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.players.Player", "setDeathEffects", "(ZII)V", new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler() {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
                        Player player = (Player) object;
                        Body body = player.getBody();

                        // Reset temperature of all body parts to very warm
                        for (byte y : bodyParts) {
                            body.getBodyPart(y).setTemperature((short)200);
                        }

                        return method.invoke(object, args);
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.behaviours.MethodsItems", "eat", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;F)Z",
            new InvocationHandlerFactory() {
                @Override
                public InvocationHandler createInvocationHandler () {
                    return new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Boolean result = (Boolean) method.invoke(proxy, args);

                            Action act = (Action) args[0];
                            Creature player = (Creature) args[1];
                            Item food = (Item) args[2];

                            // Eating hot food warms the player up
                            if (enableTemperatureSurvival && !result && player.isPlayer() && act.currentSecond() % 5 == 0 && food.getTemperature() > 1000) {
                                warmAllBodyParts((Player)player, (short)5);
                                player.getCommunicator().sendNormalServerMessage("The " + food.getName() + " warms you up.");
                                if (verboseLogging)
                                    logger.log(Level.INFO, player.getName() + " is warmed by eating some " + food.getName());
                            }
                            return result;
                        }
                    };
                }
        });

        hookManager.registerHook("com.wurmonline.server.behaviours.MethodsItems", "drink", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;Lcom/wurmonline/server/items/Item;F)Z",
            new InvocationHandlerFactory() {
                @Override
                public InvocationHandler createInvocationHandler () {
                    return new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Boolean result = (Boolean) method.invoke(proxy, args);

                            Action act = (Action) args[0];
                            Creature player = (Creature) args[1];
                            Item drink = (Item) args[2];

                            // Drinking hot drinks (or vodka) warms player up
                            if (enableTemperatureSurvival && !result && player.isPlayer() &&
                                    act.currentSecond() % 2 == 0 && (drink.getTemperature() > 600 || drink.getTemplateId() == 1231) ) {
                                warmAllBodyParts((Player)player, (short)5);
                                player.getCommunicator().sendNormalServerMessage("The " + drink.getName() + " warms you up.");
                                if (verboseLogging)
                                    logger.log(Level.INFO, player.getName() + " is warmed by drinking some " + drink.getName());
                            }

                            // Drinking low quality water causes disease
                            if (enableWaterDisease && !(player.hasSpellEffect((byte) 75) && newPlayerProtection) &&
                                    !(player.getPower() >= 2 && gmProtection) && !result && player.isPlayer() &&
                                    act.currentSecond() % 2 == 0 && drink.getTemplateId() == 128 && drink.getCurrentQualityLevel() < 100) {
                                player.setDisease((byte) (100 - drink.getCurrentQualityLevel()));
                                player.getCommunicator().sendNormalServerMessage("The " + drink.getName() + " tastes bad and you feel ill.", (byte)4);
                                if (verboseLogging)
                                    logger.log(Level.INFO, player.getName() + " contracts a disease by drinking some bad " + drink.getName());
                            }

                            return result;
                        }
                    };
                }
        });

        hookManager.registerHook("com.wurmonline.server.behaviours.MethodsItems", "drink", "(Lcom/wurmonline/server/creatures/Creature;IIIFLcom/wurmonline/server/behaviours/Action;)Z",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Boolean result = (Boolean) method.invoke(proxy, args);

                        Action act = (Action) args[5];
                        Creature player = (Creature) args[0];

                        // Drinking from the ground causes disease
                        if (enableWaterDisease && !(player.hasSpellEffect((byte) 75) && newPlayerProtection) &&
                                !(player.getPower() >= 2 && gmProtection) && !result && player.isPlayer() && act.currentSecond() % 2 == 0 ) {
                            byte randomByte =  (byte) Server.rand.nextInt(100);
                            byte diseaseAmount = (int) randomByte > (int) player.getDisease() ? randomByte : player.getDisease();
                            player.setDisease(diseaseAmount);
                            player.getCommunicator().sendNormalServerMessage("The water tastes bad and you feel ill.", (byte)4);
                            if (verboseLogging) logger.log(Level.INFO, player.getName() + " contracts a disease by drinking some bad water.");
                        }

                        return result;
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.bodys.Body", "createBodyParts", "()V",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Boolean result = (Boolean) method.invoke(proxy, args);

                        if (!enableTemperatureSurvival) return result;

                        // If it is cold, then the player logs in cold.
                        Body body = (Body)proxy;
                        try {
                            Player player = Players.getInstance().getPlayer(body.getOwnerId());
                            if (player != null) {

                                TempEffects tempEffects = getTemperatureEffects(player);
                                tempEffects = pollBodyPartTemperature(player, false, false, tempEffects);

                                if (tempEffects.averageModifiedTemperatureDelta < 0) {
                                    setTempAllBodyParts(body, (short) 90);
                                }
                            }
                            return result;
                        } catch (NoSuchPlayerException nspe) { return result; }
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.behaviours.MethodsItems", "fillContainer", "(Lcom/wurmonline/server/items/Item;Lcom/wurmonline/server/creatures/Creature;Z)V",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Boolean result = (Boolean) method.invoke(proxy, args);

                        if(enableWaterDisease) {
                            // Water from the ocean is low quality
                            Item targetItem = (Item) args[0];

                            if(targetItem.getTemplateId() != 608 && targetItem.getTemplateId() != 408 && targetItem.getTemplateId() != 635) {
                                for (Item contained : targetItem.getItems()) {
                                    if (((!contained.isFood()) && (!contained.isLiquid()))
                                            || ((contained.isLiquid()) && (contained.getTemplateId() != 128))) {
                                        return result;
                                    }
                                    if (contained.isLiquid()) {
                                        contained.setQualityLevel(Math.max(1, contained.getQualityLevel() - Server.rand.nextInt(10)));
                                    }
                                }
                            }
                        }

                        return result;
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.items.Item", "modTemp", "(Lcom/wurmonline/server/items/Item;IZ)V",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Item item = (Item) args[0];
                        Boolean result = (Boolean) method.invoke(proxy, args);

                        for (Item contained : item.getItems()) {
                            if (((!contained.isFood()) && (!contained.isLiquid()))
                                    || ((contained.isLiquid()) && (contained.getTemplateId() != 128))) {
                                return result;
                            }

                            // Boiled water becomes 100 QL
                            if (enableWaterDisease && contained.getTemperatureState(contained.getTemperature()) == (byte) 3 && contained.getCurrentQualityLevel() < 100) {
                                contained.setQualityLevel(100);
                            }
                        }

                        return result;
                    }
                };
            }
        });


        hookManager.registerHook("com.wurmonline.server.behaviours.TileBehaviour", "action", "(Lcom/wurmonline/server/behaviours/Action;Lcom/wurmonline/server/creatures/Creature;IIZISF)Z",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(enableWaterDisease) {
                            Creature creature = (Creature) args[1];
                            int tileX = (int) args[2];
                            int tileY = (int) args[3];
                            int tile = (int) args[5];
                            short actionType = (short) args[6];

                            Communicator communicator = creature.getCommunicator();

                            // Replaces behaviour for specified action types
                            switch (actionType) { // TODO
                                case 19: // Taste
                                    if (isWater(tile, tileX, tileY, creature.isOnSurface())){
                                        communicator.sendNormalServerMessage("The water tastes strange. It might need boiling.");
                                    }else{
                                        communicator.sendNormalServerMessage("The taste is very dry.");
                                    }
                                    return true;
                            }
                        }
                        return method.invoke(proxy, args);
                    }
                };
            }
        });

        hookManager.registerHook("com.wurmonline.server.zones.CropTilePoller", "checkForFarmGrowth", "(IIIBBLcom/wurmonline/mesh/MeshIO;Z)V",
            new InvocationHandlerFactory() {
            @Override
            public InvocationHandler createInvocationHandler () {
                return new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        int tileX = (int) args[1];
                        int tileY = (int) args[2];
                        byte type = (byte) args[3];
                        byte aData = (byte) args[4];

                        int crop = Crops.getCropNumber(type, aData);

                        if (enableCropSeasons && !isCropInSeason(crop, tileX, tileY)) {
                            return null;
                        }

                        return method.invoke(proxy, args);
                    }
                };
            }
        });
    }

    private boolean isCropInSeason(int crop, int tileX, int tileY) {
        double minGrowTemp;

        switch (crop) {
            case 6: // Potatoes
            case 9: // Garlic
            case 10: // Onions
                minGrowTemp = -1d;
                break;
            case 14: // Carrots
            case 15: // Cabbage
            case 17: // Sugar beet
            case 19: // Peas
                minGrowTemp = -0.5;
                break;
            case 4: // Corn
            case 5: // Pumpkins
            case 8: // Wemp
            case 13: // Strawberries
            case 16: // Tomatoes
            case 20: // Cucumber
                minGrowTemp = 0.5;
                break;
            case 7: // Cotton
            case 12: // Rice
                minGrowTemp = 1d;
                break;
            default: // Default growing season. In the south, all but winter. In the north, only during summer.
                minGrowTemp = 0d;
                break;
        }

        double simpleTemperature = getSimpleTemperature(tileX, tileY, true);
        if(verboseLogging)
            logger.log(Level.INFO, "crop: "+crop+" at temperature "+simpleTemperature+", min grow temp for this crop is: "+minGrowTemp);

        return simpleTemperature >= minGrowTemp;
    }

    private  boolean isWater(int tile, int tilex, int tiley, boolean surfaced) {
        if (surfaced) {
            for (int x = 0; x <= 1; ++x) {
                for (int y = 0; y <= 1; ++y) {
                    if (Tiles.decodeHeight(Server.surfaceMesh.getTile(tilex + x, tiley + y)) < 0) {
                        return true;
                    }
                }
            }
        } else {
            if (Tiles.isSolidCave(Tiles.decodeType(tile))) {
                return false;
            }
            for (int x = 0; x <= 1; ++x) {
                for (int y = 0; y <= 1; ++y) {
                    int tile2 = Server.caveMesh.getTile(tilex + x, tiley + y);
                    if (Tiles.decodeHeight(tile2) < 0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void warmAllBodyParts(Player player, short change) {
        Body body = player.getBody();

        for(byte y : bodyParts ) {
            try {
                Item bodyPart = body.getBodyPart(y);
                bodyPart.setTemperature((short)(bodyPart.getTemperature() + change));
            } catch (NoSpaceException nse) {
                logger.log(Level.WARNING, nse.getMessage());
            }
        }
    }

    private void setTempAllBodyParts(Body body, short temp) {
        for(byte b : bodyParts ) {
            try {
                Item bodyPart = body.getBodyPart(b);
                bodyPart.setTemperature(temp);
            } catch (NoSpaceException nse) {
                logger.log(Level.WARNING, nse.getMessage());
            }
        }
    }

    private TempEffects pollBodyPartTemperature(Player player, boolean applyTemperatureAndWounds, boolean warningMessages, TempEffects tempEffects) { // TODO: SG was here.
    try {
    String message = null;
    boolean urgentAlert = false;
    short totalTemperature = 0;
    double totalTemperatureDelta = 0;
    short countBodyParts = 0;

    Body body = player.getBody();

    for (byte y : bodyParts) {

    try {
    Item bodyPart = body.getBodyPart(y);
    short temperature = bodyPart.getTemperature();
    Item armour = null;
    double armourGeneralBonus = 0;
    double armourSwimBonus = 0;
    double armourWindBonus = 0;
    double armourRainBonus = 0;
    double armourEffects = 0;

    try{
    armour = player.getArmour(y);
    } catch (NoArmourException nae) {
    if (verboseLogging) logger.log(Level.INFO, nae.getMessage());
    }

    if(armour != null) {
    switch (armour.getMaterial()) {
    case 9:    // Steel
    case 11:   // Iron
    case 56:   // Adamantine
    case 57:   // Glimmersteel
    case 67:   // Seryll
    armourGeneralBonus = plateaffect;
    break;
    case 16:   //Leather (includes drake hide and dragon scale)
    armourGeneralBonus = leatheraffect;
    armourWindBonus = 0.5;
    armourRainBonus = 0.5;
    break;
    case 17:   // Cloth
    armourGeneralBonus = clothaffect;
    armourWindBonus = 0.25;
    break;
    case 69:   // Wool
    armourGeneralBonus = woolaffect;
    armourRainBonus = 0.25;
    armourSwimBonus = 1;
    break;
    }

    switch (armour.getTemplateId()) {
    case 960:   // Leather adventurer's hat
    armourRainBonus = 0.75;
    break;
    case 959:   // Brown bear helm
    armourGeneralBonus = 1.2;
    armourRainBonus = 0.25;
    armourWindBonus = 0;
    armourSwimBonus = 1;
    break;
    }

    if(armour.getTemplateId() >= furstartid && furendid >= armour.getTemplateId()){
    armourGeneralBonus = 1.2;
    armourRainBonus = 0.25;
    armourWindBonus = 0;
    armourSwimBonus = 1;

    }


    // Higher quality clothing gives better bonuses
    armourGeneralBonus *= armorfactor;
    armourGeneralBonus   = (armourGeneralBonus * 0.4) + (armourGeneralBonus * 0.6 * (armour.getCurrentQualityLevel()/100));
    armourRainBonus      = (armourRainBonus * 0.4) + (armourRainBonus * 0.6 * (armour.getCurrentQualityLevel()/100));
    armourWindBonus      = (armourWindBonus * 0.4) + (armourWindBonus * 0.6 * (armour.getCurrentQualityLevel()/100));
    armourSwimBonus      = (armourSwimBonus * 0.4) + (armourSwimBonus * 0.6 * (armour.getCurrentQualityLevel()/100));
    //if (verboseLogging) logger.log(Level.INFO,"gen armor "+armourGeneralBonus+"");
    if (verboseLogging) logger.log(Level.INFO, player.getName() + " - " + bodyPart.getName() + "(" + temperature + ") slot: " + armour.getName());
    }

    armourEffects = armourGeneralBonus + Math.min(0,(double)tempEffects.swimMod + armourSwimBonus) + Math.min(0,(double)tempEffects.rainMod + armourRainBonus) + Math.min(0, (double)tempEffects.windMod + armourWindBonus) + (double) tempEffects.tileMod;
    //if (verboseLogging) logger.log(Level.INFO,"armourEffects: "+armourEffects+"");
    // Apply temperature
    double doubleDelta = tempEffects.baseTemperatureDelta + armourEffects ;
    short temperatureDelta = (short) Math.round(doubleDelta);
    totalTemperatureDelta = totalTemperatureDelta + temperatureDelta;

    temperature = (short) Math.min(250, Math.max(0, Math.round(temperature + temperatureDelta)));

    if (verboseLogging) logger.log(Level.INFO, player.getName() + " - double delta: " + doubleDelta + " rounded to " + temperatureDelta);

    if (applyTemperatureAndWounds) bodyPart.setTemperature(temperature);
    totalTemperature+= temperature;
    countBodyParts++;

    // Display warning messages if player is very cold
    if (temperatureDelta < 0) {
    if (warningMessages) {
    if(temperature < 50) {
    if (message == null) {
    message = "You are very cold and should find warmth";
    urgentAlert = true;
    }
    }
    if(temperature > 150) {
    if (message == null) {
    message = "You are very warm and should cool down";
    urgentAlert = true;
    }
    }

    }
    } else {
    if (temperature < 50) {
    message = "You are warming up.";
    }
    }

    // Give the player some cold wounds if they are freezing and display a warning message
    if (applyTemperatureAndWounds) {
    if(temperature == 0) {
    if (Server.rand.nextInt(1000) > 750) {
    int dmg = Server.rand.nextInt(maxWoundSize);
    // Only apply wounds every other poll
    if (player.secondsPlayed % 30.0F == 0.0F) {
    player.addWoundOfType(null, (byte)8, y, false, 1.0F, false, dmg, 0.0F, 0.0F, false, false);
    message = "You took some damage, get warm now!";
    }
    if (warningMessages) {
    message = "You are freezing cold! Find warmth quickly!";
    urgentAlert = true;
    }
    }
    }
    if(temperature >= 200) {
    if (Server.rand.nextInt(1000) > 750) {
    int dmg = Server.rand.nextInt(maxWoundSize);
    // Only apply wounds every other poll
    if (player.secondsPlayed % 30.0F == 0.0F) {
    player.addWoundOfType(null, (byte)8, y, false, 1.0F, false, dmg, 0.0F, 0.0F, false, false);
    message = "You took some damage, get cooler now!";
    }
    if (warningMessages) {
    message = "You are boiling hot! Cool down quickly!";
    urgentAlert = true;
    }
    }
    }



    }
    } catch (NoSpaceException nse) {
    logger.log(Level.WARNING, nse.getMessage());
    }
    }
    // Send the message to the events tab
    spamfilter += 1;
    int timer = 8;
    if (urgentAlert)timer=4;
    if (spamfilter >= timer){
    spamfilter = 0;
    if (urgentAlert) {
    player.getCommunicator().sendNormalServerMessage(message, (byte)4);
    } else if (message != null) {
    player.getCommunicator().sendNormalServerMessage(message);
    }
    }
    // Calculate average temperature and temperature delta and return these values
    tempEffects.averageModifiedTemperatureDelta = (short)(totalTemperatureDelta/countBodyParts);
    tempEffects.averageTemperature = (short) (totalTemperature/countBodyParts);
    return tempEffects;
    } catch (Exception e) {
    logger.log(Level.WARNING, e.getMessage());
    return null;
    }
    }


    private double getSimpleTemperature(float tileX, float tileY, boolean onSurface) {

    double hour = (double)WurmCalendar.getHour();
    int day = (int)(WurmCalendar.currentTime % (long)29030400 / (long)86400);
    double starfall = (double)WurmCalendar.getStarfall() + ((double)day%28 / (double)28);

    // Approximation of seasonal heat differences
    // Produces number between -4 and 4
    double monthTempMod = 4 * Math.sin((starfall - 3) / 1.91);

    // Approximation of day/night heat differences
    // Produces number between -2 and 2
    double hourTempMod = 2 * Math.sin((hour - 6)/3.82);

    double simpleTemperature = monthTempMod + hourTempMod;

    // If northSouthMod is enabled, warmer in the south, colder in the north
    // -2 at north server border, +2 at south server border
    if (northSouthMode) simpleTemperature += (4 * ((double)tileY / (double)Server.surfaceMesh.getSize()) - 2);

    try {
    // Colder at very high altitudes
    // At sea level, no modifier. -1 for each 200 above sea level
    double altitudeMod =  (double) Zones.calculateHeight(tileX, tileY, onSurface) / (double) 200 * (double) -1;
    simpleTemperature+= altitudeMod;
    if (verboseLogging)  logger.log(Level.INFO, "month modifier: " + monthTempMod + ", hour modifier: " + hourTempMod + ", altitude modifier: " + altitudeMod + ", north/south modifier: " + (4 * ((double)tileY / (double)Server.surfaceMesh.getSize()) - 2));
    } catch (NoSuchZoneException nse) {
    logger.log(Level.WARNING, nse.getMessage());
    return 0;
    }

    return simpleTemperature;

    }

    private TempEffects getTemperatureEffects(Player player) {
    try {
    int tileX = player.getCurrentTile().getTileX();
    int tileY = player.getCurrentTile().getTileY();

    boolean isIndoors = player.getCurrentTile().getStructure() != null && player.getCurrentTile().getStructure().isFinished();
    boolean isInCave = !player.getCurrentTile().isOnSurface();
    boolean isUnderRoof = hasRoof(player.getCurrentTile());
    boolean isOnBoat = false;
    if (player.getVehicle() != (long)-10) {
    Vehicle vehicle = Vehicles.getVehicleForId(player.getVehicle());
    if (!vehicle.isCreature() && Items.getItem(player.getVehicle()).isBoat()) isOnBoat = true;
    }

    // Colder if strong wind or gale
    // Produces -1 or 0
    short windMod = !(isIndoors || isInCave) && Math.abs(Server.getWeather().getWindPower()) > 0.3 ? (short)-1 : 0;

    // Colder if swimming
    // Produces -2 or 0
    short swimMod = !isOnBoat && Zones.calculateHeight(player.getPosX(), player.getPosY(), player.isOnSurface()) < 0 ? (short)-3 : 0;

    // Colder if raining
    // Produces -1 or 0
    short rainMod = !(isInCave || isUnderRoof) && Server.getWeather().getRain() > 0.5 ? (short)-1 : 0;

    // Colder if on snow tile
    // Produces -1 or 0
    short tileMod = (player.isOnSurface() && Tiles.decodeType(Server.surfaceMesh.getTile(tileX, tileY)) == Tiles.Tile.TILE_SNOW.id) ? (short) -1 : 0;

    // Positive value indicates warming, negative value indicates cooling
    // Produces within a rough range of -10 to 5
    double baseTemperatureDelta = getSimpleTemperature(player.getPosX(), player.getPosY(), player.isOnSurface());

    // Make it warmer if hardMode is disabled
    baseTemperatureDelta += difficultySetting;
    if (verboseLogging) logger.log(Level.INFO, player.getName() + " has following modifiers... northSouth mod: " + (4 * ((double)tileY /  (double)Server.surfaceMesh.getSize()) - 2)  + ", windMod : " + windMod + ", swimMod: " + swimMod + ", rainMod: " + rainMod + ", tileMod: " + tileMod + ", hardMode: " + hardMode + ", in cave: " + isInCave +  ", indoors: " + isIndoors + ", roof: " + isUnderRoof);

    // Search nearby for heat sources
    int yy;
    int dist = 5; // area to check for heat sources
    int x1 = Zones.safeTileX(tileX - dist);
    int x2 = Zones.safeTileX(tileX + dist);
    int y1 = Zones.safeTileY(tileY - dist);
    int y2 = Zones.safeTileY(tileY + dist);

    short targetTemperature = 0;

    for (TilePos tPos : TilePos.areaIterator(x1, y1, x2, y2)) {
    int xx = tPos.x;
    yy = tPos.y;
    VolaTile t = Zones.getTileOrNull(xx, yy, player.isOnSurface());
    if ((t != null)) {
    for (Item item : t.getItems()) {
    short effectiveTemperature = 0;
    // Closer heat sources provide more heat. Uses pythagorean theorem to find distance, then uses inverse square law to determine intensity
    if (item.isOnFire()) {
    // effectiveTemperature = (short) (item.getTemperature() * (1 / Math.pow(Math.max(1, Math.sqrt(Math.pow(Math.abs(tileX - xx), 2) + Math.pow(Math.abs(tileY - yy), 2))),2)));
    float x = Math.abs(player.getTileX()- item.getTileX());
    float y = Math.abs(player.getTileY()- item.getTileY());
    float distancemodify = (float) ((float) 1-(((Math.max(x, y)+1)* 0.1)));
    effectiveTemperature = (short) (item.getTemperature() * distancemodify);
    //effectiveTemperature = item.isLight() ? (short)Math.round(effectiveTemperature/6) : effectiveTemperature;
    }
    // Only pay attention to the heat sources providing the biggest effect (i.e. heat sources do not stack)
    if (effectiveTemperature > targetTemperature) {
    targetTemperature = effectiveTemperature;
    }
    }
    }
    }
    // Add warming effect from heat source
    if(combatheat && player.isFighting())targetTemperature=combattemperature;
    baseTemperatureDelta += (short) Math.round(Math.min((double)7,(double) targetTemperature / (double)tempsourcefactor));
    //logger.log(Level.INFO,"best temp: "+targetTemperature );
    return new TempEffects(baseTemperatureDelta, swimMod, windMod, rainMod, tileMod);
    } catch (Exception e) {
    logger.log(Level.WARNING, e.getMessage());
    return null;
    }
    }

    private boolean hasRoof(VolaTile tile)
    {
    Floor[] floors = tile.getFloors();
    for (Floor floor : floors) {
    if (floor.isRoof()) {
    return true;
    }
    if (floor.isFloor() && floor.getFloorLevel() > 0) {
    return true;
    }
    }
    return false;
    }

    @Override
    public String getVersion() {
        return version;
    }
}