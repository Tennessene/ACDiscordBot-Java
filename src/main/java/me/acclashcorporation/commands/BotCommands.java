package me.acclashcorporation.commands;

import me.acclashcorporation.DiscordBot;
import me.acclashcorporation.files.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotCommands extends ListenerAdapter {

    List<Message> msgs;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (event.getName().equals("ver")) {
            String version = "";
            String filePath = "build.gradle";
            String artifactName = "JDA";

            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                version = extractDependencyVersion(content, artifactName);
                if (version == null) {
                    System.out.println("Artifact not found: " + artifactName + ". This is bad. Please report it to the developer.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.reply("You are using ACDiscordBot 0.4 with JDA version " + version + ". Programmed by Anston Sorensen (Tennessine/Tennessene).").setEphemeral(true).queue();
        } else if (event.getName().equals("welcome") || event.getName().equals("welcomeedit")) {
            if (event.getMember().isOwner()) {
                MessageEmbed welcome = new EmbedBuilder()
                        .setTitle("Welcome! :crossed_swords:")
                        .setColor(Color.decode("#ffa500"))
                        .setDescription("Welcome to the official AC Clash Discord server! Even though the server is in early development, we want to grow our community. We will let you know when you can test the server closer to release! We're excited to have you and hope that you have a good time with all your friends and other players. Please read both the Discord and Minecraft server rules below. We also recommend reading the FAQ.")
                        .build();
                MessageEmbed drules = new EmbedBuilder()
                        .setTitle("Discord Rules :scroll:")
                        .setColor(Color.decode("#ffa500"))
                        .setDescription("**1. Be respectful/don't be rude.**\nBe nice to everyone.\n\n**2. No discrimination.**\nDo not discriminate someone of race, ethnicity, etc.\n\n**3. No inappropriate behavior or swearing period (includes profile pics and statuses).**\nMake everyone feel at home. Don't try to bypass the filters especially.\n\n**4. No excessive spamming.**\nDon't keep spamming messages, emoji's, images, etc.\n\n**5. Don't share your own or anyone else's personal information.**\nIt's always important to be safe online.\n\n**6. No advertising.**\nPlease don't advertise other Discord's or IP's.\n\n**7. HAVE FUN!!! (feel free to say 'Hi' to staff)**\nYou can just dm us if you'd like.\n\nListen to the rules and staff or you may be banned for an amount of time the staff decides.")
                        .build();
                MessageEmbed srules = new EmbedBuilder()
                        .setTitle("Server Rules :computer:")
                        .setColor(Color.decode("#ffff00"))
                        .setDescription("The server rules apply everywhere please read them.\n\n:link: https://acclash.com/rules")
                        .build();
                MessageEmbed faq = new EmbedBuilder()
                        .setTitle("Frequently Asked Questions :pencil:")
                        .setColor(Color.decode("#00ff00"))
                        .setDescription("The FAQ is also available here: :link: https://acclash.com/rules\n\n**How do I join?**\nJava IP: **play.acclash.tk**\nBedrock IP: **br.acclash.tk**\n\n**How is this server unique?**\nPlayers can join using the Bedrock Editon of Minecraft making it **cross platform** (it's **very experimental** though). You can play multiplayer survival, creative, or special minigames including **Halo Battles** and **COD Ops**. We also have classic ones like Survival Games, Spleef, Skywars and more.\n\n**What versions do you accept?**\n1.8.x - 1.17.x.")
                        .build();
                if (event.getName().equals("welcome")) {
                    event.getChannel().sendMessageEmbeds(welcome, drules, srules, faq).queue();
                    event.reply("Successfully sent the welcome embeds.").setEphemeral(true).queue();
                } else {
                    OptionMapping option = event.getOption("message-id");
                    if (option == null) {
                        event.reply("A message ID was not provided.").queue();
                        return;
                    }

                    String messageID = option.getAsString();

                    event.getChannel().editMessageEmbedsById(messageID, welcome, drules, srules, faq).queue();
                    event.reply("Successfully edited the welcome embeds.").setEphemeral(true).queue();
                }
            } else {
                event.reply("You aren't the server owner!").setEphemeral(true).queue();
            }
        } else if (event.getName().equals("food")) {
            OptionMapping option = event.getOption("name");
            if (option == null) {
                event.reply("A food name was not provided.").queue();
                return;
            }

            String favoriteFood = option.getAsString();

            for (String badWord : DiscordBot.getBadWords()) {

                if (favoriteFood.contains(badWord)) {
                    event.reply("Hey, that's an inappropriate food name. I reported you to the staff").setEphemeral(true).queue();
                    TextChannel staffChannel = event.getJDA().getTextChannelById(Config.getProperty("channel.report.id"));

                    if (staffChannel != null) {

                        Button muteMember = Button.danger("mute-member", "Mute Member");
                        Button ignoreAlert = Button.success("ignore-alert", "Ignore Alert");

                        Message message = new MessageBuilder()
                                .append(event.getMember().getEffectiveName())
                                .append(" put in a bad word in a command. Click any of the buttons below to do an action. ")
                                .setActionRows(ActionRow.of(muteMember, ignoreAlert))
                                .build();

                        staffChannel.sendMessage(message).queue();
                    } else {
                        event.reply("Your favorite food is: " + favoriteFood).queue();
                    }
                }
            }

        } else if (event.getName().equals("modaltest")) {
            TextInput name = TextInput.create("name", "Name", TextInputStyle.SHORT)
                    .setMinLength(1)
                    .setRequired(true)
                    .build();

            TextInput message = TextInput.create("message", "Message", TextInputStyle.PARAGRAPH)
                    .setMinLength(1)
                    .setMaxLength(100)
                    .setRequired(true)
                    .setPlaceholder("Put a cool message here")
                    .build();

            Modal testModal = Modal.create("test-modal", "Test modal")
                    .addActionRows(ActionRow.of(name), ActionRow.of(message))
                    .build();

            event.replyModal(testModal).queue();
        } else if (event.getName().equals("clearchannel")) {

            for (String staffChannel : DiscordBot.getStaffChannels()) {

                if (event.getChannel().getName().equals(staffChannel)) {
                    try {
                        msgs = event.getChannel().getHistory().retrievePast(20).complete();

                        if (msgs.size() == 1) {
                            String id = event.getChannel().getLatestMessageId();
                            event.getTextChannel().deleteMessageById(id).queue();
                            event.reply("Channel Successfully Cleared.").setEphemeral(true).queue();
                        } else {
                            event.getTextChannel().deleteMessages(msgs).queue();
                            event.reply("Channel Successfully Cleared.").setEphemeral(true).queue();
                        }
                    } catch (IllegalArgumentException e) {
                        event.reply("The channel was not able to be cleared due to a message being older than 2 weeks. If a message is older than 2 weeks Discord does not allow this action.").setEphemeral(true).queue();
                    }
                } else {
                    event.reply("Woa there, this is a public channel. You can't clear this!").setEphemeral(true).queue();
                }
            }
        } else if (event.getName().equals("spam")) {
            if (event.getMember().isOwner()) {
                OptionMapping option = event.getOption("id");
                if (option == null) {
                    event.reply("An id was not provided.").queue();
                    return;
                }

                String id = option.getAsString();

                User user = event.getJDA().getUserById(id);

                event.reply("Spamming user: " + user.getName()).setEphemeral(true).queue();

                for (int i = 0; i <= 5000; i++) {
                    user.openPrivateChannel().flatMap(channel -> channel.sendMessage("poopie")).queue();
                }
            } else {
                event.reply("You aren't the server owner!").setEphemeral(true).queue();
            }
        }
    }

    public static void main(String[] args) {

    }

    private static String extractDependencyVersion(String content, String artifactName) {
        // Define a regex pattern to match dependencies in the build.gradle file
        // This pattern assumes the dependency declaration is in the form of "group:artifact:version"
        String regex = "[^:]+:" + Pattern.quote(artifactName) + ":([\\d.]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1); // Return the version part of the dependency declaration
        }
        return null;
    }
}
