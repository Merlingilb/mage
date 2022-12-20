package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class DemolitionsTrooper extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Trooper creatures you control");

    static  {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.TROOPER.getPredicate());
    }

    public DemolitionsTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.TROOPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Trooper creatures you control have "{2}{R}, {T}: Sacrifice this creature: Destroy target artifact."
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{R}"));
        simpleActivatedAbility.addCost(new TapSourceCost());
        simpleActivatedAbility.addCost(new SacrificeSourceCost());
        simpleActivatedAbility.addTarget(new TargetArtifactPermanent());
        SimpleStaticAbility simpleStaticAbility = new SimpleStaticAbility(new GainAbilityAllEffect(
                simpleActivatedAbility, Duration.WhileOnBattlefield, filter));
        this.addAbility(simpleStaticAbility);
    }

    public DemolitionsTrooper(final DemolitionsTrooper card) {
        super(card);
    }

    @Override
    public DemolitionsTrooper copy() {
        return new DemolitionsTrooper(this);
    }
}
