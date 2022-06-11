package me.acclashcorporation.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ModalListeners extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        if (event.getModalId().equals("test-modal")) {

            String name = event.getValue("name").getAsString();
            String message = event.getValue("message").getAsString();

            Optional<Member> memberOptional = event.getGuild().getMembersByName(name, true).stream().findFirst();

            if (memberOptional.isPresent()) {
                event.reply("The following message was sent using the test modal: Hello, " + memberOptional.get().getAsMention() + "! " + message).queue();
            }else{
                event.reply("The following message was sent using the test modal: Hello, " + name + "! " + message).queue();
            }

        } else if (event.getModalId().equals("mute-modal")) {

            String name = event.getValue("name").getAsString();
            String reason = event.getValue("reason").getAsString();
            int numDur = Integer.parseInt(event.getValue("num-dur").getAsString());
            String typeDur = event.getValue("type-dur").getAsString().toUpperCase();

            Optional<Member> memberOptional = event.getGuild().getMembersByName(name, true).stream().findFirst();

            if (memberOptional.isPresent()) {
                event.getGuild().addRoleToMember().getController().addRolesToMember(mentionedMember, role).queue();
                event.reply(name + " was muter for " + numDur + typeDur + " for the following reason: " + reason).queue();
            }else{
                event.reply("That user doesn't exist or isn't a member!").setEphemeral(true).queue();
            }
        }
    }
}
