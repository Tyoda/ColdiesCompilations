package org.coldie.wurmunlimited.mods.paintings;

import com.wurmonline.server.items.Item;
import com.wurmonline.shared.util.MaterialUtilities;

import org.gotti.wurmunlimited.modsupport.items.ModelNameProvider;

public class paintingsModelProvider implements ModelNameProvider {
    private String baseModel;

	public paintingsModelProvider(String newBaseModel) {
        this.baseModel = newBaseModel;
    }

    @Override
    public String getModelName(Item item) {
    	if (item.getTemplateId() == paintings.targetid+41) {
    		baseModel = "model.furniture.framedportraits.";
    	}else if (item.getTemplateId() == paintings.targetid+40) {
    		baseModel = "model.furniture.framedlandscape.";
    	}
    	
    	StringBuilder sb = new StringBuilder(baseModel);
    	
    	if (item.getTemplateId() == paintings.targetid+41 || item.getTemplateId() == paintings.targetid+40) {
			if(item.getData1() == -1) {
				item.setData1(1);
				sb.append("1.");
			}else {
				sb.append(item.getData1()).append('.');
			}
		}
    	sb.append(MaterialUtilities.getMaterialString(item.getMaterial()));
        return sb.toString();
    }
}