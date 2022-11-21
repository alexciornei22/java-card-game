package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;
import main.card.Card;
import main.card.EnvironmentCard;
import main.card.HeroCard;
import main.card.MinionCard;
import main.command.Command;
import main.command.GetPlayerDeck;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation
        Player player1 = new Player(inputData.getPlayerOneDecks());
        Player player2 = new Player(inputData.getPlayerTwoDecks());

        inputData.getGames().forEach(gameInput -> {
            StartGameInput startGameInput = gameInput.getStartGame();

            player1.setMana(1);
            player2.setMana(1);

            player1.setHeroCard(new HeroCard(startGameInput.getPlayerOneHero()));
            player2.setHeroCard(new HeroCard(startGameInput.getPlayerTwoHero()));

            Game game = new Game(
                    player1,
                    player2,
                    startGameInput
            );

//            System.out.println(game.getPlayer1Deck().getCards());
//            System.out.println(game.getPlayer2Deck().getCards());
//            System.out.println();

            for (ActionsInput actionsInput:
                 gameInput.getActions()) {

//                if (gameInput.getStartGame().getShuffleSeed() == 935132) {
//                    System.out.println(actionsInput.getCommand());
//                    game.table.forEach(row -> {
//                        row.forEach(card -> System.out.print(card.getName() + ", "));
//                        System.out.println();
//                    });
//                    System.out.println("--------");
//                }

                Command commandObject = game.getCommandObject(actionsInput);
//                System.out.println(actionsInput.getCommand());
//                System.out.println(game.rowHasTanks(1));
//                System.out.println(game.rowHasTanks(2));

                commandObject.execute(output);
                game.removeDeadCards();
//                game.getPlayer1().getHand().forEach(card -> System.out.print(card.getName() + " "));
//                System.out.println();
//                game.getPlayer2().getHand().forEach(card -> System.out.print(card.getName() + " "));
//                System.out.println();
//                System.out.println();
            }
//            game.resetCards();
        });

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
