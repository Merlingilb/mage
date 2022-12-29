package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.BoostCounter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class FeralLothCat extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public FeralLothCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.addSubType(SubType.CAT);
        this.addSubType(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Feral Loth-Cat must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        //When Feral Loth-Cat dies, put a +1/+1 counter on another target creature you control.
        DiesSourceTriggeredAbility diesSourceTriggeredAbility = new DiesSourceTriggeredAbility(
                new AddCountersTargetEffect(new BoostCounter(1, 1)));
        diesSourceTriggeredAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(diesSourceTriggeredAbility);
    }

    public FeralLothCat(final FeralLothCat card) {
        super(card);
    }

    @Override
    public FeralLothCat copy() {
        return new FeralLothCat(this);
    }
}
