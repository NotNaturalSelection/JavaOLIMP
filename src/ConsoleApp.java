import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleApp {
    static String text = "";
    static String openedFile = "";
    static boolean fileIsOpened = false;
    static String partOfOpenedFile = "";
    static String path = "";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        path = "C:\\Users\\Александр\\Desktop\\Database\\ITMO\\Programming\\Java_Olimp_test\\";
        showPath();
        String line = scanner.nextLine();
        File directory = new File(path);
        String[] list;
        while(!(line.equals("quit")||line.equals("exit")||line.equals("q"))){
            String[] data = line.split(" ");
            switch(data[0]){
                case "list":
                    directory = new File(path);
                    list = directory.list();
                    try {
                        for (String f:list) {
                            System.out.println(f);
                        }
                    } catch (NullPointerException e){
                        System.out.println("Directory is empty");
                    }
                    break;
                case "open":
                    try {
                        if (!fileIsOpened) {
                            if (Arrays.toString(directory.list()).contains(data[1])) {
                                try {
                                    if (new File(path + data[1] + "\\").isFile()) {
                                        partOfOpenedFile = data[1];
                                        openedFile = path + data[1] + "\\";
                                        text = FileUtils.readFile(openedFile);
                                        fileIsOpened = true;
                                        if (text != null) {
                                            System.out.println("File " + data[1] + " is opened");
                                        } else {
                                            System.out.println("File does not exist");
                                        }
                                    } else {
                                        path = directory.toString() + data[1] + "\\";
                                        showPath();
                                    }
                                } catch (NullPointerException e) {
                                    System.out.println("Choose file first");
                                }
                            } else {
                                FileUtils.createFile(data[1]);
                                System.out.println("File " + data[1] + " is created");
                            }
                        } else {
                            System.out.println("Close the previous file first");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "print":
                    try {
                        text = line.substring(data[0].length() + 1);
                        FileUtils.appendFile(openedFile, text);
                    } catch (StringIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "println":
                    try {
                        text = line.substring(data[0].length() + 1);
                        FileUtils.appendFile(openedFile, text+"\n");
                    } catch (StringIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "read":
                    try {
                        if(fileIsOpened) {
                            System.out.println(FileUtils.readFile(openedFile));
                        } else {
                            System.out.println("Open the file first");
                        }
                    } catch (NullPointerException e){
                        System.out.println("File is empty");
                    }
                    break;
                case "touch":
                    try {
                        FileUtils.createFile(data[1]);
                        System.out.println("File " + data[1] + " was created");
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "back":
                    String[] s = path.split("\\\\");
                    path = "";
                    if(s.length > 2) {
                        for (int i = 0; i < s.length - 1; i++) {
                            path += s[i] + "\\";
                        }
                        showPath();
                    } else {
                        path = "C:\\";
                        System.out.println("The root directory \"C:\\\" is reached");
                        showPath();
                    }
                    break;
                case "close":
                    closeFile(partOfOpenedFile);
                    break;
                case "info":
                    showPath();
                    if(fileIsOpened){
                        System.out.println("Active file: " + partOfOpenedFile);
                    } else {
                        System.out.println("No active file");
                    }
                    break;
                case "delete":
                    try {
                        File f = new File(path + data[1]);
                        if (f.isFile()) {
                            f.delete();
                            if (f.exists()) {
                                System.out.println("Permission denied");
                            } else {
                                System.out.println("The file was deleted");
                            }
                        } else {
                            f.delete();
                            if (f.exists()) {
                                System.out.println("Permission denied");
                            } else {
                                System.out.println("The directory was deleted");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "mkdir":
                    try {
                        File f1 = new File(path + "\\" + data[1]);
                        f1.mkdir();
                        if (f1.exists()) {
                            System.out.println("Permission denied");
                        } else {
                            System.out.println("The directory was created successfully");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "rename":
                    try {
                        File f2 = new File(path + "\\" + data[1]);
                        String newName;
                        try {
                            newName = data[2];
                            File newNameFile = new File(path + "\\" + newName);
                            f2.renameTo(newNameFile);
                            System.out.println("File was renamed");
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Incorrect command format");
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect command format");
                    }
                    break;
                case "help":
                    System.out.println("List of available commands:\n" +
                            "open <file or directory name> - open file or directory(if file does not exist, 'open' creates new file\n" +
                            "back - goes to the directory above the level\n" +
                            "close - to close the current file\n" +
                            "list - to show the contents of current directory\n" +
                            "touch <filename> - creates file in current directory\n" +
                            "print <data> - to write data into opened file\n" +
                            "println <data> - to write data into opened file with line break\n" +
                            "delete <file or directory name> - to delete the file or directory from current directory\n" +
                            "info - to show the current opened file and directory" +
                            "read - to show the data from the opened file\n" +
                            "touch <filename> - to create an empty file\n" +
                            "mkdir <directory name> - to create an emty directory\n" +
                            "rename <file or directory name> <new name> - to rename file or directory\n" +
                            "help - to show this message again\n" +
                            "quit, exit,q - to close console application");
                    break;
                default:
                    System.out.println("Undefined command. Type \"help\" for a list of commands");
            }
            line = scanner.nextLine();
        }
        scanner.close();
    }

    static private void closeFile(String filename){
        text = "";
        fileIsOpened = false;
        openedFile = openedFile.replace(filename, "");
        System.out.println("File "+partOfOpenedFile+" is closed now");
        partOfOpenedFile = "";
    }

    static  private void showPath(){
        System.out.println("You're in \'"+ path + "\' now");
    }
}
