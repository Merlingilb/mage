package mage.cards.j;

import mage.ApprovingObject;
import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.EndOfStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.builder.common.SimpleActivatedAbilityManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.*;

import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class JeganthaTheWellspring extends CardImpl {

    public JeganthaTheWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Companion — No card in your starting deck has more than one of the same mana symbol in its mana cost.
        this.addAbility(new CompanionAbility(JeganthaTheWellspringCompanionCondition.instance));

        // {T}: Add {W}{U}{B}{R}{G}. This mana can't be spent to pay generic mana costs.
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.PURE_BLACK_MANA.createInstance()).setText("add a black,"),
                new TapSourceCost());
        simpleActivatedAbility.addEffect(new AddCountersSourceEffect(CounterType.PURE_BLUE_MANA.createInstance())
                .setText("blue,"));
        simpleActivatedAbility.addEffect(new AddCountersSourceEffect(CounterType.PURE_GREEN_MANA.createInstance())
                .setText("green,"));
        simpleActivatedAbility.addEffect(new AddCountersSourceEffect(CounterType.PURE_RED_MANA.createInstance())
                .setText("red,"));
        simpleActivatedAbility.addEffect(new AddCountersSourceEffect(CounterType.PURE_WHITE_MANA.createInstance())
                .setText("and white mana token,"));
        this.addAbility(simpleActivatedAbility);
        EndOfStepDelayedTriggeredAbility endOfStepDelayedTriggeredAbility = new EndOfStepDelayedTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.PURE_BLACK_MANA.createInstance()), null, false);
        endOfStepDelayedTriggeredAbility.addEffect(new RemoveCounterSourceEffect(CounterType.PURE_BLUE_MANA.createInstance()));
        endOfStepDelayedTriggeredAbility.addEffect(new RemoveCounterSourceEffect(CounterType.PURE_GREEN_MANA.createInstance()));
        endOfStepDelayedTriggeredAbility.addEffect(new RemoveCounterSourceEffect(CounterType.PURE_RED_MANA.createInstance()));
        endOfStepDelayedTriggeredAbility.addEffect(new RemoveCounterSourceEffect(CounterType.PURE_WHITE_MANA.createInstance()));
        this.addAbility(endOfStepDelayedTriggeredAbility);

        this.addAbility(new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new BasicManaEffect(
                new Mana(ManaType.BLACK)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
                CounterType.PURE_BLACK_MANA), new JeganthaBlackManaCondition()));
        //this.addAbility(new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(
        //        new Mana(ManaType.BLACK)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
        //        CounterType.PURE_BLACK_MANA), new JeganthaBlackManaCondition()));
        //this.addAbility(new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(
        //        new Mana(ManaType.BLUE)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
        //        CounterType.PURE_BLUE_MANA), new JeganthaBlueManaCondition()));
        //this.addAbility(new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(
        //        new Mana(ManaType.GREEN)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
        //        CounterType.PURE_GREEN_MANA), new JeganthaGreenManaCondition()));
        //this.addAbility(new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(
        //        new Mana(ManaType.RED)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
        //        CounterType.PURE_RED_MANA), new JeganthaRedManaCondition()));
        //this.addAbility(new ActivateIfConditionManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(
        //        new Mana(ManaType.WHITE)), new RemoveCounterCost(new TargetControlledCreaturePermanent(),
        //        CounterType.PURE_WHITE_MANA), new JeganthaWhiteManaCondition()));
        //this.addAbility(new ConditionalColoredManaAbility(
        //        new TapSourceCost(), new Mana(1, 1, 1, 1, 1, 0, 0, 0), new JeganthaManaBuilder()
        //));
    }

    private JeganthaTheWellspring(final JeganthaTheWellspring card) {
        super(card);
    }

    @Override
    public JeganthaTheWellspring copy() {
        return new JeganthaTheWellspring(this);
    }
}

enum JeganthaTheWellspringCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "No card in your starting deck has more than one of the same mana symbol in its mana cost.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        return deck.stream().noneMatch(JeganthaTheWellspringCompanionCondition::checkCard);
    }

    private static boolean checkCard(Card card) {
        Map<String, Integer> symbolMap = new HashMap();
        return card.getManaCostSymbols()
                .stream()
                .anyMatch(s -> symbolMap.compute(
                s, (str, i) -> (i == null) ? 1 : i + 1
        ) > 1);
    }
}

class JeganthaManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new JeganthaConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to pay generic mana costs";
    }
}

class JeganthaConditionalMana extends ConditionalMana {

    JeganthaConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to pay generic mana costs";
        addCondition(new JeganthaManaCondition());
    }
}

class JeganthaManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        // TODO find a better method.  this one forces the user to pay off the generic mana before continuing.
        return source.getManaCostsToPay().getUnpaid().getMana().getGeneric() == 0;
    }
}

class JeganthaBlackManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return source.getManaCostsToPay().getUnpaid().getMana().getBlack() > 0;
    }
}

class JeganthaBlueManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return source.getManaCostsToPay().getUnpaid().getMana().getBlue() > 0;
    }
}

class JeganthaGreenManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return source.getManaCostsToPay().getUnpaid().getMana().getGreen() > 0;
    }
}

class JeganthaRedManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return source.getManaCostsToPay().getUnpaid().getMana().getRed() > 0;
    }
}

class JeganthaWhiteManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return source.getManaCostsToPay().getUnpaid().getMana().getWhite() > 0;
    }
}
