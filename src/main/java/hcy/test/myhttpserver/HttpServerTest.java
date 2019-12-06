package hcy.test.myhttpserver;

public class HttpServerTest {
    public static void main(String[] args) throws Exception {
        SimpleHttpServer.setBasePath("/Users/huangchenyao");
        SimpleHttpServer.start();
    }
}
