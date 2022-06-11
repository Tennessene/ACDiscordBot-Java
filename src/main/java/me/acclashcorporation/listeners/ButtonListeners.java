package me.acclashcorporation.listeners;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

public class ButtonListeners extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if (event.getButton().getId().equals("remove-message")) {

            String[] content = event.getMessage().getContentRaw().split(" ");
            String messageID = content[content.length - 1];

            event.getGuild().getTextChannelById("675831474573803560").deleteMessageById(messageID).queue();

            event.reply("Message deleted.").queue();

        } else if (event.getButton().getId().equals("mute-member")) {

            TextInput name = TextInput.create("name", "Name", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setRequired(true)
                    .build();

            TextInput reason = TextInput.create("reason", "Reason", TextInputStyle.PARAGRAPH)
                    .setMinLength(1)
                    .setMaxLength(100)
                    .setRequired(true)
                    .setPlaceholder("Put a mute reason here")
                    .build();

            TextInput numDur = TextInput.create("num-dur", "Number of Duration", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setMaxLength(2)
                    .setRequired(true)
                    .setPlaceholder("eg. 7")
                    .build();

            TextInput typeDur = TextInput.create("type-dur", "Type of Duration", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setMaxLength(100)
                    .setRequired(true)
                    .setPlaceholder("Seconds, Minutes, Hours, or Days")
                    .build();

            Modal muteModal = Modal.create("mute-modal", "Mute Member")
                    .addActionRows(ActionRow.of(name), ActionRow.of(reason), ActionRow.of(numDur), ActionRow.of(typeDur))
                    .build();

            event.replyModal(muteModal).queue();

        } else if (event.getButton().getId().equals("ignore-alert")) {

            event.getMessage().delete().queue();

            event.reply("Alert deleted").setEphemeral(true).queue();

        }
    }
}
