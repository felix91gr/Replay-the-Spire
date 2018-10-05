package com.megacrit.cardcrawl.cards.replayxover.black;

import infinitespire.abstracts.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.ChaosPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;

import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.actions.unique.MultiExhumeAction;

public class ChaosVortex extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:Chaos Vortex";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 0;
    private static final String DESCRIPTION;
    
    public ChaosVortex() {
        super(ID, NAME, "cards/replay/replayBetaPowerDark.png", COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardTarget.SELF);
        this.baseBlock = 10;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        GraveField.grave.set(this, true);
    }
    
    public AbstractCard makeCopy() {
        return new ChaosVortex();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChaosPower(p, this.magicNumber), this.magicNumber));
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ChaosVortex.cardStrings.NAME;
        DESCRIPTION = ChaosVortex.cardStrings.DESCRIPTION;
    }
}
