//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.ExportTestUtil.deleteFileIfExists;
import static seedu.address.testutil.ExportTestUtil.isFilePresent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.TypicalExportPaths;
import seedu.address.testutil.TypicalFlashCards;

public class JsonExportUtilTest {

    @Test
    public void exportFlashCardsToJson_valid_success() {
        List<List<FlashCard>> listsToTest = Arrays.asList(
                TypicalFlashCards.getTypicalFlashCards(),
                TypicalFlashCards.getSingletonFlashCardList(),
                TypicalFlashCards.getEmptyFlashCardList()
        );

        for (List<FlashCard> list : listsToTest) {
            tryExport(list);
        }
    }

    private void tryExport(List<FlashCard> list) {
        deleteFileIfExists(TypicalExportPaths.CS2105_JSON);

        try {
            JsonExportUtil.exportFlashCardsToJson(
                    list,
                    TypicalExportPaths.CS2105_JSON
            );

            assertTrue(isFilePresent(TypicalExportPaths.CS2105_JSON));
        } catch (IOException e) {
            fail("IOException when trying to export json export");
        }

        deleteFileIfExists(TypicalExportPaths.CS2105_JSON);
    }
}
