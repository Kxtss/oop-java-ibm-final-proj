import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MoodTracker {
    public static void main(String[] args) {
        ArrayList<Mood> moodList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Press any letter in the following menu to perform an action:
                    'a' to add mood
                    'd' to delete mood(s)
                    'e' to edit mood
                    's' to search for moods
                    'M' to get all moods
                    'w' to write the moods to a file
                    Type 'Exit' to exit""");
            String menuOption = scanner.nextLine();
            String moodName;
            switch (menuOption) {
                case "a":
                    System.out.println("Enter the mood name");
                    moodName = scanner.nextLine();
                    System.out.println("Are you tracking de mood for a current day? y/n");
                    String isForCurrentDate = scanner.nextLine();

                    Mood moodToAdd;
                    if (isForCurrentDate.equalsIgnoreCase("n")) {
                        try {
                            System.out.println("Input the date in MM/dd/yyyy format:");
                            String moodDateStr = scanner.nextLine();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);

                            System.out.println("Input the time in HH:mm:ss format:");
                            String moodTimeStr = scanner.nextLine();
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


                            LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                            System.out.println("Add notes about this mood");

                            String moodNotes = scanner.nextLine();
                            if (moodNotes.strip().equalsIgnoreCase("")) {
                                moodToAdd = new Mood(moodName, moodDate, moodTime);
                            } else {
                                moodToAdd = new Mood(moodName, moodDate, moodTime, moodNotes);
                            }
                        } catch (DateTimeParseException dfe) {
                            System.out.println("Incorrect format of date or time. Cannot create mood.\n" + dfe);
                            continue;
                        }
                    } else {
                        System.out.println("Add notes about this mood");
                        String moodNotes = scanner.nextLine();
                        if (moodNotes.strip().equalsIgnoreCase("")) {
                            moodToAdd = new Mood(moodName);
                        } else {
                            moodToAdd = new Mood(moodName, moodNotes);
                        }
                    }
                    try {
                        boolean isValid = isValidMood(moodToAdd, moodList);
                        if (isValid) {
                            moodList.add(moodToAdd);
                            System.out.println("The mood has been added to the tracker");
                            continue;
                        }
                    } catch (InvalidMoodException ime) {
                        System.out.println("The mood is not valid");
                    }
                    continue;
                case "d":
                    System.out.println("""
                            Enter '1' for delete all moods by date
                            Enter '2' for delete a specific mood
                            """);
                    String deleteVariant = scanner.nextLine();
                    if (deleteVariant.equals("1")) {
                        try {
                            System.out.println("Input the date in MM/dd/yyyy format:");
                            String moodDateStr = scanner.nextLine();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);

                            boolean areMoodsDeleted = deleteMoods(moodDate, moodList);
                            if (areMoodsDeleted) {
                                System.out.println("The moods have been deleted");
                            } else {
                                System.out.println("No matching moods found");
                            }
                        } catch (DateTimeParseException dfe) {
                            System.out.println("Incorrect format date. Cannot delete mood");
                            continue;
                        }
                    } else if (deleteVariant.equals("2")) {
                        try {
                            System.out.println("Enter the mood name");
                            moodName = scanner.nextLine();

                            System.out.println("Input the date in MM/dd/yyyy format:");
                            String moodDateStr = scanner.nextLine();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);

                            System.out.println("Input the time in HH:mm:ss format:");
                            String moodTimeStr = scanner.nextLine();
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                            LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                            Mood delMood = new Mood(moodName, moodDate, moodTime);
                            boolean isMoodDeleted = deleteMood(delMood, moodList);
                            if (isMoodDeleted) {
                                System.out.println("The mood has been deleted");
                            } else {
                                System.out.println("No matching mood found");
                            }
                        } catch (DateTimeParseException dfe) {
                            System.out.println("Incorrect format of date or time. Cannot delete mood.");
                            continue;
                        }
                    }
                    continue;
                case "e":
                    Mood moodToEdit;
                    try {
                        System.out.println("Enter the mood name");
                        moodName = scanner.nextLine();

                        System.out.println("Input the date in MM/dd/yyyy format:");
                        String moodDateStr = scanner.nextLine();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                        System.out.println("Input the time in HH:mm:ss format:");

                        String moodTimeStr = scanner.nextLine();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);
                        System.out.println("Add new notes about this mood");

                        String moodNotes = scanner.nextLine();
                        if (moodNotes.strip().equalsIgnoreCase("")) {
                            System.out.println("No notes entered");
                            continue;
                        } else {
                            moodToEdit = new Mood(moodName, moodDate, moodTime, moodNotes);
                            boolean isMoodEdited = editMood(moodToEdit, moodList);
                            if (isMoodEdited) {
                                System.out.println("The mood has been successfully edited");
                            } else {
                                System.out.println("No matching mood could be found");
                            }
                        }
                    } catch (DateTimeParseException dfe) {
                        System.out.println("Incorrect format of date or time. Cannot create mood.");
                        continue;
                    }
                    continue;
                case "s":
                    System.out.println("""
                            Enter '1' to search for all moods by date
                            Enter '2' to search for a specific mood""");
                    String searchVariant = scanner.nextLine();
                    if (searchVariant.equals("1")) {
                        try {
                            System.out.println("Input the date in MM/dd/yyyy format:");
                            String moodDateStr = scanner.nextLine();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);
                            searchMoods(moodDate, moodList);
                        } catch (DateTimeParseException dfe) {
                            System.out.println("Incorrect format of date. Cannot search mood.");
                            continue;
                        }
                    } else if (searchVariant.equals("2")) {
                        try {
                            System.out.println("Enter the mood name");
                            moodName = scanner.nextLine();

                            System.out.println("Input the date in MM/dd/yyyy format:");
                            String moodDateStr = scanner.nextLine();
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                            LocalDate moodDate = LocalDate.parse(moodDateStr, dateFormatter);

                            System.out.println("Input the time in HH:mm:ss format:");
                            String moodTimeStr = scanner.nextLine();
                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                            LocalTime moodTime = LocalTime.parse(moodTimeStr, timeFormatter);

                            Mood delMood = new Mood(moodName, moodDate, moodTime);
                            searchMood(delMood, moodList);
                        } catch (DateTimeParseException dfe) {
                            System.out.println("Incorrect format of date or time. Cannot search mood.");
                            continue;
                        }
                    }
                    continue;
                case "M":
                    for(Mood moodObj: moodList) {
                        System.out.println(moodObj);
                    }
                    continue;
                case "w":
                    try (PrintWriter writer = new PrintWriter(new FileWriter("Moods.txt"))) {
                        for (Mood mood : moodList) {
                            writer.println(mood+"\n\n");
                        }
                        System.out.println("The entries are written to a file");
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                    }
                    continue;
                case "Exit":
                    System.out.println("Thank you for using the MoodTracker. Goodbye!");
                    break;
                default:
                    System.out.println("Not a valid input!");
                    continue;
            }
            break;
        }
    }

    public static boolean isValidMood(Mood mood, ArrayList<Mood> moodList) throws InvalidMoodException {
        for (Mood tempMood : moodList) {
            if (tempMood.equals(mood)) {
                throw new InvalidMoodException();
            }
        }
        return true;
    }

    // Delete by date
    public static boolean deleteMoods(LocalDate moodDate, ArrayList<Mood> moodList) {
        boolean removed = false;
        for (Mood tempMood : moodList) {
            if (tempMood.getDate().equals(moodDate)) {
                moodList.remove(tempMood);
                removed = true;
            }
        }
        return removed;
    }

    // Delete by name
    public static boolean deleteMood(Mood mood, ArrayList<Mood> moodList) {
        for (Mood tempMood : moodList) {
            if (tempMood.equals(mood)) {
                moodList.remove(tempMood);
                return true;
            }
        }
        return false;
    }

    public static boolean editMood(Mood moodToEdit, ArrayList<Mood> moodList) {
        for (Mood tempMood : moodList) {
            if (tempMood.equals(moodToEdit)) {
                tempMood.setNotes(moodToEdit.getNotes());
                return true;
            }
        }
        return false;
    }

    // Search by Date
    public static void searchMoods(LocalDate moodDate, ArrayList<Mood> moodList) {
        boolean found = false;
        for (Mood tempMood : moodList) {
            if (tempMood.getDate().equals(moodDate)) {
                found = true;
                System.out.println(tempMood);
            }
        }
        if (!found) {
            System.out.println("No matching records could be found!");
        }
    }

    // Search by name
    public static void searchMood(Mood mood, ArrayList<Mood> moodList) {
        boolean found = false;

        for (Mood tempMood : moodList) {
            if (tempMood.equals(mood)) {
                found = true;
                System.out.println(tempMood);
            }
        }
        if (!found) {
            System.out.println("No matching records could be found!");
        }
    }
}
