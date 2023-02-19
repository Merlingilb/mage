package mage.cards.r;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public class RuthlessPredation extends CardImpl {
    public RuthlessPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        //Target creature you control gets +1/+2 until end of turn. It fights target creature you don’t control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new FightTargetsEffect(true).setText("It fights target creature " +
                "you don't control. <i>(Each deals damage equal to its power to the other.)</i>"));
        TargetCreaturePermanent target = new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private RuthlessPredation(final RuthlessPredation card) {
        super(card);
    }

    @Override
    public RuthlessPredation copy() {
        return new RuthlessPredation(this);
    }
}
