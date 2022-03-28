package seedu.splitlah.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.Command;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ActivityTest {

    Manager manager = new Manager();
    Session session;
    Activity activityOne;
    Activity activityTwo;

    private static final int TEST_SESSION = 1;
    private static final int TEST_ACTIVITY_ONE = 1;
    private static final int TEST_ACTIVITY_TWO = 2;
    private static final String CREATE_TEST_SESSION_INPUT =
            "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
    private static final String CREATE_TEST_ACTIVITY_INPUT_ONE =
            "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
    private static final String CREATE_TEST_ACTIVITY_INPUT_TWO =
            "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30";
    private static final String ACTIVITY_ONE_STRING =
            "Activity Id #1 --\n"
                    + "Name:  Lunch\n"
                    + "Id:    1\n"
                    + "Payer: Alice\n"
                    + "Cost:  $15.00\n"
                    + "Involved: \n"
                    + "-------------------------\n"
                    + "# | Name    | Cost Owed \n"
                    + "-------------------------\n"
                    + "1 | Alice   | 5.00      \n"
                    + "2 | Bob     | 5.00      \n"
                    + "3 | Charlie | 5.00      \n"
                    + "=========================";

    /**
     * Creates a session and an activity that are stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        Command createSessionCommand = Parser.getCommand(CREATE_TEST_SESSION_INPUT);
        createSessionCommand.run(manager);
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommandOne.run(manager);
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandTwo.run(manager);

        try {
            session = manager.getProfile().getSession(TEST_SESSION);
            activityOne = session.getActivity(TEST_ACTIVITY_ONE);
            activityTwo = session.getActivity(TEST_ACTIVITY_TWO);
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    /**
     * Checks if -1 is returned when an Activity object with a smaller unique activity identifier
     * is compared against an Activity object with a larger unique activity identifier.
     */
    @Test
    void compareTo_smallerActivityIdInput_returnsNegativeOne() {
        int compareResults = activityOne.compareTo(activityTwo);
        assertEquals(-1, compareResults);
    }

    /**
     * Checks if 1 is returned when an Activity object with a smaller unique activity identifier
     * is compared against an Activity object with a larger unique activity identifier.
     */
    @Test
    void compareTo_smallerActivityIdInput_returnsOne() {
        int compareResults = activityTwo.compareTo(activityOne);
        assertEquals(1, compareResults);
    }

    /**
     * Checks if the correct format of the String representing the Activity object is returned.
     */
    @Test
    void toString_activityOne_returnsCorrectFormat() {
        String activityString = activityOne.toString();
        assertEquals(ACTIVITY_ONE_STRING, activityString);
    }
}