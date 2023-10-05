package com.asrii.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class QuestionRestController {

    private List<Question> questionList = new ArrayList<>();
    private List<Player> playerList = new ArrayList<>();
    private int score = 0;
    private int currentQIndex = 0;
    private int currentPIndex = 0;

    public QuestionRestController() {

        //Lisätään aluksi kolme kysymystä automaattisesti. (POST-toiminnolla voi laittaa lisää.)
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("A) 1");
        list1.add("B) 2");
        list1.add("C) 11");
        Question question1 = new Question(1,
                "How much is 1+1? Submit a single letter answer, choosing from these options: ", list1, "B");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("A) 0");
        list2.add("B) 1");
        list2.add("C) 2");
        Question question2 = new Question(1,
                "How much is 1-1? Submit a single letter answer, choosing from these options: ", list2, "A");

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("A) 0");
        list3.add("B) 1");
        list3.add("C) 2");

        Question question3 = new Question(1,
                "How much is 1*1? Submit a single letter answer, choosing from these options: ", list3, "B");

        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
    }

    // Juuri - hakee aloitussivun.
    @GetMapping("/")
    public String homepage() {
        return "Welcome. <br><br> To begin the game, go to: /questions.<br> To submit an answer, use the /answer endpoint in Postman (POST): send the answer as json with 'answer' as the key and your answer as the value. <br> To view all questions and answers, go to /allquestions.";
    }

    // Näyttää kysymykset yksitellen, järjestyksessä. Uusi kysymys aina kun sivu
    // ladataan uudelleen. Kun kysymykset loppuvat, näytetään pisteet.
    @GetMapping("/questions")
    public String questionPage() {

        if (currentQIndex >= questionList.size()) {
            return "You reached the end. Your score was: " + score + ". To save your score, use POST on the endpoint /savescore. Send the answer as json with 'name' as the key and your name as the value.";
        }

        Question question = questionList.get(currentQIndex);
        currentQIndex++;
        return question.toString();
    }

    // Näyttää kaikki saatavilla olevat kysymykset raa'assa json-muodossa.
    @GetMapping("/allquestions")
    public List<Question> getAllQuestions() {
        return questionList;
    }
    // Näyttää tietyn kysymyksen raa'assa json-muodossa.
    @GetMapping("/question/{index}")
    public Question getOneQuestion(@PathVariable Integer index) {
        return questionList.get(index);
    }
    // Näyttää tietyn kysymyksen nätimmässä muodossa + vastausta ei näytetä.
    @GetMapping("/prettyquestion/{index}")
    public String getOneQuestionPretty(@PathVariable Integer index) {
        return questionList.get(index).getQuestionText() +"<br>" + questionList.get(index).getOptions();
    }

    // Ottaa vastaan lähetetyn vastauksen ja vertaa sitä oikeaan vastaukseen. Lisää tarvittaessa pisteen.
    @PostMapping("/answer")
    public String checkAnswer(@RequestBody Map<String, String> answer) {

        String userAnswer = answer.get("answer");

        Question question = questionList.get(currentQIndex - 1);
        if (question.getCorrectOption().equalsIgnoreCase(userAnswer)) {
            score++;
            return "Correct!";

        } else {
            return "Incorrect";
        }

    }

    // Näyttää nykyisen pelaajan pistemäärän hakuhetkellä.
    @GetMapping("/score")
    public String getScore() {
        return "Score: " + score;
    }

    /*
     * JSON template:
     * {
     * "id": "3",
     * "questionText":
     * "3) What's 2+2? The answer should be sent in the format of a single letter",
     * "options":[
     * "A) 2",
     * "B) 3",
     * "C) 4"
     * ],
     * "correctOption": "C"
     * }
     *
     */

    // Lisää kysymys listaan.
    @PostMapping("/postquestion")
    public String postQuestion(@RequestBody Question question) {

        questionList.add(question);

        return "Question added. " + question;
    }

   
    /*
     * {
     * "name": "John"
     * }
     */

     //Tallenna pelaajan nimi ja pistemäärä + resetoi peli. 
    @PostMapping("/savescore")
    public String addPlayer(@RequestBody Map<String, String> name) {

        String playerName = name.get("name");

        Player newPlayer = new Player(currentPIndex, playerName, score);
        playerList.add(newPlayer);
        currentPIndex++;
        score=0;
        currentQIndex=0;
        return "Player " + playerName + " added and your score was saved with the id: " +(currentPIndex-1) + "   The current session score was reset; ready for a new player.";
    }

    // Näyttää kaikki tallennetut pelaajat raa'assa json-muodossa.
    @GetMapping("/allplayers")
    public List<Player> getAllPlayers() {
        return playerList;
    }

}
