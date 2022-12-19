package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class AhsokaTanoRogueJedi extends CardImpl {
    public AhsokaTanoRogueJedi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.TOGRUTA);
        this.addSubType(SubType.JEDI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Double strike, vigilance, haste
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        //{1}{R/W}, {T}: Remove target attacking or blocking creature from combat.
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RemoveFromCombatTargetEffect(), new ManaCostsImpl<>("{1}{R/W}"));
        simpleActivatedAbility.addCost(new TapSourceCost());
        simpleActivatedAbility.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(simpleActivatedAbility);

        //{2}{R}{W}: Ahsoka Tano, Rogue Jedi gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}{W}")));
    }

    public AhsokaTanoRogueJedi(final AhsokaTanoRogueJedi card) {
        super(card);
    }

    @Override
    public AhsokaTanoRogueJedi copy() {
        return new AhsokaTanoRogueJedi(this);
    }
}
