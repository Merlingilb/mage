package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ConvokedSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Susucre
 */
public final class LethalScheme extends CardImpl {

    public LethalScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        // Each creature that convoked Lethal Scheme connives.
        this.getSpellAbility().addEffect(new LethalSchemeEffect());
    }

    private LethalScheme(final LethalScheme card) {
        super(card);
    }

    @Override
    public LethalScheme copy() {
        return new LethalScheme(this);
    }
}

// Based loosely on "Venerated Loxodon" and "Change of Plans"
class LethalSchemeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(ConvokedSourcePredicate.SPELL);
    }

    public LethalSchemeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each creature that convoked Lethal Scheme connives.";
    }

    public LethalSchemeEffect(final LethalSchemeEffect effect) {
        super(effect);
    }

    @Override
    public LethalSchemeEffect copy() {
        return new LethalSchemeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<AbstractMap.SimpleEntry<UUID, Permanent>> playerPermanentsPairs =
                game.getBattlefield()
                        .getActivePermanents(filter, source.getControllerId(), source, game)
                        .stream()
                        .map(permanent -> new AbstractMap.SimpleEntry<>(permanent.getControllerId(), permanent))
                        .collect(Collectors.toSet());

        Map<Player, Set<Permanent>> permanentsPerPlayer = new HashMap<>();

        playerPermanentsPairs.forEach(pair -> {
            Player player = game.getPlayer(pair.getKey());
            if (!permanentsPerPlayer.containsKey(player)) {
                permanentsPerPlayer.put(player, new HashSet<>());
            }
            permanentsPerPlayer.get(player).add(pair.getValue());
        });

        if (playerPermanentsPairs.isEmpty()) {
            return false;
        }

        for (Player player : game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .filter(permanentsPerPlayer::containsKey)
                .collect(Collectors.toList())) {

            Set<Permanent> permanents = permanentsPerPlayer.get(player);

            while (permanents.size() > 0) {
                Choice choiceForThisLoop = new ChoiceImpl(true);
                choiceForThisLoop.setMessage("Choose next connive to resolve.");

                permanents.forEach(permanent -> choiceForThisLoop.getChoices().add(permanent.getIdName()));

                if (player == null) {
                    break;
                }
                player.choose(Outcome.Neutral, choiceForThisLoop, game);

                String choice = choiceForThisLoop.getChoice();
                Permanent choicePermanent = permanents.stream().filter(permanent -> permanent.getIdName().equals(choice)).findFirst().get();

                ConniveSourceEffect.connive(choicePermanent, 1, source, game);
                permanents.remove(choicePermanent);
            }
        }
        return true;
    }
}
