//@@author shutingy-reused
//original code is form AB3

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;

/**
 * Finds and lists all flashcards in address book whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindQuestionCommand extends Command {

    public static final String COMMAND_WORD = "findQuest";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all flashcards whose questions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final QuestionContainsAnyKeywordsPredicate predicate;

    public FindQuestionCommand(QuestionContainsAnyKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashCardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW,
                        model.getFilteredFlashCardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindQuestionCommand // instanceof handles nulls
                && predicate.equals(((FindQuestionCommand) other).predicate)); // state check
    }
}
