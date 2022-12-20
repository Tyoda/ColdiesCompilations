package org.coldie.wurmunlimited.mods.LTdebuff;

import com.wurmonline.server.bodys.Wound;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LTdebuffReplace {
	public static final Logger logger = Logger.getLogger(LTdebuff.class.getName());
	
	static public double DoStuffs(Wound targetWound, double toHeal) {
		double heal = Math.min(targetWound.getSeverity(), (toHeal/LTdebuff.LTpower));
		if(LTdebuff.debug)
			logger.log(Level.INFO, "LT power:"+(toHeal/LTdebuff.LTpower)+" Wound severity:"+targetWound.getSeverity());
		return heal;
	}	
	
}