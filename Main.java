import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

class StringCalculator{
    public int add(String numbers) throws Exception {
        if (numbers.isEmpty()) return 0;
        numbers = parseDelimiters(numbers);
        String[] split_by_delimiters = splitByDelimiters(numbers);
        return sum(toInt(split_by_delimiters));
    }
    String delimiters= "";
    List<String> DelimitersList = new ArrayList<>();
    public StringCalculator(){
        delimiters = "[,][\n]";
    }



    private String parseDelimiters(String numbers){
        if(numbers.charAt(0)=='/') {
            if (numbers.indexOf('\n') == 3)
                delimiters += "[" + numbers.charAt(2) + "]";
            else
                delimiters += numbers.substring(2, numbers.indexOf('\n'));
        }

        DelimitersList.addAll(List.of(delimiters.substring(1,delimiters.length()-1).split("]\\[")));
        if (numbers.charAt(0)!='/')
            return numbers;
        return numbers.substring(numbers.indexOf('\n')+1);
    }

    private int[] toInt(String[] numbersList) throws Exception {
        int[] numberList = new int[numbersList.length];
        for (int i=0;i<numberList.length;i++) {
            try {
                numberList[i] = Integer.parseInt(numbersList[i]);
            }
            catch (Exception ex){
                throw new Exception("Not a number");
            }
        }
        return numberList;
    }

    private int sum(int[] numberList) throws Exception {
        int sum=0;
        for (int number: numberList) {
            if (number<0)
                throw lowerZero(numberList);
            if (number<=1000)
                sum+=number;
        }
        return sum;
    }

    Exception lowerZero(int[] numberList){
        StringBuilder stringBuilder = new StringBuilder("Numbers lower zero: ");
        for (int num: numberList){
            if (num<0)
                stringBuilder.append(num).append(", ");
        }
        return new Exception(String.valueOf(stringBuilder));
    }

    private String[] splitByDelimiters(String numbers) throws Exception {
        DelimitersList.sort(Collections.reverseOrder());
        for (String delimiter: DelimitersList) {
            numbers = numbers.replace(delimiter, " ");
        }
        if(numbers.charAt(numbers.length()-1) == ' '){
            throw new Exception("Error in the end of expression");
        }
        if (numbers.contains("  "))
            throw new Exception("Two delimiters");
        return numbers.split(" ");

    }
}


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(new StringCalculator().add("1,2,3,"));
    }
}