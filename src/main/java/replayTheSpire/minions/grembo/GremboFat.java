package replayTheSpire.minions.grembo;

import kobting.friendlyminions.monsters.*;
import basemod.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.monsters.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class GremboFat extends AbstractFriendlyMonster
{
	private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID;
    private AbstractMonster target;
    private int baseDamage;
    private int heal;
    private int strength;
    
    public GremboFat() {
        super(GremboFat.NAME, GremboFat.ID, 14, -8.0f, 10.0f, 230.0f, 240.0f, "images/monsters/exord/cook.png", -700.0f, 0.0f);
        this.img = null;
        this.loadAnimation("images/monsters/theBottom/fatGremlin/skeleton.atlas", "images/monsters/theBottom/fatGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 4;
        this.heal = 6;
        this.strength = 2;
        addMoves();
    }
    private void addMoves(){
        this.moves.addMove(new MinionMove(MOVES[0], this, new Texture("images/monsters/atk_bubble.png"), "Deal " + this.baseDamage + " damage and apply 1 Weak.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this,this.baseDamage,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this, new WeakPower(target, 1, true), 1));
        }));
        this.moves.addMove(new MinionMove("Eat", this, new Texture("images/monsters/atk_bubble.png"), this.name + " heals " + this.heal + " HP and gains " + this.strength + " for 1 round.", () -> {
            AbstractDungeon.actionManager.addToBottom(new HealAction(this,this, this.heal));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strength), this.strength));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DelayedLoseStrengthPower(this, this.strength), this.strength));
        }));
    }
    static {
    	monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinFat");
    	NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
