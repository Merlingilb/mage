package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class CT7567CaptainRex extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures you control");

    static  {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.TROOPER.getPredicate());
    }

    public CT7567CaptainRex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.TROOPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Trooper creatures you control have "When this creature enters the battlefield, you may draw a card and gain 1 life."
        EntersBattlefieldTriggeredAbility entersBattlefieldTriggeredAbility =
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), true);
        entersBattlefieldTriggeredAbility.addEffect(new GainLifeEffect(1).setText("and gain 1 life"));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(entersBattlefieldTriggeredAbility,
                Duration.WhileOnBattlefield, filter,
                "Trooper creatures you control have \"When this creature enters the battlefield, you may draw a card and gain 1 life.\"")));
    }

    public CT7567CaptainRex(final CT7567CaptainRex card) {
        super(card);
    }

    @Override
    public CT7567CaptainRex copy() {
        return new CT7567CaptainRex(this);
    }
}
