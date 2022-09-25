
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class WorkFile {
    private String path;
    private static final StringBuilder stringBuilderLog = new StringBuilder();

    public WorkFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static StringBuilder getStringBuilderLog() {
        return stringBuilderLog;
    }

    public static void writeLog(String log) {
        Date date = new Date();
        stringBuilderLog.append(log);
        stringBuilderLog.append("            ");
        stringBuilderLog.append(date.getTime());
        stringBuilderLog.append("\n");
    }

    public void createCat(List<String> listOfCat, String path) {
        listOfCat.stream()
                .forEach((x) -> {
                    File dir = new File(path + "\\" + x);
                    if (dir.mkdir()) {
                        writeLog("Каталог " + x + " создан в директории " + path);
                    } else {
                        writeLog("Каталог " + x + " не создан в директории " + path);
                    }
                });
    }

    public void createFile(List<String> listName, String path) {
        listName.stream()
                .forEach((x) -> {
                    File dir = new File(path, x);
                    try {
                        if (dir.createNewFile()) {
                            writeLog("Файл " + x + " создан ");
                        } else {
                            writeLog("Файл " + x + " не создан ");
                        }
                    } catch (IOException e) {
                        writeLog(e.getMessage());
                    }
                });
    }

    public static void createAndWriteLog(String path, String fileName) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(new File(path, fileName)))) {
            bf.write(stringBuilderLog.toString());
        } catch (IOException e) {
            writeLog(e.getMessage());
        }
    }

    public static void removeWithFormatException(String path, String formatFilesForExc) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                checkAndDeleteFile(item.getName(), formatFilesForExc, item);
            }
        } else {
            checkAndDeleteFile(dir.getName(), formatFilesForExc, dir);
        }
    }

    public static void save(Object o, String path, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(new File(path, fileName));
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(o);
            WorkFile.writeLog("Успешно сохранились в файл: " + fileName);
        } catch (Exception e) {
            WorkFile.writeLog(e.getMessage());
        }
    }

    public static void archive(String path, String fileName) {
        File dir = new File(path);
        try (ZipOutputStream zout =
                     new ZipOutputStream(new FileOutputStream(new File(path, fileName)))) {
            if (dir.isDirectory()) {
                for (File item : dir.listFiles()) {
                    if (item.getName().equals(fileName)) {
                        continue;
                    }
                    try (FileInputStream fis = new FileInputStream(item)) {
                        ZipEntry entry = new ZipEntry(item.getName());
                        zout.putNextEntry(entry);
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zout.write(buffer);
                        zout.closeEntry();
                        writeLog("файл " + entry.getName() + " заархивирован");
                    }
                }
            } else {
                WorkFile.writeLog("Не может сделать архив");
            }

        } catch (Exception e) {
            WorkFile.writeLog("Не может сделать архив " + e.getMessage());
        }
    }

    private static void checkAndDeleteFile(String fileName, String format, File item) {
        if (!format.equals(fileName.substring(fileName.indexOf(".") + 1).toLowerCase())) {
            if (item.delete()) {
                writeLog("Файл удален: " + item.getName());
            } else {
                writeLog("Файл не удален: " + item.getName());
            }
        }
    }

    public static void openZip(String path, String pathToArchive) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream((path)))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(new File(pathToArchive, name));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
                writeLog("файл : " + entry.getName() + "разархивирован");
            }
        } catch (Exception e) {
            writeLog(e.getMessage());
        }
    }

}
