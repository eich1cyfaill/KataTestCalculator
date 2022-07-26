import java.util.*;

class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Print your expression: ");
        String expression = scanner.nextLine();
        String answer = calc(expression);
        System.out.println(answer);
    }

    public static String calc(String inputExp) throws Exception {
        String[] splittedExp = inputExp.split("\\D+");
        String answer;

        if(splittedExp.length == 0){
            answer = romanCalc(inputExp);
        } else {
            answer = Integer.toString(arabicCalc(inputExp, splittedExp));
        }

        return answer;
    }
    private static int arabicCalc(String inputExp, String[] numberArray) throws Exception {
        String[] spaceFreeExp = inputExp.split("\\s+");
        String method = null;

        if((Integer.parseInt(spaceFreeExp[0]) > 10 || Integer.parseInt(spaceFreeExp[0]) < 1) ||
                (Integer.parseInt(spaceFreeExp[2]) > 10 || Integer.parseInt(spaceFreeExp[2]) < 1)) throw new Exception("Input numbers couldn't be less than 1 and more than 10");


        if(spaceFreeExp.length == 1){
            String[] methodArray = inputExp.split("\\d+");
            method = methodArray[1];
        } else {
            method = spaceFreeExp[1];
        }

        if(numberArray.length == 2){
            return calculateExpression(method, Integer.parseInt(spaceFreeExp[0]), Integer.parseInt(spaceFreeExp[2]));
        } else {
            throw new Exception("In expression must be one operand and two numbers only");
        }
    }

    private static String romanCalc(String inputExp) throws Exception {
        Map romanHash = new HashMap<String, Number>();
        romanHash.put("I", 1);
        romanHash.put("II", 2);
        romanHash.put("III", 3);
        romanHash.put("IV", 4);
        romanHash.put("V", 5);
        romanHash.put("VI", 6);
        romanHash.put("VII", 7);
        romanHash.put("VIII", 8);
        romanHash.put("IX", 9);
        romanHash.put("X", 10);
        StringBuilder firstSb = new StringBuilder();
        StringBuilder secondSb = new StringBuilder();
        StringBuilder romanAnswer = new StringBuilder();
        String[] splittedRoman = inputExp.split("");
        String method = "";
        int firstArg;
        int secondArg;
        boolean trigger = false;


        for(int i = 0; i < splittedRoman.length; i++){
            if(Objects.equals(splittedRoman[i], " ")) continue;
            if(Objects.equals(splittedRoman[i], "+") || Objects.equals(splittedRoman[i], "-") ||
                    Objects.equals(splittedRoman[i], "*") || Objects.equals(splittedRoman[i], "/"))
            {
                if(!method.isEmpty()){
                    throw new Exception("In expression must be one operand and two numbers only");
                }
                method = splittedRoman[i];
                trigger = true;
                continue;
            }
            if(!trigger){
                firstSb.append(splittedRoman[i]);
            } else {
                secondSb.append(splittedRoman[i]);
            }
        }

        firstArg = (int) romanHash.get(firstSb.toString());
        secondArg = (int) romanHash.get(secondSb.toString());
        int answer = calculateExpression(method, firstArg, secondArg);
        if(answer <= 0) throw new Exception("Answer in roman expression could not be negative or equals zero");

        while(answer >= 1){ // GG
            if(answer >= 4){
                if(answer >= 5){
                    if(answer >= 9){
                        if(answer >= 10){
                            if(answer >= 40){
                                if(answer >= 50){
                                    if(answer >= 90){
                                        if(answer >= 100){
                                            romanAnswer.append("C");
                                            answer -= 100;
                                            continue;
                                        }
                                        romanAnswer.append("XC");
                                        answer -= 90;
                                        continue;
                                    }
                                    romanAnswer.append("L");
                                    answer -= 50;
                                    continue;
                                }
                                romanAnswer.append("XL");
                                answer -= 40;
                                continue;
                            }
                            romanAnswer.append("X");
                            answer -= 10;
                            continue;
                        }
                        romanAnswer.append("IX");
                        answer -= 9;
                        continue;
                    }
                    romanAnswer.append("V");
                    answer -= 5;
                    continue;
                }
                romanAnswer.append("IV");
                answer -= 4;
                continue;
            }
            answer -= 1;
            romanAnswer.append("I");
        }
        return romanAnswer.toString();
    }


    private static int calculateExpression(String method, Integer a, Integer b) throws Exception {
        return switch (method) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "/" -> a / b;
            case "*" -> a * b;
            default -> throw new Exception("Current operation isn't exist");
        };
    }
}
