package UI;
import Controller.Service;
public class DataTransfer {
    private static Service service;

    public static void setService(Service newService) {
        service = newService;
    }

    public static Service getService() {
        return service;
    }
}
