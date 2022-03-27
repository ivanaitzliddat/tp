package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.GroupViewCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

public class GroupViewCommandParser implements CommandParser<GroupViewCommand> {

    public static final String COMMAND_TEXT = "group /view";

    public static final String COMMAND_FORMAT = "Syntax: group /view /gid [GROUP_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.GROUP_ID_DELIMITER
    };

    @Override
    public GroupViewCommand getCommand(String commandArgs) throws InvalidFormatException {
        try {
            int groupId = Parser.parseGroupId(commandArgs);
            assert groupId > 0 : Message.ASSERT_GROUPVIEW_GROUP_ID_NOT_INITIALIZED;
            return new GroupViewCommand(groupId);
        } catch (InvalidFormatException e) {
            throw new InvalidFormatException(e.getMessage() + "\n" + COMMAND_FORMAT);
        }
    }
}