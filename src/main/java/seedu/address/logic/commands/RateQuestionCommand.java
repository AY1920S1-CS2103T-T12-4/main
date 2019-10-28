package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Rating;

//@@author keiteo
/**
 * Allows users to rate the flashcard question and processes the next question.
 */
public class RateQuestionCommand extends Command {

    public static final String COMMAND_WORD = "rate";
    public static final String MESSAGE_SUCCESS = "Rated successfully! Here's the next question:\n%s";
    public static final String MESSAGE_SUCCESS_END_OF_TEST = "End of test!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rates a flashcard and gets the next question.\n"
            + "Parameter: rate easy/good/bad";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;
    private final Rating rating;

    public RateQuestionCommand(KeyboardFlashCardsParser keyboardFlashCardsParser, Rating rating) {
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        updateModelStatistics(model);
        // TODO: implement flashcard rating

        if (!model.hasTestFlashCard()) {
            keyboardFlashCardsParser.endTestMode();
            return new CommandResult(MESSAGE_SUCCESS_END_OF_TEST);
        }

        String nextQuestion = model.getTestQuestion();
        keyboardFlashCardsParser.setAwaitingAnswer(true);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nextQuestion));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RateQuestionCommand // instanceof handles nulls
                && rating.equals(((RateQuestionCommand) other).rating)
                && keyboardFlashCardsParser
                        .equals(((RateQuestionCommand) other).keyboardFlashCardsParser)); // state check
    }

    /** Updates statistics in the model. */
    private void updateModelStatistics(Model model) {
        requireNonNull(model);
        String rating = this.rating.toString();
        assert rating.equals("good") || rating.equals("hard") || rating.equals("easy");
        if (rating.equals("good")) {
            model.editStats(0);
        }
        if (rating.equals("hard")) {
            model.editStats(1);
        }
        if (rating.equals("easy")) {
            model.editStats(2);
        }
    }
}
