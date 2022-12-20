package com.wurmonline.server.questions;

import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import net.coldie.tools.BmlForm;
import org.coldie.wurmunlimited.mods.paintings.paintings;

import java.util.Properties;


public class changeportraitquestion extends Question {
	private boolean properlySent = false;
	private int currentPage;
	private final Creature performer;
	private final long target;
	private Item item = null;

	// boolean bought = false;

	public changeportraitquestion(Creature aResponder, String aTitle, String aQuestion, long aTarget, int page) {
		super(aResponder, aTitle, aQuestion, 79, aTarget);
		currentPage = page;
		performer = aResponder;
		target = aTarget;
	}

	int getnextimagenumber(int current){
		if (current > paintings.portraitnumber)
			current = 1;
		return current;
	}

	int getpreviousimagenumber(int current){
		if (current < 1)
			current = paintings.portraitnumber;
		return current;
	}

	public void answer(Properties answer) {
		if (!properlySent) return;


		// check drop down and accepted
		boolean accepted = (answer.containsKey("accept")) && (answer.get("accept") == "true");
		boolean previous = (answer.containsKey("previous")) && (answer.get("previous") == "true");
		boolean next = (answer.containsKey("next")) && (answer.get("next") == "true");

		if (accepted) {
			VolaTile vt = Zones.getOrCreateTile(item.getTilePos(), item.isOnSurface());
			vt.makeInvisible(item);
			item.setData1(currentPage);
			vt.makeVisible(item);
		}
		else if(previous) {
			currentPage = currentPage - 1;
			currentPage = getpreviousimagenumber(currentPage);
			try {
				changeportraitquestion aq = new changeportraitquestion(
						performer,
						"Change Image",
						"Which image would you like to use?",
						target, currentPage
				);

				aq.sendQuestion();
			} catch (Exception e) { logger.warning(e.toString()); }
		}
		else if(next) {
			currentPage = currentPage + 1;
			currentPage = getnextimagenumber(currentPage);
			try {
				changeportraitquestion aq = new changeportraitquestion(
						performer,
						"Change Image",
						"Which image would you like to use?",
						target, currentPage
				);
				aq.sendQuestion();
			} catch (Exception e) { logger.warning(e.toString()); }
		}
	}

	public void sendQuestion() {
		properlySent = true;
		try {
			item = Items.getItem(getTarget());
		} catch (NoSuchItemException | NumberFormatException e) { logger.warning(e.toString()); }

		BmlForm f = new BmlForm("");
		f.addHidden("id", id+"");
		f.addBoldText("                                    "+getQuestion());
		f.addText("\n ");
		f.addText("                                          This is image number "+ currentPage +" out of "+paintings.portraitnumber);
		f.addRaw("table{rows='1';cols='2';");
		f.addRaw("text{text=' '};");
		f.addRaw("image{src='img.framedportraits."+ currentPage +"';size='300,400'}}");

		//stop here
		f.addText("\n ");
		f.addRaw("table{rows='1';cols='6';");
		f.addRaw("text{text=''};");
		f.addRaw("button{text='<- Image "+getpreviousimagenumber(currentPage -1)+"';id='previous'};");
		f.addRaw("text{text=''};");
		f.addRaw("button{text='Choose Image';id='accept'};");
		f.addRaw("text{text=''};");
		f.addRaw("button{text='Image "+getnextimagenumber(currentPage +1)+" ->';id='next'};");
		f.addRaw("}");

		getResponder().getCommunicator().sendBml(
				450,
				600,
				true,
				true,
				f.toString(),
				200,
				200,
				200,
				title
		);
	}
}