package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.common.TargetPermanentIsEquippedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AwakenTheSleeper extends CardImpl {
    public AwakenTheSleeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        //Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        //If it's equipped, you may destroy all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AwakenTheSleeperEffect(), new TargetPermanentIsEquippedCondition())
                .setText("if it's equipped, you may destroy all Equipment attached to that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AwakenTheSleeper(final AwakenTheSleeper card) {
        super(card);
    }

    @Override
    public AwakenTheSleeper copy() {
        return new AwakenTheSleeper(this);
    }
}

class AwakenTheSleeperEffect extends OneShotEffect {

    public AwakenTheSleeperEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "if it's equipped, you may destroy all Equipment attached to that creature";
    }

    private AwakenTheSleeperEffect(final AwakenTheSleeperEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || targetCreature == null) {
            return false;
        }
        if (targetCreature.getAttachments().stream().anyMatch(uuid -> game.getPermanent(uuid).hasSubtype(SubType.EQUIPMENT, game))) {
            if (player.chooseUse(Outcome.DestroyPermanent, "Destroy all Equipment attached to that creature?", source, game)) {
                Set<UUID> equipments = targetCreature.getAttachments()
                        .stream()
                        .filter(uuid -> game.getPermanent(uuid).hasSubtype(SubType.EQUIPMENT, game))
                        .collect(Collectors.toSet());
                equipments.forEach(uuid -> game.getPermanent(uuid).destroy(source, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public AwakenTheSleeperEffect copy() {
        return new AwakenTheSleeperEffect(this);
    }
}
