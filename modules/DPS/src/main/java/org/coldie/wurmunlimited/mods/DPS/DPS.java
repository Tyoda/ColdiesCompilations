package org.coldie.wurmunlimited.mods.DPS;

import com.wurmonline.server.DbConnector;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.utils.DbUtilities;
import javassist.*;
import javassist.bytecode.Descriptor;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DPS implements WurmServerMod, Configurable, Initable, PlayerMessageListener, PlayerLoginListener, ServerStartedListener, Versioned {
	public static final Logger logger = Logger.getLogger(DPS.class.getName());

	public static final String version = "ty1.0";
   
	static double damage = 0;
	static int minid = 42; // black bear
	static int maxid = 42;
	public void dpshook(){
		try {
			ClassPool classPool = HookManager.getInstance().getClassPool();

			//public static boolean addWound(@Nullable Creature performer, Creature defender,
			//byte type, int pos, double damage, float armourMod, String attString,
			//@Nullable Battle battle, float infection, float poison, boolean archery,
			//boolean alreadyCalculatedResist, boolean noMinimumDamage, boolean spell)

			String descriptor = Descriptor.ofMethod(CtPrimitiveType.booleanType, new CtClass[] {
					classPool.get("com.wurmonline.server.creatures.Creature"),
					classPool.get("com.wurmonline.server.creatures.Creature"),
					CtPrimitiveType.byteType,
					CtPrimitiveType.intType,
					CtPrimitiveType.doubleType,
					CtPrimitiveType.floatType,
					classPool.get("java.lang.String"),
					classPool.get("com.wurmonline.server.combat.Battle"),
					CtPrimitiveType.floatType,
					CtPrimitiveType.floatType,
					CtPrimitiveType.booleanType,
					CtPrimitiveType.booleanType,
					CtPrimitiveType.booleanType,
					CtPrimitiveType.booleanType
			});
			HookManager.getInstance().registerHook("com.wurmonline.server.combat.CombatEngine", "addWound", descriptor, new InvocationHandlerFactory(){
				@Override
				public InvocationHandler createInvocationHandler(){
					return new InvocationHandler(){
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							//0 performer, 1 defender, 2 type, 3 pos,
							//4 damage, 5 armourMod,
							Creature performer = (Creature) args[0];
							//Creature defender = (Creature) args[1];
							float armorMod = (float) args[5];
							Long startTime;
							if (performer instanceof Player && performer.getTemplate().getCreatureAI() == null) {
								//if (defender.isUnique()) {
								performer.getCommunicator().sendCombatNormalMessage("attacking a unique");
								args[13] = true;
								//}
								damage = Math.floor((double) args[4]);
								long mDamage =  (long) Math.floor(damage * armorMod);
								startTime = getTime(performer.getName());

								if (startTime != 0){
									Connection dbcon = null;
									PreparedStatement ps = null;
									try {
										dbcon = ModSupportDb.getModSupportDb();
										ps = dbcon.prepareStatement("UPDATE DPS SET TotalDamage=TotalDamage + ? WHERE name=?");
										ps.setLong(1, mDamage);
										ps.setString(2, performer.getName());
										ps.executeUpdate();
									} catch (SQLException e) { throw new RuntimeException(e); }
									finally{
										DbUtilities.closeDatabaseObjects(ps, null);
										DbConnector.returnConnection(dbcon);
									}

									Long DPS = calcDPS(performer.getName());

									performer.getCommunicator().sendCombatNormalMessage("DPS="+DPS+";you did "+mDamage+" damage, which is "+damage+" * "+armorMod);
								}
							}
							return method.invoke(proxy, args);
						}
					};
				}
			});
		} catch (Exception e) { throw new HookException(e); }
	}

	@Override
	public void onServerStarted() {
		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		try {
			if (!ModSupportDb.hasTable(dbcon, "DPS")) {
				String sql = "CREATE TABLE DPS (name VARCHAR(30) NOT NULL DEFAULT 'Unknown',"
						+ " TotalDamage LONG NOT NULL DEFAULT 0,"
						+ " StartTime LONG NOT NULL DEFAULT 0)";
				ps = dbcon.prepareStatement(sql);
				ps.execute();
			}
		} catch (SQLException e) { throw new RuntimeException(e); }
		finally{
			DbUtilities.closeDatabaseObjects(ps, null);
			DbConnector.returnConnection(dbcon);
		}
	}

	public Long calcDPS(String performer){
		long DPS;
		Connection dbcon = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			dbcon = ModSupportDb.getModSupportDb();
			ps = dbcon.prepareStatement("SELECT * FROM DPS WHERE name=?");
			ps.setString(1, performer);
			rs = ps.executeQuery();

			long totalDamage = rs.getLong("TotalDamage");
			long startTime = rs.getLong("StartTime");
			long time = (System.currentTimeMillis() - startTime) / 1000;
			if (time == 0) time = 1;
			DPS = totalDamage / time;
		}
		catch (SQLException e) { throw new RuntimeException(e); }
		finally{
			DbUtilities.closeDatabaseObjects(ps, rs);
			DbConnector.returnConnection(dbcon);
		}

		return DPS;
	}
	
	@Override
	public void configure(Properties arg0) {
		minid = Integer.parseInt(arg0.getProperty("minid", Float.toString(minid)));
		maxid = Integer.parseInt(arg0.getProperty("maxid", Float.toString(maxid)));
		logger.info("min: "+minid+" max: "+maxid);
	}

	@Override
	public void init() {
		dpshook();
		deathhook();
	}

	public boolean onPlayerMessage(Communicator communicator, String message) {
		if ((message != null) && (message.startsWith("/TEST"))) {
			float tester = Server.rand.nextFloat();
			float result =  5.0F + tester * 5.0F;
			communicator.sendNormalServerMessage(" test: "+tester +" result:  "+ result);
			return true;
		}

		if ((message != null) && (message.startsWith("/DPS"))) {
			Long startTime = getTime(communicator.getPlayer().getName());
			if (startTime != 0){
				//stop dps
				communicator.sendNormalServerMessage("Turning off DPS calculations, end DPS:"+calcDPS(communicator.getPlayer().getName()));
				resetDPS(communicator.getPlayer().getName());
			}else{
				communicator.sendNormalServerMessage("You did /DPS omg you damage crazy fool, MORE DOTS.");
				Connection dbcon = null;
				PreparedStatement ps = null;
				try {
					dbcon = ModSupportDb.getModSupportDb();
					ps = dbcon.prepareStatement("UPDATE DPS  SET StartTime=?, TotalDamage=0 WHERE name=?");
					ps.setLong(1, System.currentTimeMillis());
					ps.setString(2, communicator.getPlayer().getName());
					ps.executeUpdate();
				}
				catch (SQLException e) { throw new RuntimeException(e); }
				finally{
					DbUtilities.closeDatabaseObjects(ps, null);
					DbConnector.returnConnection(dbcon);
				}
			}
			return true;
		}
		return false;
	  }

	public void resetDPS(String p){
		  Connection dbcon = null;
	      PreparedStatement ps = null;
	      try
	      {
		      dbcon = ModSupportDb.getModSupportDb();
		      ps = dbcon.prepareStatement("UPDATE DPS SET StartTime=0, TotalDamage=0 WHERE name=?");
			  ps.setString(1, p);
		      ps.executeUpdate();
	      } catch (SQLException e) { throw new RuntimeException(e); }
		  finally{
			  DbUtilities.closeDatabaseObjects(ps, null);
			  DbConnector.returnConnection(dbcon);
		  }
	}

	public Long getTime(String performer){
			Connection dbcon = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			long startTime;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("SELECT StartTime FROM DPS WHERE name=?");
				ps.setString(1, performer);
				rs = ps.executeQuery();
				startTime = rs.getLong("StartTime");
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally{
				DbUtilities.closeDatabaseObjects(ps, rs);
				DbConnector.returnConnection(dbcon);
			}

	      	return startTime;
	}

	public void deathhook(){
		try {
            CtClass ctc = HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.CreatureStatus");
            ctc.getDeclaredMethod("modifyWounds").instrument(new ExprEditor(){
                 public void edit(MethodCall m) throws CannotCompileException {
                	 if (m.getMethodName().equals("getPower")) {
		                 m.replace("if(this.statusHolder.getPower() >= 3 || (this.statusHolder.getTemplate().getTemplateId() >= "+minid+" && "+maxid+" >= this.statusHolder.getTemplate().getTemplateId())){$_ = 4;};");
                	}
                }

            });
        }
        catch (CannotCompileException | NotFoundException e) {
            logger.log(Level.SEVERE, "Failed to apply DPS interception", e);
        }

	}

	public void onPlayerLogin(Player p) {
		boolean founded = false;
		Connection dbcon = ModSupportDb.getModSupportDb();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = dbcon.prepareStatement("SELECT name FROM DPS");
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("name").equals(p.getName())){
					founded = true;
				}
			}
		}
		catch (SQLException e) {
		  throw new RuntimeException(e);
		}finally{
			DbUtilities.closeDatabaseObjects(ps, rs);
			DbConnector.returnConnection(dbcon);
		}

		if (!founded){
			dbcon = ModSupportDb.getModSupportDb();
			ps = null;
			try {
				dbcon = ModSupportDb.getModSupportDb();
				ps = dbcon.prepareStatement("INSERT INTO DPS (name) VALUES(\""+p.getName()+"\")");
				ps.executeUpdate();
			} catch (SQLException e) { throw new RuntimeException(e); }
			finally{
				DbUtilities.closeDatabaseObjects(ps, null);
				DbConnector.returnConnection(dbcon);
			}
		}else{
			//player logged in so reset values.
			resetDPS(p.getName());
		}

	}

	public String getVersion() {
		return version;
	}
}