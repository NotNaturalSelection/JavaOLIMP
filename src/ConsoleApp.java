import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleApp {
    private static String text = "";
    private static String openedFile = "";
    private static boolean fileIsOpened = false;
    private static String partOfOpenedFile = "";
    private static String path = "C:/Users/Александр/Desktop/Database/ITMO/Programming/Java_Olimp_test/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        showPath();
        String line = scanner.nextLine();
        File directory = new File(path);
        while (!(line.equals("quit") || line.equals("exit") || line.equals("q"))) {
            String[] data = line.split(" ");
            try {
                switch (data[0]) {
                    case "list":
                        list();
                        break;
                    case "ls":
                        ls();
                        break;
                    case "open":
                        open(directory, data[1]);
                        break;
                    case "cd":
                        cd(data[1]);
                        break;
                    case "print":
                        String txt1 = line.substring(6);
                        print(txt1);
                        break;
                    case "println":
                        String txt = line.substring(8);
                        println(txt);
                        break;
                    case "read":
                        read();
                        break;
                    case "touch":
                        touch(data[1]);
                        break;
                    case "back":
                        back();
                        break;
                    case "close":
                        closeFile(partOfOpenedFile);
                        break;
                    case "info":
                        info();
                        break;
                    case "remove":
                    case "delete":
                        delete(data[1]);
                        break;
                    case "mkdir":
                        mkdir(data[1]);
                        break;
                    case "rename":
                        rename(data[1], data[2]);
                        break;
                    case "chmod":
                        chmod(data[1], data[2]);
                        break;
                    //Закоменчен из-за неработоспособности по неизвестной причине
                    case "help":
                        printHelpMessage();
                        break;
                    default:
                        System.out.println("Undefined command. Type \"help\" for a list of commands");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect command format");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Incorrect format");
            }
            line = scanner.nextLine();
        }
        scanner.close();
    }

    /**
     * Конструктор установит значение {@code path} равное аргументу, поданному в него
     *
     * @param absolutePath строка - абсолютный путь к начальной директории работы приложения
     */
    ConsoleApp(String absolutePath) {
        path = absolutePath;
        text = "";
        openedFile = "";
        fileIsOpened = false;
        partOfOpenedFile = "";
    }

    static private void closeFile(String filename) {
        text = "";
        fileIsOpened = false;
        openedFile = openedFile.replace(filename, "");
        System.out.println("File " + partOfOpenedFile + " is closed now");
        partOfOpenedFile = "";
    }

    /**
     * Выведет на консоль абсолютный путь до текущего каталога
     */
    static private void showPath() {
        System.out.println("You're in \'" + path + "\' now");
    }

    /**
     * Поднимется на каталог уровнем выше
     */
    static private void back() {
        String[] s = path.split("\\\\");
        path = "";
        if (s.length > 2) {
            for (int i = 0; i < s.length - 1; i++) {
                path += s[i] + "\\";
            }
            showPath();
        } else {
            path = "C:\\";
            System.out.println("The root directory \"C:\\\" is reached");
            showPath();
        }
    }

    /**
     * Выведет информацию про текущее состояние консоли: путь к директории и открытый файл
     */
    static private void info() {
        showPath();
        if (fileIsOpened) {
            System.out.println("Active file: " + partOfOpenedFile);
        } else {
            System.out.println("No active file");
        }
    }

    /**
     * Удаляет файл или каталог по указанному названию в текущей директории
     *
     * @param filePath строка - название файла или каталога, который необходимо удалить
     */
    static private void delete(String filePath) {
        File f = new File(path + filePath);
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
    }

    /**
     * Создает директорию в текущем каталоге
     *
     * @param filePath строка - название создаваемой директории
     */
    static private void mkdir(String filePath) {
        File f1 = new File(path + filePath);
        if (f1.mkdir()) {
            System.out.println("The directory was created successfully");
        } else {
            System.out.println("Permission denied");
        }
    }

    /**
     * Переименует файл или директорию с именем {@code filePath} в аналогичный(ую) с именем {@code newName}
     *
     * @param filePath строка - название файла или директории, название которого(ой) необходимо изменить
     * @param newName  строка - новое название файла или директории
     */
    static private void rename(String filePath, String newName) {
        File f2 = new File(path + "\\" + filePath);
        File newNameFile = new File(path + "\\" + newName);
        if (f2.renameTo(newNameFile)) {
            System.out.println("File was renamed");
        }
    }

    /**
     * Выводит сообщение про доступные команды приложения
     */
    static private void printHelpMessage() {
        System.out.println("List of available commands:\n" +
                "open <file or directory name> - open file or directory(if file does not exist, 'open' creates new file\n" +
                "back - goes to the directory above the level\n" +
                "close - to close the current file\n" +
                "ls - to show the contents of current directory\n" +
                "list - to show the contents of current directory with some information\n" +
                //"chmod <mode> <filename> - to change access to the file\n" +
                "touch <filename> - creates file in current directory\n" +
                "print <data> - to write data into opened file\n" +
                "println <data> - to write data into opened file with line break\n" +
                "delete (or \'remove\') <file or directory name> - to delete the file or directory from current directory\n" +
                "info - to show the current opened file and directory\n" +
                "read - to show the data from the opened file\n" +
                "touch <filename> - to create an empty file\n" +
                "mkdir <directory name> - to create an emty directory\n" +
                "rename <file or directory name> <new name> - to rename file or directory\n" +
                "help - to show this message again\n" +
                "quit, exit,q - to close console application");
    }

    /**
     * Создает файл с заданным именем в текущей директории
     *
     * @param filePath строка - имя создаваемого файла
     */
    static private void touch(String filePath) {
        if (FileUtils.createFile(filePath)) {
            System.out.println("File " + filePath + " was created");
        } else {
            System.out.println("File wasn't created");
        }
    }

    /**
     * Выводит на экран содержимое открытого файла. В случае, если файл не открыт, выводит соответствующее сообщение
     */
    static private void read() {
        try {
            if (fileIsOpened) {
                System.out.println(FileUtils.readFile(openedFile));
            } else {
                System.out.println("Open the file first");
            }
        } catch (NullPointerException e) {
            System.out.println("File is empty");
        }
    }

    /**
     * Записывает строку в открытый файл и добавляет в конце перенос строки
     *
     * @param line строка, котору необходимо записать в открытый файл
     */
    static private void println(String line) {
        FileUtils.appendFile(openedFile, line + "\n");
    }

    /**
     * Записывает строку в открытый файл и
     *
     * @param line строка, котору необходимо записать в открытый файл
     */
    static private void print(String line) {
        FileUtils.appendFile(openedFile, line);
    }

    /**
     * Открывает файл для приложения
     *
     * @param directory файл - директория, в которой хранится открываемый файл
     * @param filePath  строка - название открываемого файла
     */
    static private void open(File directory, String filePath) {
        if (!fileIsOpened) {
            if (Arrays.toString(directory.list()).contains(filePath)) {
                try {
                    if (new File(path + filePath + "\\").isFile()) {
                        partOfOpenedFile = filePath;
                        openedFile = path + filePath + "\\";
                        text = FileUtils.readFile(openedFile);
                        fileIsOpened = true;
                        if (text != null) {
                            System.out.println("File " + filePath + " is opened");
                        }
                    } else {
                        System.out.println("Directory may not to be opened as a file");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Incorrect command format");
                }
            } else {
                System.out.println("File not found");
            }
        } else {
            System.out.println("Close the previous file first");
        }
    }

    /**
     * Осуществляет переход в заданный каталог
     *
     * @param filePath строка - название каталога, в который необходимо осуществить переход
     */
    static private void cd(String filePath) {
        if (new File(path + filePath).isDirectory()) {
            path += filePath + "\\";
            showPath();
        } else {
            System.out.println("Directory not found");
        }
    }

    /**
     * Выводит все файлы и директории, содержащиеся в текущем каталоге, каждый в новой строке
     */
    static private void ls() {
        File directory = new File(path);
        String[] list = directory.list();
        try {
            for (String f : list) {
                System.out.println(f);
            }
        } catch (NullPointerException e) {
            System.out.println("Directory is empty");
        }
    }

    /**
     * Выводит содержимое текущего каталога с указанием прав доступа, размера файла, скрытости
     */
    static private void list() {
        File directory;
        directory = new File(path);
        String[] list = directory.list();
        try {
            for (String filename : list) {
                File f = new File(path + filename);
                String fileFlag;
                String hiddenFlag;
                String executeFlag = "-";
                String writeFlag = "-";
                String readFlag = "-";
                if (f.canExecute()) {
                    executeFlag = "x";
                }
                if (f.canRead()) {
                    readFlag = "r";
                }
                if (f.canWrite()) {
                    writeFlag = "w";
                }
                long value;
                if (f.isFile()) {
                    fileFlag = "file";
                } else {
                    try {
                        if (f.listFiles().length > 0) {
                            fileFlag = "+dir";//если директория не пустая
                        } else {
                            fileFlag = "-dir";//если пустая
                        }
                    } catch (NullPointerException e) {
                        fileFlag = "-dir";
                    }
                }
                if (f.isHidden()) {
                    hiddenFlag = "hidden";
                } else {
                    hiddenFlag = "displayed";
                }
                if (f.length() / 1024 < 1 && f.length() > 0) {
                    value = 1;
                } else {
                    value = f.length() / 1024;
                }
                System.out.printf("%s %-30s %10d KB  %s%s%s  %s\n", fileFlag, f.getName()
                        , value, readFlag, writeFlag, executeFlag, hiddenFlag);
            }
        } catch (NullPointerException e) {
            System.out.println("Directory is empty");
        }
    }

    static private void chmod(String mode, String pathFile) {
        try {
            File f = new File(path + pathFile);
            f.setExecutable(false);
            f.setReadable(false);
            f.setWritable(false);
            if (mode.contains("r")) {
                f.setReadable(true);
            }
            if (mode.contains("w")) {
                f.setWritable(true);
            }
            if (mode.contains("x")) {
                f.setExecutable(true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect command format");
        }
    }
}
