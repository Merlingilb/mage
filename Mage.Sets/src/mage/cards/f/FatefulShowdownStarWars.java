package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class FatefulShowdownStarWars extends CardImpl {
    public FatefulShowdownStarWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        //Players can't cast spells during combat.
        this.addAbility(new SimpleStaticAbility(new FatefulShowdownStarWarsEffect()));

        //{1}{R}: Target attacking creature gains first strike until end of turn.
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        simpleActivatedAbility.addTarget(new TargetAttackingCreature());
        this.addAbility(simpleActivatedAbility);
    }

    public FatefulShowdownStarWars(final FatefulShowdownStarWars card) {
        super(card);
    }

    @Override
    public FatefulShowdownStarWars copy() {
        return new FatefulShowdownStarWars(this);
    }
}

class FatefulShowdownStarWarsEffect extends ContinuousRuleModifyingEffectImpl {

    public FatefulShowdownStarWarsEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Players can't cast spells during combat";
    }

    public FatefulShowdownStarWarsEffect(final FatefulShowdownStarWarsEffect effect) {
        super(effect);
    }

    @Override
    public FatefulShowdownStarWarsEffect copy() {
        return new FatefulShowdownStarWarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getPhase().getType() == TurnPhase.COMBAT;
    }
}
