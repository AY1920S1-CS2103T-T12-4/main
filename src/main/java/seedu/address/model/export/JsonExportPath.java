//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.model.flashcard.FlashCard;

/**
 * Represents the full path to a JSON export file, including parent directories.
 * Guarantees: immutable; is valid as declared in {@link #isValidJsonExportPath(String)}
 */
public class JsonExportPath extends ExportPath {

    public static final String MESSAGE_CONSTRAINTS =
            "JSON export file path may only consist of alphanumeric characters, spaces, and the following characters:\n"
            + "~\\/-_!:[]()\n"
            + "It must also end with \".json\".";

    /*
     * The following characters are allowed (in addition to alphanumeric):
     * ~\/-_!:[]()
     * Space is allowed.
     * Required to end with the String: ".json"
     */
    public static final String VALIDATION_REGEX = "[.~\\w\\-!:\\[\\]()/\\\\ ]+\\.[Jj][Ss][Oo][Nn]";

    private final DirectoryPath directoryPath;
    private final JsonExportFilePath jsonExportFilePath;

    /**
     * Constructs a {@code JsonExportPath}.
     *
     * @param jsonExportPath A valid JSON export path.
     */
    public JsonExportPath(String jsonExportPath) {
        requireNonNull(jsonExportPath);
        checkArgument(isValidJsonExportPath(jsonExportPath), MESSAGE_CONSTRAINTS);
        this.directoryPath = extractDirectoryPath(jsonExportPath);
        this.jsonExportFilePath = extractJsonExportFilePath(jsonExportPath);
    }

    /**
     * Returns true if a given string is a valid json export path.
     */
    public static boolean isValidJsonExportPath(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Helper method to get the directory path from a given String.
     *
     * @param jsonExportPathString String representing the full path of a JSON export file
     * @return DirectoryPath representing the path of the most nested directory within the given String
     */
    private static DirectoryPath extractDirectoryPath(String jsonExportPathString) {
        Path fullPath = Paths.get(jsonExportPathString);
        int nameCount = fullPath.getNameCount();

        if (nameCount == 1) {
            return new DirectoryPath("./");
        }

        return new DirectoryPath(
                fullPath
                .subpath(0, nameCount - 1)
                .toString()
        );
    }

    /**
     * Helper method to get the JSON export file path from a given String.
     *
     * @param jsonExportPathString String representing the full path of a JSON export file
     * @return JsonExportFilePath representing the path of the JSON export file, relative to its immediate parent directory
     */
    private static JsonExportFilePath extractJsonExportFilePath(String jsonExportPathString) {
        Path fullPath = Paths.get(jsonExportPathString);
        int nameCount = fullPath.getNameCount();

        return new JsonExportFilePath(
                fullPath
                .subpath(nameCount - 1, nameCount)
                .toString()
        );
    }

    @Override
    public Path getPath() {
        Path dirPath = directoryPath.getPath();
        Path jsonFilePath = jsonExportFilePath.getPath();

        return dirPath.resolve(jsonFilePath);
    }

    @Override
    public String toString() {
        return directoryPath.toString() + File.separator + jsonExportFilePath.toString();
    }

    @Override
    public void export(List<FlashCard> list) {
        //TODO
    }

    /**
     * Converts this JsonExportPath into a String representing its absolute path
     *
     * @return String representing the absolute path of this JsonExportPath
     */
    @Override
    public String toAbsolutePathString() {
        return this
                .getPath()
                .toAbsolutePath()
                .normalize()
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JsonExportPath // instanceof handles nulls
                && directoryPath.equals(((JsonExportPath) other).directoryPath)
                && jsonExportFilePath.equals(((JsonExportPath) other).jsonExportFilePath)); // state check
    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode() + jsonExportFilePath.hashCode();
    }

}
