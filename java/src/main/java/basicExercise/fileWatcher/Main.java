package basicExercise.fileWatcher;

/**
 * Created by jinyan on 6/11/17.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        WatchServiceImpl watchService = new WatchServiceImpl();
        String dir = "/home/jinyan/testDirectory";
        watchService.register(dir);
        watchService.process(dir);
    }
}
