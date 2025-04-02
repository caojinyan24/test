package basicExercise.algorithm.e202504;

//2. 链表
//题目：
//反转链表：反转单链表。
//合并两个有序链表：将两个有序链表合并为一个有序链表。
//环形链表检测：判断链表是否有环。
//链表的中间节点：找到链表的中间节点。
//删除链表的倒数第 N 个节点：从链表中删除倒数第 N 个节点。
//思路：
//快慢指针（如环形链表检测）。
//递归（如反转链表）。
public class LinkedArray {
    static class Node {
        Node after;
        int value;

        Node(int value, Node after) {
            this.value = value;
            this.after = after;
        }

        Node() {
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", after=" + after +
                    '}';
        }
    }

    public boolean isRound(Node node) {
        if (node.after == null || node.after.after == null) {
            return false;
        }
        Node first = node;
        Node second = node.after;
        while (first != null && second != null) {
            if (first == second) {
                return true;
            }
            first = first.after;
            second = second.after == null ? null : second.after.after;
        }
        return false;
    }

    public Node merge(Node node1, Node node2) {
        Node result = new Node();
        Node head = result;
        while (node1 != null && node2 != null) {
            if (node1.value < node2.value) {
                result.after = node1;
                node1 = node1.after;
            } else {
                result.after = node2;
                node2 = node2.after;
            }
            result = result.after;

        }
        result.after = node1 == null ? node2 : node1;
        return head.after;
    }

    public Node reverseNode(Node head) {
        Node tail = null;
        Node current = head;
        while (current != null) {
            Node currentNext = current.after;
            current.after = tail;
            tail = current;
            current = currentNext;
        }
        return tail;
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(3, new Node(4, null)));
        System.out.println(new LinkedArray().reverseNode(node));
        System.out.println(new LinkedArray().merge(new Node(1, new Node(3, new Node(4, null))),
                new Node(1, new Node(5, null))));
        System.out.println(new LinkedArray().isRound(new Node(1,
                new Node(2,
                        new Node(3,
                                new Node(4, null))))));
        Node circularList = new Node(1, null);
        Node second = new Node(2, null);
        Node third = new Node(3, null);
        Node fourth = new Node(4, null);

        circularList.after = second;
        second.after = third;
        third.after = fourth;
        fourth.after = second; // 创建环，指向第二个节点

        System.out.println(new LinkedArray().isRound(circularList));
    }
}
