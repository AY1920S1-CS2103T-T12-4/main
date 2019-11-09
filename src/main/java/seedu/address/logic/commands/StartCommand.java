package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;

//@@author keiteo
/**
 * Starts the flashcard test by going through the cards in the specified deck.
 * If no deck name is supplied, a random deck will be chosen.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the FlashCard test by going through the cards in the specified deck.\n"
            + "Parameters: DECK NAME. If no argument is supplied, a random deck will be chosen.\n"
            + "Example: " + COMMAND_WORD + " physics";

    public static final String MESSAGE_NO_FLASHCARDS = "No FlashCard to test!";

    public static final String MESSAGE_START_TEST_SUCCESS = "Starting test...\n";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    private final String tagName;

    public StartCommand(KeyboardFlashCardsParser keyboardFlashCardsParser, String tagName) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<FlashCard> testList = searchTag(model);
        model.initializeTestModel(testList);
        if (!model.hasTestFlashCard()) {
            return new CommandResult(MESSAGE_NO_FLASHCARDS);
        }
        keyboardFlashCardsParser.startTestMode();
        model.setTestFlashCard();
        //String question = model.getTestQuestion();
        keyboardFlashCardsParser.setAwaitingAnswer(true);
        CommandResult result = new CommandResult(
                MESSAGE_START_TEST_SUCCESS,
                model.getTestFlashCardPanel());
        result.setTestMode(true, false);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && tagName.equals(((StartCommand) other).tagName)
                && keyboardFlashCardsParser.equals(((StartCommand) other).keyboardFlashCardsParser)); // state check
    }

    /** Searches the list of flashcard to fetch the relevant tags. */
    private List<FlashCard> searchTag(Model model) {
        assert model != null;
        if (tagName.isEmpty()) {
            return new LinkedList<>(model.getFlashCardList());
        }
        CategoryContainsAnyKeywordsPredicate predicate = getSearchTermPredicate();
        model.updateFilteredFlashCardList(predicate);
        return new LinkedList<>(model.getFilteredFlashCardList());
    }

    /** Converts tagName to a CategoryContainsAnyKeywordsPredicate for searchTag(). */
    private CategoryContainsAnyKeywordsPredicate getSearchTermPredicate() {
        String[] tagList = tagName.split("\\s+");
        return new CategoryContainsAnyKeywordsPredicate(Arrays.asList(tagList));
    }
}
