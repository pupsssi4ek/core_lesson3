
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WorkFile workFile = new WorkFile("D:\\Games");
        List<String> stringList = new ArrayList<>(Arrays.asList("src", "res", "savegames", "temp"));
        workFile.createCat(stringList, workFile.getPath());
        stringList.clear();
        workFile.setPath("D:\\Games\\src");
        stringList = new ArrayList<>(Arrays.asList("main", "test"));
        workFile.createCat(stringList, workFile.getPath());
        stringList.clear();
        workFile.setPath("D:\\Games\\src\\main");
        stringList = new ArrayList<>(Arrays.asList("Main.java", "Utils.java"));
        workFile.createFile(stringList, workFile.getPath());
        stringList.clear();
        workFile.setPath("D:\\Games\\res");
        stringList = new ArrayList<>(Arrays.asList("drawables", "vectors", "icons"));
        workFile.createCat(stringList, workFile.getPath());
        stringList.clear();
        workFile.setPath("D:\\Games\\temp");
        WorkFile.createAndWriteLog(workFile.getPath(), "temp.txt");
        workFile.setPath("D:\\Games\\savegames");
        GameProgress gameProgress = new GameProgress(90, 40, 2, 20.55);
        GameProgress gameProgress1 = new GameProgress(80, 30, 1, 19.55);
        GameProgress gameProgress2 = new GameProgress(70, 20, 0, 18.55);
        WorkFile.save(gameProgress, workFile.getPath(), "save.dat");
        WorkFile.save(gameProgress1, workFile.getPath(), "save1.dat");
        WorkFile.save(gameProgress2, workFile.getPath(), "save2.dat");
        WorkFile.archive(workFile.getPath(), "archive.zip");
        WorkFile.removeWithFormatException(workFile.getPath(), "zip");
        WorkFile.openZip(workFile.getPath() + "\\archive.zip", workFile.getPath());
        workFile.setPath(workFile.getPath() + "\\save1.dat");
        GameProgress.openProgress(workFile.getPath());
    }

}
