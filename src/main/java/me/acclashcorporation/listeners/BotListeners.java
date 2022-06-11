package me.acclashcorporation.listeners;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

public class BotListeners extends ListenerAdapter {

    private final String[] Bad_Words = {"poop", "fword", "fuck", "bitch", "shit", "damn"};

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        for (String badWord : Bad_Words) {

            if (event.getMessage().getContentRaw().contains(badWord)) {

                if (event.getMessage().isFromGuild()) {

                    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage("Your latest message contained profanity. Profanity isn't allowed at AC Clash. I reported you to the staff.")).queue();

                    TextChannel staffChannel = event.getJDA().getTextChannelById("984866854902304799");

                    if (staffChannel != null) {

                        Button removeMessage = Button.danger("remove-message", "Remove Message");
                        Button muteMember = Button.danger("mute-member", "Mute Member");
                        Button ignoreAlert = Button.success("ignore-alert", "Ignore Alert");

                        Message message = new MessageBuilder()
                                .append(event.getMember().getEffectiveName())
                                .append(" said a bad word. Click any of the buttons below to do an action. ")
                                .append("Message ID: " + event.getMessageId())
                                .setActionRows(ActionRow.of(removeMessage, muteMember, ignoreAlert))
                                .build();

                        staffChannel.sendMessage(message).queue();

                    }
                }
            }
        }
    }
}
