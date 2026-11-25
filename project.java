import java.util.*;


public class project {

    // ---------- Simple classes ----------
    static class Question {
        String text;
        String[] options;   // ARRAY of 4 options
        int answer;         // correct option number (1 to 4)

        Question(String text, String[] options, int answer) {
            this.text = text;
            this.options = options;
            this.answer = answer;
        }
    }

    static class Player {
        String name;
        int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }

    // ---------- ARRAY 1: Question bank ----------
    static Question[] bank = {
        new Question("Which data structure follows FIFO?",
                new String[]{"Stack", "Queue", "Array", "Tree"}, 2),
        new Question("Which data structure follows LIFO?",
                new String[]{"Queue", "Graph", "Stack", "Heap"}, 3),
        new Question("Array index in Java starts from?",
                new String[]{"0", "1", "-1", "2"}, 1),
        new Question("Time complexity of Bubble Sort (worst case)?",
                new String[]{"O(n)", "O(log n)", "O(n^2)", "O(1)"}, 3),
        new Question("Which operation removes the top element of a Stack?",
                new String[]{"push()", "pop()", "peek()", "poll()"}, 2),
        new Question("Which Queue method adds an element at the end?",
                new String[]{"add()", "pop()", "peek()", "remove()"}, 1),
        new Question("Which sorting algorithm repeatedly swaps adjacent elements?",
                new String[]{"Merge Sort", "Quick Sort", "Bubble Sort", "Heap Sort"}, 3),
        new Question("Can an array change its size after creation in Java?",
                new String[]{"Yes", "No", "Only for int", "Only for String"}, 2)
    };

    // ---------- ARRAY 2: Leaderboard ----------
    static Player[] leaderboard = new Player[100];
    static int playerCount = 0;

    static Scanner sc = new Scanner(System.in);

    // ---------- Main menu ----------
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== ONLINE QUIZ SYSTEM =====");
            System.out.println("1. Start Quiz");
            System.out.println("2. View Leaderboard");
            System.out.println("3. Exit");
            int choice = readNumber("Enter choice: ", 1, 3);

            if (choice == 1) {
                startQuiz();
            } else if (choice == 2) {
                showLeaderboard();
            } else {
                System.out.println("Thank you for playing. Bye!");
                break;
            }
        }
    }

    // ---------- Quiz logic ----------
    static void startQuiz() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        // QUEUE: put all questions in the queue
        Queue<Question> queue = new LinkedList<>();
        for (int i = 0; i < bank.length; i++) {
            queue.add(bank[i]);              // add at the end
        }

        // STACK: to store wrong answers for review
        Stack<String> wrongStack = new Stack<>();

        int score = 0;
        int qNo = 1;

        // Keep asking until the queue is empty
        while (!queue.isEmpty()) {
            Question q = queue.poll();       // take from the front

            System.out.println("\nQ" + qNo + ". " + q.text);
            for (int i = 0; i < 4; i++) {
                System.out.println("   " + (i + 1) + ") " + q.options[i]);
            }

            int userAnswer = readNumber("Your answer (1-4): ", 1, 4);

            if (userAnswer == q.answer) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong!");
                String info = "Q" + qNo + ". " + q.text
                        + "\n   Your answer   : " + q.options[userAnswer - 1]
                        + "\n   Correct answer: " + q.options[q.answer - 1];
                wrongStack.push(info);       // save on top of stack
            }
            qNo++;
        }

        System.out.println("\n===== RESULT =====");
        System.out.println(name + ", your score: " + score + " / " + bank.length);

        // Save player in leaderboard array
        if (playerCount < leaderboard.length) {
            leaderboard[playerCount] = new Player(name, score);
            playerCount++;
        }

        // Review wrong answers: last wrong answer comes first (STACK)
        if (!wrongStack.isEmpty()) {
            System.out.println("\n--- Review of wrong answers (latest first) ---");
            while (!wrongStack.isEmpty()) {
                System.out.println(wrongStack.pop());
                System.out.println();
            }
        } else {
            System.out.println("Perfect score! No wrong answers.");
        }
    }

    // ---------- Leaderboard with Bubble Sort ----------
    static void showLeaderboard() {
        if (playerCount == 0) {
            System.out.println("No players yet. Play the quiz first!");
            return;
        }

        // Bubble Sort: highest score first
        for (int i = 0; i < playerCount - 1; i++) {
            for (int j = 0; j < playerCount - 1 - i; j++) {
                if (leaderboard[j].score < leaderboard[j + 1].score) {
                    Player temp = leaderboard[j];
                    leaderboard[j] = leaderboard[j + 1];
                    leaderboard[j + 1] = temp;
                }
            }
        }

        System.out.println("\n===== LEADERBOARD =====");
        for (int i = 0; i < playerCount; i++) {
            System.out.println((i + 1) + ". " + leaderboard[i].name
                    + " - " + leaderboard[i].score);
        }
    }

    // ---------- Helper: safe number input ----------
    static int readNumber(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
            } catch (NumberFormatException e) {
                // ignore, ask again
            }
            System.out.println("Invalid input. Enter a number from " + min + " to " + max + ".");
        }
    }
}