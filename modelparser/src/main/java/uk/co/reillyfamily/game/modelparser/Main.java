package uk.co.reillyfamily.game.modelparser;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by stuart on 10/01/17.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalStateException("Please specify a model file!");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName() + " does not exist!");
        } else if (!file.isFile()) {
            throw new IOException(file.getName() + " is not a file!");
        }

        ArrayList<Vector3f> vertices = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() || (line = line.trim()).isEmpty()) {
                    continue;
                }
                String[] split = line.split(" ");
                switch (split[0]) {
                    case "v":
                        if (split.length != 4) {
                            LOGGER.error("Vertex definition must contain 3 dimensions [{}]", line);
                        } else {
                            vertices.add(new Vector3f(Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3])));
                        }
                        break;
                    case "f":
                        break;
                    default:
                        LOGGER.warn("Unknown line type [{}]", split[0]);
                }
            }
        }

        LOGGER.info("Successfully loaded {} vertex definitions", vertices.size());

        UnloadedModel model = new UnloadedModel(vertices);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                new File(file.getParent(), file.getName().substring(0, file.getName().length()-3) + "model"))))) {
            out.writeObject(model);
            out.flush();
        }

        LOGGER.info("Finished");
    }
}
