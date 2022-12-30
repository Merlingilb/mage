package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ShuffleLibraryAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class FulcrumIntelligence extends CardImpl {
    public FulcrumIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        //Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        //You may look at the top card of enchanted player's library at any time.
        this.addAbility(new SimpleActivatedAbility(new FulcrumIntelligenceEffect(auraTarget), new ManaCostsImpl<>("")));

        //Sacrifice Fulcrum Intelligence: Each player shuffles their library.
        this.addAbility(new SimpleActivatedAbility(new ShuffleLibraryAllEffect(), new SacrificeSourceCost()));
    }

    public FulcrumIntelligence(final FulcrumIntelligence card) {
        super(card);
    }

    @Override
    public FulcrumIntelligence copy() {
        return new FulcrumIntelligence(this);
    }
}

class FulcrumIntelligenceEffect extends OneShotEffect {

    TargetPlayer auraTarget;

    public FulcrumIntelligenceEffect(TargetPlayer auraTarget) {
        super(Outcome.Benefit);
        this.auraTarget = auraTarget;
        this.staticText = "You may look at the top card of enchanted player's library at any time.";
    }

    public FulcrumIntelligenceEffect(final FulcrumIntelligenceEffect effect) {
        super(effect);
        this.auraTarget = effect.auraTarget;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player enchantedPlayer = game.getPlayer(game.getPermanent(source.getSourceId()).getAttachedTo());
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Card card = enchantedPlayer.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        sourcePlayer.lookAtCards(String.format("%s: top card of %s", card.getName(), enchantedPlayer.getName()), card,
                game);
        return true;
    }

    @Override
    public FulcrumIntelligenceEffect copy() {
        return new FulcrumIntelligenceEffect(this);
    }
}