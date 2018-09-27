//package basicExercise.zookeeper.netty.zookeeper;
//
//import org.apache.logging.log4j.core.jmx.Server;
//
///**
// * Created by jinyan on 6/19/17.
// */
//public class ServerMain {
//    public static void main(String[] agrs){
//        int port = 9988;
//        //register service in zookeeper
//        ServiceRegistry zsr = new ServiceRegistry(Constant.registryAddress);
//        String serverIp = "123.123.123.123:9988";
//        zsr.register(serverIp);
//        //netty bind
//        new Server().bind(port);
//    }
//}
