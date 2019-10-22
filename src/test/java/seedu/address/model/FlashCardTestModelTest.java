package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.FlashCardTestListBuilder;

public class FlashCardTestModelTest {

    private FlashCardTestModel flashCardTestModel;
    private List<FlashCard> testList = new FlashCardTestListBuilder().build();

    @Test
    public void isEmpty_emptyList_success() {
        flashCardTestModel = new FlashCardTestModel(new LinkedList<>());
        assertTrue(flashCardTestModel.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_success() {
        flashCardTestModel = new FlashCardTestModel(testList);
        assertFalse(flashCardTestModel.isEmpty());
    }

    @Test
    public void getQuestion_validTestList_success(Model model) {
        flashCardTestModel = new FlashCardTestModel(testList);
        String expectedOutput = "0 + 10";
        assertEquals(expectedOutput, flashCardTestModel.getQuestion(model));
    }
}
