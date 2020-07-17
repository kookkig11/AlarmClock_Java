package sample;

import java.util.Random;

public class ProblemManager {
    private int num1;
    private int num2;
    private int answer;
    private String operator;
    private String problem;
    private boolean checkAns = false;
    private Random random = new Random();

    private int randomNumber(){
        return random.nextInt(100);
    }

    public void createProblem(){
        num1 = randomNumber();
        num2 = randomNumber();

        int n = random.nextInt(3);
        switch (n){
            case 0:
                answer = num1 + num2;
                operator = "+";
                break;
            case 1:
                answer = num1 - num2;
                operator = "-";
                break;
            case 2:
                answer = num1 * num2;
                operator = "*";
                break;
        }
        problem = Integer.toString(num1) + " " + operator + " " + Integer.toString(num2);
    }

    public String getProblem(){
        return problem;
    }

    public void checkCompareAnswer(int ans){
        if (this.answer == ans){
            checkAns = true;
        }
    }

    public boolean getCheckAnswer(){
        return checkAns;
    }
}
