package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ExpansionExplosion extends SplitCard {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell with converted mana cost 4 or less");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
    }

    public ExpansionExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/R}{U/R}", "{X}{U}{U}{R}{R}", SpellAbilityType.SPLIT);

        // Expansion
        // Copy target instant or sorcery spell with converted mana cost 4 or less. You may choose new targets for the copy.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CopyTargetSpellEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Explosion
        // Explosion deals X damage to any target. Target player draws X cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExplosionEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    public ExpansionExplosion(final ExpansionExplosion card) {
        super(card);
    }

    @Override
    public ExpansionExplosion copy() {
        return new ExpansionExplosion(this);
    }
}

class ExplosionEffect extends OneShotEffect {

    public ExplosionEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to any target. "
                + "Target player draws X cards.";
    }

    public ExplosionEffect(final ExplosionEffect effect) {
        super(effect);
    }

    @Override
    public ExplosionEffect copy() {
        return new ExplosionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        Effect effect = new DamageTargetEffect(xValue);
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        effect.apply(game, source);
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (player != null) {
            player.drawCards(xValue, game);
        }
        return true;
    }
}
