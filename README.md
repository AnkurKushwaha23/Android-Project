#Random Image Loader App



import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    void insert(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Inserted: " + data);
    }

    void display() {
        Node temp = head;
        System.out.print("Linked List: ");
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList linkedList = new LinkedList();

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Insert");
            System.out.println("2. Display");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter data to insert: ");
                    int data = scanner.nextInt();
                    linkedList.insert(data);
                    break;
                case 2:
                    linkedList.display();
                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }
}



#Overview

The Random Image Loader App is a simple mobile application that allows users to load random images using the Unsplash API. The app is built using the OKHTTP library for making API requests and provides a clean and intuitive user interface for users to interact with.

#Features

Load Random Image: Fetches a random image from the Unsplash API and displays it in the app.
Get Image Link: Retrieves the link to the currently loaded image.

#Usage

1.Launch the app on your mobile device.
2.Press the "Load Image" button to fetch and display a random image.
3.Press the "Get Link" button to copy the link to the currently loaded image.

#Libraries Used
OKHTTP: Used for making HTTP requests to the Unsplash API.

#Acknowledgments

Thanks to the Unsplash team for providing the API.

Thanks to the FlatIcon team for providing the pngs.
![Screenshot_20240218-110003](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/c01dc3f4-57f6-403d-90a2-a62340725e1f)

![Screenshot_20240218-110031](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/d7a10a37-2546-4996-aedd-3df127f3ce7c)

![Screenshot_20240218-110106](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/2178f4a1-e979-4cdd-9300-dae15aca5ab6)

![Screenshot_20240218-110256](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/c30fa4cb-c4de-49d2-9572-a88519d94cd5)

![Screenshot_20240218-110428](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/fe89787c-4dc4-4835-bfc5-83197e19fcda)

![Screenshot_20240218-110437](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/7905a53a-fcc2-47e7-bcd2-0218b617954f)

![Screenshot_20240218-122610](https://github.com/AnkurKushwaha23/Android-demo1/assets/157258878/6081ce4b-f6d1-4e35-9ee4-3bc8667d2ed7)
