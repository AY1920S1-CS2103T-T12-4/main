package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Allows users to rate the flashcard question and processes the next question.
 */
public class RateQuestionCommand extends Command {

    public static final String COMMAND_WORD = "rate";
    public static final String MESSAGE_SUCCESS = "Rated successfully! Here's the next question:\n%s";
    public static final String MESSAGE_SUCCESS_END_OF_TEST = "End of test!";

    private final AddressBookParser addressBookParser;

    public RateQuestionCommand(AddressBookParser addressBookParser) {
        this.addressBookParser = addressBookParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // TODO: implement stats here

        if (!model.hasTestFlashCard()) {
            addressBookParser.endTest();
            return new CommandResult(MESSAGE_SUCCESS_END_OF_TEST);
        }

        String nextQuestion = model.getTestQuestion();
        addressBookParser.setAwaitingAnswer(true);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nextQuestion));
    }
}
