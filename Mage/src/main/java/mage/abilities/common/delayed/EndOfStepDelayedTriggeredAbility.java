package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class EndOfStepDelayedTriggeredAbility extends DelayedTriggeredAbility {
    private final Condition condition;

    public EndOfStepDelayedTriggeredAbility(Effect effect) {
        this(effect, null);
    }

    public EndOfStepDelayedTriggeredAbility(Effect effect, Condition condition) {
        this(effect, condition, false);
    }

    public EndOfStepDelayedTriggeredAbility(Effect effect, Condition condition, boolean optional) {
        super(effect, Duration.Custom, true, optional);
        this.condition = condition;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public EndOfStepDelayedTriggeredAbility(final EndOfStepDelayedTriggeredAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.UNTAP_STEP_POST ||
                event.getType() == GameEvent.EventType.UPKEEP_STEP_POST ||
                event.getType() == GameEvent.EventType.DRAW_STEP_POST ||
                event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_STEP_POST ||
                event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_POST ||
                event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST ||
                event.getType() == GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST ||
                event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST ||
                event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST ||
                event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_STEP_POST ||
                event.getType() == GameEvent.EventType.END_TURN_STEP_POST ||
                event.getType() == GameEvent.EventType.CLEANUP_STEP_POST
        );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return condition == null || condition.apply(game, this);
    }

    @Override
    public EndOfStepDelayedTriggeredAbility copy() {
        return new EndOfStepDelayedTriggeredAbility(this);
    }

    private String generateTriggerPhrase() {
        return "At the end of this step";
    }
}
