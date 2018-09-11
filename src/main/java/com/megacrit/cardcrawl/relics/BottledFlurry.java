package com.megacrit.cardcrawl.relics;

import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.FlurryBottleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomBottleRelic;
import replayTheSpire.patches.BottlePatches;

public class BottledFlurry extends AbstractRelic implements CustomBottleRelic
{
    public static final String ID = "ReplayTheSpireMod:Bottled Flurry";
    private boolean cardSelected;
    public AbstractCard card;
    
    public BottledFlurry() {
        super(ID, "bottledFlurry.png", RelicTier.UNCOMMON, LandingSound.CLINK);
        this.cardSelected = true;
        this.card = null;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public AbstractCard getCard() {
        return this.card.makeCopy();
    }
    
    @Override
    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, this.DESCRIPTIONS[1] + this.name + ".", false, false, false, false);
    }
    
    @Override
    public void onUnequip() {
        if (this.card != null) {
            final AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null) {
            	BottlePatches.BottleFields.inBottleFlurry.set(cardInDeck, false);
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottlePatches.BottleFields.inBottleFlurry.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }
    
    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void onPlayerEndTurn() {
    	AbstractDungeon.actionManager.addToTop(new FlurryBottleAction(this));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BottledFlurry();
    }

	@Override
	public Predicate<AbstractCard> isOnCard() {
		return BottlePatches.BottleFields.inBottleFlurry::get;
	}
}