package basicExercise.algorithm.Airbnb;
// You are given two non-empty linked lists representing two non-negative integers.
// The digits are stored in reverse order and each of their nodes contain a single digit.
// Add the two numbers and return it as a linked list.

// You may assume the two numbers do not contain any leading zero, except the number 0 itself.

// Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
// Output: 7 -> 0 -> 8


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AddTwoNumbers {
    List<Integer> list1 = new LinkedList<>();
    List<Integer> list2 = new LinkedList<>();

    public void process() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("totalCount:");
        Integer total = scanner.nextInt();
        Integer couter = 0;
        while (true) {
            list1.add(scanner.nextInt());
            list2.add(scanner.nextInt());
            couter++;
            if (Objects.equals(couter, total)) {
                break;
            }
        }
        System.out.println("the list:");
        System.out.println(list1);
        System.out.println(list2);

        List<Integer> result = new LinkedList<>();
        Integer toAdd = 0;
        for (Integer i = total - 1; i >= 0; i--) {
            Integer sum = list1.get(i) + list2.get(i) + toAdd;
            result.add(sum % 10);
            toAdd = sum / 10;
        }
        if (toAdd > 0) {
            result.add(toAdd);
        }
        System.out.println("result:" + result);
    }

    public static void main(String[] args) {
        AddTwoNumbers instance = new AddTwoNumbers();
        instance.process();
    }

}