package mage.cards.e;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class EvadeTheEmpire extends CardImpl {
    public EvadeTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        //Target creature you control gets +0/+1 and gains hexproof until end of turn. That creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, 1, Duration.EndOfTurn)
                .setText("Target creature you control gets +0/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains hexproof until end of turn."));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new SimpleStaticAbility(
                new CantBeBlockedSourceEffect()), Duration.EndOfTurn)
                .setText("That creature can't be blocked this turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public EvadeTheEmpire(final EvadeTheEmpire card) {
        super(card);
    }

    @Override
    public EvadeTheEmpire copy() {
        return new EvadeTheEmpire(this);
    }
}
