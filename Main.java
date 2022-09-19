import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StringCalculator{

    //Головний метод програми, що поверне суму
    public int add(String numbers) throws Exception {
        if (numbers.isEmpty()) return 0;
        numbers = parseDelimiters(numbers);
        String[] split_by_delimiters = splitByDelimiters(numbers);
        return sum(toInt(split_by_delimiters));
    }
    //Строка з роздільниками виду [роздільник1][роздільник2]...[роздільник n]
    String delimiters;
    //Список роздільників
    List<String> DelimitersList = new ArrayList<>();
    //Початковий ініціалізатор класу з ініціалізацією початкових роздільників
    public StringCalculator(){
        delimiters = "[,][\n]";
    }

    //Парсимо роздільники з початкової строки
    private String parseDelimiters(String numbers){
        //перевірка чи є взагалі роздільники в строці
        if(numbers.charAt(0)=='/') {
            //перевірка на те чи роздільник один
            if (numbers.indexOf('\n') == 3)
                delimiters += "[" + numbers.charAt(2) + "]";
            else
                delimiters += numbers.substring(2, numbers.indexOf('\n'));
        }

        //додамо до списку роздільників роздільники зі строки
        DelimitersList.addAll(List.of(delimiters.substring(1,delimiters.length()-1).split("]\\[")));
        if (numbers.charAt(0)!='/')
            return numbers;
        return numbers.substring(numbers.indexOf('\n')+1);
    }

    //Намагаємось перетворити масив строк з числами у числа і повернути масив чисел
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

    //Сумуємо числа з умовою
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

    //Повертає помилку якщо знайдено числа менше 0
    Exception lowerZero(int[] numberList){
        StringBuilder stringBuilder = new StringBuilder("Numbers lower zero: ");
        for (int num: numberList){
            if (num<0)
                stringBuilder.append(num).append(", ");
        }
        return new Exception(String.valueOf(stringBuilder));
    }

    //Проходиться по строці і спочатку заміняє всі роздільники на ' ' а потім методом split розбиває на масив строк (чисел)
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
        String expression = "//[123][**][***]\n11231***-1";
        System.out.println(new StringCalculator().add(expression));
    }
}
