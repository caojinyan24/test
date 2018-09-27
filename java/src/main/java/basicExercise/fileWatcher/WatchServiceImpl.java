package basicExercise.fileWatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 文件监控服务类
 * Created by jinyan on 6/11/17.
 */
public class WatchServiceImpl {
    private static WatchService watchService;

    static {
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (Exception e) {
            System.out.println("init error:" + e);
        }
    }

    public void register(String filePath) throws IOException {
        Path path = new File(filePath).toPath();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
    }

    //文件更新会先写入一个缓存文件，等保存后才会把数据写入到真正写入的文件，所以更新一个文件时，会产生两次更新操作，同时会有一个创建和删除操作
    public void process(String dir) throws Exception {
        int mark = 0;
        while (true) {
            WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
            System.out.println("mark1:" + mark++);
            if (watchKey != null && watchKey.isValid()) {
                System.out.println("mark2:" + mark++);
                List<WatchEvent<?>> events = watchKey.pollEvents();
                System.out.println("process:" + events);
                for (WatchEvent watchEvent : events) {
                    Path path = (Path) watchEvent.context();
                    System.out.println("process path-ENTRY_MODIFY:" + path.toString());
                    if (!path.toString().startsWith(".")) {//过滤临时文件的event
                        printFileContent(new File(dir, path.toString()));
                    }
//                    if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
//                        Path path = (Path) watchEvent.context();
//                        System.out.println("process path-ENTRY_CREATE:" + path.toString());
//                        if (path != null) {
//                            printFileContent(new File(dir,path.toString()));
//                        }
//                    } else if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
//                        Path path = (Path) watchEvent.context();
//                        System.out.println("process path-ENTRY_DELETE:" + path.toString());
//                        if (path != null) {
//                            printFileContent(new File(dir,path.toString()));
//                        }
//                    } else
//                    if (watchEvent.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
//
//                    }
                }
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }
            }


        }
    }

    private void printFileContent(File file) throws Exception {
        System.out.println("processFile:" + file);
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("content:" + line);
            }
        }
    }

}
