import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {

    static String broker = "broker.emqx.io";
    static int port = 1883;
    static String topic = "java/mqtt";
    static String clientId = "java-mqtt-iu9reader";

    public static void main(String[] args) {
        try {
            MqttClient client = new MqttClient("tcp://" + broker + ":" + port, clientId, new MemoryPersistence());
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost because: " + cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message from topic: " + topic + ", message: " + message.toString());

                    // Extract coordinates from the message
                    String[] coordinates = message.toString().split(", ");
                    double x1 = Double.parseDouble(coordinates[0]);
                    double y1 = Double.parseDouble(coordinates[1]);
                    double z1 = Double.parseDouble(coordinates[2]);
                    double x2 = Double.parseDouble(coordinates[3]);
                    double y2 = Double.parseDouble(coordinates[4]);
                    double z2 = Double.parseDouble(coordinates[5]);
                    double x3 = Double.parseDouble(coordinates[6]);
                    double y3 = Double.parseDouble(coordinates[7]);
                    double z3 = Double.parseDouble(coordinates[8]);

                    // Calculate the coordinates of the average point
                    double avgX = (x1 + x2 + x3) / 3.0;
                    double avgY = (y1 + y2 + y3) / 3.0;
                    double avgZ = (z1 + z2 + z3) / 3.0;

                    System.out.println("Average point coordinates: (" + avgX + ", " + avgY + ", " + avgZ + ")");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected to broker");

            client.subscribe(topic);

            while (true) {
                Thread.sleep(1000);
            }

        } catch (MqttException | InterruptedException me) {
            System.out.println("Msg: " + me.getMessage());
            System.out.println("Loc: " + me.getLocalizedMessage());
            System.out.println("Cause: " + me.getCause());
            System.out.println("Exception: " + me);
            me.printStackTrace();
        }
    }
}