package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;

//@@author LeonardTay748
/**
 * Lists Statistics
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the number of GOOD, HARD & EASY flashcards ";

    private final RatingContainsKeywordPredicate predicateGood = new RatingContainsKeywordPredicate("good");
    private final RatingContainsKeywordPredicate predicateHard = new RatingContainsKeywordPredicate("hard");
    private final RatingContainsKeywordPredicate predicateEasy = new RatingContainsKeywordPredicate("easy");

    ////////// Should return a CommandResult with the statistics ////////////////
    @Override
    public CommandResult execute(Model model)  {
        requireNonNull(model);

        int numGood = 0;
        int numHard = 0;
        int numEasy = 0;

        model.updateFilteredFlashCardList(predicateGood);
        numGood = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicateHard);
        numHard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicateEasy);
        numEasy = model.getFilteredFlashCardList().size();

        int[] stats = model.getStats();

        StringBuilder sb = new StringBuilder("STATISTICS" + System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("Total: ");
        sb.append("Good (" + numGood + ")  ");
        sb.append("Hard (" + numHard + ")  ");
        sb.append("Easy (" + numEasy + ")" + System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("Completed in test: " );
        sb.append("Good (" + stats[0] + ")  ");
        sb.append("Hard (" + stats[1] + ")  ");
        sb.append("Easy (" + stats[2] + ")  ");

        return new CommandResult(sb.toString(), false, true, false);
    }

}
