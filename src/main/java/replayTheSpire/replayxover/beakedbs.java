package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked.RavenHex;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.beaked.WingsOfSteel;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses.CompoundingHeadache;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import beaked.cards.AbstractWitherCard;
import replayTheSpire.ReplayTheSpireMod;

public class beakedbs {
	static void AddAndUnlockCard(AbstractCard c)
	{
		BaseMod.addCard(c);
		UnlockTracker.unlockCard(c.cardID);
	}
	public static void addBeakedCards() {
		AddAndUnlockCard(new RavenHex());
		AddAndUnlockCard(new WingsOfSteel());
		AddAndUnlockCard(new CompoundingHeadache());
	}
	public static boolean chaosCheck(AbstractCard c) {
		return (c instanceof AbstractWitherCard && ((AbstractWitherCard)c).linkWitherAmountToMagicNumber);
	}
}
