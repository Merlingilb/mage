package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class ShuffleLibraryAllEffect extends OneShotEffect {

    public ShuffleLibraryAllEffect() {
        super(Outcome.Neutral);
        this.staticText = "each player shuffles their library";
    }

    public ShuffleLibraryAllEffect(final ShuffleLibraryAllEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleLibraryAllEffect copy() {
        return new ShuffleLibraryAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }
}
