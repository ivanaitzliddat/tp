package seedu.splitlah.command;

import seedu.splitlah.data.*;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;
import seedu.splitlah.util.PersonCostPair;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a command object that edits an Activity.
 *
 * @author Saurav
 */
public class ActivityEditCommand extends Command {

    private int sessionId;
    private int activityId;
    private String activityName;
    private String payer;
    private String[] involvedList;
    private Double totalCost;

    /**
     * Constructs an ActivityEditCommand object.
     *
     * @param sessionId An integer that uniquely identifies a session.
     * @param activityId An integer that uniquely identifies an activity.
     * @param activityName A string that represents the Activity object's name.
     * @param payer A String that represents the person who paid for the activity.
     * @param involvedList An array of String objects representing the participants in the activity.
     * @param totalCost A Double representing the total cost of the activity.
     */
    public ActivityEditCommand(int sessionId, int activityId, String activityName, String payer, String[] involvedList,
        Double totalCost) {
        assert sessionId != -1 : Message.ASSERT_ACTIVITYEDIT_SESSIONID_MISSING;
        assert activityId != -1 : Message.ASSERT_ACTIVITYEDIT_ACTIVITYID_MISSING;
        this.sessionId = sessionId;
        this.activityId = activityId;
        this.activityName = activityName;
        this.payer = payer;
        this.involvedList = involvedList;
        this.totalCost = totalCost;
    }

    private void fillEmptyActivityFields(Activity activity) {
        if (activityName == null) {
            activityName = activity.getActivityName();
        }
        if (payer == null) {
            payer = activity.getPersonPaid().getName();
        }
        if (involvedList == null) {
            involvedList = getNamesOfPersonList(activity.getInvolvedPersonList());
        }
        if (totalCost == null) {
            totalCost = activity.getTotalCost();
        }
    }

    private String[] getNamesOfPersonList(ArrayList<Person> personList) {
        String[] nameList = new String[personList.size()];
        for (int personIndex = 0; personIndex < personList.size(); ++personIndex) {
            nameList[personIndex] = personList.get(personIndex).getName();
        }
        return nameList;
    }

    private ArrayList<Person> getPersonListFromNameList(String[] nameList) {
        ArrayList<Person> personList = new ArrayList<>();
        for (String name : nameList) {
            personList.add(new Person(name));
        }
        return personList;
    }

    /**
     * Runs the command with the session identifier and activity identifier as provided by the user input.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage objects.
     */
    @Override
    public void run(Manager manager) {
        assert manager != null : Message.ASSERT_ACTIVITYEDIT_MANAGER_DOES_NOT_EXIST;
        Profile profile = manager.getProfile();
        TextUI ui = manager.getUi();
        try {
            Session session = profile.getSession(sessionId);
            Activity oldActivity = session.getActivity(activityId);
            fillEmptyActivityFields(oldActivity);
            session.removeActivity(activityId);
            session.addActivity(new Activity(activityId, activityName, totalCost,
                    new Person(payer), getPersonListFromNameList(involvedList)));
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }


}
