package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.RebelToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class EzraBridger extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Rebel creature you control, that is not Ezra Bridger");

    static  {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.REBEL.getPredicate());
    }

    public EzraBridger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.JEDI);
        this.addSubType(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //When Ezra Bridger enters the battlefield, create a 1/1 white Rebel creature token.
        this.addAbility(new SimpleStaticAbility(new EntersBattlefieldEffect(new CreateTokenEffect(new RebelToken()))));

        //Whenever Ezra Bridger attacks, put a +1/+1 counter on another target rebel creature you control.
        AttacksTriggeredAbility attacksTriggeredAbility = new AttacksTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()));
        attacksTriggeredAbility.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(attacksTriggeredAbility);

        //Meditate {1}{G/W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{G/W}")));
    }

    public EzraBridger(final EzraBridger card) {
        super(card);
    }

    @Override
    public EzraBridger copy() {
        return new EzraBridger(this);
    }
}
