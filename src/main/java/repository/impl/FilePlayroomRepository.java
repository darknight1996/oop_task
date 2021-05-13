package repository.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import playroom.Playroom;
import repository.PlayroomRepository;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.List;

public class FilePlayroomRepository implements PlayroomRepository {

    private List<Playroom> playrooms;

    public FilePlayroomRepository() {
        init();
    }

    public void reload() {
        init();
    }

    private void init() {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/getPlayrooms");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
            playrooms = (List<Playroom>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Playroom> getPlayrooms() {
        return playrooms;
    }

    @Override
    public void updatePlayrooms(List<Playroom> playrooms) {
        // -------------
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("src/main/resources/playrooms.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(playrooms);
            objectOut.close();

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(new File("src/main/resources/playrooms.dat")))
                    .build();

            HttpPost request = new HttpPost("http://localhost:8080/updatePlayrooms");
            request.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // -------------
    }

    private void send() throws IOException {
        String url = "http://localhost:8080/updatePlayrooms";
        String charset = "UTF-8";
        String param = "value";
        File binaryFile = new File("src/main/resources/playrooms.dat");
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.

        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {
            // Send normal param.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(param).append(CRLF).flush();

            // Send binary file.
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(binaryFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            writer.append("--" + boundary + "--").append(CRLF).flush();
        }

        // Request is lazily fired whenever you need to obtain information about response.
        int responseCode = ((HttpURLConnection) connection).getResponseCode();
        System.out.println(responseCode); // Should be 200
    }

}
